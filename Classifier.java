package com.javaml.ml;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.io.File;
import java.util.stream.Collectors;

import weka.core.Stopwords;
import weka.core.pmml.Array;
import weka.core.stemmers.SnowballStemmer;
import weka.core.stemmers.Stemmer;

import static java.util.Collections.reverseOrder;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class scratch_14 {

    public static void main(String[] args) throws Exception {

        //BufferedReader filereader = new BufferedReader(new FileReader("spam-train-set.txt"));

        //String filename = filereader.readLine();
        //int count = 0; // count number of words in a mail

        //while(filename != null)
        //{
        //String path = "C:\\Users\\Lenovo\\Desktop\\JavaML\\spam-train\\"+filename;
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\dishant\\Desktop\\spam_master.txt"));
        Map<String, Integer> frequency = new LinkedHashMap<>();

        String line = reader.readLine();

        Functions f=new Functions(); //Object of the Functions class to implement any preprocessing tasks.
        line = f.stemming_string(line);
        line = f.remove_marks(line);  //Return a line without any marks (specified in the 'or' condition)
        while(line != null) {
            //System.out.println("Processing line: " + line);

            String [] words = line.split(" ");

            for(String word : words) {
                if(word == null || word.trim().equals("")) {
                    continue;
                }
                String processed = word.toLowerCase();
                processed = processed.replace(",", "");

                if(frequency.containsKey(processed)) {
                    frequency.put(processed,frequency.get(processed) + 1);
                } else {
                    frequency.put(processed, 1);
                }
            }
            line = reader.readLine();
        }
        //=======================================
        BufferedReader reader1 = new BufferedReader(new FileReader("C:\\Users\\dishant\\Desktop\\spmsga11.txt"));
        Map<String, Integer> frequency1 = new LinkedHashMap<>();

        String line1 = reader1.readLine();

        Functions f1=new Functions(); //Object of the Functions class to implement any preprocessing tasks.
        line1 = f1.stemming_string(line1);
        line1 = f1.remove_marks(line1);  //Return a line without any marks (specified in the 'or' condition)
        while(line1 != null) {
            //System.out.println("Processing line: " + line);

            String [] words = line1.split(" ");

            for(String word : words) {
                if(word == null || word.trim().equals("")) {
                    continue;
                }
                String processed1 = word.toLowerCase();
                processed1 = processed1.replace(",", "");

                if(frequency1.containsKey(processed1)) {
                    frequency1.put(processed1,
                            frequency1.get(processed1) + 1);
                } else {
                    frequency1.put(processed1, 1);
                }
            }
            line1 = reader.readLine();
        }
        //=======================================
        f1.stopwords_removal(frequency1);
        System.out.println(frequency1);
        System.out.println("Important words");
        String[] Imp_Words = f.important_word(frequency);
        f1.classifier(frequency1,Imp_Words);
    }
}


class Functions{
    void stopwords_removal(Map<String,Integer> frequency){
        for(int pos=0;pos<frequency.size();pos++) {
            String response = (String) (frequency.keySet().toArray())[pos];
            if(Stopwords.isStopword(response)){
                frequency.remove(response);
            }
        }
    }

    String remove_marks(String line){
        StringBuilder str=new StringBuilder(line);  //String builder was used because, I wanted to use setCharAt()
        for(int i=0;i<line.length();i++) {
            char r = line.toCharArray()[i];
            if (r == '?' || r == '!' || r == '/' || r == '@') {
                str.setCharAt(i,' ');
            }
        }
        return str.toString();
    }
    String stemming_string(String line) {
        Stemmer stem = new SnowballStemmer(line); //Snowball is a type of stemming

        line = stem.stem(line);  //In-built function for stemming

        return line;
    }
    String[] important_word(Map<String,Integer> frequency) {
        System.out.println("Important words started");
        String[] imp_words = new String[61];
        int k=0;
        for (Map.Entry<String, Integer> entry : frequency.entrySet()) {
            if (entry.getValue() > 200) {
                imp_words[k] = entry.getKey();
                k++;
            }
        }
        System.out.println("Important words done!");
        return imp_words;
    }

    void classifier(Map<String,Integer> frequency,String[] Imp_words){
        String[] file_words = new String[frequency.size()];
        for(int i=0;i<frequency.size();i++){
            for(int j=0;j<Imp_words.length;j++){
                if(file_words[i].equals(Imp_words[j])) {
                    System.out.println("The file is a Spam");
                    break;
                }
                System.out.println(file_words[i]+" Checked!");
            }
        }
    }
}
