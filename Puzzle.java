package com.sample;

import java.util.ArrayList;

public class Puzzle {
    /*
    This class represents a word scramble puzzle.
     */
    private String puzzle;
    private int answer_key;

    public Puzzle(String puzzle) {
        this.puzzle = puzzle;
    }

    public Puzzle(String aScrambledString, int answer_hash_code){
        puzzle = aScrambledString;
        answer_key = answer_hash_code;

        System.out.println("PUZZLE: " + this.puzzle);
        System.out.println("PUZZLE HASH CODE " + this.hashCode());
    }

    public String getPuzzleString(){return puzzle;}

    public ArrayList<String> getPuzzleWords(){
        ArrayList<String> wordList = new ArrayList<>();
        for(String word : puzzle.split(" ")){
            wordList.add(word);
        }
        return wordList;

    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Puzzle)) return false;
        return this.getPuzzleString().equals(((Puzzle) obj).getPuzzleString());
    }
    public int hashCode(){
        int sum = 0;
        for(int i=0; i<puzzle.length(); i++){
            sum += (i+1)*puzzle.charAt(i);
        }
        return sum;
    }

    //each puzzle have sentence


    public static Puzzle puzzle1(){

        int num[] = {
                97,32,115,111,32,104,97,100,32,104,101,32,109,105,110,100,32,111,104,32,99,97,110,39,
                116,32,97,110,100,32,97,32,106,117,115,116,32,104,105,115,32,104,105,115,32,102,114,111,
                109,32,119,104,111,32,98,114,111,107,101,32,110,111,119,32,109,97,110,32,112,108,117,99,
                107,32,98,117,116,32,115,111,110,103,115,32,105,116,32,84,104,101,114,101,32,110,101,118,
                101,114,32,78,97,110,116,117,99,107,101,116,32,119,111,117,108,100,32,115,116,114,105,
                110,103,115,32,97,108,108,32,104,101,32,119,97,115,32,121,111,117,110,103,32,103,117,
                105,116,97,114,32,115,97,105,100,32,104,101,32,115,105,110,103,32

        };
        String words = getSentence(num);



        return new Puzzle(words,
                1161819);
    }
    public static Puzzle puzzle2(){

        int num[] = {

                97,32,97,110,100,32,97,119,97,114,101,32,105,116,32,73,39,109,32,73,
                39,109,32,111,102,32,112,111,101,116,32
        };

        String words = getSentence(num);



        return new Puzzle(words,
                39731);
    }

    public static Puzzle puzzle3(){


        int num[] = {

                89,111,117,32,108,105,118,101,32,111,110,99,101,32,97,
                110,100,32,111,110,99,101,32,105,115,32,101,110,111,117,
                103,104,44,32,105,102,32,121,111,117,32,100,111,32,105,116,32,114,105,103,104,116,32
        };

        String words = getSentence(num);



        int hashCode = words.hashCode();
        return new Puzzle(words, hashCode);
    }


    //method to re-construct the actual sentence from the code
    public static String getSentence(int[] num){

        String actualString="";

        String str =null;
        for(int i: num){
            str = Character.toString((char)i);
            actualString+=str;
        }
        return actualString;
    }
}