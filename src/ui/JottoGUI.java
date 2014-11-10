
package ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.GroupLayout.Group;
import javax.swing.table.DefaultTableModel;

import model.JottoModel;

/**
 * Creates a GUI for players to play the game Jotto. 
 * 
 * Player enters a 5-letter word in the textbox. After pressing 'enter', the guessed word shows up in the GUI.  
 * In addition, the second column displays the number of letters in common with the hidden word.
 * The third column displays the number of letters in the correct position of the hidden word. 
 * If there is an error, the correct error message appears in the second and third columns. 
 * appears in the latter two columns of the table. 
 * If the guess is correct, a message appears to inform the player that it's correct. 
 * If the guess contains a placeholder character "*", all errors and integer reports should still return correctly. 
 * The player can also change the puzzle number by typing in a new number in the textbox next to "New Puzzle". 
 * If this number is not valid, a random puzzle ID is used. 
 * The columns clear when a new puzzle ID is used.  
 */
public class JottoGUI extends JFrame {
    private static int curPuzzleID = -5; //Instantiate a value for puzzle ID so puzzle starts with random ID.  
    private String curGuess = "";
    private static int guesses = 0; 

    private final JButton newPuzzleButton;
    private final JTextField newPuzzleNumber;
    private final JLabel puzzleNumber;
    private final JTextField guess;
    private final JTable guessTable;
    private final JLabel newGuessText;
    
    private final Group horizontal;
    private final Group vertical;

    private final Group rowOne;
    private final Group rowTwo;
    private final Group rowThree;

    private final Group columnOne;
    private final Group columnTwo;
    private final Group columnThree;

    private final DefaultTableModel table;

