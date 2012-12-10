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
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author dnes
 */
public class PokerTable implements Runnable {
    private String querystr;
    private String table_id;
    private String description;
    private int default_min_players;
    private int default_max_players;
    private int default_max_playtime;
    private int user_min_players;
    private int user_max_players;
    private ResultSet resultSet = null;
    private PlayerHoldEmPoker[] player;
    private Card[] community;
    private Deck deck;
    private HoldEmPokerScoreEngine eng;
    private PokerGameResult gameresult; 
    private MySQLAccess db; 
        
    @SuppressWarnings("empty-statement")
    public PokerTable(){
        this(2,10);
    }
    
    public PokerTable(int min_p, int max_p){
        this.user_min_players = min_p; this.user_max_players = max_p;
        String DBAUTH_FILE = System.getProperty("user.dir") + "/database.properties";
        this.db = new MySQLAccess(DBAUTH_FILE);
    }
    
    @Override
    public void run() {
        this.setTableParams();
        System.out.println("thread : "+ Thread.currentThread().getName());
        System.out.println("Table Id: " + this.table_id + ", " + this.user_min_players + ", " + this.user_max_players);
        System.out.println("");
        
        
        while(true){
            this.initializeGame();
            int playtime = (int)(Math.random() * this.default_max_playtime);
            this.gameresult.setPlayTime(playtime);
//System.out.println("Playtime: " +playtime + ", "+ this.default_max_playtime); 
            this.playGame();
            try
            {
                Thread.sleep(playtime);
            }catch(InterruptedException e){};
        }
    }
    
    private void setTableParams(){
        String num = Thread.currentThread().getName();
        this.querystr = String.format("SELECT table_id,description,minplayers,maxplayers,maxplaytime FROM pokertables WHERE id=\"%s\"",num);
        this.resultSet = db.excuteSelectQuery(querystr);
        try{
            this.resultSet.next();
            this.table_id    = this.resultSet.getString("table_id");
            this.description = this.resultSet.getString("description");
            this.default_min_players  = this.resultSet.getInt("minplayers");
            this.default_max_players  = this.resultSet.getInt("maxplayers");
            this.default_max_playtime = this.resultSet.getInt("maxplaytime");
        } catch (Exception e) {
            this.table_id =  ""; this.description = "";
            this.default_min_players = 0; this.default_max_players = 0; this.default_max_playtime = 0;     
        }
    }
    
    private void initializeGame(){
         /* intialize score engine */
        this.eng = new HoldEmPokerScoreEngine();
        this.gameresult = new PokerGameResult();
        this.gameresult.setTableId(this.table_id);
        
        /*current datetime*/
        DateFormat datetimeA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat datetimeB = new SimpleDateFormat("yyyyMMddHHmmss");
        //get current date time with Date()
        Date date = new Date();
        this.gameresult.setInputTime(datetimeA.format(date));
        this.gameresult.setGameId(this.table_id + "_" + datetimeB.format(date));
        
        /* intialize players */
        if(this.user_max_players > this.default_max_players ) { this.user_max_players = this.default_max_players; }
        if(this.user_min_players < this.default_min_players ) { this.user_min_players = this.default_min_players; }
        
        /*generate a number between min and max inclusive*/
        int players = this.user_min_players + (int)(Math.random() * ((this.user_max_players - this.user_min_players)+1));
        this.gameresult.setPlayerTotal(players);
        this.player = new PlayerHoldEmPoker[players];
        for ( int i = 0; i < this.player.length; i++ ){
            this.player[i] = new PlayerHoldEmPoker(i);
        }

        /* intialize community cards */
        this.community = new Card[5];
        for ( int i = 0; i < this.community.length; i++ ){
            this.community[i] = null;
        }

        /* intialize deck */
        this.deck = new Deck();
        this.deck.shuffle();

    }
    
