package com.example.shen.ciphertools;

//import edu.duke.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class VigenereBreaker {
    private String alph;
    public VigenereBreaker() {
       alph = "abcdefghijklmnopqrstuvwxyz";
    }
    private String transformKey(int[] key) {
       StringBuilder message = new StringBuilder();
       for(int idx : key) {
          message.append(alph.charAt(idx)); 
       }
       return message.toString();
    }
    private String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder msge = new StringBuilder(message);
        StringBuilder slice = new StringBuilder();
        for(int i = whichSlice; i < msge.length(); i += totalSlices) {
            slice.append(msge.charAt(i));
        }
        return slice.toString();
    }

    private int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        //WRITE YOUR CODE HERE
        CaesarCracker cb = new CaesarCracker(mostCommon);
        String[] slices = new String[klength];
        for(int i = 0; i < klength; i++) {
           slices[i] = sliceString(encrypted, i, klength);
           key[i] = cb.getKey(slices[i]);
        }
        return key;
    }

   /* private HashSet readDictionary(FileResource fr) {
        HashSet<String> words = new HashSet<String>();
        for(String word : fr.lines()) {
            words.add(word.toLowerCase());
        }
        return words;
    }
    */
    private int countWords(String message, HashSet<String> dictionary) {
        int count = 0;
        for(String word : message.split("\\W")) {
           if(dictionary.contains(word.toLowerCase())) {
              count++;
           }
        }
        return count;
    }
    
    private char mostCommonCharIn(HashSet<String> dictionary) {
        int[] freqs = new int[26];
        int max = 0;
        int idx = 0;
        for(String word : dictionary) {
           for(int i = 0; i < word.length(); i++) {
              word = word.toLowerCase();
              if(Character.isLetter(word.charAt(i)) && alph.indexOf(word.charAt(i)) != -1) {
                 freqs[alph.indexOf(word.charAt(i))]++;
              }
           }
        }
        for(int i = 0; i < freqs.length; i++) {
            if(max < freqs[i]) {
               max = freqs[i];
               idx = i;
            }
        }
        return alph.charAt(idx);
    }
    
    private String breakForAllLanguages(String encrypted, HashMap<String, HashSet<String>> languages) {
        int maxValidWords = 0;
        String rightLang = "";
        for(String lang : languages.keySet()) {
            String tryDecrypt = breakForLanguage(encrypted, languages.get(lang));
            int valids = countWords(tryDecrypt, languages.get(lang));
            if(maxValidWords < valids) {
               maxValidWords = valids;
               rightLang = lang;
            }
        }
        System.out.println("Language: " + rightLang);
        String decrypted = breakForLanguage(encrypted, languages.get(rightLang));
        return decrypted;
    }
    
    private String breakForLanguage(String encrypted, HashSet<String> dictionary) {
        int maxValidWords = 0;
        int[] rightKey = {};
        char mostCommon = mostCommonCharIn(dictionary);
        for(int i = 1; i <= 100; i++) {
           int[] key = tryKeyLength(encrypted, i, mostCommon);
           VigenereCipher vc = new VigenereCipher(key);
           String tryDecrypt = vc.decrypt(encrypted);
           int valids = countWords(tryDecrypt, dictionary);
           if(maxValidWords < valids) {
              maxValidWords = valids;
              rightKey = key;
           }
        }
        System.out.println("Key is " + Arrays.toString(rightKey) + "\n" + transformKey(rightKey));
        System.out.println("Keylength: " + rightKey.length);
        VigenereCipher vc = new VigenereCipher(rightKey);
        return vc.decrypt(encrypted);
    }
    /*public void breakVigenere () {
        //WRITE YOUR CODE HERE
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        HashMap<String, HashSet<String>> languages = new HashMap<String, HashSet<String>>();
        String[] langs = {"Danish", "Dutch", "English", "French", "German", "Italian", "Portuguese", "Spanish"};
        for(String lang : langs) {
           FileResource dic = new FileResource(lang);
           HashSet<String> dictionary = readDictionary(dic);
           languages.put(lang, dictionary);
        }
        String decrypted = breakForAllLanguages(encrypted, languages);
        System.out.println(decrypted);
        System.out.println("------------------------------------------------------");
    }
    */
}
