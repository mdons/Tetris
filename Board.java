package Tetris;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import cs015.prj.Shape.RectangleShape;

/**
 * The Board class is a JPanel that contains, manages, and displays the components
 * of Tetris. Once generated, it sets its own dimensions and color, sets the score
 * to 0, and builds a board array containing RectangleShapes (blocks). The Board
 * then creates a PieceFactory, first Piece, and PieceProxy. Finally, it creates a
 * Timer, TimerListener, and KeyListener. These components interact to create the
 * Tetris game. Board's many methods are used to facilitate these interactions.
 * 
 * @author mjdonnel
 *
 */
public class Board extends JPanel {
	
	private JLabel _label;
	private int _score;
	private RectangleShape[][] _boardArray;
	private PieceFactory _pieceFactory;
	private PieceProxy _pieceProxy;
	private Timer _timer;
	private MyKeyListener _myKeyListener;
	
	public Board(JLabel label) { // Receives an association to the JLabel
		super();
		
		this.setFocusable(true);
		this.grabFocus();
		
		int boardWidth = Constants.NUM_COLUMNS * Constants.BLOCK_SIZE;
		int boardHeight = Constants.NUM_ROWS * Constants.BLOCK_SIZE;
		Dimension boardDimensions = new Dimension(boardWidth, boardHeight);
		this.setPreferredSize(boardDimensions);
		this.setSize(boardDimensions);
		this.setBackground(Color.BLACK);
		
		_label = label;
		_score = 0;
		
		_boardArray = new RectangleShape[Constants.NUM_COLUMNS][Constants.NUM_ROWS];
		this.buildBoardBorders(); // Builds the grey border of blocks
		
		_pieceFactory = new PieceFactory(this);
		Piece firstPiece = _pieceFactory.getNewPiece();
		_pieceProxy = new PieceProxy(firstPiece);
		
		_timer = new Timer(Constants.TIMESTEP, new TimerListener(this));
		_timer.start();
		
		_myKeyListener = new MyKeyListener();
		this.addKeyListener(_myKeyListener);
	}
	
	/**
	 * This method restarts the game from either a playing, paused, or game over condition.
	 * The score, board array of blocks, current piece, and timer delay are all reset to
	 * initial values. A game over or paused state is reset as well.
	 */
	public void restart() {
		_score = 0; // reset the score
		
		_boardArray = new RectangleShape[Constants.NUM_COLUMNS][Constants.NUM_ROWS]; // build a new board array
		this.buildBoardBorders();
		
		Piece newPiece = _pieceFactory.getNewPiece(); // make a new Piece
		_pieceProxy.setCurrentPiece(newPiece);
		
		if (!_timer.isRunning()) { // If restarting from a Game Over or a Pause (timer stopped)
			_timer.start(); // Restart the timer
			_myKeyListener.unpause(); // Let the KeyListener know the game is no longer paused
			
			KeyListener[] currentKeyListeners = this.getKeyListeners();
			if (currentKeyListeners.length == 0) { // If restarting from a Game Over (no KeyListener)
				this.addKeyListener(_myKeyListener); // Add a KeyListener
			}
		}
		
		_timer.setDelay(Constants.TIMESTEP); // Reset difficulty level to initial value
		
		this.grabFocus();
	}
	
	/**
	 * This method is called when the score is incremented. If the score is a multiple
	 * of 10, the delay between timer ticks is halved. This increases game difficulty
	 * every time 10 lines have been cleared.
	 */
	public void checkLevel() {
		if (_score % 10 == 0) {
			int delay = _timer.getDelay();
			_timer.setDelay(delay / 2);
		}
	}
	
