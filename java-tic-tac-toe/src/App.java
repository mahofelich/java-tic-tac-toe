
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class App {
    private static boolean playerturn = true;  //True = X's turn False = O's turn
    private static String[][] board = new String[3][3];  //Game state
    private static int moves = 0;  //Number of moves made

    public static void main(String[] args) throws Exception {
        //Set up window
        JFrame frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setResizable(false);

        //Set X and O icons for buttons
        ImageIcon xIcon = new ImageIcon(App.class.getResource("/icons/XIcon.png"));
        ImageIcon oIcon = new ImageIcon(App.class.getResource("/icons/OIcon.png"));
        Image scaledXImage = xIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        Image scaledOImage = oIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon xIcon1 = new ImageIcon(scaledXImage);
        ImageIcon oIcon1 = new ImageIcon(scaledOImage);

        //Set up grid and action listener
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));

        //Create buttons and add action listeners
        JButton[][] buttons = new JButton[3][3];  //Store buttons for easier reference
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton();
                final int r = row;  
                final int c = col;
                
                buttons[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (playerturn) {
                            buttons[r][c].setIcon(xIcon1);
                            board[r][c] = "X";
                        } else {
                            buttons[r][c].setIcon(oIcon1);
                            board[r][c] = "O";
                        }

                        buttons[r][c].setEnabled(false);
                        playerturn = !playerturn;
                        moves++;

                        //Check for a winner or tie after each move
                        String winner = checkWinner();
                        if (winner != null) {
                            JOptionPane.showMessageDialog(frame, winner + " wins!");
                            resetGame(buttons);
                        } else if (moves == 9) {
                            JOptionPane.showMessageDialog(frame, "It's a tie!");
                            resetGame(buttons);
                        }
                    }
                });
                panel.add(buttons[row][col]);
            }
        }

        frame.add(panel);

        //Deploy window
        frame.setVisible(true);
    }

    //Check for a winner
    private static String checkWinner() {
        //Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != null && board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2])) {
                return board[i][0];  // Return the winner ("X" or "O")
            }
            if (board[0][i] != null && board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i])) {
                return board[0][i];
            }
        }
        //Check diagonals
        if (board[0][0] != null && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2])) {
            return board[0][0];
        }
        if (board[0][2] != null && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0])) {
            return board[0][2];
        }
        //No winner yet
        return null;
    }

    //Reset the game
    private static void resetGame(JButton[][] buttons) {
        playerturn = true;
        moves = 0;
        board = new String[3][3];  // Reset the board

        //Enable all buttons and clear their icons
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setEnabled(true);
                buttons[row][col].setIcon(null);
            }
        }
    }
}