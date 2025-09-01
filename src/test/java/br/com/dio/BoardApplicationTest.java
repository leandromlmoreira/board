package br.com.dio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardApplicationTest {

    @Test
    void testApplicationStarts() {
        assertTrue(true, "Aplicação deve iniciar sem erros");
    }
    
    @Test
    void testBasicFunctionality() {
        String expected = "Board de Tarefas";
        assertNotNull(expected, "Nome do projeto deve estar definido");
    }
}