    private void playGame(){
        /* deal cards */
        for ( int i = 0; i < 2; i++ ) {
            for ( int j = 0; j < this.player.length; j++ ){
                this.player[j].setCard(i,this.deck.dealCard());
            }
        }

        for ( int i = 0; i < 5; i++ ){
            this.community[i] = this.deck.dealCard();
        }
        
        this.calculatePlayerScores();
        this.setHandsResult();
        this.writeGameResultToDatabase();
        //this.showTable()
        //this.showHands();
    }
    
    private void calculatePlayerScores(){
        for ( int i = 0; i < this.player.length; i++ ){
                this.player[i].setScore(this.eng.getScore(this.player[i].getCard(0),this.player[i].getCard(1),this.community));
            }
    }
    
    private void setHandsResult(){
        float hiscore = 0f;
        int winner = 0;
        String result_str = "";
        for ( int i = 0; i < this.player.length; i++ ){
            result_str += "p" + Integer.toString(i) + "=";
            for ( int j = 0; j < 2; j++ ){
                result_str += this.player[i].getCard(j).getDisplay() + ".";
            }
            result_str = result_str.substring(0, result_str.length() - 1);
            result_str += ";";
            if(this.player[i].getScore() > hiscore){
                hiscore = this.player[i].getScore();
                winner = i;
            }
        }
        result_str = result_str.substring(0, result_str.length() - 1);
        result_str = this.getCommunityCards() + result_str;
        this.gameresult.setResult(result_str);
        this.gameresult.setWinner(this.player[winner].getName());
        this.gameresult.setWinningHand( this.player[winner].getHandname( this.player[winner].getScore() ) );
    }
    
    private String getCommunityCards(){
        String result_str = "com=";
        for ( int i = 0; i < this.community.length; i++ ){
            if(this.community[i] != null){
                result_str += this.community[i].getDisplay() + ".";
            }
        }
        result_str = result_str.substring(0, result_str.length() - 1);
        result_str += ";";
        return result_str;
    }
    
    private void showHands(){
        float hiscore = 0f;
        int winner = 0;
        for ( int i = 0; i < player.length; i++ ){
            System.out.print(player[i].getName() + " : ");
            for ( int j = 0; j < 2; j++ ){
                System.out.print(player[i].getCard(j).getDisplay() + " ");
            }
            if(player[i].getScore() > hiscore){
                hiscore = player[i].getScore();
                winner = i;
            }
            System.out.print("(" + String.format("%.5g",player[i].getScore()) + ")");
            System.out.println();
        }
        System.out.println("Player With Best Hand : " + player[winner].getName() + "(" + player[winner].getHandname(player[winner].getScore()) + ")");
        System.out.println("----------------------------------");
    }

    private void showTable(){
        System.out.print("Table : ");
        for ( int i = 0; i < this.community.length; i++ ){
            if(this.community[i] != null){
                System.out.print(this.community[i].getDisplay() + " ");
            }
        }
        System.out.println();
    }
    
    private void writeGameResultToDatabase(){
        String fields = "game_id,table_id,player_total,playtime,result,winner,winning_hand,input_time";
        String values = "";
        values += "\""+ this.gameresult.getGameId()+"\",";
        values += "\""+ this.gameresult.getTableId()+"\",";
        values += "\""+ Integer.toString(this.gameresult.getPlayerTotal())+"\",";
        values += "\""+ Integer.toString(this.gameresult.getPlayTime())+"\",";
        values += "\""+ this.gameresult.getResult()+"\",";
        values += "\""+ this.gameresult.getWinner()+"\",";
        values += "\""+ this.gameresult.getWinningHand()+"\",";
        values += "\""+ this.gameresult.getInputTime()+"\",";
        values = values.substring(0, values.length() - 1);
        querystr = String.format("INSERT INTO pokergames (%s) VALUES(%s)",fields,values);
System.out.println(querystr);
        int insert_count = db.excuteNonSelectQuery(querystr);
    }
    
    
}
