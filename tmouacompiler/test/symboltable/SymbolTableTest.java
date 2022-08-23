package symboltable;

import org.junit.Test;
import static org.junit.Assert.*;
import symboltable.SymbolTable.Kind;
import symboltable.SymbolTable.Type;

/**
 * This is using Junit test to test for the SymbolTable class. It is checking
 * string inputs if it is being added.
 *
 * @author timlymoua
 */
public class SymbolTableTest {

    public SymbolTableTest() {
    }

    @Test
    public void testAddVariableName() {
        String name = "variable Foo";
        SymbolTable test = new SymbolTable();
        test.addVariableName(name, null);
        boolean expected = true;
        // returns true for variable named "Variable"
        expected = true;
        boolean actual = test.isVariableName(name);
        assertEquals(expected, actual);
    }

    @Test
    public void testAddVariableNameBad() {
        String name = "Procedure Foo";
        SymbolTable test = new SymbolTable();
        test.addVariableName(name, null);
        boolean expected = false;
        // returns true for variable named "Variable"
        expected = true;
        boolean actual = test.isVariableName(name);
        assertEquals(expected, actual);
    }

    @Test
    public void testAddProgramName() {
        String name = "Program Foo";
        SymbolTable test = new SymbolTable();
        test.addProgramName(name);
        boolean expected = true;
        // returns true for Program named "Program"
        expected = true;
        boolean actual = test.isProgramName(name);
        assertEquals(expected, actual);
    }

    @Test
    public void testAddArrayName() {
        String name = "Array Foo";
        //Type t = Type.INT;
        SymbolTable test = new SymbolTable();
        test.addArrayName(name, null);
        boolean expected = true;
        // returns true for Array named "Array"
        expected = true;
        boolean actual = test.isArrayName(name);
        assertEquals(expected, actual);
    }

    @Test
    public void testAddFunctionName() {
        String name = "Function Foo";
        //Type t = Type.INT;
        SymbolTable test = new SymbolTable();
        test.addFunctionName(name, null);
        boolean expected = true;
        // returns true for Array named "Array"
        expected = true;
        boolean actual = test.isFunctionName(name);
        assertEquals(expected, actual);
    }

    @Test
    public void toStringtest() {
        SymbolTable symbols = new SymbolTable();
        symbols.addProgramName("NORTH");
        //symbols.addArrayName("SOUTH");
        symbols.addFunctionName("WEST", Type.INT);
        symbols.addVariableName("EAST", Type.FLOAT);
        System.out.println(symbols.toString());
    }

}
