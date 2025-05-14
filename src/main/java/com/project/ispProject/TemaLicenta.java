package com.project.ispProject;


public class TemaLicenta {

    private int idTema;
    private String titlu;
    private String descriere;
    private boolean disponibilitateLogica; 
    private Profesor profesorResponsabil;
    private StatusTema statusActual;

    public TemaLicenta(int idTema, String titlu, String descriere, boolean disponibilitate, Profesor profesorResponsabil, StatusTema statusActual) {
        this.idTema = idTema;
        this.titlu = titlu;
        this.descriere = descriere;
        this.profesorResponsabil = profesorResponsabil;
        this.statusActual = statusActual;
        this.disponibilitateLogica = (statusActual == StatusTema.DISPONIBILA);

    }

    public int getIdTema() {
        return idTema;
    }

    public String getTitlu() {
        return titlu;
    }

    public String getDescriere() {
        return descriere;
    }


    public boolean getDisponibilitate() {
        return this.disponibilitateLogica;
    }

    public Profesor getProfesorResponsabil() {
        return profesorResponsabil;
    }
    

    public void setProfesorResponsabil(Profesor profesor) {
        this.profesorResponsabil = profesor;
    }

    public StatusTema getStatusActual() {
        return statusActual;
    }

    public void seteazaStatus(StatusTema statusNou) {
        this.statusActual = statusNou;
        this.disponibilitateLogica = (statusNou == StatusTema.DISPONIBILA); 


        switch (statusNou) {
            case DISPONIBILA:
                System.out.println("INFO: Tema \"" + titlu + "\" este acum DISPONIBILA.");
                break;
            case ALOCATA:
                System.out.println("INFO: Tema \"" + titlu + "\" a fost ALOCATA.");
                break;
            case FINALIZATA:
                System.out.println("INFO: Tema \"" + titlu + "\" a fost FINALIZATA.");
                break;
            default:
                System.out.println("INFO: Statusul temei \"" + titlu + "\" a fost schimbat la " + statusNou + " (status necunoscut in switch).");
                break;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemaLicenta that = (TemaLicenta) o;
        return idTema == that.idTema;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idTema);
    }

    @Override
    public String toString() {
        return "TemaLicenta [idTema=" + idTema + ", titlu=" + titlu + ", statusActual=" + statusActual +
               ", profesorResponsabil=" + (profesorResponsabil != null ? profesorResponsabil.getNume() : "N/A") + "]";
    }
}