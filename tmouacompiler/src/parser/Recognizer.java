package parser;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import scanner.MyScanner;
import scanner.Token;
import scanner.TokenType;
import symboltable.SymbolTable;

/**
 * This is the production rules for all grammars that will be made into a acting
 * parser.
 *
 * @author timlymoua
 */
public class Recognizer {

    ///////////////////////////////
    //   Instance Variables
    ///////////////////////////////
    private Token lookahead;
    private MyScanner scanner;
    private SymbolTable sTable;

    ///////////////////////////////
    //       Constructors
    ///////////////////////////////
    public Recognizer(String text, boolean isFilename) {
        sTable = new SymbolTable();
        if (isFilename) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(text);
            } catch (FileNotFoundException ex) {
                error("No file");
            }
            InputStreamReader isr = new InputStreamReader(fis);
            scanner = new MyScanner(isr);
        } else {
            scanner = new MyScanner(new StringReader(text));
        }
        try {
            lookahead = scanner.nextToken();
        } catch (IOException ex) {
            error("Scan error");
        }
    }

    ////////////////////////
    //    Methods
    ////////////////////////
    /**
     * This executes the rule for Program non-terminal symbol in the expression
     * grammar.
     */
    public void program() {
        function_declarations();
        match(TokenType.MAIN);
        match(TokenType.LEFTPARENTHESIS);
        match(TokenType.RIGHTPARENTHESIS);
        compound_statement();
        function_definitions();
    }

    /**
     * Executes the rule for the identifier_list non-terminal symbol in the
     * expression grammar.
     */
    public void identifier_list() {
        String idName = lookahead.getLexeme();
        match(TokenType.ID);
        sTable.addVariableName(idName, null);
        if (lookahead.getTokenType() == TokenType.COMMA) {
            match(TokenType.COMMA);
            identifier_list();
        }
    }

    /**
     * This executes the rule for declarations non-terminal symbol in the
     * expression grammar.
     */
    public void declarations() {
        String dName = lookahead.getLexeme();
        if (lookahead.getTokenType() == TokenType.VOID
                || lookahead.getTokenType() == TokenType.INT
                || lookahead.getTokenType() == TokenType.FLOAT) {
            type();
            identifier_list();
            match(TokenType.SEMICOLON);
            declarations();
        } else {
            //lambda
        }

    }

    /**
     * This executes the rule for type non-terminal symbol in the expression
     * grammar.
     */
    public void type() {
        if (lookahead.getTokenType() == TokenType.VOID) {
            match(TokenType.VOID);
        } else if (lookahead.getTokenType() == TokenType.INT) {
            match(TokenType.INT);
        } else if (lookahead.getTokenType() == TokenType.FLOAT) {
            match(TokenType.FLOAT);
        }
    }

    /**
     * This executes the rule for function declarations non-terminal symbol in
     * the expression grammar.
     */
    public void function_declarations() {
        if (lookahead.getTokenType() == TokenType.VOID
                || lookahead.getTokenType() == TokenType.INT
                || lookahead.getTokenType() == TokenType.FLOAT) {
            function_declaration();
            match(TokenType.SEMICOLON);
            function_declarations();
        } else {
            //lambda
        }
    }

    /**
     * This executes the rule for function declaration non-terminal symbol in
     * the expression grammar.
     */
    public void function_declaration() {
        type();
        match(TokenType.ID);
        parameters();
    }

    /**
     * This executes the rule for function definitions non-terminal symbol in
     * the expression grammar.
     */
    public void function_definitions() {
        if (lookahead.getTokenType() == TokenType.VOID
                || lookahead.getTokenType() == TokenType.INT
                || lookahead.getTokenType() == TokenType.FLOAT) {
            function_definition();
            function_definitions();
        } else {
            //lambda
        }

    }

    /**
     * This executes the rule for function definition non-terminal symbol in the
     * expression grammar
     */
    public void function_definition() {
        type();
        match(TokenType.ID);
        parameters();
        compound_statement();
    }

    /**
     * This executes the rule for parameter non-terminal symbol in the
     * expression grammar.
     */
    public void parameters() {
        if (lookahead.getTokenType() == TokenType.LEFTPARENTHESIS) {
            match(TokenType.LEFTPARENTHESIS);
            parameter_list();
            match(TokenType.RIGHTPARENTHESIS);
        }
    }

    /**
     * This executes the rule for parameter list non-terminal symbol in the
     * expression grammar.
     */
    public void parameter_list() {
        if (lookahead.getTokenType() == TokenType.VOID
                || lookahead.getTokenType() == TokenType.INT
                || lookahead.getTokenType() == TokenType.FLOAT) {
            type();
            match(TokenType.ID);
            if (lookahead.getTokenType() == TokenType.COMMA) {
                parameter_list();
            }
        }

    }

    /**
     * This executes the rule for compound statements non-terminal symbol in the
     * expression grammar.
     */
    public void compound_statement() {
        match(TokenType.BEGIN);
        declarations();
        optional_statements();
        match(TokenType.END);

    }

    /**
     * This executes the rule for optional statements non-terminal symbol in the
     * expression grammar.
     */
    public void optional_statements() {
        if (lookahead.getTokenType() == TokenType.BEGIN
                || lookahead.getTokenType() == TokenType.ID
                || lookahead.getTokenType() == TokenType.IF
                || lookahead.getTokenType() == TokenType.WHILE
                || lookahead.getTokenType() == TokenType.WRITE
                || lookahead.getTokenType() == TokenType.READ
                || lookahead.getTokenType() == TokenType.RETURN) {
            statement_list();
        } else {
            //lambda
        }

    }

    /**
     * This executes the rule for Statement list non-terminal symbol in the
     * expression grammar.
     */
    public void statement_list() {
        if (lookahead.getTokenType() == TokenType.BEGIN
                || lookahead.getTokenType() == TokenType.ID
                || lookahead.getTokenType() == TokenType.IF
                || lookahead.getTokenType() == TokenType.WHILE
                || lookahead.getTokenType() == TokenType.WRITE
                || lookahead.getTokenType() == TokenType.READ
                || lookahead.getTokenType() == TokenType.RETURN) {
            statement();
        }
        if (lookahead.getTokenType() == TokenType.SEMICOLON) {
            match(TokenType.SEMICOLON);
            statement_list();
        }
    }

    /**
     * This executes the rule for Statement non-terminal symbol in the
     * expression grammar.
     */
    public void statement() {
        if (lookahead != null && lookahead.getTokenType() == TokenType.ID && this.sTable.isVariableName(lookahead.getLexeme())) {
            {
                variable();
                match(TokenType.ASSIGN);
                expression();
            }
//            else if(lookahead != null && lookahead.getTokenType() == TokenType.ID && this.sTable.isProcedureName(lookahead.getLexeme())) {
//                    procedure_statement();
//                    }
            //procedure_statement();
        } else if (lookahead.getTokenType() == TokenType.BEGIN) {
            compound_statement();
        } else if (lookahead.getTokenType() == TokenType.IF) {
            match(TokenType.IF);
            expression();
            match(TokenType.THEN);
            statement();
            match(TokenType.ELSE);
            statement();
        } else if (lookahead.getTokenType() == TokenType.WHILE) {
            match(TokenType.WHILE);
            expression();
            match(TokenType.DO);
            statement();
        } else if (lookahead.getTokenType() == TokenType.READ) {
            match(TokenType.READ);
            match(TokenType.LEFTPARENTHESIS);
            match(TokenType.ID);
            match(TokenType.RIGHTPARENTHESIS);
        } else if (lookahead.getTokenType() == TokenType.WRITE) {
            match(TokenType.WRITE);
            match(TokenType.LEFTPARENTHESIS);
            expression();
            match(TokenType.RIGHTPARENTHESIS);
        } else if (lookahead.getTokenType() == TokenType.RETURN) {
            match(TokenType.RETURN);
            expression();
        }

    }

    /**
     * Executes the rule for procedure statement; non-terminal symbol in the
     * expression grammar.
     */
    public void procedure_statement() {

        match(TokenType.ID);

        if (lookahead.getTokenType() == TokenType.LEFTPARENTHESIS) {
            match(TokenType.LEFTPARENTHESIS);
            expression_list();
            match(TokenType.RIGHTPARENTHESIS);
        }
    }

    /**
     * This executes the rule for Variable non-terminal symbol in the expression
     * grammar.
     */
    public void variable() {
        String varName = lookahead.getLexeme();
        if (lookahead.getTokenType() == TokenType.ID) {
            match(TokenType.ID);
        } else {
            match(TokenType.ID);
            match(TokenType.LEFTBRACKET);
            expression();
            match(TokenType.RIGHTBRACKET);
        }
    }

    /**
     * This executes the rule for Expression List non-terminal symbol in the
     * expression grammar.
     */
    public void expression_list() {
        if (lookahead.getTokenType() == TokenType.ID
                || lookahead.getTokenType() == TokenType.NUMBER
                || lookahead.getTokenType() == TokenType.LEFTPARENTHESIS
                || lookahead.getTokenType() == TokenType.NOT
                || lookahead.getTokenType() == TokenType.PLUS
                || lookahead.getTokenType() == TokenType.MINUS) {
            expression();
            if (lookahead.getTokenType() == TokenType.COMMA) {
                match(TokenType.COMMA);
                expression_list();
            } else {
                // nothing
            }
        }
    }

    private boolean Relop(TokenType input) {
        if (input == TokenType.EQUALS
                || input == TokenType.GUILLEMENTS
                || input == TokenType.LESS_THAN
                || input == TokenType.GREATER_THAN
                || input == TokenType.LESS_THAN_OR_EQUAL
                || input == TokenType.GREATER_THAN_OR_EQUAL) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * This executes the rule for expression non-terminal symbol in the grammar.
     */
    public void expression() {
        if (lookahead.getTokenType() == TokenType.ID
                || lookahead.getTokenType() == TokenType.NUMBER
                || lookahead.getTokenType() == TokenType.LEFTPARENTHESIS
                || lookahead.getTokenType() == TokenType.NOT
                || lookahead.getTokenType() == TokenType.PLUS
                || lookahead.getTokenType() == TokenType.MINUS) {
            simple_exp();
            if (Relop(lookahead.getTokenType())) {
                match(TokenType.EQUALS);
                match(TokenType.GUILLEMENTS);
                match(TokenType.LESS_THAN);
                match(TokenType.GREATER_THAN);
                match(TokenType.LESS_THAN_OR_EQUAL);
                match(TokenType.GREATER_THAN_OR_EQUAL);
                simple_exp();
            }
        }
    }

    /**
     * This executes the rule for simple_exp non-terminal symbol in the
     * expression grammar.
     */
    public void simple_exp() {
        if (lookahead.getTokenType() == TokenType.ID
                || lookahead.getTokenType() == TokenType.NUMBER
                || lookahead.getTokenType() == TokenType.LEFTPARENTHESIS
                || lookahead.getTokenType() == TokenType.NOT) {
            term();
            simple_part();
        } else if (lookahead.getTokenType() == TokenType.PLUS
                || lookahead.getTokenType() == TokenType.MINUS) {
            sign();
            term();
            simple_part();

        }

    }

    /**
     * This executes the rule for the simple_part non-terminal symbol in the
     * expression grammar.
     */
    public void simple_part() {
        if (Addop(lookahead.getTokenType())) {
            match(TokenType.PLUS);
            match(TokenType.MINUS);
            match(TokenType.OR);
            term();
            simple_part();
        } else {
            //lambda
        }
    }

    /**
     * A Boolean Test to check if it is a Addop.
     *
     * @param input
     * @return true if input matches
     */
    private boolean Addop(TokenType input) {
        if (input == TokenType.PLUS
                || input == TokenType.MINUS
                || input == TokenType.OR) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * This executes the rule for the term non-terminal symbol in the expression
     * grammar.
     */
    public void term() {
        factor();
        term_part();
    }

    /**
     * This executes the rule for term_part non-terminal symbol in the
     * expression grammar.
     */
    public void term_part() {
        if (isMulop(lookahead.getTokenType())) {
            match(TokenType.ASTERISK);
            match(TokenType.SLASH);
            match(TokenType.PERCENT);
            match(TokenType.AND);
            factor();
            term_part();
        } else {
            //lambda
        }
    }

    /**
     * A Boolean Test to check if it is a Mulop.
     *
     * @param input
     * @return true if input matches
     */
    private boolean isMulop(TokenType input) {
        if (input == TokenType.ASTERISK
                || input == TokenType.SLASH
                || input == TokenType.PERCENT
                || input == TokenType.AND) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This executes the rule for sign non-terminal symbol in the expression
     * grammar.
     */
    public void factor() {
        if (lookahead.getTokenType() == TokenType.ID) {
            match(TokenType.ID);
            if (lookahead.getTokenType() == TokenType.LEFTBRACKET) {
                match(TokenType.LEFTBRACKET);
                expression();
                match(TokenType.RIGHTBRACKET);
            }
            if (lookahead.getTokenType() == TokenType.LEFTPARENTHESIS) {
                match(TokenType.LEFTPARENTHESIS);
                expression_list();
                match(TokenType.RIGHTPARENTHESIS);
            }
        } else if (lookahead.getTokenType() == TokenType.NUMBER) {
            match(TokenType.NUMBER);
        } else if (lookahead.getTokenType() == TokenType.LEFTPARENTHESIS) {
            match(TokenType.LEFTPARENTHESIS);
            expression();
            match(TokenType.RIGHTPARENTHESIS);
        } else if (lookahead.getTokenType() == TokenType.FACTORIAL) {
            match(TokenType.FACTORIAL);
            factor();
        } else {
            error("factor error");
        }

    }

    /**
     * This executes the rule for sign non-terminal symbol in the expression
     * grammar.
     */
    public void sign() {
        if (lookahead.getTokenType() == TokenType.PLUS) {
            match(TokenType.PLUS);
        } else if (lookahead.getTokenType() == TokenType.MINUS) {
            match(TokenType.MINUS);
        } else {
            error("sign error");
        }

    }

    /**
     * Matches the expected token. If the current token in the input stream from
     * the scanner matches the token that is expected, the current token is
     * consumed and the scanner will move on to the next token in the input. The
     * null at the end of the file returned by the scanner is replaced with a
     * fake token containing no type.
     *
     * @param expected The expected token type.
     */
    public void match(TokenType expected) {
        System.out.println("match( " + expected + ")");
        if (this.lookahead.getTokenType() == expected) {
            try {
                this.lookahead = scanner.nextToken();
                if (this.lookahead == null) {
                    this.lookahead = new Token("End of File", null);
                }
            } catch (IOException ex) {
                error("Scanner exception");
            }
        } else {
            error("Match of " + expected + " found "
                    + this.lookahead.getTokenType()
                    + " instead.");
        }
    }

    /**
     * Errors out of the parser. Prints an error message and then exits the
     * program.
     *
     * @param message The error message to print.
     */
    public void error(String message) {
        System.out.println("Error " + message);
        throw new RuntimeException();
    }

    public boolean isDone() {
        System.out.println("Lookahead is " + this.lookahead);
        return this.lookahead.getTokenType() == null;
    }

    /**
     * this function will saves a a filename in the directory where it is ran
     *
     * @param filename is the filename for filecreated
     */
    public void writeToFile() {
        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/" + "output.symboltable")));
            printWriter.println(this.sTable.toString());
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SymbolTable getSymbolTable() {
        return sTable;
    }

}
