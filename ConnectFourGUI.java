import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.*;

public class ConnectFourGUI<and> {
    private final String CONFIGFILE = "config.txt";
    private JLabel[][] slots;
    private JFrame Framen;
    private JTextField[] playerScore;
    private ImageIcon[] playerIcon;
    private ImageIcon[] scoreIcon;
    private ImageIcon blank;
    private JLabel nextPlayerIcon;
    private Color scorelines = new Color(128, 64, 0);
    private Color background = new Color(220, 128, 5);
    private String logoIcon;
    private String blankfile;
    private String[] iconFile;
    private String[] scoreIconFile;
    public final int NUMPLAYER = 2;
    public final int NUMROW = 5;
    public final int NUMCOL = 5;
    public int MAXGAME; // number of games needed to win the series
    private final int PieceSize = 70;
    private final int GridW = NUMCOL * PieceSize;
    private final int GridH = NUMROW * PieceSize;
    private final int Scoreboardw = 2 * PieceSize;
    private final int ScoreboardH = GridH;
    private final int LogoH = 2 * PieceSize;
    private final int logow = GridW + Scoreboardw;
    private final int FrameW = (int) (logow * 1.02);
    private final int FrameH = (int) ((LogoH + GridH) * 1.1);

    // Constructor: ConnectFourGUI
    // – intialize variables from config files
    // – initialize the imageIcon array
    // – initialize the slots array
    // – create the main frame
    public ConnectFourGUI() {
        initConfig();
        InitilizeImageIcons();
        initSlots();
        createMainFrame();
    }

    private void initConfig() {
        /*
         * TO DO: initialize the following variables with information read from the
         * config file
         * – MAXGAME
         * – logoIcon
         * – iconFile
         */

        iconFile = new String[NUMPLAYER];
        scoreIconFile = new String[NUMPLAYER];
        try {
            BufferedReader read = new BufferedReader(new FileReader(CONFIGFILE));
            MAXGAME = Integer.parseInt(read.readLine());
            logoIcon = read.readLine();
            iconFile[0] = read.readLine();
            iconFile[1] = read.readLine();
            scoreIconFile[0] = read.readLine();
            scoreIconFile[1] = read.readLine();
            blankfile = read.readLine();
        } catch (IOException io) {
            System.out.println("Not Working");
        }
    }

    // InitilizeImageIcons
    // Initialize playerIcon arrays with graphic files
    private void InitilizeImageIcons() {
        playerIcon = new ImageIcon[NUMPLAYER];
        scoreIcon = new ImageIcon[NUMPLAYER];
        for (int i = 0; i < NUMPLAYER; i++) {
            playerIcon[i] = new ImageIcon(iconFile[i]);
            scoreIcon[i] = new ImageIcon(scoreIconFile[i]);
        }
        blank = new ImageIcon(blankfile);
    }

