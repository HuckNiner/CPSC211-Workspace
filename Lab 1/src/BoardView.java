
public class BoardView {

	private IsolaBoard Board;

		    public BoardView (IsolaBoard Board)
		    {
		    	this.Board = Board;
		    }
		    public void DisplayBoard() {
		    	for (int i = 0; i <= 6; i++){
		    		for (int j = 0; j <= 6; j++) {
		    		if (Board.get(i, j)==BoardSpace.Player1) {
		    			System.out.print(1);
		    		}
		    		if (Board.get(i, j)==BoardSpace.Player2) {
		    			System.out.print(2);
		    		}
		    		if (Board.get(i, j)==BoardSpace.Available) {
		    			System.out.print("-");
		    		}
		    		if (Board.get(i, j)==BoardSpace.Missing) {
		    			System.out.print(" ");
		    		}
		    	}
		    		System.out.println();
		    }
	  }
}