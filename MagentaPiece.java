package Tetris;

import java.awt.Color;
import cs015.prj.Shape.RectangleShape;

/**
 * Subclass of Piece. See Piece class for explanation.
 * 
 * @author mjdonnel
 *
 */
public class MagentaPiece extends Piece {
	
	private RectangleShape[] _blocks;
	
	public MagentaPiece(Board board) {
		super(Color.MAGENTA, board);
		_blocks = super.getBlocks();
		_blocks[0].setLocation(6 * Constants.BLOCK_SIZE, 2 * Constants.BLOCK_SIZE);
		_blocks[1].setLocation(7 * Constants.BLOCK_SIZE, 2 * Constants.BLOCK_SIZE);
		_blocks[2].setLocation(6 * Constants.BLOCK_SIZE, 3 * Constants.BLOCK_SIZE);
		_blocks[3].setLocation(7 * Constants.BLOCK_SIZE, 3 * Constants.BLOCK_SIZE);
	}
	
	@Override
	public boolean rotate() {
		return true;
	}

}
