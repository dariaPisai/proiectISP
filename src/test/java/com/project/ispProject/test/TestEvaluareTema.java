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

class TestEvaluareTema {

	private Profesor profesorTest;
    private Student studentTest;
    private TemaLicenta temaPentruEvaluare;
    private InputStream originalIn;

    @BeforeEach
    void setUp() {
        Aplicatie.utilizatori.clear();
        Aplicatie.toateTemele.clear();
        Aplicatie.toateProgresele.clear();
        Aplicatie.clearAllFeedbackForTesting(); 

        profesorTest = new Profesor(202, "ancaIonita", "anca_ionita@upb.ro", "parolaProf", "Ionita", "Anca", "Automatica si Informatica Aplicata");
        Aplicatie.utilizatori.add(profesorTest);

        studentTest = new Student(302, "adrianaTomescu", "adriana_tomescu@stud.acs.upb.ro", "parolaStud", "Tomescu", "Adriana", "Facultatea de Automatica si Calculatoare");
        Aplicatie.utilizatori.add(studentTest);

        temaPentruEvaluare = new TemaLicenta(502, "Aplicatie web pentru design-ul de masini", "Tema cuprinde materii precum proiectarea orientata pe obiecte, baze de date, tehnologii web", false, profesorTest, StatusTema.ALOCATA);
        profesorTest.adaugaTemaPropusa(temaPentruEvaluare);
        Aplicatie.toateTemele.add(temaPentruEvaluare);
        studentTest.setTemaDeAles(temaPentruEvaluare);

        Progres progresInitial = new Progres(701, studentTest, temaPentruEvaluare, "Prima etapa de progres.", LocalDate.now().minusDays(7));
        studentTest.adaugaProgres(progresInitial); 
        Aplicatie.toateProgresele.add(progresInitial); 

        originalIn = System.in;
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
    }

    @Test
    @DisplayName("Testare Evaluare Tema si Marcare ca FINALIZATA")
    void testProfesorEvalueazaSiFinalizeazaTema() {
        assertEquals(StatusTema.ALOCATA, temaPentruEvaluare.getStatusActual(), "Tema ar trebui sa fie initial ALOCATA.");
        assertFalse(studentTest.getProgresInregistrat().isEmpty(), "Studentul trebuie sa aiba progres inregistrat.");

        String inputSimulat = "da\n10\nLucrare excelenta!\n"; 
        System.setIn(new ByteArrayInputStream(inputSimulat.getBytes()));

        profesorTest.evaluareStudent(studentTest);

        assertEquals(StatusTema.FINALIZATA, temaPentruEvaluare.getStatusActual(), "Statusul temei ar trebui schimbat in FINALIZATA.");
        
    }

}
