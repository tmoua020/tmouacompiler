package parser;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import scanner.MyScanner;
import scanner.Token;
import scanner.TokenType;
import symboltable.SymbolTable;
import symboltable.SymbolTable.Type;
import syntaxtree.*;

/**
 * This is the production rules for all grammars that will be made into a acting
 * parser.
 *
 * @author timlymoua
 */
public class Parser {

    ///////////////////////////////
    //   Instance Variables
    ///////////////////////////////
    private Token lookahead;
    private MyScanner scanner;
    private SymbolTable sTable;

    ///////////////////////////////
    //       Constructors
    ///////////////////////////////
    public Parser(String text, boolean isFilename) {
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
    public ProgramNode program() {
        ProgramNode pNode = new ProgramNode();
        function_declarations();
        match(TokenType.MAIN);
        match(TokenType.LEFTPARENTHESIS);
        match(TokenType.RIGHTPARENTHESIS);
        pNode.setMain(compound_statement());
        function_definitions();
        return pNode;
    }

    /**
     * Executes the rule for the identifier_list non-terminal symbol in the
     * expression grammar.
     */
    public ArrayList<String> identifier_list() {
        ArrayList<String> idList = new ArrayList<>();
        idList.add(lookahead.getLexeme());
        match(TokenType.ID);
        if (lookahead.getTokenType() == TokenType.COMMA) {
            match(TokenType.COMMA);
            idList.addAll(identifier_list());
        }
        return idList;
    }

    /**
     * This executes the rule for declarations non-terminal symbol in the
     * expression grammar.
     */
    public DeclarationsNode declarations() {
        DeclarationsNode dNode = new DeclarationsNode();

        String dName = lookahead.getLexeme();
        if (lookahead.getTokenType() == TokenType.VOID
                || lookahead.getTokenType() == TokenType.INT
                || lookahead.getTokenType() == TokenType.FLOAT) {
            Type type = type();
            identifier_list();
            match(TokenType.SEMICOLON);
            dNode.addDeclarations(declarations());
        } else {
            //lambda
        }
        return dNode;
    }

    /**
     * This executes the rule for type non-terminal symbol in the expression
     * grammar.
     */
    public Type type() {
        Type type = null;
        if (lookahead.getTokenType() == TokenType.VOID) {
            type = type.VOID;
            match(TokenType.VOID);
        } else if (lookahead.getTokenType() == TokenType.INT) {
            type = type.INT;
            match(TokenType.INT);
        } else if (lookahead.getTokenType() == TokenType.FLOAT) {
            type = type.FLOAT;
            match(TokenType.FLOAT);
        }
        return type;
    }

    /**
     * This executes the rule for function declarations non-terminal symbol in
     * the expression grammar.
     */
    public ArrayList<FunctionNode> function_declarations() {
        ArrayList<FunctionNode> fdecNode = new ArrayList<FunctionNode>();
        if (lookahead.getTokenType() == TokenType.VOID
                || lookahead.getTokenType() == TokenType.INT
                || lookahead.getTokenType() == TokenType.FLOAT) {
            function_declaration();
            match(TokenType.SEMICOLON);
            fdecNode = function_declarations();
        } else {
            //lambda
        }
        return fdecNode;
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
    public ArrayList<VariableNode> function_definitions() {
        ArrayList<VariableNode> fdsNode = new ArrayList<VariableNode>();
        if (lookahead.getTokenType() == TokenType.VOID
                || lookahead.getTokenType() == TokenType.INT
                || lookahead.getTokenType() == TokenType.FLOAT) {
            function_definition();
            fdsNode = function_definitions();
        } else {
            //lambda
        }
        return fdsNode;
    }

    /**
     * This executes the rule for function definition non-terminal symbol in the
     * expression grammar
     */
    public FunctionNode function_definition() {
        FunctionNode fdNode = new FunctionNode(lookahead.getLexeme());
        fdNode.getReturnType();
        type();
        String fdName = lookahead.getLexeme();
        match(TokenType.ID);
        fdNode.addParameter((parameters()));
        fdNode.setBody(compound_statement());
        
        return fdNode;
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
    public CompoundStatementNode compound_statement() {
        CompoundStatementNode CSNode = new CompoundStatementNode();
        match(TokenType.BEGIN);
        CSNode.setVariables(declarations());
        ArrayList<StatementNode> statementList = optional_statements();
        for (StatementNode statement : statementList) {
            CSNode.addStatement(statement);
        }
        match(TokenType.END);
        return CSNode;

    }

    /**
     * This executes the rule for optional statements non-terminal symbol in the
     * expression grammar.
     */
    public ArrayList<StatementNode> optional_statements() {

        ArrayList<StatementNode> opNode = new ArrayList<StatementNode>();
        if (lookahead.getTokenType() == TokenType.BEGIN
                || lookahead.getTokenType() == TokenType.ID
                || lookahead.getTokenType() == TokenType.IF
                || lookahead.getTokenType() == TokenType.WHILE
                || lookahead.getTokenType() == TokenType.WRITE
                || lookahead.getTokenType() == TokenType.READ
                || lookahead.getTokenType() == TokenType.RETURN) {
            opNode = statement_list();
        }
        //lamda
        return opNode;

    }

    /**
     * This executes the rule for Statement list non-terminal symbol in the
     * expression grammar.
     */
    public ArrayList<StatementNode> statement_list() {
        ArrayList<StatementNode> slNode = new ArrayList<StatementNode>();
        if (lookahead.getTokenType() == TokenType.BEGIN
                || lookahead.getTokenType() == TokenType.ID
                || lookahead.getTokenType() == TokenType.IF
                || lookahead.getTokenType() == TokenType.WHILE
                || lookahead.getTokenType() == TokenType.WRITE
                || lookahead.getTokenType() == TokenType.READ
                || lookahead.getTokenType() == TokenType.RETURN) {
            slNode.add(statement());
        }
        if (lookahead.getTokenType() == TokenType.SEMICOLON) {
            match(TokenType.SEMICOLON);
            slNode.addAll(statement_list());
        }
        return slNode;
    }

    /**
     * This executes the rule for Statement non-terminal symbol in the
     * expression grammar.
     */
    public StatementNode statement() {
        StatementNode SNode = null;
        if (lookahead.getTokenType() == TokenType.ID) {
            // assignment statement node
            if (true) {  // check inside symbol table to see if KindType = Variable
                AssignmentStatementNode asNode = new AssignmentStatementNode();
                VariableNode var = variable();
                asNode.setLvalue(var);
                match(TokenType.ASSIGN);
                ExpressionNode exp = expression();
                asNode.setExpression(exp);

                return asNode;
            }
            //else {
            //procedure_statement();
            //}
            // compound statement node
        } else if (lookahead.getTokenType() == TokenType.BEGIN) {
            SNode = compound_statement();
            // if node (create)

            return SNode;

        } else if (lookahead.getTokenType() == TokenType.IF) {
            // create if node
            IfStatementNode ifNode = new IfStatementNode();
            match(TokenType.IF);
            ExpressionNode e = expression();
            match(TokenType.THEN);
            StatementNode s1 = statement();
            match(TokenType.ELSE);
            StatementNode s2 = statement();

            // create if node
            // and then plug into if node
            ifNode.setTest(e);
            ifNode.setThenStatement(s1);
            ifNode.setElseStatement(s2);

            return ifNode;

            // while node (create)
        } else if (lookahead.getTokenType() == TokenType.WHILE) {
            WhileStatementNode wNode = new WhileStatementNode();
            match(TokenType.WHILE);
            ExpressionNode e1 = expression();
            match(TokenType.DO);
            StatementNode s1 = statement();

            wNode.setTest(e1);
            wNode.setDoStatement(s1);

            return wNode;

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
        } else {
            error("Error in statement");
        }
        return SNode;
    }

    /**
     * This executes the rule for Variable non-terminal symbol in the expression
     * grammar.
     */
    public VariableNode variable() {
        String varName = lookahead.getLexeme();
        VariableNode varNode = new VariableNode(varName);
        varNode.setType(sTable.getType(varName));
//        if (lookahead.getTokenType() == TokenType.ID) {
        match(TokenType.ID);
        if (lookahead.getTokenType() == TokenType.LEFTBRACKET) {
            match(TokenType.LEFTBRACKET);
            expression();
            match(TokenType.RIGHTBRACKET);
        }
        return varNode;
    }

    /**
     * This executes the rule for Expression List non-terminal symbol in the
     * expression grammar.
     */
    public ArrayList<ExpressionNode> expression_list() {
        ArrayList<ExpressionNode> exp = new ArrayList();
        if (lookahead.getTokenType() == TokenType.ID
                || lookahead.getTokenType() == TokenType.NUMBER
                || lookahead.getTokenType() == TokenType.LEFTPARENTHESIS
                || lookahead.getTokenType() == TokenType.NOT
                || lookahead.getTokenType() == TokenType.PLUS
                || lookahead.getTokenType() == TokenType.MINUS) {
            exp.add(expression());
            if (lookahead.getTokenType() == TokenType.COMMA) {
                match(TokenType.COMMA);
                exp.addAll(expression_list());
            } else {
                // nothing
            }
        }
        return exp;
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
    public ExpressionNode expression() {
        ExpressionNode left = simple_exp();
        if (lookahead.getTokenType() == TokenType.ID
                || lookahead.getTokenType() == TokenType.NUMBER
                || lookahead.getTokenType() == TokenType.LEFTPARENTHESIS
                || lookahead.getTokenType() == TokenType.NOT
                || lookahead.getTokenType() == TokenType.PLUS
                || lookahead.getTokenType() == TokenType.MINUS) {
            left = simple_exp();
            if (Relop(lookahead.getTokenType())) {
                OperationNode oNode = new OperationNode(lookahead.getTokenType());
                match(lookahead.getTokenType());
                oNode.setLeft(left);
                oNode.setRight(simple_exp());
                return oNode;
            }
        }
        return left;
    }

    /**
     * This executes the rule for simple_exp non-terminal symbol in the
     * expression grammar.
     */
    public ExpressionNode simple_exp() {
        ExpressionNode eNode = null;
        if (lookahead.getTokenType() == TokenType.ID
                || lookahead.getTokenType() == TokenType.NUMBER
                || lookahead.getTokenType() == TokenType.LEFTPARENTHESIS
                || lookahead.getTokenType() == TokenType.NOT) {
            eNode = term();
            eNode = simple_part(eNode);
        } else if (lookahead.getTokenType() == TokenType.PLUS
                || lookahead.getTokenType() == TokenType.MINUS) {
            UnaryOperationNode op = sign();
            eNode = term();
            op.setExpression(simple_part(eNode));
            return op;

        }
        return eNode;

    }

    /**
     * This executes the rule for the simple_part non-terminal symbol in the
     * expression grammar.
     */
    public ExpressionNode simple_part(ExpressionNode posLeft) {
        if (Addop(lookahead.getTokenType())) {
            OperationNode oNode = new OperationNode(lookahead.getTokenType());
            match(lookahead.getTokenType());
            ExpressionNode right = term();
            oNode.setLeft(posLeft);
            oNode.setRight(right);
            return simple_part(oNode);
        } else {
            //lambda
        }
        return posLeft;
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
    public ExpressionNode term() {
        if (lookahead.getTokenType() == TokenType.ID || lookahead.getTokenType() == TokenType.NUMBER
                || lookahead.getTokenType() == TokenType.LEFTPARENTHESIS || lookahead.getTokenType() == TokenType.NOT) {
            ExpressionNode left = factor();
            return term_part(left);
        }
        {
            return null;
        }
    }

    /**
     * This executes the rule for term_part non-terminal symbol in the
     * expression grammar.
     */
    public ExpressionNode term_part(ExpressionNode posLeft) {
        if (isMulop(lookahead.getTokenType())) {
            OperationNode oNode = new OperationNode(lookahead.getTokenType());
            match(lookahead.getTokenType());
            ExpressionNode right = factor();
            oNode.setLeft(posLeft);
            oNode.setRight(term_part(right));
            return oNode;
//            match(TokenType.ASTERISK);
//            match(TokenType.SLASH);
//            match(TokenType.PERCENT);
//            match(TokenType.AND);
//            factor();
//            term_part();
//        } else {
//            //lambda
        }
        return posLeft;
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
    public ExpressionNode factor() {
        ExpressionNode eNode = null;
        if (lookahead.getTokenType() == TokenType.ID) {
            eNode = new VariableNode(lookahead.getLexeme());
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
            String number = lookahead.getLexeme();
            ValueNode vNode = new ValueNode(number);
            match(TokenType.NUMBER);
            return vNode;
        } else if (lookahead.getTokenType() == TokenType.LEFTPARENTHESIS) {
            match(TokenType.LEFTPARENTHESIS);
            eNode = expression();
            match(TokenType.RIGHTPARENTHESIS);
        } else if (lookahead.getTokenType() == TokenType.FACTORIAL) {
            match(TokenType.FACTORIAL);
            eNode = factor();
        } else {
            error("factor error");
        }
        return eNode;

    }

    /**
     * This executes the rule for sign non-terminal symbol in the expression
     * grammar.
     */
    public UnaryOperationNode sign() {
        UnaryOperationNode opNode = null;
        if (lookahead.getTokenType() == TokenType.PLUS) {
            opNode = new UnaryOperationNode(TokenType.PLUS);
            match(TokenType.PLUS);
        } else if (lookahead.getTokenType() == TokenType.MINUS) {
            match(TokenType.MINUS);
            opNode = new UnaryOperationNode(TokenType.MINUS);
        } else {
            error("sign error");
        }
        return opNode;

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
