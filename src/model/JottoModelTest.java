package model;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class JottoModelTest {
    
    /**
     * Testing strategy: 
     * Test valid requests and invalid requests. Invalid requests should return 
     * the appropriate error message: ill-formatted request, non-number ID, or invalid guess. 
     * Valid requests should have a correct return of 'in common' and 'correct position' values. 
     */

    @Test
    public void basicGuessTest() throws IOException{
        String test = JottoModel.makeGuess(16952, "clean");
        assertEquals("guess 2 1\n\r", test);
    }
    
    @Test
    public void basicOtherIDTest() throws IOException{
        String test = JottoModel.makeGuess(320, "loved");
        assertEquals("guess 1 0\n\r", test);
    }
    
    @Test
    public void perfectGuessTest() throws IOException{
        String test = JottoModel.makeGuess(8888, "rased");
        assertEquals("guess 5 5\n\r", test);
    }
    
    @Test
    public void emptyStringGuessErrorTest() throws IOException{
        String test = JottoModel.makeGuess(1290,"");
        assertEquals("error 0: Ill-formatted request.\n\r", test);
    }
    
    @Test
    public void guessLengthErrorTest() throws IOException{
        String test = JottoModel.makeGuess(5, "normal");
        assertEquals("error 2: Invalid guess. Length of guess != 5 or guess is not a dictionary word.\n\r", test);
    }
    
    @Test
    public void invadlidIDNegativeTest() throws IOException{
        String test = JottoModel.makeGuess(-2, "jokes");
        assertEquals("error 1: Non-number puzzle ID.\n\r", test);
    }
    
    @Test
    public void invalidIDZeroTest() throws IOException{
        String test = JottoModel.makeGuess(0, "moors");
        assertEquals("error 1: Non-number puzzle ID.\n\r", test);
    }
}
