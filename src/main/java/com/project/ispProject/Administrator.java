package com.project.ispProject;

import java.util.List; 

public class Administrator extends Utilizator {


    public Administrator(int id, String username, String email, String parola) {
        super(id, username, email, parola);
    }

    public void monitorizareActivități(List<Utilizator> utilizatori, List<TemaLicenta> teme, List<Progres> toateProgresele) {
        System.out.println("\n--- Monitorizare Activitati Sistem (Administrator: " + getUsername() + ") ---");

        System.out.println("\nUtilizatori in sistem: " + (utilizatori != null ? utilizatori.size() : 0));
        if (utilizatori != null) {
            for (Utilizator u : utilizatori) {
                System.out.print("  ID: " + u.getID() + ", User: " + u.getUsername() + ", Email: " + u.getEmail());
                if (u instanceof Student) {
                    Student s = (Student) u;
                    System.out.print(", Tip: Student, Facultate: " + s.getFacultate());
                    if (s.getTemaDeAles() != null) {
                        System.out.print(", Tema: " + s.getTemaDeAles().getTitlu());
                    } else {
                        System.out.print(", Tema: Niciuna");
                    }
                } else if (u instanceof Profesor) {
                    Profesor p = (Profesor) u;
                    System.out.print(", Tip: Profesor, Departament: " + p.getDepartament() + ", Teme Propuse: " + p.getTemePropuse().size());
                } else if (u instanceof Administrator) {
                    System.out.print(", Tip: Administrator");
                }
                System.out.println();
            }
        }

        System.out.println("\nTeme de licenta in sistem: " + (teme != null ? teme.size() : 0));
        if (teme != null) {
            long disponibile = teme.stream().filter(t -> t.getStatusActual() == StatusTema.DISPONIBILA).count();
            long alocate = teme.stream().filter(t -> t.getStatusActual() == StatusTema.ALOCATA).count();
            long finalizate = teme.stream().filter(t -> t.getStatusActual() == StatusTema.FINALIZATA).count();
            System.out.println("  Disponibile: " + disponibile);
            System.out.println("  Alocate: " + alocate);
            System.out.println("  Finalizate: " + finalizate);
        }

        System.out.println("\nIntrari de progres inregistrate: " + (toateProgresele != null ? toateProgresele.size() : 0));
        if (toateProgresele != null && !toateProgresele.isEmpty()) {
            System.out.println("  Ultimele 5 intrari de progres (exemplu):");
            toateProgresele.stream()
                           .sorted((p1, p2) -> p2.getData().compareTo(p1.getData())) 
                           .limit(5)
                           .forEach(p -> System.out.println("    - Data: " + p.getData() +
                                                           ", Student: " + p.getStudent().getUsername() +
                                                           ", Tema: " + p.getTema().getTitlu().substring(0, Math.min(p.getTema().getTitlu().length(), 20)) + "..." +
                                                           ", Descriere: " + p.getDescriereEtapa().substring(0, Math.min(p.getDescriereEtapa().length(), 30)) + "..."));
        }



        System.out.println("\n--- Sfarsit Monitorizare Activitati ---");
    }

    @Override
    public void vizualizareProfil() {
        super.vizualizareProfil();
        System.out.println("Rol: Administrator");
        System.out.println("------------------------------------------");
    }
}