package com.project.ispProject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Aplicatie {

    private static List<Utilizator> utilizatori = new ArrayList<>();
    private static List<TemaLicenta> toateTemele = new ArrayList<>();
    private static List<Progres> toateProgresele = new ArrayList<>();
    private static List<Feedback> toateFeedbackurile = new ArrayList<>();

    private static Utilizator utilizatorLogat = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializareDate();

        while (true) { 
            if (utilizatorLogat == null) {
                afiseazaMeniuAutentificare();
                String optiuneLogin = scanner.nextLine();
                switch (optiuneLogin) {
                    case "1":
                        procesAutentificare();
                        break;
                    case "2":
                        procesCreareCont();
                        break;
                    case "3":
                        System.out.println("La revedere!");
                        scanner.close();
                        return; 
                    default:
                        System.out.println("Optiune invalida.");
                }
            }

            if (utilizatorLogat != null) {
                buclaMeniuUtilizatorLogat();
            }
        }
    }

    public static void initializareDate() {
        Administrator admin = new Administrator(1, "admin", "admin@isp.com", "admin123");
        utilizatori.add(admin);

        Profesor prof1 = new Profesor(101, "profesor1", "prof1@isp.com", "prof1pass", "Ion", "Popescu", "Calculatoare");
        Profesor prof2 = new Profesor(102, "profesor2", "prof2@isp.com", "prof2pass", "Ana", "Ionescu", "Automatica");
        utilizatori.add(prof1);
        utilizatori.add(prof2);

        TemaLicenta tema1Prof1 = new TemaLicenta(1001, "Retele Neuronale Aplicate", "Studiu...", true, prof1, StatusTema.DISPONIBILA);
        TemaLicenta tema2Prof1 = new TemaLicenta(1002, "Sistem IoT Smart Home", "Dezvoltare...", true, prof1, StatusTema.DISPONIBILA);
        prof1.adaugaTemaPropusa(tema1Prof1);
        prof1.adaugaTemaPropusa(tema2Prof1);
        toateTemele.add(tema1Prof1);
        toateTemele.add(tema2Prof1);

        TemaLicenta tema1Prof2 = new TemaLicenta(2001, "Algoritmi Genetici Avansati", "Cercetare...", true, prof2, StatusTema.DISPONIBILA);
        prof2.adaugaTemaPropusa(tema1Prof2);
        toateTemele.add(tema1Prof2);


        Student stud1 = new Student(10001, "student1", "stud1@isp.com", "stud1pass", "Mihai", "Georgescu", "AC");
        Student stud2 = new Student(10002, "student2", "stud2@isp.com", "stud2pass", "Elena", "Vasilescu", "AC");
        ArrayList<TemaLicenta> temeDisponibileInitial = toateTemele.stream()
                .filter(t -> t.getStatusActual() == StatusTema.DISPONIBILA)
                .collect(Collectors.toCollection(ArrayList::new));
        stud1.setTemeDisponibile(new ArrayList<>(temeDisponibileInitial));
        stud2.setTemeDisponibile(new ArrayList<>(temeDisponibileInitial));


        utilizatori.add(stud1);
        utilizatori.add(stud2);

    }

    public static void afiseazaMeniuAutentificare() {
        System.out.println("\n--- Bine ati venit! ---");
        System.out.println("1. Autentificare");
        System.out.println("2. Creare Cont Nou");
        System.out.println("3. Iesire"); 
        System.out.print("Alegeti o optiune: ");
    }

    public static void procesCreareCont() {
        System.out.println("\n--- Creare Cont Nou ---");

        System.out.print("Introduceti username: ");
        String username = scanner.nextLine();

        if (utilizatori.stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username))) {
            System.out.println("Acest username este deja utilizat. Va rugam alegeti altul.");
            asteaptaEnter();
            return;
        }

        System.out.print("Introduceti email: ");
        String email = scanner.nextLine();

        System.out.print("Introduceti parola: ");
        String parola = scanner.nextLine();

        System.out.print("Introduceti numele de familie: ");
        String nume = scanner.nextLine();

        System.out.print("Introduceti prenumele: ");
        String prenume = scanner.nextLine();

        System.out.println("\nCe tip de cont doriti sa creati?");
        System.out.println("1. Student");
        System.out.println("2. Profesor");
        System.out.print("Alegeti tipul de cont (1 sau 2): ");
        String tipUtilizatorAles = scanner.nextLine();

        int idNou = utilizatori.stream()
                .mapToInt(Utilizator::getID)
                .max()
                .orElse(0) + 1;

        Utilizator utilizatorNouCreat = null;

        switch (tipUtilizatorAles) {
            case "1":
                System.out.print("Introduceti facultatea: ");
                String facultate = scanner.nextLine();
                Student studentNou = new Student(idNou, username, email, parola, nume, prenume, facultate);

                ArrayList<TemaLicenta> temeDisponibileInitial = toateTemele.stream()
                        .filter(t -> t.getStatusActual() == StatusTema.DISPONIBILA)
                        .collect(Collectors.toCollection(ArrayList::new));
                studentNou.setTemeDisponibile(new ArrayList<>(temeDisponibileInitial));
                utilizatorNouCreat = studentNou;
                break;

            case "2":
                System.out.print("Introduceti departamentul: ");
                String departament = scanner.nextLine();
                Profesor profesorNou = new Profesor(idNou, username, email, parola, nume, prenume, departament);
                utilizatorNouCreat = profesorNou;
                break;

            default:
                System.out.println("Tip de cont invalid. Crearea contului a esuat.");
                asteaptaEnter();
                return;
        }

        if (utilizatorNouCreat != null) {
            utilizatori.add(utilizatorNouCreat);
            String rol = (utilizatorNouCreat instanceof Student) ? "Student" : "Profesor";
            System.out.println("Contul de " + rol + " pentru '" + username + "' a fost creat cu succes!");
            System.out.println("ID-ul dvs. este: " + idNou + ". Folositi username-ul si parola pentru autentificare.");
        }
        asteaptaEnter();
    }

    public static void procesAutentificare() {
        System.out.print("Introduceti username: ");
        String username = scanner.nextLine();
        System.out.print("Introduceti parola: ");
        String parola = scanner.nextLine();

        for (Utilizator u : utilizatori) {
            if (u.autentificare(username, parola)) {
                utilizatorLogat = u;
                System.out.println("Autentificare reusita! Bine ai venit, " + utilizatorLogat.getUsername() + "!");
                // Nu mai afisam profilul aici, se va afisa meniul direct
                return;
            }
        }
        System.out.println("Autentificare esuata. Username sau parola incorecta.");
        asteaptaEnter();
    }

    // Metoda buclaMeniuUtilizatorLogat - NOUA / MODIFICATA
    public static void buclaMeniuUtilizatorLogat() {
        boolean continuaSesiunea = true;
        while (continuaSesiunea) {
            System.out.println("\n--- Meniu Principal (" + utilizatorLogat.getUsername() + " - " + utilizatorLogat.getClass().getSimpleName() + ") ---");
            String optiuneMeniuSpecific;

            if (utilizatorLogat instanceof Student) {
                System.out.println("1. Vizualizare Profil");
                System.out.println("2. Alegere Tema Licenta");
                System.out.println("3. Adaugare Progres");
                System.out.println("4. Vizualizare Progres");
                System.out.println("5. Comunicare cu Profesorul");
                System.out.println("6. Vizualizare Feedback Primit");
                System.out.println("0. Delogare");
                System.out.print("Alegeti o optiune: ");
                optiuneMeniuSpecific = scanner.nextLine();
                if (optiuneMeniuSpecific.equals("0")) {
                    continuaSesiunea = false;
                } else {
                    proceseazaOptiuneStudent((Student) utilizatorLogat, optiuneMeniuSpecific);
                }
            } else if (utilizatorLogat instanceof Profesor) {
                System.out.println("1. Vizualizare Profil");
                System.out.println("2. Propunere Tema Noua");
                System.out.println("3. Vizualizare Teme Propuse");
                System.out.println("4. Ofera Feedback unui Student");
                System.out.println("5. Evalueaza Student");
                System.out.println("6. Comunicare");
                System.out.println("0. Delogare");
                System.out.print("Alegeti o optiune: ");
                optiuneMeniuSpecific = scanner.nextLine();
                if (optiuneMeniuSpecific.equals("0")) {
                    continuaSesiunea = false;
                } else {
                    proceseazaOptiuneProfesor((Profesor) utilizatorLogat, optiuneMeniuSpecific);
                }
            } else if (utilizatorLogat instanceof Administrator) {
                System.out.println("1. Vizualizare Profil");
                System.out.println("2. Monitorizare Activitati");
                System.out.println("0. Delogare");
                System.out.print("Alegeti o optiune: ");
                optiuneMeniuSpecific = scanner.nextLine();
                if (optiuneMeniuSpecific.equals("0")) {
                    continuaSesiunea = false;
                } else {
                    proceseazaOptiuneAdministrator((Administrator) utilizatorLogat, optiuneMeniuSpecific);
                }
            } else {
                System.out.println("Eroare: Tip de utilizator necunoscut. Se incearca delogarea.");
                continuaSesiunea = false;
            }
        }
        System.out.println("Delogare reusita.");
        utilizatorLogat = null;
    }


    public static void proceseazaOptiuneStudent(Student student, String optiuneStudent) {
        switch (optiuneStudent) {
            case "1":
                student.vizualizareProfil();
                break;
            case "2":
                ArrayList<TemaLicenta> temePentruAlegere = toateTemele.stream()
                        .filter(t -> t.getStatusActual() == StatusTema.DISPONIBILA)
                        .collect(Collectors.toCollection(ArrayList::new));
                student.setTemeDisponibile(temePentruAlegere);
                student.alegereTema();
                break;
            case "3":
                if (student.getTemaDeAles() != null && student.getTemaDeAles().getStatusActual() == StatusTema.ALOCATA) {
                    System.out.print("Introduceti descrierea etapei de progres: ");
                    String descriereProgres = scanner.nextLine();
                    Progres pNou = new Progres(toateProgresele.size() + 1, student, student.getTemaDeAles(), descriereProgres, LocalDate.now());
                    student.adaugaProgres(pNou);
                    toateProgresele.add(pNou);
                } else {
                    System.out.println("Trebuie sa aveti o tema alocata pentru a adauga progres.");
                }
                break;
            case "4":
                student.vizualizareProgres();
                break;
            case "5":
                student.comunicare();
                break;
            case "6":
                System.out.println("\n--- Feedback Primit ---");
                boolean feedbackGasit = false;
                for (Feedback f : toateFeedbackurile) {
                    if (f.getStudentDestinatar().equals(student)) {
                        System.out.println("------------------------------------");
                        System.out.println("De la: Prof. " + f.getProfesorEmitent().getNume() + " " + f.getProfesorEmitent().getPrenume());
                        System.out.println("Tema: " + (f.getTemaAsociata() != null ? f.getTemaAsociata().getTitlu() : "N/A"));
                        System.out.println("Data: " + f.getData());
                        System.out.println("Continut: " + f.getContinut());
                        System.out.println("------------------------------------");
                        feedbackGasit = true;
                    }
                }
                if (!feedbackGasit) {
                    System.out.println("Nu aveti niciun feedback.");
                }
                break;
            default:
                System.out.println("Optiune invalida pentru student.");
        }
        if(!optiuneStudent.equals("2")) { // Metoda alegereTema este deja foarte interactiva
            asteaptaEnter();
        }
    }

    public static void proceseazaOptiuneProfesor(Profesor profesor, String optiuneProfesor) {
        List<Student> studentiSupervizati;
        switch (optiuneProfesor) {
            case "1":
                profesor.vizualizareProfil();
                break;
            case "2":
                profesor.propunereTema();
                if (!profesor.getTemePropuse().isEmpty()) {
                    TemaLicenta ultimaTemaPropusa = profesor.getTemePropuse().get(profesor.getTemePropuse().size() - 1);
                    if (!toateTemele.contains(ultimaTemaPropusa)) {
                        toateTemele.add(ultimaTemaPropusa);
                    }
                }
                break;
            case "3":
                System.out.println("\n--- Teme propuse de Prof. " + profesor.getNume() + " " + profesor.getPrenume() + " ---");
                if (profesor.getTemePropuse().isEmpty()) {
                    System.out.println("  Nicio tema propusa momentan.");
                } else {
                    for (int i = 0; i < profesor.getTemePropuse().size(); i++) {
                        TemaLicenta t = profesor.getTemePropuse().get(i);
                        System.out.print("  " + (i + 1) + ". \"" + t.getTitlu() + "\" (Status: " + t.getStatusActual() + ")");
                        if (t.getStatusActual() == StatusTema.ALOCATA) {
                            Student studentAlocat = gasesteStudentCuTema(t);
                            if (studentAlocat != null) {
                                System.out.print(" - Alocata lui: " + studentAlocat.getNume() + " " + studentAlocat.getPrenume());
                            }
                        }
                        System.out.println();
                    }
                }
                break;
            case "4":
                studentiSupervizati = profesor.getStudentiSupervizati(
                        utilizatori.stream().filter(u -> u instanceof Student).map(u -> (Student) u).collect(Collectors.toList())
                );
                if (studentiSupervizati.isEmpty()) {
                    System.out.println("Nu aveti studenti alocati carora sa le oferiti feedback.");
                    break;
                }
                System.out.println("\n--- Selectati studentul pentru feedback ---");
                for (int i = 0; i < studentiSupervizati.size(); i++) {
                    Student s = studentiSupervizati.get(i);
                    System.out.println((i + 1) + ". " + s.getNume() + " " + s.getPrenume() +
                                       (s.getTemaDeAles() != null ? " (Tema: \"" + s.getTemaDeAles().getTitlu() + "\")" : " (Fara tema alocata)"));
                }
                System.out.print("Numarul studentului (sau 0 pentru a anula): ");
                try {
                    String inputIndex = scanner.nextLine();
                    int indexStud = Integer.parseInt(inputIndex);
                    if (indexStud == 0) break;
                    indexStud--; 
                    if (indexStud >= 0 && indexStud < studentiSupervizati.size()) {
                        Student studentSelectat = studentiSupervizati.get(indexStud);
                        if (studentSelectat.getTemaDeAles() != null && studentSelectat.getTemaDeAles().getProfesorResponsabil().equals(profesor)) {
                            profesor.oferaFeedback(studentSelectat);
                        } else {
                             System.out.println("Acest student nu are o tema alocata de la dvs.");
                        }
                    } else {
                        System.out.println("Selectie invalida.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input invalid (nu este un numar).");
                }
                break;
            case "5":
                studentiSupervizati = profesor.getStudentiSupervizati(
                        utilizatori.stream().filter(u -> u instanceof Student).map(u -> (Student) u).collect(Collectors.toList())
                );
                if (studentiSupervizati.isEmpty()) {
                    System.out.println("Nu aveti studenti alocati pentru a evalua.");
                    break;
                }
                System.out.println("\n--- Selectati studentul pentru evaluare ---");
                for (int i = 0; i < studentiSupervizati.size(); i++) {
                     Student s = studentiSupervizati.get(i);
                    System.out.println((i + 1) + ". " + s.getNume() + " " + s.getPrenume() +
                            " (Tema: \"" + (s.getTemaDeAles() != null ? s.getTemaDeAles().getTitlu() : "N/A") + "\"" +
                            ", Status: " + (s.getTemaDeAles() != null ? s.getTemaDeAles().getStatusActual() : "N/A") + ")");
                }
                System.out.print("Numarul studentului (sau 0 pentru a anula): ");
                try {
                    String inputIndexEval = scanner.nextLine();
                    int indexStudEval = Integer.parseInt(inputIndexEval);
                    if (indexStudEval == 0) break;
                    indexStudEval--; 
                    if (indexStudEval >= 0 && indexStudEval < studentiSupervizati.size()) {
                        profesor.evaluareStudent(studentiSupervizati.get(indexStudEval));
                    } else {
                        System.out.println("Selectie invalida.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input invalid (nu este un numar).");
                }
                break;
            case "6":
                profesor.comunicare();
                break;
            default:
                System.out.println("Optiune invalida pentru profesor.");
        }
        asteaptaEnter();
    }

    public static void proceseazaOptiuneAdministrator(Administrator admin, String optiuneAdmin) {
        switch (optiuneAdmin) {
            case "1":
                admin.vizualizareProfil();
                break;
            case "2":
                admin.monitorizareActivități(utilizatori, toateTemele, toateProgresele);
                break;
            default:
                System.out.println("Optiune invalida pentru administrator.");
        }
        asteaptaEnter();
    }

    private static Student gasesteStudentCuTema(TemaLicenta tema) {
        return utilizatori.stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .filter(s -> s.getTemaDeAles() != null && s.getTemaDeAles().equals(tema))
                .findFirst()
                .orElse(null);
    }

    private static void asteaptaEnter() {
        System.out.println("\n--- Apasati Enter pentru a continua ---");
        scanner.nextLine();
    }

    public static void adaugaFeedbackGlobal(Feedback feedback) {
        if (feedback != null) {
            toateFeedbackurile.add(feedback);
        }
    }


}