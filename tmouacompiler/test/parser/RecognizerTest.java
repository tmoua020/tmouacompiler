package parser;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This is the junit testing to good/bad paths to Program, Declarations,
 * function declaration, statement, simple expressions, factor.
 *
 * @author timlymoua
 */
public class RecognizerTest {

    public RecognizerTest() {
    }

    @Test
    public void testProgramGood() {
        String input = "main() { int f; }";
        Recognizer r = new Recognizer(input, false);
        r.program();
        System.out.println("Program is done " + r.isDone());
        assertTrue(r.isDone());
        System.out.println("---------------------");
    }

    @Test
    public void testFunctionDeclarationGood() {
        String input = "int value";
        Recognizer r = new Recognizer(input, false);
        r.function_declaration();
        System.out.println("Function Declaration is done " + r.isDone());
        assertTrue(r.isDone());
        System.out.println("---------------------");
    }

    @Test
    public void testFunctionDeclarationBad() {
        String input = "double value";
        Recognizer r = new Recognizer(input, false);
        r.function_declaration();
        System.out.println("Function Declaration is done " + r.isDone());
        assertFalse(r.isDone());
        System.out.println("---------------------");
    }

    @Test
    public void testDeclarationGood() {
        String input = "float t;";
        Recognizer r = new Recognizer(input, false);
        r.declarations();
        System.out.println("Declaration is done " + r.isDone());
        assertTrue(r.isDone());
        System.out.println("---------------------");
    }

    @Test
    public void testStatementGood() {
        String input = "Hi := 7";
        Recognizer r = new Recognizer(input, false);
        r.statement();
        System.out.println("Statement is done " + r.isDone());
        assertTrue(r.isDone());
        System.out.println("---------------------");
    }

    @Test
    public void testExpressionListGood() {
        String input = "Hi [50]";
        Recognizer r = new Recognizer(input, false);
        r.expression_list();
        System.out.println("ExpList is done " + r.isDone());
        assertTrue(r.isDone());
        System.out.println("---------------------");
    }

    @Test
    public void testSimple_expGood() {
        String input = "32 - 7 * 8";
        Recognizer r = new Recognizer(input, false);
        r.simple_exp();
        System.out.println("Simple_exp is done " + r.isDone());
        assertTrue(r.isDone());
        System.out.println("---------------------");
    }

    @Test
    public void testSimple_expBad() {
        String input = "begin + (j)";
        Recognizer r = new Recognizer(input, false);
        r.simple_exp();
        System.out.println("Simple_exp is done " + r.isDone());
        assertTrue(r.isDone());
        System.out.println("---------------------");
    }

    @Test
    public void testFactorGood1() {
        String input = "37";
        Recognizer r = new Recognizer(input, false);
        r.factor();
        System.out.println("factor is done " + r.isDone());
        assertTrue(r.isDone());
        System.out.println("---------------------");
    }

    @Test
    public void testFactorBad() {
        String input = "3 5";
        Recognizer r = new Recognizer(input, false);
        r.factor();
        System.out.println("factor is done " + r.isDone());
        assertFalse(r.isDone());
        System.out.println("---------------------");
    }

}