    public JottoGUI() {
        newPuzzleButton = new JButton();
        newPuzzleButton.setName("newPuzzleButton");
        newPuzzleNumber = new JTextField(60);
        newPuzzleNumber.setName("newPuzzleNumber");
        puzzleNumber = new JLabel();
        puzzleNumber.setName("puzzleNumber");
        newGuessText = new JLabel();
        newGuessText.setName("newGuessText");
        guess = new JTextField(10);
        guess.setName("guess");
        guessTable = new JTable(0,3);
        guessTable.setName("guessTable");
        newPuzzleButton.setText("New Puzzle");

        //Initiate number of guesses to keep track of number of guesses
        //guesses = 0;

        table = (DefaultTableModel) guessTable.getModel();
        
        // Start GUI with random puzzle ID before one is entered
        if(!(curPuzzleID > 0)){
            curPuzzleID = (int) (Math.random()*10000);
            table.setNumRows(1); 
        }
        
        //Text showing current puzzle ID
        puzzleNumber.setText("Puzzle #"+curPuzzleID);

        //Text for textbox
        newGuessText.setText("Type your guess here: ");
        
        Container cp = this.getContentPane();
        GroupLayout layout = new GroupLayout(cp);
        cp.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        //Create vertical components
        columnOne = layout.createParallelGroup(BASELINE);
        columnOne.addComponent(puzzleNumber);
        columnOne.addComponent(newPuzzleButton);
        columnOne.addComponent(newPuzzleNumber);

        columnTwo = layout.createParallelGroup(BASELINE);
        columnTwo.addComponent(newGuessText);
        columnTwo.addComponent(guess);

        columnThree = layout.createParallelGroup(BASELINE);
        columnThree.addComponent(guessTable);

        vertical = layout.createSequentialGroup();
        vertical.addGroup(columnOne);
        vertical.addGroup(columnTwo);
        vertical.addGroup(columnThree);
        layout.setVerticalGroup(vertical);
        
        //Create horizontal components
        rowOne = layout.createSequentialGroup();
        rowOne.addComponent(puzzleNumber);
        rowOne.addComponent(newPuzzleButton);
        rowOne.addComponent(newPuzzleNumber);

        rowTwo = layout.createSequentialGroup();
        rowTwo.addComponent(newGuessText);
        rowTwo.addComponent(guess);

        rowThree = layout.createSequentialGroup();
        rowThree.addComponent(guessTable);

        horizontal = layout.createParallelGroup();
        horizontal.addGroup(rowOne);
        horizontal.addGroup(rowTwo);
        horizontal.addGroup(rowThree);
        layout.setHorizontalGroup(horizontal);

        this.pack();

        //Listener for "New Puzzle" button
        newPuzzleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newPuzzle();              
            };
        });

        //If user presses 'enter' instead of clicking button
        //Listener for this action in the puzzle number text field
        newPuzzleNumber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newPuzzle();          
            };
        });

        //When user presses 'enter' after inputting a guess
        //Listener for guess text field 
        guess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGuess();   
            };
        });
    }

    /**
     * Create a new puzzle number based on random generation or player's input. 
     * If there is no number entered or a negative number, create random puzzle number between 0 and 10,000
     * Otherwise if the player's input is not valid, the server will return correct error message. 
     */
    private void newPuzzle() {
        int inputNum;
        int puzzleNum;
        try{
            inputNum = Integer.parseInt(newPuzzleNumber.getText());
        }catch(Exception e){
            inputNum = -5; 
        }

        //If player had correct input, use it 
        if(newPuzzleNumber.getText() != "" && inputNum > 0){
            puzzleNum = inputNum;
        } else{ 
            // Randomly generate puzzle number otherwise
            puzzleNum = (int) (Math.random()*10000);
        }
        curPuzzleID = puzzleNum; 
        puzzleNumber.setText("Puzzle #"+curPuzzleID);
        newPuzzleNumber.setText("");
        table.setNumRows(1); //Clear the table 
        guesses = 0; //Restart this counter every time a new puzzle ID is chosen 
    }

    /**
     * When a guess is made, print the word in the first column. If guess includes placeholder "*", 
     * keep track of number of characters in guess, ignoring character "*" but all else correct.  
     * Then, print the corresponding numbers in the second and 
     * third columns: respectively, number of shared letters and number of letters in correct position. 
     * If an error occurs, the error message will appear in the second and third columns. 
     * If the guess is the correct word, then "Yay! You win!" appears in the second and third columns. 
     */
    private void newGuess(){
        curGuess = guess.getText();
        guesses++;
        // If guess includes placeholder "*", temporarily replace with character "0" 
        if (curGuess.contains("*")){ 
            curGuess.replace('*', '0'); 
        }
        
        //Get the current guess word, puzzle number, and number of guesses already 
        final String threadGuess = curGuess;
        final int puzzleNumber = curPuzzleID;
        final int threadCount = guesses;
        
        //Create a new row for the new guess
        String [] row = {"","",""};
        table.addRow(row);
       
        //First show the guessed word in the first column 
        table.setValueAt(curGuess, guesses, 0);
        table.setValueAt("", guesses, 1);
        table.setValueAt("", guesses, 2);
        guess.setText("");
        
        // Now send the new thread (the guessed word) to the server 
        Thread thread = new Thread(new Runnable() { 
            public void run() { 
                String received; //This is the info sent back from the server 
                
                try {
                    received = JottoModel.makeGuess(curPuzzleID, curGuess);
                    String numShared = ""; 
                    String numCorrectPos = ""; 
                    
                    //Check whether info received back indicates that guessed word was valid 
                    if(received.contains("guess") && !received.contains("error")){
                        try{
                            // Find the integer in the string representation received 
                            int numSharedAsInt = Integer.parseInt((""+received.charAt(6))); 
                            numShared = ""+numSharedAsInt;

                            int numCorrectPosInt = Integer.parseInt((""+received.charAt(8)));
                            numCorrectPos = ""+numCorrectPosInt;

                            //Check whether there's a correct guess
                            if(numSharedAsInt == 5 && numCorrectPosInt == 5){
                                numShared = "You win!";
                                numCorrectPos = "The secret word was "+curGuess+"!";
                            }
                        } catch(Exception e){
                            numShared = "Oops. Can't get number of shared letters.";
                            numCorrectPos = "Oops. Can't get letters in correct position.";
                        }
                    }  
                    // Catch and print correct error messages from server 
                    else if(received.contains("error 0")){
                        numShared = "Error:";
                        numCorrectPos = "Ill-formatted request.";
                    }else if(received.contains("error 1")){
                        numShared = "Error:";
                        numCorrectPos = "Non-number puzzle ID.";
                    }else if(received.contains("error 2")){
                        numShared = "Error:";
                        numCorrectPos = "Please enter a valid 5-letter dictionary word guess.";
                    }
                    
                    // This is important for thread safety. 
                    // Update values if player is still making guesses to same puzzle
                    if(puzzleNumber == curPuzzleID){
                        table.setValueAt(threadGuess, threadCount, 0);
                        table.setValueAt(numShared, threadCount, 1);
                        table.setValueAt(numCorrectPos, threadCount, 2);
                    }
                    
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        });
        thread.start();
        this.pack();
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JottoGUI main = new JottoGUI();

                main.setVisible(true);
            }
        });
    }
}