    // initSlots
    // initialize the array of JLabels
    private void initSlots() {
        slots = new JLabel[NUMROW][NUMCOL];
        for (int i = 0; i < NUMROW; i++) {
            for (int j = 0; j < NUMCOL; j++) {
                slots[i][j] = new JLabel();
                slots[i][j].setPreferredSize(new Dimension(PieceSize, PieceSize));
                slots[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                slots[i][j].setBorder(new LineBorder(scorelines));
            }
        }
    }

    // Create a the grid
    private JPanel CreateGrid() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(GridW, GridH));
        panel.setBackground(background);
        panel.setLayout(new GridLayout(NUMROW, NUMCOL));
        for (int i = 0; i < NUMROW; i++) {
            for (int j = 0; j < NUMCOL; j++) {
                panel.add(slots[i][j]);
            }
        }
        return panel;
    }

    // createInfoPanel
    private JPanel createInfoPanel() {

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(Scoreboardw, ScoreboardH));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(scorelines);

        Font HeaderFont = new Font("Serif", Font.PLAIN, 18);
        Font regularFont = new Font("Serif", Font.BOLD, 16);

        // Create a panel for the scoreboard
        JPanel scorePanel = new JPanel();
        scorePanel.setBackground(scorelines);

        // Create the label to display “SCOREBOARD” heading
        JLabel scoreLabel = new JLabel("SCOREBOARD", JLabel.CENTER);
        scoreLabel.setFont(HeaderFont);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // scoreLabel.setForeground(Color.white);

        // Create JLabels for players
        JLabel[] playerLabel = new JLabel[NUMPLAYER];
        for (int i = 0; i < NUMPLAYER; i++) {
            playerLabel[i] = new JLabel(scoreIcon[i]);
        }
        // Create the array of textfield for players’ score
        playerScore = new JTextField[NUMPLAYER];
        for (int i = 0; i < NUMPLAYER; i++) {
            playerScore[i] = new JTextField();
            playerScore[i].setFont(regularFont);
            playerScore[i].setText("0");
            playerScore[i].setEditable(false);
            playerScore[i].setHorizontalAlignment(JTextField.CENTER);
            playerScore[i].setPreferredSize(new Dimension(Scoreboardw - PieceSize - 10, 30));
            // playerScore[i].setBackground(background);
            playerScore[i].setBackground(scorelines);
        }
        scorePanel.add(scoreLabel);
        for (int i = 0; i < NUMPLAYER; i++) {
            scorePanel.add(playerLabel[i]);
            scorePanel.add(playerScore[i]);
        }
        JPanel nextPanel = new JPanel();
        // nextPanel.setBackground(background);
        nextPanel.setBackground(scorelines);
        // Create the label to display “NEXT TURN” heading
        JLabel nextLabel = new JLabel("NEXT TURN", JLabel.CENTER);
        nextLabel.setFont(HeaderFont);
        nextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // nextLabel.setForeground(Color.white);
        // Create the JLabel for the nextPlayer
        nextPlayerIcon = new JLabel();
        System.out.println(nextPlayerIcon.getAlignmentX());
        nextPlayerIcon.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        nextPlayerIcon.setIcon(scoreIcon[0]);
        nextPanel.add(nextLabel);
        nextPanel.add(nextPlayerIcon);
        panel.add(scorePanel);
        panel.add(nextPanel);
        return panel;
    }

    // createMainFrame
    private void createMainFrame() {
        // Create the frame
        Framen = new JFrame("Connect Four");
        JPanel panel = (JPanel) Framen.getContentPane();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // Create the panel for the logo
        JPanel logoPane = new JPanel();
        logoPane.setPreferredSize(new Dimension(logow, LogoH));
        JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon(logoIcon));
        logoPane.add(logo);
        // Create the bottom Panel which contains the play panel and info Panel
        JPanel bottomPane = new JPanel();
        bottomPane.setLayout(new BoxLayout(bottomPane, BoxLayout.X_AXIS));
        bottomPane.setPreferredSize(new Dimension(GridW + Scoreboardw, GridH));
        bottomPane.add(CreateGrid());
        bottomPane.add(createInfoPanel());
        // Add the logo and bottom panel to the main frame
        panel.add(logoPane);
        panel.add(bottomPane);

        Framen.setContentPane(panel);
        Framen.setSize(FrameW, FrameH);
        Framen.setVisible(true);
    }

    // Returns the column number of where the given JLabel is on
    public int getColumn(JLabel label) {
        int result = -1;
        for (int i = 0; i < NUMROW && result == -1; i++) {
            for (int j = 0; j < NUMCOL && result == -1; j++) {
                if (slots[i][j] == label) {
                    result = j;
                }
            }
        }
        return result;
    }

    public void addListener(ConnectFourListener listener) {
        for (int i = 0; i < NUMROW; i++) {
            for (int j = 0; j < NUMCOL; j++) {
                slots[i][j].addMouseListener(listener);
            }
        }
    }

    // Display the specified player icon on the specified slot
    public void setPiece(int row, int col, int player) {
        int i = -1;
        // Animation
        try {
            // Moves pieces down a column
            while (i < row) {
                if (i != -1) {// ”clears” boxes
                    slots[i][col].setIcon(blank);
                    slots[i][col].paint(slots[i][col].getGraphics());
                }
                i++;
                // Draws Icon
                slots[i][col].setIcon(playerIcon[player]);
                slots[i][col].paint(slots[i][col].getGraphics());
                Thread.sleep(1);// delays
            }
        } catch (InterruptedException ie) {
        }
    }

    // Display the score on the textfield of the corresponding player
    public void setPlayerScore(int player, int score) {
        playerScore[player].setText(score + "");
    }

    // Display the appropriate player icon under”Next Turn”
    public void setNextPlayer(int player) {
        nextPlayerIcon.setIcon(scoreIcon[player]);
    }

    // Reset the game board (clear all the pieces on the game board)
    public void resetGameBoard() {
        for (int i = 0; i < NUMROW; i++) {
            for (int j = 0; j < NUMCOL; j++) {
                slots[i][j].setIcon(null);
            }
        }
    }

    // Display a pop up window displaying the message about a tie game
    public void showTieGameMessage() {
        JOptionPane.showMessageDialog(null, "Stalemate", "Tie Game", JOptionPane.PLAIN_MESSAGE, null);
    }

    // Display a window showing the winner of this game
    public void showWinnerMessage(int player) {
        JOptionPane.showMessageDialog(null, " has won this game!", "Winner! ", JOptionPane.PLAIN_MESSAGE,
                playerIcon[player]);
    }

    // Display a window showing the winner of this match
    public void MatchWinner(int player) {
        JOptionPane.showMessageDialog(null, " has won the match with " + MAXGAME + " wins", "Overall Winner!",
                JOptionPane.PLAIN_MESSAGE, playerIcon[player]);
        System.exit(0);
    }

    public static void main(String[] args) {
        ConnectFourGUI gui = new ConnectFourGUI();
        ConnectFour game = new ConnectFour(gui);
        ConnectFourListener listener = new ConnectFourListener(game, gui);
    }
}
