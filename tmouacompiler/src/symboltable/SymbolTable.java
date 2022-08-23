package symboltable;

import scanner.TokenType;
import java.util.HashMap;
import java.util.Set;

/**
 * The SymbolTable creates a hashmap which accepts objects of type(String,
 * DataType). For every Type of ID there is a add and check function.
 *
 * @author timlymoua
 */
public class SymbolTable {

    private HashMap<String, Datatype> symbols;

    public SymbolTable() {
        symbols = new HashMap<String, Datatype>();
    }
    
    public Type getType(String varName){
        return symbols.get(varName).getType();
    }
    

    public boolean isProcedureName(String lexeme) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Tracks the different Datatype (INT, FLOAT, VOID)
     */
    public enum Type {
        INT,
        FLOAT,
        VOID,
    }

    /**
     * Tracks the different kinds of identifiers (Program, Variable, Array,
     * Function)
     */
    public enum Kind {
        PROGRAM,
        VARIABLE,
        ARRAY,
        FUNCTION,
    }

    /**
     * A data structure to store each symbol in the symbol table. Depending on
     * the type of symbol different constructors are called.
     */
    public class Datatype {

        //Instance Variables
        private String name;
        private Kind kind;
        private Type type;

        //Constructors 
        public Datatype(String name, Kind kind) {
            this.name = name;
            this.kind = kind;
        }

        public Datatype(String name, Kind kind, Type type) {
            this.name = name;
            this.kind = kind;
            this.type = type;

        }

        //Methods
        public String getName() {
            return name;
        }

        public void setName() {
            this.name = name;
        }

        public Kind getKind() {
            return kind;
        }

        public void setKind() {
            this.kind = kind;
        }

        public Type getType() {
            return type;
        }

        public void setType() {
            this.type = type;
        }
    }

    /**
     * This method adds a variable name into the symbol table and checks if
     * table already contains a Variable name.
     *
     * @param name variable name
     * @param kind variable
     * @param type int,float,void
     */
    public boolean addVariableName(String name, Type type) {
        Datatype T = new Datatype(name, Kind.VARIABLE, type);
        if (symbols.containsKey(name)) {
            return false;
        } else {
            T.name = name;
            T.kind = Kind.VARIABLE;
            symbols.put(name, T);
            return true;
        }

    }

    /**
     * Checks if ID matches enum Variable.
     *
     * @param name
     * @return
     */
    public boolean isVariableName(String name) {
        Datatype data = (Datatype) symbols.get(name);
        if (data == null) {
            return false;
        } else if (data.kind == Kind.VARIABLE) {
            return true;
        }
        return false;
    }

    /**
     * Checks if a existing Program name is on the symboltable, if not it add a
     * program name onto the symbol table.
     * @param name
     * @return
     */
    public boolean addProgramName(String name) {
        Datatype T = new Datatype(name, Kind.PROGRAM);
        if (symbols.containsKey(name)) {
            return false;
        } else {
            T.name = name;
            T.kind = Kind.PROGRAM;
            symbols.put(name, T); //puts (name, T) on the table
            return true;
        }
    }

    /**
     * Checks if the data is a Kind.Program
     * @param name
     * @return
     */
    public boolean isProgramName(String name) {
        Datatype data = (Datatype) symbols.get(name);
        if (data == null) {
            return false;
        } else if (data.kind == Kind.PROGRAM) {
            return true;
        }
        return false;
    }

    /**
     * Checks if a existing name Array is in the symboltable, if not it add a
     * Array name onto the symboltable with its Type.
     *
     * @param name Array name
     * @param type INT, FLOAT, VOID
     * @return
     */
    public boolean addArrayName(String name, Type type) {
        Datatype T = new Datatype(name, Kind.ARRAY);
        if (symbols.containsKey(name)) {
            return false;
        } else {
            T.name = name;
            T.kind = Kind.ARRAY;
            symbols.put(name, T);
            return true;
        }
    }

    /**
     * Checks if the data is Kind.Array
     * @param name
     * @return
     */
    public boolean isArrayName(String name) {
        Datatype data = (Datatype) symbols.get(name);
        if (data == null) {
            return false;
        } else if (data.kind == Kind.ARRAY) {
            return true;
        }
        return false;
    }

    /**
     * Checks if existing name Function is on the symboltable, if not it is
     * added onto the table.
     *
     * @param name Function name
     * @param type INT, VOID, FLOAT
     * @return
     */
    public boolean addFunctionName(String name, Type type) {
        Datatype T = new Datatype(name, Kind.FUNCTION, type);
        if (symbols.containsKey(name)) {
            return false;
        } else {
            T.name = name;
            T.kind = Kind.FUNCTION;
            symbols.put(name, T);
            return true;
        }
    }

    public boolean isFunctionName(String name) {
        Datatype data = (Datatype) symbols.get(name);
        if (data == null) {
            return false;
        } else if (data.kind == Kind.FUNCTION) {
            return true;
        }
        return false;
    }
    
    

    @Override
    public String toString() {
        String ans = String.format("%-20s %-20s %-15s  ", "=NAME=", "=KIND=", "=TYPE=") + "\n";
        Set<String> keys = symbols.keySet();
        for (String key : keys) {
            ans += String.format("%-20s %-20s %-15s", key, symbols.get(key).kind, symbols.get(key).type) + "\n";
        }
        return ans;
    }

}
