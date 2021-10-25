package mySQL;

import java.sql.*;

public class DB_create {

    public static void main(String[] args) throws SQLException {

        String urlDB = "jdbc:mysql://localhost:3306/database";
        String userID = "root";
        String userPassword = "Apacove12";
        String table = "CREATE TABLE firma ( "
                +"ico int DEFAULT NULL,"
                +"com_name varchar(45) DEFAULT NULL,"
                +"com_addres varchar(55) DEFAULT NULL,"
                +"epm_mail varchar(45) DEFAULT NULL,"
                +"epm_name varchar(45) DEFAULT NULL,"
                +"epm_surn varchar(45) DEFAULT NULL,"
                +"date_update date DEFAULT NULL,"
                +"UNIQUE KEY ico_UNIQUE (ico),"
                +"UNIQUE KEY epm_mail_UNIQUE (epm_mail))";

        /**
         * creates database
         */
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/?user=root&password=Apacove12");
        Statement s = conn.createStatement();
        try {
            s.addBatch("CREATE DATABASE database");
        } catch (BatchUpdateException e){
            e.printStackTrace();
        } finally {
            System.out.println("Databaze vytvorena");
        }
        s.executeBatch();

        /**
         * creates table  in database
         */
        Connection connection = DriverManager.getConnection(urlDB, userID, userPassword);
        Statement statement = connection.createStatement();
        try {
            statement.addBatch(table);

        } catch (BatchUpdateException e){
            e.printStackTrace();
        } finally {
            System.out.println("Tabulka vytvorena");
        }
        statement.executeBatch();





    }
}
