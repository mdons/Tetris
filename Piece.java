package Tetris;

import java.awt.Color;
import java.awt.Graphics;
import cs015.prj.Shape.RectangleShape;

/**
 * A Piece is a composite shape composed of RectangleShapes (blocks).
 * It is a abstract class because the location of its blocks and the
 * color of its blocks must be set polymorphically in a subclass.
 * The subclass, passes its color through the super constructor, gets
 * the generated blocks through the getBlocks accessor method, and gives
 * the blocks their appropriate starting locations. The abstract rotate
 * method is also written polymophically, specifically for each subclass.
 * 
 * @author mjdonnel
 *
 */
public abstract class Piece {
	
	private RectangleShape[] _blocks;
	private Board _board;
	
	public Piece(Color color, Board board) {
		_blocks = new RectangleShape[4];
		_board = board;
		
		for (int i=0; i<_blocks.length; i++) {
			RectangleShape newBlock = new RectangleShape();
			newBlock.setSize(Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
			newBlock.setColor(color);
			newBlock.setBorderColor(Color.BLACK);
			_blocks[i] = newBlock;
		}
		
		_board.repaint();
	}
	
	public boolean moveLeft() {
		boolean moveValid = true; // assume the piece can move
		
		for (int i=0; i<_blocks.length; i++) { // for each block
			int x = (int) _blocks[i].getX();
			int y = (int) _blocks[i].getY();
			
			if (!_board.checkCoordinatesValid(x - Constants.BLOCK_SIZE, y)) { // check if the destination is occupied
				moveValid = false; // if it is occupied, this move is not valid
			}
		}
		
		if (moveValid) { // if the move is valid
			for (int i=0; i<_blocks.length; i++) {
				int x = (int) _blocks[i].getX();
				int y = (int) _blocks[i].getY();
				_blocks[i].setLocation(x - Constants.BLOCK_SIZE, y); // move the blocks to their intended location
			}
			_board.repaint(); // repaint the board
		}
		
		return moveValid; // returns whether or not the move was performed (only necessary for moveDown method, but still useful)
	}
	
	/**
	 * See moveLeft method
	 * 
	 * @return
	 */
	public boolean moveRight() {
		boolean moveValid = true;
		
		for (int i=0; i<_blocks.length; i++) {
			int x = (int) _blocks[i].getX();
			int y = (int) _blocks[i].getY();
			
			if (!_board.checkCoordinatesValid(x + Constants.BLOCK_SIZE, y)) {
				moveValid = false;
			}
		}
		
		if (moveValid) {
			for (int i=0; i<_blocks.length; i++) {
				int x = (int) _blocks[i].getX();
				int y = (int) _blocks[i].getY();
				_blocks[i].setLocation(x + Constants.BLOCK_SIZE, y);
			}
			_board.repaint();
		}
		
		return moveValid;
	}
	
	/**
	 * See moveLeft method
	 * 
	 * @return
	 */
	public boolean moveDown() {
		boolean moveValid = true;
		
		for (int i=0; i<_blocks.length; i++) {
			int x = (int) _blocks[i].getX();
			int y = (int) _blocks[i].getY();
			if (!_board.checkCoordinatesValid(x, y + Constants.BLOCK_SIZE)) {
				moveValid = false;
			}
		}
		
		if (moveValid) {
			for (int i=0; i<_blocks.length; i++) {
				int x = (int) _blocks[i].getX();
				int y = (int) _blocks[i].getY();
				_blocks[i].setLocation(x, y + Constants.BLOCK_SIZE);
			}
			_board.repaint();
		}
		
		return moveValid;
	}
	
	/**
	 * This method is specific to the subclass. Generally, it specifies one of
	 * the blocks as the center of rotation, and moves the other blocks around
	 * the center according to the equations given in the help session slides.
	 * 
	 * @return
	 */
	public abstract boolean rotate();
	
	public boolean drop() {
		boolean moveValid = true;
		
		while (moveValid) { // moves piece down until it cannot move down anymore
			moveValid = this.moveDown();
		}
		
		/*
		 * The below functions like a timer tick. It immediately add the piece
		 * to the board array, checks the board array for filled lines, checks
		 * for a game over, and makes a new piece if the game isn't over. This
		 * occurs so the player doesn't have to wait until the next timer tick
		 * after dropping a piece.
		 */
		_board.addPieceToBoard();
		_board.checkLines();
		boolean gameOver = _board.checkGameOver();
		if (!gameOver) {
			_board.makeNewPiece();
		}
		
		return moveValid;
	}
	
	public void paint(Graphics g) {
		for (int i=0; i<4; i++) {
			_blocks[i].paint(g);
		}
	}
	
	public RectangleShape[] getBlocks() { // accessor method for blocks
		return _blocks;
	}

}
