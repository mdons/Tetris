package Tetris;

import java.awt.Color;
import cs015.prj.Shape.RectangleShape;

/**
 * Subclass of Piece. See Piece class for explanation.
 * 
 * @author mjdonnel
 *
 */
public class OrangePiece extends Piece {
	
	private Board _board;
	private RectangleShape[] _blocks;
	
	public OrangePiece(Board board) {
		super(Color.ORANGE, board);
		_board = board;
		_blocks = super.getBlocks();
		_blocks[0].setLocation(5 * Constants.BLOCK_SIZE, 3 * Constants.BLOCK_SIZE);
		_blocks[1].setLocation(6 * Constants.BLOCK_SIZE, 3 * Constants.BLOCK_SIZE);
		_blocks[2].setLocation(7 * Constants.BLOCK_SIZE, 3 * Constants.BLOCK_SIZE);
		_blocks[3].setLocation(6 * Constants.BLOCK_SIZE, 2 * Constants.BLOCK_SIZE);
	}
	
	@Override
	public boolean rotate() {
		int centerOfRotationX = (int) _blocks[1].getX();
		int centerOfRotationY = (int) _blocks[1].getY();
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
