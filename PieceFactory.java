package Tetris;

/**
 * PieceFactory generates a random type of piece and
 * returns it through the getNewPiece method.
 * 
 * @author mjdonnel
 *
 */
public class PieceFactory {
	
	private Board _board;
	
	public PieceFactory(Board board) {
		_board = board;
	}
	
	public Piece getNewPiece() {
		int rand = (int) (Math.random() * 7); // random integer, 0-6
		Piece newPiece = null;
		switch (rand) {
		case 0:
			newPiece = new BluePiece(_board);
			break;
		case 1:
			newPiece = new CyanPiece(_board);
			break;
		case 2:
			newPiece = new GreenPiece(_board);
			break;
		case 3:
			newPiece = new MagentaPiece(_board);
			break;
		case 4:
			newPiece = new OrangePiece(_board);
			break;
		case 5:
			newPiece = new RedPiece(_board);
			break;
		case 6:
			newPiece = new YellowPiece(_board);
			break;
		}
		return newPiece;
	}

}
