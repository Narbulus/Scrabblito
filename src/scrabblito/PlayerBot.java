/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabblito;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Bertha
 */
public class PlayerBot extends Player {
    
    public PlayerBot(char[] tiles, Board board) {
        super(tiles, board);
    }

    @Override
    public Move getMove() {
        List<String> tileSet = new ArrayList<String>();
        
        for (char c : tiles)
            tileSet.add(c + "");
        
        Set<List<String>> powerTiles = Game.powerSet(tileSet);
        
        long start = System.currentTimeMillis();
        for (int row = 0; row < board.getBoardSize(); row++) {
            List<String> chunks = getHorizontalChunks(row);
            Set<List<String>> powerChunks = Game.powerSet(chunks);
            List<String> tempList = new ArrayList<String>();
            List<String> playableWords = new ArrayList<String>();
            // Go through each chunk
            for (List<String> cList : powerChunks) {
                // Ignore the empty set in powersets
                if (!cList.isEmpty()) {
                    for (List<String> tList : powerTiles) {
                        // Ignore the tile empty set
                        if (!tList.isEmpty()) {
                            // Make every combination of chunk and tile-subset
                            tempList.clear();
                            tempList.addAll(cList);
                            tempList.addAll(tList);
                            // Create a key from each combo and lookup viable words
                            String key = Game.setToKey(tempList);
                            Set<String> words = Game.getWords(key);
                            if (words != null) {
                                // Make sure the possible words contain the chunks in-order
                                for (String word : words) {
                                    boolean valid = true;
                                    for (String chunk : cList) {
                                        if (!word.contains(chunk)) {
                                            valid = false;
                                            break;
                                        }
                                    }
                                    // Check that the word will fit
                                    if (valid) {
                                        playableWords.add(word);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println(playableWords.toString());
        }
        System.out.println(System.currentTimeMillis() - start + " ms");
        return null;
    }
    
    private Move getViableMove(List<String> chunks) {
        return null;
    }
    
    public List<String> getHorizontalChunks(int row) {
        return getChunks(row, true);
    }
    
    public List<String> getVerticalChunks(int col) {
        return getChunks(col, false);
    }
    
    public List<String> getChunks(int i, boolean horizontal) {
        List<String> chunks = new ArrayList<String>();
        int pos = 0;
        String chunk = "";
        while (pos < board.getBoardSize()) {
            char c;
            if (horizontal)
                c = board.getTile(pos, i);
            else
                c = board.getTile(i, pos);
            
            if (c == '-') {
                if (!chunk.isEmpty()) {
                    chunks.add(chunk);
                    chunk = "";
                }
            } else {
                chunk += c;
            }
            pos++;
        }
        if (!chunk.isEmpty())
            chunks.add(chunk);
        return chunks;
    }
    
}
