package ui;

import static org.junit.Assert.*;

import org.junit.Test;

/* Testing strategy for Prob 2: Setting the Puzzle Number: 
 * 
 * JUnit tests for setting up the GUI are difficult to implement, so most of the tests are done to the GUI. 
 * Testing process on GUI: 
 * 1. invalid inputs: all non-number characters trigger server's error message for non-number ID. All negative or blank
 *    inputs will generate random puzzle ID, a number between 0 and 10,000. 
 * 2. valid inputs: updates puzzle number - any number between 0 - 10,000. 
 */


/* Testing strategy for Prob 3: Making a Guess: 
 * Again, JUnit tests are more difficult to implement, so testing done in GUI. 
 * Testing includes valid inputs, non-valid inputs, and the special placeholder '*' in inputs. 
 * 1. When the input is valid, the correct integer outputs should be returned. This was ensured by testing against
 *    the known puzzle IDs - i.e. input of 'rased' for puzzle ID 8888 returned 'rased You win! The secret word was rased". 
 *    Testing 'based' with puzzle ID 8888, for instance, returns 'based 4 4' as expected. 
 * 
 * 2. Placeholder test: inputs including '*'. Tested '*****', still valid input, returns '***** 0 0' as expected. 
 *    Testing an input with '*' that exceeds 5 characters returns correct error. Inputs with one or more '*' that do not exceed
 *    5 characters still return correct integer responses. 
 *    
 * 3. Invalid inputs: A word that is too long returns error 2 as expected. Just pressing 'enter' with no guess 
 *    returns error 0 as expected. Entering non-dictionary words returns error 2 as expected.
 */



/* Testing strategy for Prob 4: Recording Guess: 
 * Still doing tests on GUI rather than JUnit tests because of difficulty. 
 * Testing included looking that words were correctly listed in the table. 
 * Guessed words showed up immediately - while the numbers returned sometimes could be delayed. Words with '*' still 
 * appeared immediately. 
 * Each time the puzzle button was pressed or a new puzzle ID entered, the table clears. 
 */ 

