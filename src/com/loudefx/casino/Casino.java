/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loudefx.casino;

/**
 *
 * @author dnes
 */
public class Casino {
    private int tables_total;
    private int tables_startnum;
    private int players_min;
    private int players_max;
    private Thread[] th_table;
    
    public Casino(){
        this.setTablesTotal(10);
        this.setTablesStartnum(0);
        this.setPlayersMin(2);
        this.setPlayersMax(10);
        this.initialize();
    }
    
    public Casino(int tablecount,int startnum){
        this.setTablesTotal(tablecount);
        this.setTablesStartnum(startnum);
        this.setPlayersMin(2);
        this.setPlayersMax(10);
        this.initialize();
    }
    
    public Casino(int tablecount,int startnum, int playermin, int playermax){
        this.setTablesTotal(tablecount);
        this.setTablesStartnum(startnum);
        this.setPlayersMin(playermin);
        this.setPlayersMax(playermax);
        this.initialize();
    }
   
    private void initialize(){
       int number_of_tables = this.getTablesTotal();
       this.th_table = new Thread[number_of_tables];
       for(int i=0; i<number_of_tables; i++){
           this.th_table[i]= new Thread(new PokerTable());  
           this.th_table[i].setName(Integer.toString(i+1));
       }
    }
    
    public void start(){
        int tablesTotal = this.getTablesTotal();
        for(int i=0; i<tablesTotal; i++){
           this.th_table[i].start();
        }
    }
    
    public void stop(){
        int tablesTotal = this.getTablesTotal();
        for(int i=0; i<tablesTotal; i++){
           //this.th_table[i].start();
        }
    }
    
    /**
     * @return the tables_total
     */
    public int getTablesTotal() {
        return tables_total;
    }

    /**
     * @param tables_total the tables_total to set
     */
    private void setTablesTotal(int tables_total) {
        this.tables_total = tables_total;
    }

    /**
     * @return the tables_startnum
     */
    public int getTablesStartnum() {
        return tables_startnum;
    }

    /**
     * @param tables_startnum the tables_startnum to set
     */
    private void setTablesStartnum(int tables_startnum) {
        this.tables_startnum = tables_startnum;
    }

    /**
     * @return the players_min
     */
    public int getPlayersMin() {
        return players_min;
    }

    /**
     * @param players_min the players_min to set
     */
    private void setPlayersMin(int players_min) {
        this.players_min = players_min;
    }

    /**
     * @return the players_max10
     */
    public int getPlayersMax() {
        return players_max;
    }

    /**10
     * @param players_max the players_max to set
     */
    private void setPlayersMax(int players_max) {
        this.players_max = players_max;
    }
           
    
}
