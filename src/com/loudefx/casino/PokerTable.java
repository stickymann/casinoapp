/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loudefx.casino;
import com.loudefx.libpoker.PlayerHoldEmPoker;
import com.loudefx.libpoker.Card;
import com.loudefx.libpoker.Deck;
import com.loudefx.libpoker.HoldEmPokerScoreEngine;
import java.sql.ResultSet;
import java.util.Formatter;
/**
 *
 * @author dnes
 */
public class PokerTable implements Runnable {
    private String querystr;
    private String table_id;
    private String description;
    private int maxplayers;
    private int maxplaytime;
    private ResultSet resultSet = null;
    private PlayerHoldEmPoker[] player;
    private Card[] community;
    private Deck deck;
    private HoldEmPokerScoreEngine eng;
    private MySQLAccess db; 
        
    @Override
    @SuppressWarnings("empty-statement")
    public void run() {
        String DBAUTH_FILE = System.getProperty("user.dir") + "/database.properties";
        this.db = new MySQLAccess(DBAUTH_FILE);
        this.setTableParams();
        System.out.println("thread : "+ Thread.currentThread().getName());
        System.out.println("Table Id: " + table_id);
        System.out.println("");
        
        while(true){
            this.gameInitialize();
            try
            {
                Thread.sleep((int)Math.random() * this.maxplaytime);
            }catch(InterruptedException e){};
        
        
        }
        /*
        System.out.println("thread : "+ Thread.currentThread().getName());
        System.out.println("Table Id: " + table_id);
        System.out.println("");
        */
        /*
        this.querystr = "SELECT id,table_id,description from pokertables";
        try
        {
            this.resultSet = db.excuteSelectQuery(querystr);
            System.out.println("thread : "+ Thread.currentThread().getName());
            while (this.resultSet.next()) {
                String id = this.resultSet.getString("id");
                String table_id = this.resultSet.getString("table_id");
                String description = this.resultSet.getString("description");
                System.out.println("Table Id: " + id + ", " + table_id + ", " + description);
            }
            System.out.println("");
        } catch (Exception ex) {
                System.out.println("Error: resultset failed");
        }
        */
    }
    
    private void setTableParams(){
        String num = Thread.currentThread().getName();
        this.querystr = String.format("SELECT table_id,description,maxplayers,maxplaytime FROM pokertables WHERE id=\"%s\"",num);
        this.resultSet = db.excuteSelectQuery(querystr);
        try{
            this.resultSet.next();
            this.table_id    = this.resultSet.getString("table_id");
            this.description = this.resultSet.getString("description");
            this.maxplayers  = this.resultSet.getInt("maxplayers");
            this.maxplaytime = this.resultSet.getInt("maxplaytime");
        } catch (Exception e) {
            this.table_id =  ""; this.description = "";
            this.maxplayers = 0; this.maxplaytime = 0;     
        }
    }
    
    private void gameInitialize(){
         /* intialize score engine */
        eng = new HoldEmPokerScoreEngine();

        /* intialize players */
        (int)Math.random() * this.maxplayers 
        
        if(players > 10){ players = 10;} else if (players < 2){players = 2;}
        player = new PlayerHoldEmPoker[players];
        for ( int i = 0; i < player.length; i++ ){
            player[i] = new PlayerHoldEmPoker(i);
        }

        /* intialize community cards */
        community = new Card[5];
        for ( int i = 0; i < community.length; i++ ){
            community[i] = null;
        }

        /* intialize deck */
        deck = new Deck();
        deck.shuffle();
    }
            
    
    
}
