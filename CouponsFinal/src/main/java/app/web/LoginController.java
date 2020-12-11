package app.web;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.ClientType;
import app.LoginManager;
import app.services.ClientService;

@RestController
@RequestMapping("bob")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

	@Autowired
	private LoginManager loginManager;

	@Autowired
	private Map<String, Session> sessionsMap;

	@GetMapping("login/{email}/{password}/{type}")
	public LocalSession login(@PathVariable String email, @PathVariable String password, @PathVariable int type)
			throws SQLException {
		Long currentTime = System.currentTimeMillis();
		ClientService service = loginManager.login(email, password, ClientType.valueOf(type));
		if (service != null) {
			String token = UUID.randomUUID().toString();
			Session session = new Session(service, currentTime);
			sessionsMap.put(token, session);
			LocalSession localSession = new LocalSession(token, currentTime);
			return localSession; // token;
		}
		else
			throw new SQLException();
	}
	
	@GetMapping("logout/{token}")
	public String logout (@PathVariable String token) {
	    sessionsMap.remove(token);
		return "logout complete";
	}
	
}
