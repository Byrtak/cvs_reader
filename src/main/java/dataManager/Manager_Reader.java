package dataManager;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import static java.lang.Integer.parseInt;

public class Manager_Reader {
    List<String> dataListICO =new ArrayList<>();
    List<String> dataListMail =new ArrayList<>();
    Map<String, dataManager.Entry> entryMap = new HashMap<>();
    Map<String, dataManager.Entry> entryMap1 = new HashMap<>();

    int insert=0;
    int duple =0;
    int update=0;

    //CVS reader
    public void readDataCSV(){
        String ico_Firmy,nazev_firmy,adresa_firmy,email_zamestance,jmeno_zamestance,prijmeni_zamestance,datum_aktualizace;
        try {
            String line;
            BufferedReader bufferedReader = null;
            try {
                //loading filepath to reader
                bufferedReader = new BufferedReader(new FileReader(path()));
            } catch (FileNotFoundException e){
                System.out.println("Wrong filepath ---->" +e);
            }
            //Skip header
            bufferedReader.readLine();
            //reading  the CSV file
            while ((line = bufferedReader.readLine()) != null ) { // one line in the CSV -> values[] -> Entry object -> prepareStatement(save || update)
                String[] values = line.split(";");
                ico_Firmy = values[0];
                nazev_firmy = values[1];
                adresa_firmy = values[2];
                email_zamestance = values[3];
                jmeno_zamestance = values[4];
                prijmeni_zamestance = values[5];
                datum_aktualizace = values[6];
                dataManager.Entry entry = new dataManager.Entry(ico_Firmy,nazev_firmy,adresa_firmy,email_zamestance,jmeno_zamestance,prijmeni_zamestance,datum_aktualizace);

                // if it's not already saved to database, add it to the database.
                if(!dataListICO.contains(ico_Firmy)) {
                    dataListICO.add(ico_Firmy);
                    entryMap.put(ico_Firmy, entry);
                    if (!dataListMail.contains(email_zamestance)){
                        dataListMail.add(email_zamestance);
                        entryMap1.put(email_zamestance, entry);
                        saveToDatabse(entry);
                    }
                } else {
                    dataManager.Entry databaseEntry = entryMap.get(ico_Firmy);
                    dataManager.Entry databaseEntry1 = entryMap1.get(email_zamestance);
                    //duplicate -- the entry in the database == this entry
                    if (databaseEntry.theSame(entry) && databaseEntry1.theSame(entry)) {
                        duple++;
                    } else {
                        //update -- the entry in the database is not the same as this entry. ico and mail are the same.
                        updateToDatabse(entry);
                        entryMap.remove(databaseEntry.getIco_Firmy(), databaseEntry);
                        entryMap1.remove(databaseEntry.getEmail_zamestance(), databaseEntry1);
                        entryMap.put(entry.getIco_Firmy(), entry);
                        entryMap1.put(entry.getEmail_zamestance(), entry);
                    }
                }
            } bufferedReader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        //info statements
        System.out.println("New files has been uploaded into the database  :"+insert);
        System.out.println("Files duplicated :"+ duple);
        System.out.println("Files has been updated into the database :"+update);
        System.out.println("Table row(s)  :"+count());
        System.out.println("Data has been inserted successfully.");
    }

    //connect to database
    private  Connection connect(){
        String urlDB = "jdbc:mysql://localhost:3306/database";
        String userID = "root";
        String userPassword = "Apacove12";
        try {
            return DriverManager.getConnection(urlDB, userID, userPassword);
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    //saving data to database
    private void saveToDatabse(dataManager.Entry entry){
        String sql="INSERT INTO firma(ico,com_name,com_addres,epm_mail,epm_name,epm_surn,date_update) VALUES(?,?,?,?,?,?,?)";
        try(Connection connection = connect();
            PreparedStatement prepareStatement = connection.prepareStatement(sql) ) {
            prepareStatement.setInt(1,parseInt(entry.getIco_Firmy()));
            prepareStatement.setString(2,entry.getNazev_firmy());
            prepareStatement.setString(3,entry.getAdresa_firmy());
            prepareStatement.setString(4,entry.getEmail_zamestance());
            prepareStatement.setString(5,entry.getJmeno_zamestance());
            prepareStatement.setString(6,entry.getPrijmeni_zamestance());
            prepareStatement.setDate(7,java.sql.Date.valueOf(dateFormat(entry.getDatum_aktualizace())));

            try {
                prepareStatement.executeUpdate(); //execute prepared statement
                insert++;//count how many new are uploaded
            } catch (SQLException e) {
                e.printStackTrace(); // throw exception Duplicate entry '63675312' for key bcs,.... list and maps do not persist
                //TODO make data and map as file.txt to persist after close app
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //saving data to database
    private void updateToDatabse(dataManager.Entry entry){
        String querry="UPDATE firma SET com_name = ?,com_addres = ? ,epm_name = ?,epm_surn = ?,date_update = ? WHERE ico=? AND epm_mail=?";
        try(Connection connection = connect();
            PreparedStatement prepareStatement = connection.prepareStatement(querry)) {
            prepareStatement.setString(1,entry.getNazev_firmy());
            prepareStatement.setString(2,entry.getAdresa_firmy());
            prepareStatement.setString(3,entry.getJmeno_zamestance());
            prepareStatement.setString(4,entry.getPrijmeni_zamestance());
            prepareStatement.setDate(5,java.sql.Date.valueOf(dateFormat(entry.getDatum_aktualizace())));
            prepareStatement.setInt(6,parseInt(entry.getIco_Firmy()));
            prepareStatement.setString(7,entry.getEmail_zamestance());
            try {
                prepareStatement.executeUpdate();
                update++;//count how many new are updated
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    private Integer count(){ //row(s) a record count
        try(Connection connection = connect()) {
            Statement s = connection.createStatement();
            ResultSet r = s.executeQuery("SELECT COUNT(*) AS ico FROM firma");
            r.next();
            return r.getInt("ico") ;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private  String path(){ //path for file
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a file path: ");
        System.out.flush();
        String filename = scanner.nextLine();
        System.out.println("Transferring data from "+ filename +" to database");
        return filename;
    }
    //Shift date format for MySQL(yyyy-MM-dd)
    public String dateFormat(String date){
        final String OLD_FORMAT = "dd.MM.yyyy";
        final String NEW_FORMAT = "yyyy-MM-dd";
        String newDateString;
        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        Date d = null;
        try {
            d = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.applyPattern(NEW_FORMAT);
        newDateString = sdf.format(d);
        return newDateString;
    }
}

class RunTime {
    //CSV   filepath:  src\main\resources\data_firmy22.csv
    public static void main(String[] args)  {
        Manager_Reader rd = new Manager_Reader();
        rd.readDataCSV();
    }
}

