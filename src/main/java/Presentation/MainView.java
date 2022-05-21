package Presentation;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame{
    private JPanel panel1;
    private JButton clientButton;
    private JButton productButton;
    private JButton orderButton;
    private JFrame frame;

    public MainView(){
        frame = new JFrame("Main");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public JFrame getFrame(){
        return frame;
    }
    public void addActionListenerClient(ActionListener e){clientButton.addActionListener(e);}
    public void addActionListenerProduct(ActionListener e){productButton.addActionListener(e);}
    public void addActionListenerOrder(ActionListener e){orderButton.addActionListener(e);}
}
