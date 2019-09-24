package Tetris;

import javax.swing.JFrame;

/**
 * It's time for Tetris! This is the  main class to get things started.
 * The main method of this application calls the App constructor. You 
 * will need to fill in the constructor to instantiate your Tetris game.
 *
 * App creates a new frame, creates a new top level object (MainPanel),
 * and adds the top level object to the frame.
 *
 * @author mjdonnel
 * Did you discuss your design with another student?
 * No
 * If so, list their login here:
 * N/A
 */

public class App {

	public App() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MainPanel panel = new MainPanel();
		frame.add(panel);
		frame.pack();
		
		frame.setVisible(true);
	}

	/*Here's the mainline!*/
	public static void main(String[] argv) {
		new App();
	}

}
