package Tetris;

import java.awt.Color;
import cs015.prj.Shape.RectangleShape;

/**
 * Subclass of Piece. See Piece class for explanation.
 * 
 * @author mjdonnel
 *
 */
public class RedPiece extends Piece {
	
	private Board _board;
	private RectangleShape[] _blocks;
	
	public RedPiece(Board board) {
		super(Color.RED, board);
		_board = board;
		_blocks = super.getBlocks();
		for (int i=0; i<_blocks.length; i++) {
			_blocks[i].setLocation((5 + i) * Constants.BLOCK_SIZE, 2 * Constants.BLOCK_SIZE);
		}
	}
	
	@Override
	public boolean rotate() {
		int centerOfRotationX = (int) _blocks[2].getX();
		int centerOfRotationY = (int) _blocks[2].getY();
		boolean moveValid = true;
		for (int i=0; i<_blocks.length; i++) {
			int x = (int) _blocks[i].getX();
			int y = (int) _blocks[i].getY();
			if (!_board.checkCoordinatesValid(centerOfRotationX - centerOfRotationY + y, centerOfRotationX + centerOfRotationY - x)) {
				moveValid = false;
			}
		}
		if (moveValid) {
			for (int i=0; i<_blocks.length; i++) {
				int x = (int) _blocks[i].getX();
				int y = (int) _blocks[i].getY();
				_blocks[i].setLocation(centerOfRotationX - centerOfRotationY + y, centerOfRotationX + centerOfRotationY - x);
			}
			_board.repaint();
		}
		return moveValid;
	}

}
