package grafika2;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

@SuppressWarnings("serial")
public class Renderer extends JLabel implements ListCellRenderer<String> 
{
	 
    public Renderer() 
    {
        setOpaque(true);
    }
 
    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, String text, int index,
            boolean isSelected, boolean cellHasFocus) {
 
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("cat1.bmp"));
 
        setIcon(imageIcon);
        setText(text);
 
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
 
        return this;
    }
}