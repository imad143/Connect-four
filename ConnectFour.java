public class ConnectFour {

    final int Empty = -1; // empty space on the game board
    final int Blank = 2; // “null” not playable space
    final int NUMPLAYER;
    final int MAXGAME; // number of players
    final int NUMROW; // number of rows on the game board
    final int NUMCOL; // number of columns on the game board

    int CONNECT = 4;

    ConnectFourGUI gui; // the gui for the game
    boolean win = false;
    int numMove; // number of moves that has been made in the game
    int CurrentPlayer; // identifies the current player
    int grid[][]; // represents the grid of the game board
    int score[]; // represents the scores of the players
    boolean playermove; // checks whether the CurrentPlayer has made a move
    int connect4; // checks if there are four pieces in a row
    int latestpiece; // saves the last piece checked on the board
    int i; // variables for coordinate calculation
    int j;
    int x; // the coordinates of the last piece placed
    int y;

    public ConnectFour(ConnectFourGUI gui) {
        this.gui = gui;
        NUMPLAYER = gui.NUMPLAYER;
        NUMROW = gui.NUMROW;
        NUMCOL = gui.NUMCOL;
        MAXGAME = gui.MAXGAME;

        numMove = 0;
        CurrentPlayer = 0;
        grid = new int[NUMROW][NUMCOL];
        EmptyGrid(grid);
        score = new int[NUMPLAYER];
        for (i = 0; i < NUMPLAYER; i++) {
            score[i] = 0;
        }
    }

    // TO DO: creation of arrays, and initialization of variables should be added
    // here
    // This changes the current player and updates the “NEXT” section
    public void ChangePlayer() {
        if (CurrentPlayer == 0) {
            CurrentPlayer = 1;
            gui.setNextPlayer(CurrentPlayer);
        } else if (CurrentPlayer == 1) {
            CurrentPlayer = 0;
            gui.setNextPlayer(CurrentPlayer);
        }
    }

    // This checks for the available spaces in the column selected
    public void placePiece(int column) {
        i = NUMROW - 1;
        do // moves through the rows in a column
        {
            // checks if spot is available
            if (grid[i][column] == Empty) {
                gui.setPiece(i, column, CurrentPlayer);
                grid[i][column] = CurrentPlayer;
                playermove = true;
                numMove += 1;
                x = i;
                y = column;
            }
            i--;
        } while (playermove == false && i >= 0);
    }

    // This method checks whether there are any winners. It updates the scoreboard
    // and displays the winning message. If one of the players reach 3 wins, it
    // displays the final winning message. If there is no winner, it displays the
    // tie came message.
    public void checkWin(int[][] board) {
        win = false;
        checkHorizontal(NUMROW, NUMCOL);
        checkVertical(NUMROW, NUMCOL);
        checkBackDiag(NUMROW, NUMCOL);
        checkDiag(NUMROW, NUMCOL);

        // checks for final winner
        if (win == true && score[CurrentPlayer] == MAXGAME - 1) {
            score[CurrentPlayer]++;
            gui.setPlayerScore(CurrentPlayer, score[CurrentPlayer]);
            gui.MatchWinner(CurrentPlayer);
        }
        // checks for round winner
        else if (win == true) {
            score[CurrentPlayer]++;
            gui.setPlayerScore(CurrentPlayer, score[CurrentPlayer]);
            gui.showWinnerMessage(CurrentPlayer);
            gui.resetGameBoard();
            EmptyGrid(board);
            numMove = 0;
        }
        // checks for tie game
        else if (win == false && numMove == NUMCOL * NUMROW) {
            gui.showTieGameMessage();
            gui.resetGameBoard();
            EmptyGrid(board);
            numMove = 0;
        }
    }

    // checks for a horizantal win
    public void checkHorizontal(int row, int column) { // checks for a horizontal win
        int count = 0;
        for (int i = column; i < NUMCOL && grid[row][i] == CurrentPlayer; i++) { // checks each box to the right of the
                                                                                 // piece that has just been set, until
                                                                                 // the box doesn’t have a piece from
                                                                                 // the current player
            count++; // increase count for every continuous piece from the same player
        }

        for (int i = column; i >= 0 && grid[row][i] == CurrentPlayer; i--) { // checks each box to the left of the piece
                                                                             // that has just been set, until the box
                                                                             // doesn’t have a piece from the current
                                                                             // player
            count++;
        }
        if (count >= CONNECT) { // if count is greater then the number of continuous pieces required to win, the
                                // current player is declared the winner
            win = true; // declares a win
        }
    }

    // checks for a vertical win
    public void checkVertical(int row, int column) {
        int count = 0;
        for (int i = row; i < NUMROW && grid[column][i] == CurrentPlayer; i++) { // checks each box to the right of the
                                                                                 // piece that has just been set, until
                                                                                 // the box doesn’t have a piece from
                                                                                 // the current player
            count++; // increase count for every continuous piece from the same player
        }

        for (int i = row; i < NUMROW && grid[column][i] == CurrentPlayer; i--) { // checks each box to the left of the
                                                                                 // piece that has just been set, until
                                                                                 // the box doesn’t have a piece from
                                                                                 // the current player
            count++;
        }
        if (count >= CONNECT) { // if count is greater then the number of continuous pieces required to win, the
                                // current player is declared the winner
            win = true; // declares a win
        }

    }

    public void checkDiag(int row, int column) {
        int count = 0;
        for (i = row, j = column; i >= 0 && j < NUMCOL && grid[i][j] == CurrentPlayer; i--, j++) {
            count++;
        }
        for (i = row, j = column; i >= 0 && j < NUMCOL && grid[i][j] == CurrentPlayer; i++, j--) {
            count++;
        }
        if (count >= 4) {
            win = true;
        }
    }

    public void checkBackDiag(int row, int column) {
        int count = 0;
        for (i = row, j = column; i >= 0 && j < NUMCOL && grid[i][j] == CurrentPlayer; i++, j++) {
            count++;
        }
        for (i = row, j = column; i >= 0 && j < NUMCOL && grid[i][j] == CurrentPlayer; i--, j--) {
            count++;
        }
        if (count >= 4) {
            win = true;
        }

    }

    public void EmptyGrid(int[][] board) {
        for (int i = 0; i < NUMROW; i++) {
            for (int j = 0; j < NUMCOL; j++) {
                board[i][j] = Empty;
            }
        }
    }

    // This method will be called when a column is clicked. “column” is
    // the number of the column that is clicked by the user

    public void play(int column) {
        playermove = false;
        placePiece(column);
        if (playermove == true) {
            checkWin(grid);
            ChangePlayer();
        }
    }
}
