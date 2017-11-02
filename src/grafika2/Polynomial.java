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
	
	public ArrayList<Point> getPoints()
	{
		ArrayList<Point> out = points;
		out.add(0, start);
		return out;
	}

	public void draw()
	{
		int i = 0;
		try
		{
			DrawPanel.g2d.drawLine((int)start.getX(), (int)start.getY(), (int)points.get(0).getX(), (int)points.get(0).getY());
			for(i = 0; i < points.size() - 1; i++)
			{
				MyImagePanel.g2d.drawLine((int)points.get(i).getX(), (int)points.get(i).getY(), 
						(int)points.get(i + 1).getX(), (int)points.get(i + 1).getY());
			}
			MyImagePanel.g2d.drawLine((int)points.get(i).getX(), (int)points.get(i).getY(), (int)start.getX(), (int)start.getY());
		}
		catch(IndexOutOfBoundsException e)
		{
			System.out.println("IndexOutOfBounds polyDraw\n");
		}
	}
	
	public boolean isInside(Point test) 
	{
		points.add(0, start);
	    int i;
	    int j;
	    boolean result = false;
	    for (i = 0, j = points.size() - 1; i < points.size(); j = i++) 
	    {
	    	if ((points.get(i).y > test.y) != (points.get(j).y > test.y) &&
	    			(test.x < (points.get(j).x - points.get(i).x) * 
	            	(test.y - points.get(i).y) / (points.get(j).y-points.get(i).y) + points.get(i).x)) 
	    	{
	    		result = !result;
	        }
	    }
	    points.remove(0);
	    return result;
	 }
}
