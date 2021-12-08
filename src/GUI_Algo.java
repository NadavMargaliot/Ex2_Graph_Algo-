import javax.swing.*;
import java.awt.*;

public class GUI_Algo {
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame j = new JFrame();
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setSize(screenSize.width/2, screenSize.height/2);
        ImageIcon img = new ImageIcon("/Users/adielbenmeir/Desktop/dwg/DWGPIC");
        j.setIconImage(img.getImage());
        j.setTitle("Directed Weighted Graph");
        j.setVisible(true);
    }
}