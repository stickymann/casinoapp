/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loudefx.casino;
import java.sql.ResultSet;
/**
 *
 * @author dnes
 */
public class PokerTable implements Runnable {
    private ResultSet resultSet = null;
    private String querystr;
    @Override
    public void run() {
        
        MySQLAccess db = new MySQLAccess();
        querystr = "SELECT table_id from pokertables";
        try
        {
            resultSet = db.excuteSelectQuery(querystr);
            System.out.println("thread : "+ Thread.currentThread().getName());
            while (resultSet.next()) {
                String table_id = resultSet.getString("table_id");
                System.out.println("Table Id: " + table_id);
            }
            System.out.println("");
        } catch (Exception ex) {
                System.out.println("Error: resultset failed");
        }
        
    }
}
