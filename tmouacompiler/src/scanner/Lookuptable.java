package scanner;

import java.util.HashMap;
/*
* This is the lookuptable for assigning the tokens into a hashmap.
*
*/

public class Lookuptable extends HashMap<String, TokenType>{
	public Lookuptable(){
		HashMap<String, TokenType> Map = new HashMap<String, TokenType>();
                        this.put("main", TokenType.MAIN);
                        this.put("void", TokenType.VOID);
			this.put("and", TokenType.AND);
			this.put("program", TokenType.PROGRAM);
			this.put("array", TokenType.ARRAY);
			this.put("{", TokenType.BEGIN);
			this.put("div", TokenType.DIV);
			this.put("do", TokenType.DO);
			this.put("else", TokenType.ELSE);
			this.put("function", TokenType.FUNCTION);
			this.put("if", TokenType.IF);
			this.put("int", TokenType.INT);
			this.put("digit", TokenType.DIGIT);
			this.put("mod", TokenType.MOD);
			this.put("not", TokenType.NOT);
			this.put("of", TokenType.OF);
			this.put("or", TokenType.OR);
			this.put("procedure", TokenType.PROCEDURE);
			this.put("real", TokenType.REAL);
			this.put("then", TokenType.THEN);
			this.put("var", TokenType.VAR);
			this.put("while", TokenType.WHILE);
                        this.put("}", TokenType.END);
                        this.put("read", TokenType.READ);
                        this.put("write", TokenType.WRITE);
                        this.put("float", TokenType.FLOAT);
			this.put(";", TokenType.SEMICOLON);
			this.put(",", TokenType.COMMA);
			this.put(":", TokenType.COLON);
			this.put(".", TokenType.PERIOD);
			this.put("[", TokenType.LEFTBRACKET);
			this.put("]", TokenType.RIGHTBRACKET);
			this.put("(", TokenType.LEFTPARENTHESIS);
			this.put(")", TokenType.RIGHTPARENTHESIS);
			this.put("+", TokenType.PLUS);
			this.put("-", TokenType.MINUS);
			this.put("=", TokenType.EQUALS);
			this.put("<", TokenType.LESS_THAN);
			this.put("<=", TokenType.LESS_THAN_OR_EQUAL);
			this.put(">", TokenType.GREATER_THAN);
			this.put(">=", TokenType.GREATER_THAN_OR_EQUAL);
			this.put("*",  TokenType.ASTERISK);
			this.put("/", TokenType.SLASH);
			this.put(":=", TokenType.ASSIGN);
			this.put("<>", TokenType.GUILLEMENTS);
                        this.put("%", TokenType.PERCENT);
                        this.put("!", TokenType.FACTORIAL);
	}
}
