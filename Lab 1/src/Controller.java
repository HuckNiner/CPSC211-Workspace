
public class Controller {

	public static void main(String[] args) {
		Controller x = new Controller();
		x.go();
	}
	public void go () {
		IsolaBoard Board = new IsolaBoard();
		
		BoardView View = new BoardView(Board);
		
		View.DisplayBoard();
		
		
			    
	}
	
}
