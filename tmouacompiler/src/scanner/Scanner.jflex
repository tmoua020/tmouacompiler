/* Declarations */

package scanner;


/**
* In this JFLEX file I will be creating a scanner that will look at Patterns and Lexical Rules.
*/



%%
%public
%class  MyScanner   /* Names the produced java file */
%function nextToken /* Renames the yylex() function */
%type   Token     /* Defines the return type of the scanning function */
%eofval{
  return null;
%eofval}

/* LookUpTable */
%{
Lookuptable t = new Lookuptable();
%}

/* Patterns */

other               = .
letter              = [A-Za-z]
digit               = [0-9]
digits              = {digit}{digit}*
optional_fraction   = (\.{digits})?
optional_exponent   = ((E[\+\-]?){digits})?
number              = {digits}{optional_fraction}{optional_exponent}
id                  = {letter}({letter} | {digit})*
symbol              = [=<>+\-*/;,.\[\]():]
symbols             = {symbol}|:=|<=|>=|<>
commentContent      = [^\{\}]
comment             = \{{commentContent}*\}
whitespace          = [ \n\t\r\f]|{comment}

%%
/* Lexical Rules */

/*{letter} {
      /** Print out the letter that was found. */
      System.out.println("Found a letter: " + yytext());
      return( yytext());
}*/

{number}	{
			 /** Print out the number that was found. */
       Token token = new Token (yytext(), TokenType.NUMBER);
       System.out.println("Found a number: " + yytext());
       return token;
			}


{id}      {
			/** Print out the id that was found. */
        String str = yytext();
        TokenType type = t.get(str);
        if(type == null)
        {
            type = TokenType.ID;
        }
            Token token = new Token (str, type);
            System.out.println("ID: " + token);
            return token;
        }

{whitespace}	{
				/** Ignore Whitespace */
        //if(yytext().equals("\n"))
				}

{symbols}		{
        /** Print out the symbol */
        String str = yytext();
        TokenType type = t.get(str);
        Token token = new Token (str, type);
        System.out.println("Symbol found: " + token);
        return token;
				}

{other}     {
             System.out.println("Illegal character: '" + yytext() + "' found.");
             return null;
        }
