package com.example.shen.ciphertools;
/**
 * Write a description of RSADecipher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import java.lang.*;
public class RSADecipher {
    private long n;
    private long d;
    public RSADecipher(long n, long d) {
        this.n = n;
        this.d = d;
    }
    private String dec2bin(long dec) {
           String bin = "";
           while(dec > 0) {
               if(dec % 2 != 0)  { 
                 bin = "1" + bin;
                }
               else { 
                  bin = "0" + bin;
               }
               dec /= 2;
           }
           return bin;
        }
    
    private long decryptNum(long c) {
           String exp = dec2bin(d);
           long m = 1;
           long power = c;
           for(int i = exp.length() - 1; i >= 0; i--) {
              if(exp.charAt(i) == '1') {
                  m = (m * power) % n;
              }
              power = ((power % n) * (power % n)) % n;
           }
           return m;
    }
    
    public String decryptMessage(String message) {
        String[] textLines = message.split("\n");
        String s = "";
        for(int i = 0; i < textLines.length; i++) {
            int m = (int)decryptNum(Long.parseLong(textLines[i]));
            int l = (m >> 8);
            int r = m - (l << 8);
            if(r != 0) {
               char[] cArr = {(char) l, (char) r};
               s += new String(cArr);
            }
            else {
               char[] carr = {(char) l};
               s += new String(carr);
            }
        }
        return s;
    }

}
