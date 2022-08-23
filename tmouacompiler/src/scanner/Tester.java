package scanner;

import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Tester
{

  public static void main( String[] args)
    {
        String filename = args[0];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream( filename);
        } catch (Exception e ) { e.printStackTrace();}
        InputStreamReader isr = new InputStreamReader( fis);
        MyScanner scanner = new MyScanner( isr);
        Token aToken = null;
        do
        {
            try {
                aToken = scanner.nextToken();
            }
            catch( Exception e) { e.printStackTrace();}
            if( aToken != null && !aToken.equals( "")){

            }
              // System.out.println("The token returned was " + aToken);
        } while( aToken != null);
    }

}
