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
		frame.setSize(800, 500);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}
}