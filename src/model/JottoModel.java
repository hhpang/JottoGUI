package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * JottoModel connects to the server through the makeGuess method, to play Jotto and also activate the JottoGUI. 
 * 
 * Thread-safety argument:
 * Each makeGuess call creates a single thread, with its corresponding puzzle number and guessed word. 
 * Therefore, successive guesses should not conflict with or override any others. Also, Swing is single-threaded
 * and not thread-safe, but at any one moment, the counter of each guess keeps track of it in the GUI, 
 * so even multiple guesses will not interleave because each guess will be allotted a row in the table.  
 */
public class JottoModel {
    
    /**
     * Connects to server and sends guess, given a puzzle number and five-letter word.
     * Request returns "guess x y"  
     * x is the number of letters the two words have in common 
     * y is the number of letters that are in the correct position 
     * If request has an error, the following appropriate error is returned 
     *  error 0: Ill-formatted request.
     *  error 1: Non-number puzzle ID.
     *  error 2: Invalid guess. Length of guess != 5 or guess is not a dictionary word.
     * 
     * @param puzzleNumber the number in the URL corresponding to guess
     * @param guess Guessed word 
     * @return String String of all the information the server returns, with guessed word and 
     *         number of letters in common with hidden word and number of letters in correct position 
     * @throws IOException corresponding to the appropriate error 
     */
    public static String makeGuess(int puzzleNumber, String guess) throws IOException {
    	String returnedString = "";
    	
    	URL sendGuess = new URL("http://courses.csail.mit.edu/6.005/jotto.py?puzzle="+puzzleNumber+"&guess="+guess);

    	BufferedReader in = new BufferedReader(
    			new InputStreamReader(sendGuess.openStream()));
    	String line;
    	while ((line = in.readLine()) != null){
    		returnedString += line+"\n\r";
    		
    	}
    	in.close();
    	return returnedString;
    }
    
//    public static void main(String[] args) throws IOException{
//    	 makeGuess(1, "clean");
//    }
}
