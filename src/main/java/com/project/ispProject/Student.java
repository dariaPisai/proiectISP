package com.project.ispProject;

import java.util.ArrayList;
import java.util.List; 
import java.util.Scanner;

public class Student extends Utilizator {

    private String nume;
    private String prenume;
    private String facultate;
    private TemaLicenta temaDeAles;
    private ArrayList<TemaLicenta> temeDisponibile;
    private List<Progres> progresInregistrat; 
    Scanner scanner = new Scanner(System.in);

    public Student(int id, String username, String email, String parola, String nume, String prenume, String facultate) {
        super(id, username, email, parola);
        this.nume = nume;
        this.prenume = prenume;
        this.facultate = facultate;
        this.temeDisponibile = new ArrayList<>(); 
        this.progresInregistrat = new ArrayList<>(); 
    }

    public TemaLicenta getTemaDeAles() {
        return temaDeAles;
    }

    public void setTemaDeAles(TemaLicenta temaDeAles) {
        this.temaDeAles = temaDeAles;
    }

    public ArrayList<TemaLicenta> getTemeDisponibile() {
        return temeDisponibile;
    }

    public void setTemeDisponibile(ArrayList<TemaLicenta> temeDisponibile) {
        this.temeDisponibile = temeDisponibile;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getFacultate() {
        return facultate;
    }

    public List<Progres> getProgresInregistrat() {
        return progresInregistrat;
    }

    public void adaugaProgres(Progres progres) {
        if (this.temaDeAles != null && this.temaDeAles.getStatusActual() == StatusTema.ALOCATA) {
            this.progresInregistrat.add(progres);
            System.out.println("Progres adaugat pentru tema: " + this.temaDeAles.getTitlu());
        } else {
            System.out.println("Nu se poate adauga progres. Nu este alocata nicio tema sau tema nu este in starea ALOCATA.");
        }
    }

    public void alegereTema() {

        if (temaDeAles != null && temaDeAles.getStatusActual() == StatusTema.ALOCATA) {
            System.out.println("Aveti deja o tema alocata: \"" + temaDeAles.getTitlu() + "\". Nu puteti alege alta tema momentan.");
            return;
        }

        if (temaDeAles == null) {
            System.out.println("Momentan nu aveti nicio tema de licenta preselectata.");
            if (temeDisponibile != null && !temeDisponibile.isEmpty()) { 
                System.out.println("Doriti sa selectati acum din lista de teme disponibile? (da/nu): ");
                if (scanner.nextLine().trim().equalsIgnoreCase("da")) {
                    System.out.println("\nTeme disponibile pentru selectie: ");
                    for (int i = 0; i < temeDisponibile.size(); i++) {
                        TemaLicenta t = temeDisponibile.get(i);
                        if (t.getStatusActual() == StatusTema.DISPONIBILA) {
                           System.out.println((i + 1) + ". " + t.getTitlu() + " (Profesor: " + t.getProfesorResponsabil().getNume() + " " + t.getProfesorResponsabil().getPrenume() + ")");
                        }
                    }
                    System.out.println("Introduceti numarul temei dorite: ");
                    try {
                        String inputIndex = scanner.nextLine();
                        int indexAles = Integer.parseInt(inputIndex) - 1;

                        if (indexAles >= 0 && indexAles < temeDisponibile.size()) {
                            if (temeDisponibile.get(indexAles).getStatusActual() == StatusTema.DISPONIBILA) {
                                setTemaDeAles(temeDisponibile.get(indexAles));
                                System.out.println("Ati preselectat tema: \"" + this.temaDeAles.getTitlu() + "\". Continuam procesul de alocare...");
                            } else {
                                System.out.println("Tema selectata nu este disponibila. Procesul de alegere se opreste.");
                                return;
                            }
                        } else {
                            System.out.println("Numar invalid. Procesul de alegere se opreste.");
                            return;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Input invalid (nu este un numar). Procesul de alegere se opreste.");
                        return; 
                    }
                } else {
                    System.out.println("Nu ati selectat o tema. Procesul de alegere se opreste.");
                    return;
                }
            } else {
                System.out.println("Nu exista teme disponibile pentru a face o selectie. Procesul de alegere se opreste");
                return;
            }
        }

        if (temaDeAles == null) {
            System.out.println("Eroare interna: Nu s-a putut determina o tema pentru alocare. Procesul se opreste");
            return;
        }

        boolean continuaProcesulAlegere = true;

        do {
            System.out.println("\n=========================================");
            System.out.println("Verificare pentru alocarea temei: \"" + temaDeAles.getTitlu() + "\"");
            System.out.println("Statusul curent al acestei teme: " + this.temaDeAles.getStatusActual());
            System.out.println("--------------------------------------------------");
            if (this.temeDisponibile != null && !this.temeDisponibile.isEmpty()) {
                System.out.println("Context: Teme disponibile general (de la profesor/facultate):");
                for (TemaLicenta temaDinLista : this.temeDisponibile) {
                     if (temaDinLista.getStatusActual() == StatusTema.DISPONIBILA) { 
                        System.out.println("  - " + temaDinLista.getTitlu() + " (Status: " + temaDinLista.getStatusActual() + ")");
                     }
                }
            } else {
                System.out.println("  (Lista generala de teme disponibile nu este incarcata sau este goala)");
            }
            System.out.println("==================================================");


            if (this.temaDeAles.getStatusActual() == StatusTema.DISPONIBILA) {
                System.out.print("Tema \"" + this.temaDeAles.getTitlu() + "\" este DISPONIBILA. Doriti sa o alocati pentru dvs.? (da/nu): ");
                String raspunsPrincipal = scanner.nextLine().trim().toLowerCase();

                switch (raspunsPrincipal) {
                    case "da":
                        this.temaDeAles.seteazaStatus(StatusTema.ALOCATA);
                        System.out.println("Felicitari! Tema \"" + this.temaDeAles.getTitlu() + "\" v-a fost alocata.");
                        Profesor profResponsabil = this.temaDeAles.getProfesorResponsabil();
                        if (profResponsabil != null) {
                            System.out.println("Profesorul responsabil " + profResponsabil.getNume() + " " + profResponsabil.getPrenume() + " a fost notificat (simulare).");
                        }
                        continuaProcesulAlegere = false;
                        break;

                    case "nu":
                        System.out.print("Ati ales 'nu' pentru tema curenta. Doriti sa iesiti din procesul de alegere sau sa selectati o alta tema? (iesire / alta tema): ");
                        String raspunsSecundar = scanner.nextLine().trim().toLowerCase();
                        if (raspunsSecundar.equals("iesire")) {
                            System.out.println("Ati anulat procesul de alegere a temei.");
                            this.temaDeAles = null; 
                            continuaProcesulAlegere = false;
                        } else if (raspunsSecundar.equals("alta tema")) {
                            System.out.println("Veti fi redirectionat pentru a selecta o alta tema.");
                            this.temaDeAles = null; 
                            System.out.println("Pentru a alege o alta tema, va trebui sa apelati din nou optiunea de alegere tema.");
                            continuaProcesulAlegere = false;
                        } else {
                            System.out.println("Raspuns invalid ('" + raspunsSecundar + "'). Se va reincerca alocarea pentru tema curenta.");
                        }
                        break;

                    default:
                        System.out.println("Raspuns invalid ('" + raspunsPrincipal + "'). Va rugam introduceti 'da' sau 'nu'. Se va reincerca alocarea pentru tema curenta.");
                        break;
                }
            } else { 
                System.out.println("Tema \"" + this.temaDeAles.getTitlu() + "\" NU mai este DISPONIBILA (Status actual: " + this.temaDeAles.getStatusActual() + ").");
                System.out.println("Probabil a fost alocata altui student intre timp.");
                System.out.print("Doriti sa iesiti din procesul de alegere sau sa incercati selectarea altei teme? (iesire / alta tema): ");
                String raspunsNonDisponibil = scanner.nextLine().trim().toLowerCase();
                if (raspunsNonDisponibil.equals("iesire")) {
                    System.out.println("Ati anulat procesul de alegere a temei.");
                    this.temaDeAles = null; 
                    continuaProcesulAlegere = false;
                } else if (raspunsNonDisponibil.equals("alta tema")) {
                    System.out.println("Veti fi redirectionat pentru a selecta o alta tema.");
                    this.temaDeAles = null; 
                    System.out.println("Pentru a alege o alta tema, va trebui sa apelati din nou optiunea de alegere tema.");
                    continuaProcesulAlegere = false;
                } else {
                    System.out.println("Raspuns invalid ('" + raspunsNonDisponibil + "'). Se va reincerca afisarea starii temei curente (dacă rămâne selectată).");
                    this.temaDeAles = null; 
                    continuaProcesulAlegere = false; 
                    System.out.println("Procesul de alegere se opreste. Va rugam incercati din nou cu o alta tema.");
                }
            }
        } while (continuaProcesulAlegere);

        if (temaDeAles != null && temaDeAles.getStatusActual() == StatusTema.ALOCATA) {
            System.out.println("\nProces de alegere finalizat. Tema alocata studentului " + getNume() + " " + getPrenume() + " este: \"" + this.temaDeAles.getTitlu() + "\"");
        } else {
            System.out.println("\nProces de alegere finalizat. Nu a fost alocata nicio tema in aceasta sesiune.");
            if (this.temaDeAles != null && this.temaDeAles.getStatusActual() != StatusTema.ALOCATA) {
                this.temaDeAles = null;
            }
        }
    }


    public void vizualizareProgres() {
        System.out.println("\n--- Progres pentru student: " + getNume() + " " + getPrenume() + " ---");
        if (temaDeAles != null) {
            System.out.println("Tema licenta: \"" + temaDeAles.getTitlu() + "\" (Status: " + temaDeAles.getStatusActual() + ")");
            if (progresInregistrat != null && !progresInregistrat.isEmpty()) {
                System.out.println("Etape progres:");
                for (Progres p : progresInregistrat) {
                    System.out.println("  - Data: " + p.getData() + ", Descriere: " + p.getDescriereEtapa());
                }
            } else {
                System.out.println("Niciun progres inregistrat pentru aceasta tema.");
            }
        } else {
            System.out.println("Nicio tema de licenta alocata momentan.");
        }
        System.out.println("------------------------------------------");
    }

    public void comunicare() {
        System.out.println("\n--- Sistem de Comunicare Student ---");
        if (this.temaDeAles == null || this.temaDeAles.getProfesorResponsabil() == null) {
            System.out.println("Nu aveti o tema alocata sau un profesor responsabil cu care sa comunicati.");
            return;
        }

        Profesor prof = this.temaDeAles.getProfesorResponsabil();
        System.out.println("Comunicati cu Prof. " + prof.getNume() + " " + prof.getPrenume());
        System.out.println("Introduceti mesajul dvs.:");
        String mesaj = scanner.nextLine();

        System.out.println("Mesaj trimis (simulare): \"" + mesaj + "\" catre Prof. " + prof.getNume());
        System.out.println("------------------------------------");
    }

    @Override
    public void vizualizareProfil() {
        super.vizualizareProfil(); 
        System.out.println("Nume: " + nume);
        System.out.println("Prenume: " + prenume);
        System.out.println("Facultate: " + facultate);
        if (temaDeAles != null) {
            System.out.println("Tema licenta aleasa: " + temaDeAles.getTitlu() + " (Status: " + temaDeAles.getStatusActual() + ")");
        } else {
            System.out.println("Tema licenta aleasa: Niciuna");
        }
        System.out.println("------------------------------------------");
    }
}