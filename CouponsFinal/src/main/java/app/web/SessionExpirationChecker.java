package app.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionExpirationChecker extends Thread {

	static boolean alive = true;

	@Autowired
	private Map<String, Session> sessionsMap;

	@Override
	public void run() {
		while (alive) {
			if(sessionsMap!=null) {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
				for (String cell : sessionsMap.keySet()) {
					//System.out.println(sessionsMap.get(cell).getLastAccessed());
					if ((System.currentTimeMillis() - sessionsMap.get(cell).getLastAccessed()) >= 1800000)
						sessionsMap.remove(cell);
				}
			}
		}
	}

	/**
	public void terminate() {
		alive = false;
	}
	*/

}
