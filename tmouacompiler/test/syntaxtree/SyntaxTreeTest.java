/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntaxtree;

import org.junit.Test;
import static org.junit.Assert.*;
import parser.Parser;

/**
 * This is a junit test for the SyntaxTree. It should be testing the indented
 * toString and also a bitcoin example file.
 *
 * @author timlymoua
 */
public class SyntaxTreeTest {

    public SyntaxTreeTest() {
    }

    @Test
    public void bitcoinTest() {
        Parser parser = new Parser("money.ac", true);
        ProgramNode actual = parser.program();
        String actualString = actual.indentedToString(0);
        String expectedString
                = "Program\n"
                + "|-- FunctionDefinitions\n"
                + "|-- Compound Statement\n"
                + "|-- --- Declarations\n"
                + "|-- --- --- Name: dollars\n"
                + "|-- --- --- Name: yen\n"
                + "|-- --- --- Name: bitcoins\n"
                + "|-- --- Assignment\n"
                + "|-- --- --- Name: dollars\n"
                + "|-- --- --- Value: 30\n"
                + "|-- --- Assignment\n"
                + "|-- --- --- Name: yen\n"
                + "|-- --- --- Operation: MULTIPLY\n"
                + "|-- --- --- --- Name: dollars\n"
                + "|-- --- --- --- Value: 25\n"
                + "|-- --- Assignment\n"
                + "|-- --- --- Name: bitcoins\n"
                + "|-- --- --- Operation: DIVIDE\n"
                + "|-- --- --- --- Name: dollars\n"
                + "|-- --- --- --- Value: 6\n";

        assertEquals(expectedString, actualString);
    }

    /**
     * Test of indentedToString method, of class SyntaxTreeNode.
     */
    @Test
    public void testIndentedToString() {
        System.out.println("indentedToString");
        int level = 0;
        OperationNode actual = new OperationNode(TokenType.MULTIPLY);
        ValueNode left = new ValueNode("3");
        ValueNode right = new ValueNode("4.7");
        actual.setLeft(left);
        actual.setRight(right);
        String expected = "Operation: MULTIPLY\n"
                + "|-- Value: 3\n"
                + "|-- Value: 4.7\n";
        String actualString = actual.indentedToString(0);
        System.out.println("The actual string is:");
        System.out.print(actualString);
        assertEquals(expected, actualString);
    }
}
