package com.jttech.demo.SpringBatchEncryptText;

/**
 * Implementation of CaesarCipher, encrypt is positive shift, decrypt is negative shift.
 * 
 * 
 * @author Jun Li
 *
 */
public class CaesarCipher {

    public static final int ALPHABET_SIZE = 26;

    public static String encrypt(String plaintext, int shift) {
        return caesarCipher(plaintext, shift, true);
    }

    public static String decrypt(String ciphertext, int shift) {
        return caesarCipher(ciphertext, shift, false);
    }

    private static String caesarCipher(String input, int shift, boolean encrypt) {

        StringBuilder output = new StringBuilder(input.length());

        for (int i = 0; i < input.length(); i++) {
            char inputChar = input.charAt(i);
            
            // treat decrypt as shift backwards
            int calculatedShift = (encrypt) ? shift : (ALPHABET_SIZE - shift);

            char startOfAlphabet;
            if (Character.isLowerCase(inputChar)) {    
                startOfAlphabet = 'a';
            } else if (Character.isUpperCase(inputChar)) {
                startOfAlphabet = 'A';
            } else {                
                // retain all other characters
                output.append(inputChar);
                continue;
            }                
                
            int inputCharIndex = inputChar - startOfAlphabet;

            // cipher / decipher operation
            int outputCharIndex = (inputCharIndex + calculatedShift) % ALPHABET_SIZE;

            char outputChar = (char) (outputCharIndex + startOfAlphabet);

            output.append(outputChar);
        }

        return output.toString();
    }
}
