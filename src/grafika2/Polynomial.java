package grafika2;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Polynomial
{
	Point start;
	ArrayList<Point> points;
	Graphics2D g;
	
	public Polynomial()
	{
		start = new Point(0, 0);
		points = new ArrayList<Point>();
	}
	
	public Polynomial(Point start, ArrayList<Point> arr, Graphics2D g)
	{
		points = arr;
		this.g = g;
		this.start = start;
	}

	public void draw()
	{
		int i = 0;
		try
		{
			g.drawLine((int)start.getX(), (int)start.getY(), (int)points.get(0).getX(), (int)points.get(0).getY());
			for(i = 0; i < points.size() - 1; i++)
			{
				g.drawLine((int)points.get(i).getX(), (int)points.get(i).getY(), 
						(int)points.get(i + 1).getX(), (int)points.get(i + 1).getY());
			}
			g.drawLine((int)points.get(i).getX(), (int)points.get(i).getY(), (int)start.getX(), (int)start.getY());
		}
		catch(IndexOutOfBoundsException e)
		{
			System.out.println("IndexOutOfBounds polyDraw\n");
		}
	}
}
