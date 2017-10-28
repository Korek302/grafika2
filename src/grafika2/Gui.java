package grafika2;

import javax.swing.JFrame;

//todo
//clipImage (?)
//rozbic na klasy (?) rect, oval, poly (?)
//rysowanie tylko na obrazku

public class Gui 
{
	JFrame frame;
	public static void main(String[] args)
	{
		Gui gui = new Gui();
		gui.GUI();
	}
	
	private void GUI()
	{
		frame = new JFrame();
		DrawPanel panel = new DrawPanel();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("lab2");
		frame.setSize(800, 540);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}
	
	static int int2RGB( int red, int green, int blue)
	{
		red = red & 0x000000FF;
		green = green & 0x000000FF;
		blue = blue & 0x000000FF;
		return (red << 16) + (green << 8) + blue;
	}
}

