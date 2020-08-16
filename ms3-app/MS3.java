import java.sql.*;
import org.apache.commons.csv.*;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.logging.*;



public class MS3 {

      // creates database with name <insert-filename>.db
      private Connection connect(String name) {
         Connection c = null;
         String url = "jdbc:sqlite:"+name+".db";
         try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url);
         } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
         }
         createNewTable(name);
         return c;
      }

      // create new table inside <insert-filename>.db
      public static void createNewTable(String name) {
         // SQLite connection string
         String url = "jdbc:sqlite:"+name+".db";
         
         // SQL statement for creating a new table
         String sql = "CREATE TABLE IF NOT EXISTS ms3 (\n"
                 + "	A text,\n"
                 + "	B text,\n"
                 + "	C text PRIMARY KEY,\n"
                 + "	D text,\n"
                 + "	E text,\n"
                 + "	F text,\n"
                 + "	G text,\n"
                 + "	H text,\n"
                 + "	I text,\n"
                 + "	J text\n"
                 + ");";
         
         try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement()) {
             // create a new table
             stmt.execute(sql);
         } catch (SQLException e) {
             System.out.println(e.getMessage());
         }
     }
      
      // writes bad record to csv file of name <insert-filename>-bad.csv
      public void writeBadRecord(CSVRecord record, String fileName) {
         try {
            FileWriter writer = new FileWriter(fileName+"-bad.csv",true);
            try(CSVPrinter printer = new CSVPrinter(writer, CSVFormat.RFC4180)) {
               printer.printRecord(record);
               printer.flush();
            }
         } catch (IOException e) {
            e.printStackTrace();
            }
      }

      // write log file to log of name <input-filename>.log
      public void writeLogFile(Integer received, Integer success, Integer fail, String name) {
         Logger ms3Logger = Logger.getLogger(name);
         FileHandler ms3FileHandler; 
         try {
            ms3FileHandler = new FileHandler(name+".log", true);
            ms3Logger.addHandler(ms3FileHandler);

            SimpleFormatter formatter = new SimpleFormatter();	
            ms3FileHandler.setFormatter(formatter);
            ms3Logger.info("\nRecords Received - " + received + "\nSuccesses - " + success + "\nFailures - " + fail);
         } catch (SecurityException e) {
			e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         } 
      }

      // add record to database of name <input-filename>.db
      public void addRecord(CSVRecord record, String fileName) {
         String sql = "INSERT INTO ms3 (A,B,C,D,E,F,G,H,I,J) values (?,?,?,?,?,?,?,?,?,?)";
         try (Connection conn = this.connect(fileName); PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // add values to the prepared statement
            stmt.setString(1,record.get(0));
            stmt.setString(2,record.get(1));
            stmt.setString(3,record.get(2));
            stmt.setString(4,record.get(3));
            stmt.setString(5,record.get(4));
            stmt.setString(6,record.get(5));
            stmt.setString(7,record.get(6));
            stmt.setString(8,record.get(7));
            stmt.setString(9,record.get(8));
            stmt.setString(10,record.get(9));
            stmt.executeUpdate();
         }
         catch (SQLException e) {
            System.out.println(e.getMessage());
         }
      }

      public static void main( String args[] ) {
         
         MS3 app = new MS3();
         String fileName = args[0].substring(0,args[0].length() - 4);
         int successCount = 0;
         int failCount = 0; 
         int receivedCount = 0;

         try {
            Reader in = new FileReader(args[0]);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().withIgnoreSurroundingSpaces().withNullString("").parse(in);
            
            for (CSVRecord record : records) {
               receivedCount++;
               boolean insert = true;
               for (String cell : record) {
                  if (cell == null) {
                     app.writeBadRecord(record,fileName);
                     failCount++;
                     insert = false;
                     break;
                  }
               }
               if (insert) {
                  successCount++;
                  app.addRecord(record, fileName);
               }
            }
         } catch (Exception e) {
            
            System.err.println(e);
         } finally {
            app.writeLogFile(receivedCount,successCount, failCount, fileName);
         }
         
      }
}