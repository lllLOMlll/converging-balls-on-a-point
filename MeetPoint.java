package exam;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Class to handle the meeting
 * @author Mushu
 *
 */
public class MeetPoint extends JPanel implements Runnable, MouseListener {
	private static final long serialVersionUID = 1L;
	private Ball ball1, ball2;
	private Square square;
	
	// meeting point
	private int xArrival, yArrival;
	private Thread threadBall1, threadBall2, mainThread;

	public MeetPoint() {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setContentPane(this);
		f.setSize(510, 500);
		f.setLocationRelativeTo(null);

		setBackground(Color.white);

		//CREATE OF EACH BALL (TODO)
		ball1 = new Ball(45, 80, 40, Color.RED, this);
		ball2 = new Ball (450, 80, 30, Color.BLUE, this);
		
		
		//GIVE EACH BALL A PARTNER (TODO)
		ball1.setPartner(ball2);
		ball2.setPartner(ball1);
		
		addMouseListener(this);
		f.setVisible(true);
	}

	//Mouse Listener
	//Set the meeting point based on the position of the click
	public void mouseClicked(MouseEvent evt) {
		if ((mainThread != null) && mainThread.isAlive())
			return;
		xArrival = evt.getX();
		yArrival = evt.getY();
		
		//create new square
		square = new Square(xArrival, yArrival, 50, Color.GREEN);
		repaint();
		
		//init and start mainThread
		mainThread = new Thread(this);
		mainThread.start();
	}

	public void mousePressed(MouseEvent evt) {}
	public void mouseReleased(MouseEvent evt) {}
	public void mouseEntered(MouseEvent evt) {}
	public void mouseExited(MouseEvent evt) {}

	//Painting all three shapes
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    if (ball1 != null)
	        ball1.drawCircle(g);

	    if (ball2 != null)
	        ball2.drawCircle(g);

	    if (square != null)
	        square.drawSquare(g);
	}


	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 * 
	 * 	Run the main thread, in which will start the two other threads
	 *	Set Meeting point of the balls
	 *	Use thread.join() in order to run all threads at the same time.
	 *	Don't forget to erase the square after they've met
	 *	(TODO)
	 */
	public void run() {
	    // set the meeting point for both balls
	    ball1.setMeetPoint(xArrival, yArrival);
	    ball2.setMeetPoint(xArrival, yArrival);
	    
	    // start the threads for both balls
	    threadBall1 = new Thread(ball1);
	    threadBall2 = new Thread(ball2);
	    threadBall1.start();
	    threadBall2.start();
	    
	    try {
	        threadBall1.join(); // wait for both threads to finish
	        threadBall2.join();
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	    
	    square = null; // erase the square after they've met
	    repaint();
	   
	}

	//Main
	public static void main(String[] args) {
		MeetPoint mg = new MeetPoint();
	}
}