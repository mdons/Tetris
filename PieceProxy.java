package Tetris;

import java.awt.Graphics;
import cs015.prj.Shape.RectangleShape;

/**
 * PieceProxy stores a pointer to the current
 * piece. Any object can move the current piece
 * by making calls on the PieceProxy. Additionally,
 * the PieceProxy can paint the current piece,
 * return the current piece's blocks, and set
 * a new current piece.
 * 
 * @author mjdonnel
 *
 */
public class PieceProxy {
	
	private Piece _currentPiece;
	
	public PieceProxy(Piece piece) {
		_currentPiece = piece;
	}
	
	public boolean moveLeft() {
		return _currentPiece.moveLeft();
	}
	
	public boolean moveRight() {
		return _currentPiece.moveRight();
	}
	
	public boolean moveDown() {
		return _currentPiece.moveDown();
	}
	
	public boolean rotate() {
		return _currentPiece.rotate();
	}
	
	public boolean drop() {
		return _currentPiece.drop();
	}
	
	public RectangleShape[] getBlocks() {
		return _currentPiece.getBlocks();
	}
	
	public void paint(Graphics g) {
		_currentPiece.paint(g);
	}
	
	public void setCurrentPiece(Piece piece) {
		_currentPiece = piece;
	}

}
