import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class myLabel extends JFrame implements ActionListener {
    JTextField textField;
    JLabel label;
    JButton button;
    public myLabel(JTextField txt , JLabel l , JButton b) throws HeadlessException {
        setLayout(null);
        this.textField = txt;
        this.label = l;
        this.button = b;
        this.button.setBounds(50,150,50,20);
        this.label.setBounds(50,100,500,20);
        this.textField.setBounds(50,50,100,20);
        this.add(this.textField);
        this.add(this.label);
        this.add(this.button);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
