package com.project.ispProject;

public class Utilizator {
	
	private int id;
	private String username;
	private String email;
	private String parola;
	
	public Utilizator(int id, String username, String email, String parola) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.parola = parola;
	}
	
	public int getID() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getParola() {
		return parola;
	}
	
	
	public boolean autentificare(String inputUsername, String inputParola) {
		if(inputUsername == null || inputParola == null) {
			return false;
		}
		
		boolean parolaCorecta = parola != null && parola.equals(inputParola);
		
		if(!parolaCorecta) {
			return false;
		}
		boolean usernameCorect = username != null & username.equals(inputUsername);
		
		return usernameCorect;
	}
	
	
	
	

}
