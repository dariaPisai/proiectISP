package com.project.ispProject;
import java.util.Scanner;

public class Aplicatie {
	public static void main(String[] args) {
		Utilizator utilizatorExistent = new Utilizator(1, "admin", "admin@exemplu.ro", "parola123");
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Introduceti username-ul: ");
		String usernameIntrodus = scanner.nextLine();
		
		System.out.println("Introduceti parola: ");
		String parolaIntrodusa = scanner.nextLine();
		
		boolean esteAutentificat = utilizatorExistent.autentificare(usernameIntrodus, parolaIntrodusa);
		
		if(esteAutentificat) {
			System.out.println("Autentificare reusita! Bine ai venit, "+utilizatorExistent.getUsername() + "!");
			System.out.println("ID: " +utilizatorExistent.getID());
			System.out.println("Email: " +utilizatorExistent.getEmail());
		} else {
			System.out.println("Autentificare esuata.");
		}
		
		scanner.close();
		
	}

}
