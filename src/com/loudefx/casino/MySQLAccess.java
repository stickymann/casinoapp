/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loudefx.casino;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
/**
 *
 * @author dnes
 */
public class MySQLAccess {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private Properties properties = null;
    
    public MySQLAccess(String DBPARAMS){
        this.connectToDatabase(DBPARAMS);
    }

    private void connectToDatabase(String DBAUTH) {
        FileInputStream instream;
        this.properties = new Properties();
        try {
                instream = new FileInputStream(DBAUTH);
                this.properties.load(instream);
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + DBAUTH);
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error: file not found " + DBAUTH);
            System.out.println(ex.getMessage());
        }
        
        String url = this.properties.getProperty("db.url");
        String user = this.properties.getProperty("db.user");
        String passwd = this.properties.getProperty("db.passwd");

        try {
                this.connect = DriverManager.getConnection(url, user, passwd);
        } catch (Exception ex) {
                System.out.println("Error: cannot connect to database " + url);
                System.out.println(ex.getMessage());
        } 
    }
    
    public ResultSet excuteSelectQuery(String querystr){
        try {
                this.preparedStatement = this.connect.prepareStatement(querystr);
                this.resultSet = this.preparedStatement.executeQuery();
                return this.resultSet;
        } catch (Exception ex) {
                System.out.println("Error: select query failed");
                System.out.println(ex.getMessage());
        }
       return null;
    }
    
    public int excuteNonSelectQuery(String querystr){
        int updatecount = 0;
        try {
                this.preparedStatement = this.connect.prepareStatement(querystr);
                updatecount = this.preparedStatement.executeUpdate();
            } catch (Exception ex) {
                System.out.println("Error: non select query failed");
                System.out.println(ex.getMessage());
        }
        return updatecount;
    }
    
    public void close() throws Exception {
        try {
                if (this.resultSet != null) {
                        this.resultSet.close();
                }
      
                if (this.statement != null) {
                        this.statement.close();
                }
                
                if (this.connect != null) {
                        this.connect.close();
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                throw ex;
            }
    }
}
