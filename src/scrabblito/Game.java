/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabblito;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bertha
 */
public class Game {
    
    public static final String WORD_PATH = "src/resources/words.txt";
    private static Map<String, Set<String>> wordMap;
    
    public Game() {
        initWordMap();
        Board board = new Board();
        board.loadBoardFromFile("src/resources/board1.txt");
        char[] tiles = new char[] { 'c', 's', 'h', 'e', 'b', 'm', 'b' };
        Player playerBot = new PlayerBot(tiles, board);
        playerBot.getMove();
    }

    private static void initWordMap() {
        wordMap = new HashMap<String, Set<String>>();
        try {
            File f = new File(WORD_PATH);
            Scanner wordScan = new Scanner(f);
            int i = 0;
            while (wordScan.hasNext()) {
                i++;
                String word = wordScan.next();
                char[] letters = word.toCharArray();
                Arrays.sort(letters);
                String key = new String(letters);
                Set<String> wordPool;
                if (!wordMap.containsKey(key)) {
                    wordPool = new HashSet<String>();
                    wordMap.put(key, wordPool);
                } else {
                    wordPool = wordMap.get(key);
                }
                wordPool.add(word);
            }
            System.out.println(i);
        } catch (FileNotFoundException ex) {
            System.out.println("Error processing words");
        }
    }
    
    public static Set<String> getWords (String key) {
        return wordMap.get(key);
    }
    
    public static <T> Set<List<T>> powerSet(List<T> originalSet) {
        Set<List<T>> lists = new HashSet<List<T>>();
        if (originalSet.isEmpty()) {
            lists.add(new ArrayList<T>());
            return lists;
        }
        List<T> list = new ArrayList<T>(originalSet);
        T head = list.get(0);
        List<T> rest = new ArrayList<T>(list.subList(1, list.size())); 
        for (List<T> aList : powerSet(rest)) {
            List<T> newList = new ArrayList<T>();
            newList.add(head);
            newList.addAll(aList);
            lists.add(newList);
            lists.add(aList);
        }		
        return lists;
    }
    
    public static String setToKey(List<String> set) {
        String key = "";
        for (String s : set)
            key += s;
        char[] letters = key.toCharArray();
        Arrays.sort(letters);
        return new String(letters);
    }
}
