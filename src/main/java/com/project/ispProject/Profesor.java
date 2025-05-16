package com.project.ispProject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Profesor extends Utilizator {

    private String nume;
    private String prenume;
    private String departament;
    private List<TemaLicenta> temePropuse; 
    private Scanner scanner = new Scanner(System.in);

    public Profesor(int id, String username, String email, String parola, String nume, String prenume, String departament) {
        super(id, username, email, parola);
        this.nume = nume;
        this.prenume = prenume;
        this.departament = departament;
        this.temePropuse = new ArrayList<>();
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getDepartament() {
        return departament;
    }

    public List<TemaLicenta> getTemePropuse() {
        return temePropuse;
    }

    public void adaugaTemaPropusa(TemaLicenta tema) {
        this.temePropuse.add(tema);
    }


    public void propunereTema() {
        System.out.println("\n--- Propunere Tema Noua ---");
        System.out.print("Introduceti ID-ul temei: ");
        int idTema;
        try {
            idTema = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID invalid. Trebuie sa fie un numar.");
            return;
        }

        System.out.print("Introduceti titlul temei: ");
        String titlu = scanner.nextLine();
        System.out.print("Introduceti descrierea temei: ");
        String descriere = scanner.nextLine();

        TemaLicenta temaNoua = new TemaLicenta(idTema, titlu, descriere, true, this, StatusTema.DISPONIBILA);
        this.adaugaTemaPropusa(temaNoua);

        System.out.println("Tema \"" + titlu + "\" a fost propusa cu succes si este acum disponibila.");
        System.out.println("------------------------------------------");
    }

    public void oferaFeedback(Student student) {
        System.out.println("\n--- Oferire Feedback pentru Student: " + student.getNume() + " " + student.getPrenume() + " ---");

        if (student.getTemaDeAles() == null) {
            System.out.println("Studentul " + student.getNume() + " " + student.getPrenume() + " nu are nicio tema de licenta alocata.");
            return;
        }

        TemaLicenta temaStudent = student.getTemaDeAles();

        if (!temaStudent.getProfesorResponsabil().equals(this)) {
            System.out.println("Studentul " + student.getNume() + " " + student.getPrenume() +
                               " are tema \"" + temaStudent.getTitlu() + "\" alocata altui profesor.");
            return;
        }

        System.out.println("Tema studentului: \"" + temaStudent.getTitlu() + "\""); 
        System.out.print("Introduceti continutul feedback-ului: ");
        String continutFeedback = scanner.nextLine();

        Feedback feedbackNou = new Feedback(
                student,
                this,          
                temaStudent,    
                continutFeedback,
                LocalDate.now() 
        );

        feedbackNou.trimite(); 
        
        Aplicatie.adaugaFeedbackGlobal(feedbackNou);


        System.out.println("Feedback-ul a fost generat.");
        System.out.println("------------------------------------------");
    }
    
    public List<Student> getStudentiSupervizati(List<Student> totiStudentii) {
        if (totiStudentii == null) return new ArrayList<>();
        return totiStudentii.stream()
                .filter(s -> s.getTemaDeAles() != null && s.getTemaDeAles().getProfesorResponsabil().equals(this))
                .collect(Collectors.toList());
    }


    public void evaluareStudent(Student student) {
        System.out.println("\n--- Evaluare Student: " + student.getNume() + " " + student.getPrenume() + " ---");
        if (student.getTemaDeAles() == null || !student.getTemaDeAles().getProfesorResponsabil().equals(this)) {
            System.out.println("Nu puteti evalua acest student. Fie nu are o tema alocata, fie nu sunteti profesorul coordonator.");
            return;
        }

        TemaLicenta temaStudent = student.getTemaDeAles();
        System.out.println("Tema evaluata: \"" + temaStudent.getTitlu() + "\"");
        System.out.println("Status actual tema: " + temaStudent.getStatusActual());

        if (temaStudent.getStatusActual() == StatusTema.FINALIZATA) {
            System.out.println("Aceasta tema a fost deja marcata ca FINALIZATA.");
            return;
        }
        
        if (student.getProgresInregistrat() == null || student.getProgresInregistrat().isEmpty()) {
            System.out.println("Studentul nu are niciun progres inregistrat pentru aceasta tema. Evaluarea nu poate continua momentan.");
            return;
        }
        
        System.out.println("Progresul studentului:");
        student.vizualizareProgres(); 

        System.out.print("Considerati ca tema este finalizata si gata de evaluare finala? (da/nu): ");
        String raspunsFinalizare = scanner.nextLine().trim().toLowerCase();

        if (raspunsFinalizare.equals("da")) {
            temaStudent.seteazaStatus(StatusTema.FINALIZATA);
            System.out.println("Tema \"" + temaStudent.getTitlu() + "\" a fost marcata ca FINALIZATA.");
            System.out.print("Introduceti nota finala (ex. 1-10): ");
            String nota = scanner.nextLine();
            System.out.print("Introduceti comentarii finale pentru evaluare: ");
            String comentarii = scanner.nextLine();
            System.out.println("Evaluare salvata (simulare): Nota " + nota + ", Comentarii: " + comentarii);
        } else {
            System.out.println("Tema nu a fost marcata ca finalizata. Studentul poate continua lucrul.");
            System.out.println("Puteti oferi feedback suplimentar prin optiunea dedicata.");
        }
        System.out.println("------------------------------------------");
    }


    public void comunicare() {
        System.out.println("\n--- Sistem de Comunicare Profesor ---");
        System.out.println("Cu cine doriti sa comunicati?");
        System.out.println("1. Trimite mesaj general");
        System.out.println("2. Vezi mesaje primite (neimplementat)");
        System.out.print("Alegeti o optiune: ");
        String optiune = scanner.nextLine();

        if (optiune.equals("1")) {
            System.out.print("Introduceti mesajul: ");
            String mesaj = scanner.nextLine();
            System.out.println("Mesaj general trimis (simulare): \"" + mesaj + "\"");
        } else {
            System.out.println("Optiune nevalida sau neimplementata.");
        }
        System.out.println("------------------------------------");
    }
    

    public void actualizeazaStatusTemaInLista(TemaLicenta temaActualizata, StatusTema nouStatus) {
        for (int i = 0; i < temePropuse.size(); i++) {
            if (temePropuse.get(i).getIdTema() == temaActualizata.getIdTema()) {
                temePropuse.get(i).seteazaStatus(nouStatus);
                System.out.println("(Profesor) Statusul temei '" + temaActualizata.getTitlu() + "' actualizat la " + nouStatus + " in lista de teme propuse.");
                return;
            }
        }
    }


    @Override
    public void vizualizareProfil() {
        super.vizualizareProfil();
        System.out.println("Nume: " + nume);
        System.out.println("Prenume: " + prenume);
        System.out.println("Departament: " + departament);
        System.out.println("Teme propuse: ");
        if (temePropuse.isEmpty()) {
            System.out.println("  Nicio tema propusa momentan.");
        } else {
            for (TemaLicenta tema : temePropuse) {
                System.out.println("  - " + tema.getTitlu() + " (Status: " + tema.getStatusActual() + ")");
            }
        }
        System.out.println("------------------------------------------");
    }
}