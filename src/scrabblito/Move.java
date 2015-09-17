/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabblito;

/** This is a scrabble move
 *
 * @author Bertha
 */
public class Move {
    
    public int x;
    public int y;
    public char[] letters;
    public boolean horizontal;
    
    public Move(int x, int y, char[] letter, boolean horizontal) {
        this.x = x;
        this.y = y;
        this.letters = letters;
        this.horizontal = horizontal;
    }
    
}
