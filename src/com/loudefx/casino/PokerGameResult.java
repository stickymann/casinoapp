/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loudefx.casino;

/**
 *
 * @author sandra
 */
public class PokerGameResult {
    private String game_id;
    private String table_id;
    private int player_total;
    private int playtime;
    private String result;
    private String winner;
    private String winning_hand;
    private String input_time;

    /**
     * @return the game_id
     */
    public String getGameId() {
        return game_id;
    }

    /**
     * @param game_id the game_id to set
     */
    public void setGameId(String game_id) {
        this.game_id = game_id;
    }

    /**
     * @return the table_id
     */
    public String getTableId() {
        return table_id;
    }

    /**
     * @param table_id the table_id to set
     */
    public void setTableId(String table_id) {
        this.table_id = table_id;
    }

    /**
     * @return the player_total
     */
    public int getPlayerTotal() {
        return player_total;
    }

    /**
     * @param player_total the player_total to set
     */
    public void setPlayerTotal(int player_total) {
        this.player_total = player_total;
    }

    /**
     * @return the playtime
     */
    public int getPlayTime() {
        return playtime;
    }

    /**
     * @param playtime the playtime to set
     */
    public void setPlayTime(int playtime) {
        this.playtime = playtime;
    }

    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * @return the winner
     */
    public String getWinner() {
        return winner;
    }

    /**
     * @param winner the winner to set
     */
    public void setWinner(String winner) {
        this.winner = winner;
    }

    /**
     * @return the winning_hand
     */
    public String getWinningHand() {
        return winning_hand;
    }

    /**
     * @param winning_hand the winning_hand to set
     */
    public void setWinningHand(String winning_hand) {
        this.winning_hand = winning_hand;
    }

    /**
     * @return the input_time
     */
    public String getInputTime() {
        return input_time;
    }

    /**
     * @param input_time the input_time to set
     */
    public void setInputTime(String input_time) {
        this.input_time = input_time;
    }
}
