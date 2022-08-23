package compiler;

import parser.Parser;
import syntaxtree.ProgramNode;

/**
 * This is the Main class that will take in C language file in the command line, 
 * to be parsed into the Recognizer to print out the symbol table.
 * @author timlymoua
 */
public class Main {
    public static void main(String[] args){
        String filename = args[0];
        
        try{
            //Recognizer r = new Recognizer(filename, true);
            Parser r = new Parser(filename, true);
            
            ProgramNode syntaxtree = r.program();
            //r.writeToFile();
            System.out.println("System ran without errors.");
            System.out.println(syntaxtree.indentedToString(0));
            
        } catch (Exception e){
            
        }
    }
}
