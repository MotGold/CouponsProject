package app.web;

import app.services.ClientService;

public class Session {
	
	private ClientService service;
	private Long lastAccessed;
	
	public Session(ClientService mainService, Long lastAccessed) {
		super();
		this.service = mainService;
		this.lastAccessed = lastAccessed;
	}

	public Long getLastAccessed() {
		return lastAccessed;
	}

	public void setLastAccessed(Long lastAccessed) {
		this.lastAccessed = lastAccessed;
	}

	public ClientService getService() {
		return service;
	}
	
	

}
