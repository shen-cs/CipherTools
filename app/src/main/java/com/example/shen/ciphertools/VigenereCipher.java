package com.example.shen.ciphertools;

import java.lang.reflect.Array;
import java.util.*;

public class VigenereCipher {
    CaesarCipher[] ciphers;
    
    public VigenereCipher(int[] key) {
        ciphers = new CaesarCipher[key.length];
        for (int i = 0; i < key.length; i++) {
            ciphers[i] = new CaesarCipher(key[i]);
        }
    }
    public VigenereCipher(String key) {
        String alph = "abcdefghijklmnopqrstuvwxyz";
        key = key.toLowerCase();
        int[] keys = new int[key.length()];
        for(int i = 0; i < keys.length; i++) {
            keys[i] = alph.indexOf(key.charAt(i));
        }
        ciphers = new CaesarCipher[keys.length];
        for(int j = 0; j < keys.length; j++) {
            CaesarCipher cc = new CaesarCipher(keys[j]);
            Array.set(ciphers, j, cc);
        }
    }
    
    public String encrypt(String input) {
        StringBuilder answer = new StringBuilder();
        int i = 0;
        for (char c : input.toCharArray()) {
            int cipherIndex = i % ciphers.length;
            CaesarCipher thisCipher = ciphers[cipherIndex];
            answer.append(thisCipher.encryptLetter(c));
            i++;
        }
        return answer.toString();
    }
    
    public String decrypt(String input) {
        StringBuilder answer = new StringBuilder();
        int i = 0;
        for (char c : input.toCharArray()) {
            int cipherIndex = i % ciphers.length;
            CaesarCipher thisCipher = ciphers[cipherIndex];
            answer.append(thisCipher.decryptLetter(c));
            i++;
        }
        return answer.toString();
    }
    
    public String toString() {
        return Arrays.toString(ciphers);
    }
    
}
