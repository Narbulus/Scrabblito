/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabblito;

import java.util.HashSet;
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
        Set<String> tileSet = new HashSet<String>();
        
        for (char c : tiles)
            tileSet.add(c + "");
        
        Set<Set<String>> powerTiles = Game.powerSet(tileSet);
        
        long start = System.currentTimeMillis();
        for (int row = 0; row < board.getBoardSize(); row++) {
            Set<String> chunks = getHorizontalChunks(row);
            Set<Set<String>> powerChunks = Game.powerSet(chunks);
            Set<String> tempSet = new HashSet<String>();
            Set<String> playableWords = new HashSet<String>();
            // Go through each chunk
            for (Set<String> cSet : powerChunks) {
                // Ignore the empty set in powersets
                if (!cSet.isEmpty()) {
                    for (Set<String> tSet : powerTiles) {
                        // Ignore the tile empty set
                        if (!tSet.isEmpty()) {
                            // Make every combination of chunk and tile-subset
                            tempSet.clear();
                            tempSet.addAll(cSet);
                            tempSet.addAll(tSet);
                            // Create a key from each combo and lookup viable words
                            String key = Game.setToKey(tempSet);
                            Set<String> words = Game.getWords(key);
                            if (words != null) {
                                // Make sure the possible words contain the chunks in-order
                                for (String word : words) {
                                    boolean valid = true;
                                    for (String chunk : cSet) {
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
    
    private Move getViableMove(Set<String> chunks) {
        return null;
    }
    
    public Set<String> getHorizontalChunks(int row) {
        return getChunks(row, true);
    }
    
    public Set<String> getVerticalChunks(int col) {
        return getChunks(col, false);
    }
    
    public Set<String> getChunks(int i, boolean horizontal) {
        Set<String> chunks = new HashSet<String>();
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
