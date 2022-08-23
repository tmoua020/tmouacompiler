package scanner;

import java.util.HashMap;

public class Token
{

	private String lexeme;
	private TokenType tokentype;

	public Token(String lexeme, TokenType tokentype){
		this.lexeme = lexeme;
		this.tokentype = tokentype;
	}
//Getters and setters for Lexeme
	public String getLexeme(){
		return this.lexeme;
	}

	public void setLexeme(String lexeme){
		this.lexeme = lexeme;
	}

	//Getter and setters for TokenType
	public TokenType getTokenType() {
		return this.tokentype;
	}

	public void setTokenType(TokenType tokentype) {
		this.tokentype = tokentype;
	}
//Formats the output of a Token: Type: xxxx, Lexeme: xxxx
	public String toString() {
		return ("Type: " + tokentype + " Lexeme: " + lexeme);
	}
        
        //@Overide
        //public boolean equals(Object o)

}