	/**
	 * This method paints every block on the board and the current piece as referenced
	 * by the PieceProxy. Note that if a position on the board array does not contain
	 * a block, that position contains a null, and it will not be called to paint itself.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int col=0; col<Constants.NUM_COLUMNS; col++) {
			for (int row=0; row<Constants.NUM_ROWS; row++) {
				if (_boardArray[col][row] != null) {
					_boardArray[col][row].paint(g);
				}
			}
		}
		_pieceProxy.paint(g);
	}
	
	/**
	 * This method builds the grey borders of the Tetris board by filling in the first and
	 * last 2 rows and columns with grey blocks. The nested for loop iterates through every
	 * position on the board, and the if statement checks if it is in a border region. If it
	 * is, a new block is generated and is given size, color, border color, and location
	 * (based on the position in the array), and the block is finally stored in the board array.
	 */
	public void buildBoardBorders() {
		for (int col=0; col<Constants.NUM_COLUMNS; col++) {
			for (int row=0; row<Constants.NUM_ROWS; row++) {
				if ((col<2 || col>=Constants.NUM_COLUMNS-2) || (row<2 || row>=Constants.NUM_ROWS-2)) {
					RectangleShape newBlock = new RectangleShape();
					newBlock.setSize(Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
					newBlock.setColor(Color.GRAY);
					newBlock.setBorderColor(Color.BLACK);
					newBlock.setLocation(col * Constants.BLOCK_SIZE, row * Constants.BLOCK_SIZE);
					_boardArray[col][row] = newBlock;
				}
			}
		}
	}
	
	/**
	 * This method is called during Piece movement in order to determine if a location, given by its
	 * x and y coordinates in pixels, is already occupied by a block. The pixel location is
	 * converted into an array position, and a boolean value is returned based upon whether or not
	 * that position in the board array contains a block.
	 * 
	 * @param x
	 * @param y
	 * @return boolean
	 */
	public boolean checkCoordinatesValid(int x, int y) {
		int col = x / Constants.BLOCK_SIZE;
		int row = y / Constants.BLOCK_SIZE;
		if (_boardArray[col][row] == null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * This method is called when a piece has reached its final destination and must
	 * be added to the board array. The blocks of the piece are returned as an array
	 * through the getBlocks method. For each block, the x and y coordinate is
	 * converted into a position in the board array. Finally, each block is added to
	 * the array in the appropriate position.
	 */
	public void addPieceToBoard() {
		RectangleShape[] blocks = _pieceProxy.getBlocks();
		for (int i=0; i<blocks.length; i++) {
			int col = (int) (blocks[i].getX() / Constants.BLOCK_SIZE);
			int row = (int) (blocks[i].getY() / Constants.BLOCK_SIZE);
			_boardArray[col][row] = blocks[i];	
		}
	}
	
	/**
	 * This method gets a new piece from PieceFactory and gives it to the PieceProxy.
	 * The old piece is garbage collected, but its blocks have already been added
	 * to the board using the addPieceToBoard method, above.
	 */
	public void makeNewPiece() {
		Piece newPiece = _pieceFactory.getNewPiece();
		_pieceProxy.setCurrentPiece(newPiece);
	}
	
	/**
	 * This method checks for filled lines in the board array, and if it finds them,
	 * it erases the filled line and moves the above lines down. Additionally, for
	 * each filled line, the score in augmented, the JLabel is changed to reflect the
	 * new score, the difficulty level is checked and set appropriately, and the board
	 * panel is repainted.
	 */
	public void checkLines() {
		for (int row = Constants.NUM_ROWS-3; row>=2; row--) { // row is the row to be checked
			boolean rowFilled = true; // the row is assumed to be filled
			while (rowFilled) {
				for (int col=2; col<Constants.NUM_COLUMNS-2; col++) {
					if (_boardArray[col][row] == null) { // if there is an empty spot in the row
						rowFilled = false; // we now know the row is not filled
						break;
					}
				}
				
				if (rowFilled) { // if row is filled
					for (int col=2; col<Constants.NUM_COLUMNS-2; col++) {
						_boardArray[col][row] = null; // clear it
					}
					
					for (int row2 = row - 1; row2>=2; row2--) { // for every row (row2) above the filled row
						for (int col=2; col<Constants.NUM_COLUMNS-2; col++) {
							if (_boardArray[col][row2] != null) {
								double x = _boardArray[col][row2].getX();
								double y = _boardArray[col][row2].getY();
								_boardArray[col][row2+1] = _boardArray[col][row2]; // copy each block into the next row
								_boardArray[col][row2+1].setLocation(x, y + Constants.BLOCK_SIZE); // set the new block's graphical location
								_boardArray[col][row2] = null; // erase the original block
							}
						}
					}
					_score++; // increase the score by one per filled row
					_label.setText("Score= " + _score); // reset the JLabel
					this.checkLevel(); // check if the difficulty level is right, change accordingly
					this.repaint(); // repaint the board
				}
			}
		}
	}
	
	/**
	 * This method checks for a game over and returns true if so. It also
	 * effects the game over by changing the JLabel to display GAME OVER,
	 * stopping the timer, and removing the KeyListener. This prevents the
	 * player or the TimerListener from moving a piece. A game over is
	 * triggered when there is a block in the top row (or a block in any
	 * piece's starting location).
	 * 
	 * @return boolean
	 */
	public boolean checkGameOver() {
		boolean rowFilled = false; // the first row (below the border) is assumed to be empty
		for (int col=2; col<Constants.NUM_COLUMNS-2; col++) {
			if (_boardArray[col][2] != null) { // if there is a filled spot in the row
				rowFilled = true; // we now know the row is partially occupied
				break;
			}
		}
		/*
		 * The below checks the three positions in the second row beneath the border
		 * that must remain empty in case a new piece needs to be generated. If these
		 * spots are filled, a new piece cannot be generated, and the game must end.
		 */
		for (int col=5; col<=7; col++) {
			if (_boardArray[col][3] != null) {
				rowFilled = true;
				break;
			}
		}
		if (rowFilled) { // if the game over condition is met
			_label.setText("GAME OVER Score= " + _score); // notify the player
			_timer.stop(); // stop dropping pieces
			this.removeKeyListener(_myKeyListener); // prevent player input
		}
		return rowFilled;
	}
	
	/**
	 * MyKeyListener handles user input. In addition, it manages whether
	 * or not the game is paused. As long as the game is unpaused, movement
	 * commands are passed on to the PieceProxy, which passes them on to the
	 * current piece in turn. If a game is restarted during a pause, the
	 * KeyListener can be reset to its initial, unpaused state using the
	 * unpause method.
	 * 
	 * @author mjdonnel
	 *
	 */
	private class MyKeyListener implements KeyListener {
		
		private boolean _gamePaused;
		
		public MyKeyListener() {
			_gamePaused = false;
		}
		
		public void unpause() { // may be used by the restart method
			_gamePaused = false; // to reset game state
		}
		
		public void keyPressed(KeyEvent e) {
			
			int keyPressed = e.getKeyCode();
			
			if ((keyPressed == KeyEvent.VK_P) && !_gamePaused) { // pauses the game
				_label.setText("PAUSED Score= " + _score);
				_timer.stop();
				_gamePaused = true;
			}
			else if ((keyPressed == KeyEvent.VK_P) && _gamePaused) { // unpauses the game
				_label.setText("Score= " + _score);
				_timer.start();
				_gamePaused = false;
			}
			
			if ((keyPressed == KeyEvent.VK_LEFT) && !_gamePaused) {
				_pieceProxy.moveLeft();
			}
			
			if ((keyPressed == KeyEvent.VK_RIGHT) && !_gamePaused) {
				_pieceProxy.moveRight();
			}
			
			if ((keyPressed == KeyEvent.VK_DOWN) && !_gamePaused) {
				_pieceProxy.moveDown();
			}
			
			if ((keyPressed == KeyEvent.VK_UP) && !_gamePaused) {
				_pieceProxy.rotate();
			}
			
			if ((keyPressed == KeyEvent.VK_SPACE) && !_gamePaused) {
				_pieceProxy.drop();
			}
			
		}
		
		public void keyTyped(KeyEvent e) {}
		
		public void keyReleased(KeyEvent e) {}
		
	}
	
	/**
	 * The TimerListener moves the current piece down every timer
	 * tick. If the piece cannot move down anymore, the game must
	 * be cycled. The current piece is added to the board array,
	 * the board checks for filled lines, the board checks for a
	 * game over, and if the game is not over, a new piece is
	 * generated and set as the current piece.
	 * 
	 * @author mjdonnel
	 *
	 */
	private class TimerListener implements ActionListener {
		
		private Board _board;
		
		public TimerListener(Board board) {
			_board = board;
		}
		
		public void actionPerformed(ActionEvent e) {
			
			boolean moveCompleted = _pieceProxy.moveDown(); // returns a boolean indicating whether or not the piece was moved
			
			if (!moveCompleted) { // if the piece did not move down
				_board.addPieceToBoard(); // add it to board array
				_board.checkLines(); // check for filled lines
				
				boolean gameOver = _board.checkGameOver(); // returns a boolean indicating whether or not the game is now over
				if (!gameOver) {
					_board.makeNewPiece(); // if the game isn't over, generate a new piece and make it the current piece
				}
			}
		}
		
	}

}
