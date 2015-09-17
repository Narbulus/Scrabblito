/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabblito;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import static scrabblito.Game.WORD_PATH;

/**
 *
 * @author Bertha
 */
public class Board {
    
    private static final int BOARD_SIZE = 15;
    private char[][] board;
    private int[][] mult;
    
    public Board() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        mult = new int[BOARD_SIZE][BOARD_SIZE];
    }
    
    public void loadBoardFromFile(String filePath) {
        try {
            File f = new File(filePath);
            Scanner boardScan = new Scanner(f);
            int l = 0;
            while (boardScan.hasNextLine()) {
                String line = boardScan.nextLine().trim();
                for (int i = 0; i < BOARD_SIZE; i++) {
                    board[i][l] = line.charAt(i);
                }
                l++;
            }
            boardScan.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error loading board");
        }
    }
    
    public boolean isTileEmpty(int x, int y) {
        char tile = getTile(x, y);
        return (tile == ' ');
    }
    
    public boolean placeTile(char tile, int x, int y) {
        if (isTileEmpty(x, y) && inBounds(x, y)) {
            board[x][y] = tile;
            return true;
        }
        return false;
    }
    
    public char getTile(int x, int y) {
        if (inBounds(x, y)) {
            return board[x][y];
        }
        return '-';
    }
    
    public boolean playMove(Move move) {
        int curX = move.x;
        int curY = move.y;
        for (char c : move.letters) {
            // Do fast bounds checking before more expensive stuff
            if (move.horizontal && curX + move.letters.length >= BOARD_SIZE)
                return false;
            if (!move.horizontal && curY + move.letters.length >= BOARD_SIZE)
                return false;
            // Move in move.dir till we hit an empty tile
            while (!isTileEmpty(curX, curY)) {
                curX += move.horizontal ? 1 : 0;
                curY += move.horizontal ? 0 : 1;
            }
            // Impossible tile place
            if (!placeTile(c, curX, curY)) {
                System.out.println("Bad tile place, your bad");
                return false;
            }
        }
        return true;
    }
    
    public int getMult(int x, int y) {
        if (inBounds(x, y)) {
            return mult[x][y];
        }
        return -1;
    }
    
    public boolean inBounds(int x, int y) {
        return (x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE);
    }
    
    public int getBoardSize() {
        return BOARD_SIZE;
    }
    
}
