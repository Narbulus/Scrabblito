/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabblito;

/**
 *
 * @author Bertha
 */
public abstract class Player {
    
    protected char[] tiles;
    protected Board board;
    
    public Player(char[] tiles, Board board) {
        this.tiles = tiles;
        this.board = board;
    }
       
    public abstract Move getMove();
    
}
