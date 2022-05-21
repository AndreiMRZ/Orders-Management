package Presentation;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ClientView extends JFrame {
    private JPanel panel1;
    private JTextField nameAdd;
    private JTextField addressAdd;
    private JTextField emailAdd;
    private JTextField phoneNumberAdd;
    private JButton addClientButton;
    private JTextField idUpdate;
    private JTextField nameUpdate;
    private JTextField addressUpdate;
    private JTextField emailUpdate;
    private JTextField phoneNumberUpdate;
    private JButton updateClientButton;
    private JTextField idDelete;
    private JButton deleteClientButton;
    private JTable table1;
    private JButton viewTableButton;
    private JFrame frame;

    public ClientView () {
        frame = new JFrame("Client");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(false);

    }
    public JFrame getFrame(){
        return frame;
    }

    public String getNameAdd() {
        return nameAdd.getText();
    }

    public String getAddressAdd() {
        return addressAdd.getText();
    }

    public String getEmailAdd() {
        return emailAdd.getText();
    }

    public String getPhoneNumberAdd() {
        return phoneNumberAdd.getText();
    }

    public String getIdUpdate() {
        return idUpdate.getText();
    }

    public String getNameUpdate() {
        return nameUpdate.getText();
    }

    public String getAddressUpdate() {
        return addressUpdate.getText();
    }

    public String getEmailUpdate() {
        return emailUpdate.getText();
    }

    public String getPhoneNumberUpdate() {
        return phoneNumberUpdate.getText();
    }

    public String getIdDelete() {
        return idDelete.getText();
    }

    public JTable getTable1() {
        return table1;
    }

    public void setTable1(JTable table1) {
        this.table1 = table1;
    }

    public void addActionListenerAddClient(ActionListener e){
        addClientButton.addActionListener(e);
    }
    public void addActionListenerUpdateClient(ActionListener e) {updateClientButton.addActionListener(e);}
    public void addActionListenerDeleteClient(ActionListener e){deleteClientButton.addActionListener(e);}
    public void addActionListenerViewTableButton(ActionListener e){viewTableButton.addActionListener(e);}

    public void itsError(String error){JOptionPane.showMessageDialog(this, error);}
}
