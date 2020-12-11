package app.web;

public class LocalSession {
	String Token;
	Long currentTime;

	public LocalSession(String token, Long currentTime) {
		super();
		Token = token;
		this.currentTime = currentTime;
	}

	public String getToken() {
		return Token;
	}

	public Long getCurrentTime() {
		return currentTime;
	}

	@Override
	public String toString() {
		return "Token=" + Token + ", time passed=" + currentTime;
	}
}
