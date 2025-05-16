package com.project.ispProject.test;

import com.project.ispProject.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

class TestAlegereTema {

	private Profesor profesorTest;
    private Student studentTest;
    private TemaLicenta temaDisponibilaPentruAlegere;
    private InputStream originalIn;

    @BeforeEach
    void setUp() {
        Aplicatie.utilizatori.clear();
        Aplicatie.toateTemele.clear();
        Aplicatie.toateProgresele.clear();
        Aplicatie.clearAllFeedbackForTesting();

        profesorTest = new Profesor(203, "ancaIonita", "anca_ionita@ub.com", "parolaProf", "Ionita", "Anca", "Automatica si Informatica Aplicata");
        Aplicatie.utilizatori.add(profesorTest);

        studentTest = new Student(303, "dariaPisai", "daria_ioana.pisai@stud.acs.upb.com", "parolaStud", "Pisai", "Daria", "Facultatea de Automatica si Calculatoare");
        Aplicatie.utilizatori.add(studentTest);
        studentTest.setTemaDeAles(null); 

        temaDisponibilaPentruAlegere = new TemaLicenta(503, "Aplicatii ale retelelor neuronale in bioinginerie", "Tema aleasa cuprinde o gama larga de materii invatate in cadrul facultatii", true, profesorTest, StatusTema.DISPONIBILA);
        profesorTest.adaugaTemaPropusa(temaDisponibilaPentruAlegere);
        Aplicatie.toateTemele.add(temaDisponibilaPentruAlegere);

        ArrayList<TemaLicenta> temeDisponibilePtStudent = new ArrayList<>();
        temeDisponibilePtStudent.add(temaDisponibilaPentruAlegere);
        studentTest.setTemeDisponibile(temeDisponibilePtStudent);

        originalIn = System.in;
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
    }

    @Test
    @DisplayName("Testare Alegere Tema cu Succes de catre Student")
    void testStudentAlegeTemaDisponibilaCuSucces() {
        String inputSimulat = "da\n1\nda\n";
        System.setIn(new ByteArrayInputStream(inputSimulat.getBytes()));

        studentTest.alegereTema();

        assertNotNull(studentTest.getTemaDeAles(), "Studentul ar trebui sa aiba o tema aleasa dupa proces.");
        assertEquals(temaDisponibilaPentruAlegere.getIdTema(), studentTest.getTemaDeAles().getIdTema(), "Tema aleasa de student este incorecta.");
        assertEquals(StatusTema.ALOCATA, studentTest.getTemaDeAles().getStatusActual(), "Statusul temei alese ar trebui sa fie ALOCATA.");

        TemaLicenta temaDinListaGlobala = Aplicatie.toateTemele.stream()
                .filter(t -> t.getIdTema() == temaDisponibilaPentruAlegere.getIdTema())
                .findFirst()
                .orElse(null);
        assertNotNull(temaDinListaGlobala, "Tema ar trebui sa existe in lista globala.");
        assertEquals(StatusTema.ALOCATA, temaDinListaGlobala.getStatusActual(), "Statusul temei in lista globala ar trebui sa fie actualizat la ALOCAT.");
    }

}
