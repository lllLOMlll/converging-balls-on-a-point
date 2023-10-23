package exam;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Class to draw the square
 * @author Mushu
 *
 */
public class Square {
	private int x, y, side;
	Color color;

	public Square(int x, int y, int side, Color color) {
		this.x = x;
		this.y = y;
		this.side = side;
		this.color = color;
	}

	public void drawSquare(Graphics g) {
		if (g == null)
			return;
		
		g.setColor(color);
		g.drawRect(x - side / 2, y - side / 2, side, side);
	}
}