import java.sql.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileReader;


public class MS3 {
      public static void main( String args[] ) {
         try {
            Reader in = new FileReader("./resources/ms3Interview.csv");
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().withIgnoreSurroundingSpaces().withNullString("").parse(in);
            Integer counter = 0;
            for (CSVRecord record : records) {
               for (String cell : record) {
                  System.out.println(cell);
                  if (cell == null) {
                     // add this record to the log
                     // increase failure count
                  }
               }
               // increase success count
               
               break;
               // String A = record.get(0);
               // System.out.println(A==null);
               // System.out.println(A);
               // counter = counter + 1;
               

            }
         } catch (Exception e) {
            System.err.println(e);
         }
         
         
         //  Connection c = null;
         //  try {
         //     Class.forName("org.sqlite.JDBC");
         //     c = DriverManager.getConnection("jdbc:sqlite:test.db");
         //  } catch ( Exception e ) {
         //     System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         //     System.exit(0);
         //  }
         //  System.out.println("Opened database successfully");
       }
}