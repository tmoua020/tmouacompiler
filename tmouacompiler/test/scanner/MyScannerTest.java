package scanner;

import java.io.StringReader;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This will use JUNIT test for testing TokenType Variables on the scanner and
 * also the nextToken function.
 *
 * @author timlymoua
 */
public class MyScannerTest {

    public MyScannerTest() {
    }

    /**
     * Test for HappyPath TokenType Period 
     * String input ". +"
     */
    @Test
    public void testPeriod1() {
        Token myToken = null;
        String input = ". +";
        MyScanner instance = new MyScanner(new StringReader(input));
        TokenType expected = TokenType.PERIOD;

        try {
            myToken = instance.nextToken();
            assertEquals(expected, myToken.getTokenType());
            System.out.println(myToken);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * Test for sad path TokenType Period 
     * String input is "- . +" 
     * Expected TokenType fails because - is reading in first.
     */
    @Test
    public void testPeriod2() {
        Token myToken = null;
        String input = "- . +";
        MyScanner instance = new MyScanner(new StringReader(input));
        TokenType expected = TokenType.PERIOD;

        try {
            myToken = instance.nextToken();
            assertEquals(expected, myToken.getTokenType());
            System.out.println(myToken);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * Test for Happy Path for TokenType Number 
     * String input "4.07 + 10" 
     * The expected TokenType is found as 4.07E10 reading in as scientific notation.
     */
    @Test
    public void testNumber1() {
        Token myToken = null;
        String input = "4.07 + 10";
        MyScanner instance = new MyScanner(new StringReader(input));
        TokenType expected = TokenType.NUMBER;

        try {
            myToken = instance.nextToken();
            assertEquals(expected, myToken.getTokenType());
            System.out.println(myToken);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Test for Sad Path for TokenType Number String input ".5 + 10" 
     * The expected TokenType is not found because string input is .5 instead of 0.5
     * making the nextToken pass in a period first.
     */
    @Test
    public void testNumber2() {

        Token myToken = null;
        String input = ".5 + 10";
        MyScanner instance = new MyScanner(new StringReader(input));
        TokenType expected = TokenType.NUMBER;

        try {
            myToken = instance.nextToken();
            assertEquals(expected, myToken.getTokenType());
            System.out.println(myToken);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * Test for happy path TokenType Slash
     * 
     */
    @Test
    public void testComment1() {
        Token myToken = null;
        String input = "// hello";
        MyScanner instance = new MyScanner(new StringReader(input));
        TokenType expected = TokenType.SLASH;

        try {
            myToken = instance.nextToken();
            assertEquals(expected, myToken.getTokenType());
            System.out.println(myToken);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    @Test
    public void testLessThanOrEqual(){
        Token myToken = null;
        String input = "<= 543";
        MyScanner instance = new MyScanner(new StringReader(input));
       
        TokenType expected = TokenType.LESS_THAN_OR_EQUAL;
        
        try {
            myToken = instance.nextToken();
            assertEquals(expected, myToken.getTokenType());
            System.out.println(myToken);
            }
            catch( Exception e) {e.printStackTrace();}
     }

}
