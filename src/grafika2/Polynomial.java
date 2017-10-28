package grafika2;

import java.awt.Point;
import java.util.ArrayList;

public class Polynomial
{
	Point start;
	ArrayList<Point> points;
	
	public Polynomial()
	{
		start = new Point(0, 0);
		points = new ArrayList<Point>();
	}
	
	public Polynomial(Point start, ArrayList<Point> arr)
	{
		points = arr;
		this.start = start;
	}

	public void draw()
	{
		int i = 0;
		try
		{
			DrawPanel.g2d.drawLine((int)start.getX(), (int)start.getY(), (int)points.get(0).getX(), (int)points.get(0).getY());
			for(i = 0; i < points.size() - 1; i++)
			{
				DrawPanel.g2d.drawLine((int)points.get(i).getX(), (int)points.get(i).getY(), 
						(int)points.get(i + 1).getX(), (int)points.get(i + 1).getY());
			}
			DrawPanel.g2d.drawLine((int)points.get(i).getX(), (int)points.get(i).getY(), (int)start.getX(), (int)start.getY());
		}
		catch(IndexOutOfBoundsException e)
		{
			System.out.println("IndexOutOfBounds polyDraw\n");
		}
	}
}
