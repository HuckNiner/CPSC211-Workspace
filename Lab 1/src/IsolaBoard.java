
public class IsolaBoard
{
    private BoardSpace [][] board = new BoardSpace [7][7];
    private BoardSpace lastPlayer = BoardSpace.Player1;
    
    public IsolaBoard ()
    {
        createBoard(7, 7);
    }

    public IsolaBoard (int width, int height)
    {
        createBoard(width, height);
    }
    
    public int getWidth ()
    {
        return board[0].length;
    }
    
    public int getHeight ()
    {
        return board.length;
    }
    
    public BoardSpace get (int row, int column)
    {
        if (row < 0 || row >= board.length || column < 0 || column >= board[0].length)
            return null;
        
        return board[row][column];
    }
    
    public BoardPosition findPosition(BoardSpace what)
    {
        for (int row = 0; row < board.length; row++)
            for (int column = 0; column < board[0].length; column++)
                if (board[row][column] == what)
                    return new BoardPosition(row, column);;
        
        return null;
    }
    
    public void movePlayer (BoardSpace which, BoardPosition newPosition)
    {
        // All moves passed to this method should already have been checked
        // to make sure they're legal.  Models need to check again, though, just
        // in case the Controller has a bug.  But we don't need to try to return
        // an error message, since if the Controller has a bug the programmer
        // can debug into this method to see what went wrong.
        
        // It makes no sense to ask us to move a non-player
        if (which != BoardSpace.Player1 && which != BoardSpace.Player2)
            return;
        
        // It makes no sense to move to a spot outside the board
        if (illegalBoardPosition(newPosition.getRow(), newPosition.getColumn()))
            return;
        
        // We have a sane request, find the position of the player
        BoardPosition position = findPosition(which);
        
        // Make sure the move is legal.  A move is legal if:
        //
        // 1) And the destination is available
        // 2) It is 1 space away from the current position
        if (board[newPosition.getRow()][newPosition.getColumn()] != BoardSpace.Available)
            return;
        
        if (Math.abs(position.getRow() - newPosition.getRow()) > 1 || Math.abs(position.getColumn() - newPosition.getColumn()) > 1)
            return;
        
        // Should be a legal move, make it
        lastPlayer = which;
        board[position.getRow()][position.getColumn()] = BoardSpace.Missing;
        board[newPosition.getRow()][newPosition.getColumn()] = which;
    }
    
    public BoardSpace checkWinner ()
    {
        // This returns BoardSpace.Player1 if player 1 has won,
        // BoardSpace.Player2 if player 2 has won, and 
        // BoardSpace.Available if the game is not over yet
        //
        // This is complicated a bit when both players are isolated.
        // In that case the person who took the last move wins,
        // because the person who has the next move would be unable
        // to do so.
        
        BoardPosition position1 = findPosition(BoardSpace.Player1);
        BoardPosition position2 = findPosition(BoardSpace.Player2);
        boolean player1Isolated = positionIsIsolated(position1);
        boolean player2Isolated = positionIsIsolated(position2);

        if (player1Isolated && ! player2Isolated)
            return BoardSpace.Player2;
        
        if (player2Isolated && ! player1Isolated)
            return BoardSpace.Player1;
        
        if (player1Isolated && player2Isolated)
            return lastPlayer;
        
        return BoardSpace.Available;
    }

    private void createBoard (int width, int height)
    {
        board = new BoardSpace[height][width];
        
        for (int row = 0; row < height; row++)
            for (int column = 0; column < width; column++)
                board[row][column] = BoardSpace.Available;

        // Place the players in their starting spaces
        int startColumn = width/2;
        
        board[0][startColumn] = BoardSpace.Player1;
        board[height-1][startColumn] = BoardSpace.Player2;
    }
    
    private boolean positionIsIsolated (BoardPosition position)
    {
        for (int row = position.getRow()-1; row <= position.getRow()+1; row++)
            for (int column = position.getColumn()-1; column <= position.getColumn()+1; column++)
            {
                // If we are outside the bounds of the board, skip this spot
                if (illegalBoardPosition(row, column))
                    continue;
                
                // This shouldn't matter, since the current position should have 
                // a player on it, but just in case don't check the current position.
                // This allows the Controller to do enhancements such as checking to 
                // see if a move would make a player isolated, for possible AI players
                if (row == position.getRow() && column == position.getColumn())
                    continue;
                
                // If this spot is available, then we're not isolated
                if (board[row][column] == BoardSpace.Available)
                    return false;
            }
        
        return true;
    }
    
    private boolean illegalBoardPosition (int row, int column)
    {
        return (row < 0 || row >= board.length || column < 0 || column >= board[0].length);
    }
}