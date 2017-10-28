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
	
	Boolean onSegment(Point p, Point q, Point r)
	{
	    if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) &&
	            q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y))
	        return true;
	    return false;
	}
	
	int orientation(Point p, Point q, Point r)
	{
	    int val = (q.y - p.y) * (r.x - q.x) -
	              (q.x - p.x) * (r.y - q.y);
	 
	    if (val == 0) return 0;  // colinear
	    return (val > 0)? 1: 2; // clock or counterclock wise
	}
	
	Boolean doIntersect(Point p1, Point q1, Point p2, Point q2)
	{
	    int o1 = orientation(p1, q1, p2);
	    int o2 = orientation(p1, q1, q2);
	    int o3 = orientation(p2, q2, p1);
	    int o4 = orientation(p2, q2, q1);
	 
	    // General case
	    if (o1 != o2 && o3 != o4)
	        return true;
	 
	    // Special Cases
	    // p1, q1 and p2 are colinear and p2 lies on segment p1q1
	    if (o1 == 0 && onSegment(p1, p2, q1)) return true;
	 
	    // p1, q1 and p2 are colinear and q2 lies on segment p1q1
	    if (o2 == 0 && onSegment(p1, q2, q1)) return true;
	 
	    // p2, q2 and p1 are colinear and p1 lies on segment p2q2
	    if (o3 == 0 && onSegment(p2, p1, q2)) return true;
	 
	     // p2, q2 and q1 are colinear and q1 lies on segment p2q2
	    if (o4 == 0 && onSegment(p2, q1, q2)) return true;
	 
	    return false; // Doesn't fall in any of the above cases
	}
	
	Boolean isInside2(Point p)
	{
	    // Create a point for line segment from p to infinite
	    Point extreme = new Point((int)Double.POSITIVE_INFINITY, p.y);
	 
	    // Count intersections of the above line with sides of polygon
	    /*int count = 0;
	    
	    if (doIntersect(start, points.get(0), p, extreme))
        {
	    	if (orientation(start, p, points.get(0)) == 0)
    		{
    			return onSegment(start, p, points.get(0));
    		}
	    	count++;
        }
	    for(int i = 0; i < points.size() - 1; i++)
	    {
	    	if (doIntersect(points.get(i), points.get(i + 1), p, extreme))
	        {
	    		if (orientation(points.get(i), p, points.get(i + 1)) == 0)
	    		{
	    			return onSegment(points.get(i), p, points.get(i + 1));
	    		}
	    		count++;
	        }
	    }
	    if (doIntersect(points.get(points.size() - 1), start, p, extreme))
	    {
	    	if (orientation(points.get(points.size() - 1), p, start) == 0)
    		{
    			return onSegment(points.get(points.size() - 1), p, start);
    		}
	    	count++;
	    }*/
	    //points.add(0, start);
	    int count = 0, i = 0;
	    do
	    {
	        int next = (i+1)%(points.size());
	 
	        // Check if the line segment from 'p' to 'extreme' intersects
	        // with the line segment from 'polygon[i]' to 'polygon[next]'
	        if (doIntersect(points.get(i), points.get(next), p, extreme))
	        {
	            // If the point 'p' is colinear with line segment 'i-next',
	            // then check if it lies on segment. If it lies, return true,
	            // otherwise false
	            if (orientation(points.get(i), p, points.get(next)) == 0)
	               return onSegment(points.get(i), p, points.get(next));
	 
	            count++;
	        }
	        i = next;
	    } while (i != 0);
	    
	    //points.remove(0);
	    
	    return (count%2 == 1);
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
