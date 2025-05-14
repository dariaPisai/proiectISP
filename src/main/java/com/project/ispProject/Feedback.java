package com.project.ispProject;

import java.time.LocalDate;

public class Feedback {
    private static int nextId = 1; 
    private int id;
    private Student studentDestinatar; 
    private Profesor profesorEmitent;  
    private String continut;
    private LocalDate data;
    private TemaLicenta temaAsociata; 

    public Feedback(Student studentDestinatar, Profesor profesorEmitent, TemaLicenta temaAsociata, String continut, LocalDate data) {
        this.id = nextId++;
        this.studentDestinatar = studentDestinatar;
        this.profesorEmitent = profesorEmitent;
        this.temaAsociata = temaAsociata;
        this.continut = continut;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public Student getStudentDestinatar() {
        return studentDestinatar;
    }

    public Profesor getProfesorEmitent() {
        return profesorEmitent;
    }

    public TemaLicenta getTemaAsociata() {
        return temaAsociata;
    }

    public String getContinut() {
        return continut;
    }

    public LocalDate getData() {
        return data;
    }


    public void trimite() {
        System.out.println("\n--- Feedback Nou (ID: " + id + ") ---");
        System.out.println("De la: Prof. " + profesorEmitent.getNume() + " " + profesorEmitent.getPrenume());
        System.out.println("Pentru: " + studentDestinatar.getNume() + " " + studentDestinatar.getPrenume());
        System.out.println("Tema: " + (temaAsociata != null ? temaAsociata.getTitlu() : "N/A"));
        System.out.println("Data: " + data);
        System.out.println("Continut: " + continut);
        System.out.println("Status: Feedback pregatit pentru trimitere/stocare.");
        System.out.println("------------------------------------");


    }

    @Override
    public String toString() {
        return "Feedback [ID=" + id +
               ", Pentru=" + studentDestinatar.getUsername() +
               ", De la=" + profesorEmitent.getUsername() +
               (temaAsociata != null ? ", Tema='" + temaAsociata.getTitlu() + '\'' : "") +
               ", Data=" + data +
               ", Continut='" + continut.substring(0, Math.min(continut.length(), 50)) + "...']";
    }
}