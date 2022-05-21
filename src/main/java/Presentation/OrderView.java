package Presentation;

import javax.swing.*;
import java.awt.event.ActionListener;

public class OrderView extends JFrame {
    private JPanel panel1;
    private JTextField idOrdAdd;
    private JTextField idClientOrdAdd;
    private JTextField idProductOrdAdd;
    private JTextField idQuantityOrdAdd;
    private JButton addOrderButton;
    private JTextField idOrdUpdate;
    private JTextField quantityOrdUpdate;
    private JButton updateOrderButton;
    private JTextField idOrdDelete;
    private JButton deleteOrderButton;
    private JTable table1;
    private JButton viewTableButton;
    private JFrame frame;

    public OrderView(){
        frame = new JFrame("Order");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(false);
    }
    public JFrame getFrame(){
        return frame;
    }
    public String getIdOrdAdd() {
        return idOrdAdd.getText();
    }

    public String getIdClientOrdAdd() {
        return idClientOrdAdd.getText();
    }

    public String getIdProductOrdAdd() {
        return idProductOrdAdd.getText();
    }

    public String getIdQuantityOrdAdd() {
        return idQuantityOrdAdd.getText();
    }

    public String getIdOrdUpdate() {
        return idOrdUpdate.getText();
    }

    public String getQuantityOrdUpdate() {
        return quantityOrdUpdate.getText();
    }

    public String getIdOrdDelete() {
        return idOrdDelete.getText();
    }

    public JTable getTable1() {
        return table1;
    }

    public void setTable1(JTable table1) {
        this.table1 = table1;
    }

    public void addActionListenerAddOrder(ActionListener e){
        addOrderButton.addActionListener(e);}
    public void addActionListenerUpdateOrder(ActionListener e){
        updateOrderButton.addActionListener(e);}
    public void addActionListenerDeleteOrder(ActionListener e){deleteOrderButton.addActionListener(e);}
    public void addActionListenerViewOrder(ActionListener e){viewTableButton.addActionListener(e);}
    public void itsError(String error){JOptionPane.showMessageDialog(this, error);}
}
