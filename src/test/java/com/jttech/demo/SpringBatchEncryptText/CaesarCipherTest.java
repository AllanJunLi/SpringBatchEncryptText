package com.jttech.demo.SpringBatchEncryptText;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CaesarCipherTest {
	
	@Test
    public void shouldDoNothingWithEmptyString() {
        assertEquals("", CaesarCipher.encrypt("", 3));
    }
 
    @Test
    public void shouldNotCipherSymbols() {
        assertEquals("-", CaesarCipher.encrypt("-", 3));
 
        String symbols = "1!@#$%^&*(){}/";
        assertEquals(symbols, CaesarCipher.encrypt(symbols, 3));
    }
 
    @Test
    public void shouldCipherLowerCaseLetter() {
        assertEquals("a", CaesarCipher.encrypt("a", 0));
 
        assertEquals("b", CaesarCipher.encrypt("a", 1));
        assertEquals("d", CaesarCipher.encrypt("a", 3));
        assertEquals("j", CaesarCipher.encrypt("e", 5));
 
        assertEquals("a", CaesarCipher.encrypt("z", 1));
        assertEquals("c", CaesarCipher.encrypt("x", 5));
    }
 
    @Test
    public void shouldCipherUpperCaseLetter() {
        assertEquals("A", CaesarCipher.encrypt("A", 0));
 
        assertEquals("B", CaesarCipher.encrypt("A", 1));
        assertEquals("D", CaesarCipher.encrypt("A", 3));
        assertEquals("J", CaesarCipher.encrypt("E", 5));
 
        assertEquals("A", CaesarCipher.encrypt("Z", 1));
        assertEquals("C", CaesarCipher.encrypt("X", 5));
    }
 
    @Test
    public void shouldCipherWholeAlphabet() {
        String allChars = "Pack my box with five dozen liquor jugs";
        assertEquals("Vgiq se hud cozn lobk jufkt rowaux pamy", CaesarCipher.encrypt(allChars, 6));
    }
 
    @Test
    public void shouldCycle() {
        assertEquals("aoeu-snth", CaesarCipher.encrypt("aoeu-snth", 52));
        assertEquals("cqgw-upvj", CaesarCipher.encrypt("aoeu-snth", 54));
    }
 
    @Test
    public void shouldDecipher() {
        String s = "aoeu"; 
        String encrypted = CaesarCipher.encrypt(s, 2);
        assertEquals(s, CaesarCipher.decrypt(encrypted, 2));
    }	
}
