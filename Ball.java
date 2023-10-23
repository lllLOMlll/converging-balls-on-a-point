package exam;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Class to draw the ball and make it move
 * 
 * @author Mushu
 *
 */
public class Ball implements Runnable {
	private int x, y; // coordinates of the center of the ball
	private int diameter; // diameter
	private int xMeet, yMeet; // meeting point;
	private Color color; // ball color
	private JPanel jPanel; // JPanel where the ball will be trace
	/* The other ball to meet */
	private Ball partner;

	private boolean met; // Should be false up until the balls met

	public Ball(int x, int y, int diameter, Color color, JPanel jPanel) {
		this.x = x;
		this.y = y;
		this.diameter = diameter;
		this.color = color;
		this.jPanel = jPanel;
	}

	/* Set meeting point */
	void setMeetPoint(int xMeet, int yMeet) {
		this.xMeet = xMeet;
		this.yMeet = yMeet;
	}

	/*
	 * Move the ball from the starting point to the end point Implement the code to
	 * make the ball move (remember lab 5?) TODO
	 */

	// Method to make the balls move to the square

	void path(int xArrival, int yArrival) {
		// Calculate the distance in "x" between the ball and the arrival
		int distanceInX = xArrival - x;
		// Calculate the distance in "y" between the ball and the arrival
		int distanceInY = yArrival - y;
		// Using the Pythagorean theorem to calculate the distance between the ball and the destination
		// a^2 + b^2 = c^2
		/*
		 * In a right triangle, the square of the length of the hypotenuse 
		 * (the longest side) is equal to the sum of the squares of the lengths 
		 * of the other two sides. In this case, the two sides are the distances 
		 * along the x and y axes (distanceInX and distanceInY).
		 * Hence, we get the value of c (because sqrt = square root)
		 */
		double distance = Math.sqrt(distanceInX * distanceInX + distanceInY * distanceInY);
		// Dividing by 10 (number of steps)
		// If I increase the number by wich I divided distance ---> less steps
		int steps = (int) distance / 10;
		// Calculate the movement of X of each steps
		double xIncrement = (double) distanceInX / steps;
		// Calculate the movement of Y of each steps
		double yIncrement = (double) distanceInY / steps;

		for (int i = 0; i < steps; i++) {
			// Updating the position of the balls after each loop
			x += xIncrement;
			y += yIncrement;
			jPanel.repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// Set the position of the 2 balls when the loop is over
		x = xArrival;
		y = yArrival;
		jPanel.repaint();
	}

	public Ball getPartner() {
		return partner;
	}

	public void setPartner(Ball partner) {
		this.partner = partner;
	}

	/*
	 * The ball goes from starting position to the meeting point. Wait for his
	 * partner, then wait for about 3 seconds, then return to the starting position.
	 * Use the MeetPoint in order to bring the ball to the meeting point. Don't
	 * forget to synchronize the thread in order to wait TODO
	 */
	public void run() {
		int xStart = x;
		int yStart = y;
		path(xMeet, yMeet); // move the ball to the meeting point
		// Synchronized to ensure that only one ball can access this block of code at a time
		synchronized (partner) {
			try {
				partner.wait(); // wait for the partner ball to arrive
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(3000); // wait for 3 seconds
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		x = xStart;
		y = yStart;
		jPanel.repaint();
		
	}

	// draw circle base using the graphics
	public void drawCircle(Graphics g) {
		if (g == null)
			return;
		g.setColor(color);
		g.fillOval(x - diameter / 2, y - diameter / 2, diameter, diameter);
	}
}