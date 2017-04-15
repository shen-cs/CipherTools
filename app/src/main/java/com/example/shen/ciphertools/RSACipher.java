package com.example.shen.ciphertools;

/**
 * Write a description of RSACipher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class RSACipher {
    private long n;
    private long e;
    public RSACipher(long n, long e) {
        this.n = n;
        this.e = e;
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
  
    private String encryptNum(long m) {
           String exp = dec2bin(e);
           long c = 1;
           long power = m;
           for(int i = exp.length() - 1; i >= 0; i--) {
              if(exp.charAt(i) == '1') {
                  c = (c * power) % n;
              }
              power = ((power % n) * (power % n)) % n;
           }
           return c + "";
    }
    
    public String encryptMessage(String message) {
        long m;
        if (message.length() < 2) {
            if (message.isEmpty()) {
                return "";
            }
            m = ((long) message.charAt(0)) << 8;
            return encryptNum(m) + "\n";
        }
        m = (((long) message.charAt(0)) << 8) + (long) message.charAt(1);
        return encryptNum(m) + "\n" + encryptMessage(message.substring(2, message.length()));
    }
}
