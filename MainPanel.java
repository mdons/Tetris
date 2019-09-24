package Tetris;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * My top level object, MainPanel, is a JPanel. It creates a new Board,
 * where the game will be displayed and run. MainPanel also creates a
 * JLabel to display the score and game conditions, a JButton to quit,
 * a JButton to restart, and a JPanel within which the JButtons will
 * be displayed. The label, board, and controlPanel are all displayed
 * in a BorderLayout, while the buttons are displayed in a FlowLayout
 * within the controlPanel.
 * 
 * @author mjdonnel
 *
 */
public class MainPanel extends JPanel {
	
	private Board _board;
	private JLabel _label;
	
	public MainPanel() {
		super();
		this.setLayout(new BorderLayout());
		
		_label = new JLabel("Score= 0", SwingConstants.CENTER);
		
		_board = new Board(_label); // Board receives an association to JLabel
		
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new QuitListener());
		
		JButton restartButton = new JButton("Restart");
		restartButton.addActionListener(new RestartListener());
		
		JPanel controlPanel = new JPanel();
		controlPanel.add(quitButton);
		controlPanel.add(restartButton);
		
		this.add(_label, BorderLayout.NORTH);	
		this.add(_board, BorderLayout.CENTER);
		this.add(controlPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * QuitListener is an ActionListener which will be triggered
	 * when the quit button is pressed. It quits the program.
	 * 
	 * @author mjdonnel
	 *
	 */
	private class QuitListener implements ActionListener {
		
		public QuitListener() {}
		
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
		
	}
	
	/**
	 * RestartListener is an actionListener which will be triggered
	 * when the restart button is pressed. It resets the label, and
	 * it resets board values through Board's restart method.
	 * 
	 * @author mjdonnel
	 *
	 */
	private class RestartListener implements ActionListener {
		
		public RestartListener() {}
		
		public void actionPerformed(ActionEvent e) {
			_label.setText("Score= 0");
			_board.restart();
		}
		
	}

}
