package com.project.ispProject;

import java.time.LocalDate;
import java.util.Scanner;

public class Progres {

    private int id;
    private Student student; 
    private TemaLicenta tema; 
    private String descriereEtapa;
    private LocalDate data;

    public Progres(int id, Student student, TemaLicenta tema, String descriereEtapa, LocalDate data) {
        this.id = id;
        this.student = student;
        this.tema = tema;
        this.descriereEtapa = descriereEtapa;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public TemaLicenta getTema() { 
        return tema;
    }

    public String getDescriereEtapa() { 
        return descriereEtapa;
    }

    public void setDescriereEtapa(String descriereEtapa) {
        this.descriereEtapa = descriereEtapa;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void actualizare() {
        System.out.println("\n--- Actualizare Etapa Progres (ID: " + this.id + ") ---");
        Scanner inputScanner = new Scanner(System.in); 

        System.out.println("Descriere actuala: " + this.descriereEtapa);
        System.out.print("Introduceti noua descriere (lasati gol pentru a nu modifica): ");
        String nouaDescriere = inputScanner.nextLine();
        if (nouaDescriere != null && !nouaDescriere.trim().isEmpty()) {
            this.setDescriereEtapa(nouaDescriere);
            System.out.println("Descrierea etapei a fost actualizata.");
        }

        System.out.println("Data actuala: " + this.data);
        System.out.print("Doriti sa actualizati data? (da/nu): ");
        if (inputScanner.nextLine().trim().equalsIgnoreCase("da")) {
            System.out.print("Introduceti noua data (YYYY-MM-DD): ");
            try {
                LocalDate nouaData = LocalDate.parse(inputScanner.nextLine());
                this.setData(nouaData);
                System.out.println("Data etapei a fost actualizata.");
            } catch (Exception e) {
                System.out.println("Format data invalid. Data nu a fost modificata.");
            }
        }
        System.out.println("Actualizare progres finalizata pentru etapa ID: " + this.id);
        System.out.println("------------------------------------");
    }

    @Override
    public String toString() {
        return "Progres [ID=" + id + ", Student=" + student.getNume() + " " + student.getPrenume() +
               ", Tema=" + tema.getTitlu() + ", Descriere='" + descriereEtapa + '\'' +
               ", Data=" + data + ']';
    }
}