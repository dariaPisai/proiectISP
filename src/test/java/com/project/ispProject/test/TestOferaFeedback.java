package com.project.ispProject.test;

import com.project.ispProject.*;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;


class TestOferaFeedback {

	private Profesor profesorTest;
    private Student studentTest;
    private TemaLicenta temaAlocataStudentului;
    private InputStream originalIn;

    @BeforeEach
    void setUp() {
        Aplicatie.utilizatori.clear();
        Aplicatie.toateTemele.clear();
        Aplicatie.toateProgresele.clear();
        Aplicatie.clearAllFeedbackForTesting();

        profesorTest = new Profesor(201, "ancaIonita", "anca_ionita@upb.ro", "parolaProf", "Ionita", "Anca", "Automatica si Informatica Aplicata");
        Aplicatie.utilizatori.add(profesorTest);

        studentTest = new Student(301, "andreeaIurea", "andreea_iurea@stud.acs.upb.ro", "parolaStud", "Iurea", "Andreea", "Facultatea de Automatica si Calculatoare");
        Aplicatie.utilizatori.add(studentTest);

        temaAlocataStudentului = new TemaLicenta(501, "Aplicație Web pentru Organizarea de Evenimente", "Tema este compusa din materii aprofundate de student in anii 1-4.", false, profesorTest, StatusTema.ALOCATA);
        profesorTest.adaugaTemaPropusa(temaAlocataStudentului);
        Aplicatie.toateTemele.add(temaAlocataStudentului);
        studentTest.setTemaDeAles(temaAlocataStudentului); 

        originalIn = System.in;
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
    }

    @Test
    @DisplayName("Testare Oferire Feedback cu Succes de catre Profesor")
    void testProfesorOferaFeedbackCuSucces() {
        String feedbackContent = "Feedback pozitiv, studentul a respectat cerintele impuse de profesor si a parcurs etapele la timp.";
        System.setIn(new ByteArrayInputStream(feedbackContent.getBytes()));

        // Act
        profesorTest.oferaFeedback(studentTest); 

        // Assert
        List<Feedback> feedbackuriActuale = Aplicatie.getAllFeedbackForTesting();
        assertEquals(1, feedbackuriActuale.size(), "Ar trebui sa existe un singur feedback inregistrat.");
        Feedback feedbackGenerat = feedbackuriActuale.get(0);

        assertNotNull(feedbackGenerat, "Feedback-ul generat nu ar trebui sa fie null.");
        assertEquals(studentTest.getID(), feedbackGenerat.getStudentDestinatar().getID(), "ID-ul destinatarului este incorect.");
        assertEquals(profesorTest.getID(), feedbackGenerat.getProfesorEmitent().getID(), "ID-ul emitentului este incorect.");
        assertEquals(temaAlocataStudentului.getIdTema(), feedbackGenerat.getTemaAsociata().getIdTema(), "ID-ul temei asociate este incorect.");
        assertTrue(feedbackGenerat.getContinut().contains(feedbackContent), "Continutul feedback-ului nu corespunde.");
        assertEquals(LocalDate.now(), feedbackGenerat.getData(), "Data feedback-ului ar trebui sa fie cea curenta.");
    }

}
