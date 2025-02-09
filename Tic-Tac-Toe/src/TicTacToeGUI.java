import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class TicTacToeGUI extends JFrame{
    // frame and panel sizes
    int windowWidth = 800, windowHeight = 600;
    int scoreWidth = 250, scoreHeight = 600;

    // label, panels, and button variables
    JPanel statusPanel, boardPanel;
    JLabel scoreTitle, xScore, oScore, turnTitle, playerTurn, winner, draw;
    JButton [][]button = new JButton[3][3];
    JButton retryButton;

    // player variable
    String currentPlayer = "X";
    int xPoints = 0, oPoints = 0;
    int turn = 0;

    // winner flag
    boolean winnerDisplayed = false;

    public TicTacToeGUI(){
        this.setTitle("Tic-Tac-Toe");
        this.setSize(windowWidth, windowHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        addPanels();
        createBoard();
    }

    private void addPanels(){
        // contains game status (player's turn and score)
        statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
        statusPanel.setBackground(new Color(0x363638));
        statusPanel.setPreferredSize(new Dimension(scoreWidth, scoreHeight));
        this.add(statusPanel, BorderLayout.EAST);

        // score text label
        scoreTitle = new JLabel("Score");
        scoreTitle.setFont(new Font("Arial", Font.BOLD, 40));
        scoreTitle.setForeground(Color.WHITE);
        scoreTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusPanel.add(scoreTitle);
        statusPanel.add(Box.createVerticalStrut(15));

        // player X score status
        xScore = new JLabel("Player X - " + xPoints);
        xScore.setFont(new Font("Arial", Font.BOLD, 30));
        xScore.setForeground(new Color(0xd9702f));
        xScore.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusPanel.add(xScore);
        statusPanel.add(Box.createVerticalStrut(10));

        // player O score status
        oScore = new JLabel("Player O - " + oPoints);
        oScore.setFont(new Font("Arial", Font.BOLD, 30));
        oScore.setForeground(new Color(0x367576));
        oScore.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusPanel.add(oScore);
        statusPanel.add(Box.createVerticalStrut(50));

        // players turn status
        turnTitle = new JLabel("Turn");
        turnTitle.setFont(new Font("Arial", Font.BOLD, 40));
        turnTitle.setForeground(Color.WHITE);
        turnTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusPanel.add(turnTitle);
        statusPanel.add(Box.createVerticalStrut(15));

        playerTurn = new JLabel("Player " + currentPlayer + "'s turn");
        playerTurn.setFont(new Font("Arial", Font.BOLD, 25));
        playerTurn.setForeground(Color.GREEN);
        playerTurn.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusPanel.add(playerTurn);

        // game board (3 x 3 grid)
        boardPanel = new JPanel(new GridLayout(3, 3));
        boardPanel.setBackground(new Color(0x3b3b3d));
        this.add(boardPanel);

    }

    private void createBoard(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                JButton tile = new JButton();
                tile.setBackground(new Color(0x3b3b3d));
                tile.setFont(new Font("Fira Code", Font.BOLD, 130));
                tile.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                tile.setFocusable(false);

                button[i][j] = tile;
                boardPanel.add(tile);

                tile.addActionListener(e -> {
                    if(winnerDisplayed) return;

                    JButton tileClicked = (JButton) e.getSource();
                    if(Objects.equals(tileClicked.getText(), "")) {
                        if(currentPlayer.equals("X")){
                            tileClicked.setForeground(new Color(0xd9702f));
                        }
                        else{
                            tileClicked.setForeground(new Color(0x367576));
                        }
                        tileClicked.setText(currentPlayer);
                        turn++;
                        if(!isGameOver()){
                            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
                            playerTurn.setText("Player " + currentPlayer + "'s turn");
                            System.out.println(turn);
                        }
                        else{
                            winnerPlayer(currentPlayer);
                            winnerDisplayed = true;
                            retry();
                            currentPlayer = "X";
                            return;
                        }
                        if(turn == 9){
                            isDraw();
                            winnerDisplayed = true;
                            retry();
                        }

                    }

                });
            }
        }
    }

    private boolean isGameOver(){
        // check horizontal
        for(int i = 0; i < 3; i++){
            if(Objects.equals(button[i][0].getText(), currentPlayer)
                    && Objects.equals(button[i][1].getText(), currentPlayer)
                    && Objects.equals(button[i][2].getText(), currentPlayer)){
                button[i][0].setBackground(new Color(0xe5d76e));
                button[i][1].setBackground(new Color(0xe5d76e));
                button[i][2].setBackground(new Color(0xe5d76e));
                return true;
            }
        }

        // check vertical
        for(int i = 0; i < 3; i++){
            if(Objects.equals(button[0][i].getText(), currentPlayer)
                    && Objects.equals(button[1][i].getText(), currentPlayer)
                    && Objects.equals(button[2][i].getText(), currentPlayer)){
                button[0][i].setBackground(new Color(0xe5d76e));
                button[1][i].setBackground(new Color(0xe5d76e));
                button[2][i].setBackground(new Color(0xe5d76e));
                return true;
            }
        }

        // check diagonal
        if(Objects.equals(button[0][2].getText(), currentPlayer)
                && Objects.equals(button[1][1].getText(), currentPlayer)
                && Objects.equals(button[2][0].getText(), currentPlayer)){
            button[0][2].setBackground(new Color(0xe5d76e));
            button[1][1].setBackground(new Color(0xe5d76e));
            button[2][0].setBackground(new Color(0xe5d76e));
            return true;
        }
        if(Objects.equals(button[0][0].getText(), currentPlayer)
                && Objects.equals(button[1][1].getText(), currentPlayer)
                && Objects.equals(button[2][2].getText(), currentPlayer)){
            button[0][0].setBackground(new Color(0xe5d76e));
            button[1][1].setBackground(new Color(0xe5d76e));
            button[2][2].setBackground(new Color(0xe5d76e));
            return true;
        }

        return false;
    }

    private void winnerPlayer(String currentPlayer){
        // to avoid displaying winner multiple times
        if(winnerDisplayed) return;

        // displays the winner
        statusPanel.add(Box.createVerticalStrut(50));
        winner = new JLabel("Player " + currentPlayer + " wins!");
        winner.setFont(new Font("Arial", Font.BOLD, 30));
        winner.setForeground(Color.YELLOW);
        winner.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusPanel.add(winner);
        statusPanel.add(Box.createVerticalStrut(30));

        // add points to the winner
        if(currentPlayer.equals("X")){
            xPoints++;
            xScore.setText("Player X - " + xPoints);
        }
        else{
            oPoints++;
            oScore.setText("Player O - " + oPoints);
        }
    }

    private void isDraw(){
        statusPanel.add(Box.createVerticalStrut(50));
        draw = new JLabel("Draw!");
        draw.setFont(new Font("Arial", Font.BOLD, 30));
        draw.setForeground(Color.YELLOW);
        draw.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusPanel.add(draw);
        statusPanel.add(Box.createVerticalStrut(30));
    }

    private void retry(){
        retryButton = new JButton("Play again");
        retryButton.setFont(new Font("Arial", Font.BOLD, 20));
        retryButton.setMaximumSize(new Dimension(150, 50));
        retryButton.setBackground(new Color(0x3b3b3d));
        retryButton.setForeground(Color.WHITE);
        retryButton.setBorder(BorderFactory.createLineBorder(null));
        retryButton.setFocusable(false);
        retryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        retryButton.addActionListener(e -> {
            resetGame();
        });
        statusPanel.add(retryButton);
    }

    private void resetGame(){
        // reset the board
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                button[i][j].setText("");
                button[i][j].setBackground(new Color(0x3b3b3d));
            }
        }
        turn = 0;
        if (winner != null) {
            statusPanel.remove(winner);
            winner = null;
        }
        if (draw != null) {
            statusPanel.remove(draw);
            draw = null;
        }
        playerTurn.setText("Player X's turn");
        statusPanel.remove(retryButton);
        statusPanel.repaint();
        statusPanel.revalidate();
        winnerDisplayed = false;

        // removes all added vertical struts below playerTurn label
        boolean removeStruts = false;
        Component[] components = statusPanel.getComponents();
        for (Component component : components) {
            if (component == playerTurn) {
                removeStruts = true;
            }
            else if (removeStruts && component instanceof Box.Filler) {
                statusPanel.remove(component);
            }
        }
    }

}
