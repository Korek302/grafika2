package grafika2;

import javax.swing.JFrame;

//To do z tego co jest w mouse released/draged zrobic metody/fcje np drawRect itp (?)
//polynomial
//jak sie uda to wywalic troche globalnych i porobic milion metod
//rozbic na klasy (?) rect, oval, poly (?)

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