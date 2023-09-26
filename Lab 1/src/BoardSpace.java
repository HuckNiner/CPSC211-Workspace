public enum BoardSpace
{
    // This is using an enum.  If you're not sure what that is, ask me about it.
    Available, 
    Player1 { public String toString () { return "Player 1";}}, 
    Player2 { public String toString () { return "Player 2";}}, 
    Missing
}