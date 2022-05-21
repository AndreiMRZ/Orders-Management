package Presentation;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ProductView extends JFrame {
    private JPanel panel1;
    private JTextField nameProdAdd;
    private JTextField priceProdAdd;
    private JTextField stockProdAdd;
    private JButton addProductButton;
    private JTextField idProdUpdate;
    private JTextField nameProdUpdate;
    private JTextField priceProdUpdate;
    private JTextField stockProdUpdate;
    private JButton updateProductButton;
    private JTextField idProdDelete;
    private JButton deleteProductButton;
    private JTable table1;
    private JButton viewTableButton;
    private JFrame frame;

    public ProductView(){
        frame = new JFrame("Product");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(false);
    }
    public JFrame getFrame(){
        return frame;
    }
    public String getNameProdAdd() {
        return nameProdAdd.getText();
    }

    public String getPriceProdAdd() {
        return priceProdAdd.getText();
    }

    public String getStockProdAdd() {
        return stockProdAdd.getText();
    }

    public String getIdProdUpdate() {
        return idProdUpdate.getText();
    }

    public String getNameProdUpdate() {
        return nameProdUpdate.getText();
    }

    public String getPriceProdUpdate() {
        return priceProdUpdate.getText();
    }

    public String getStockProdUpdate() {
        return stockProdUpdate.getText();
    }

    public String getIdProdDelete() {
        return idProdDelete.getText();
    }

    public JTable getTable1() {
        return table1;
    }

    public void setTable1(JTable table1) {
        this.table1 = table1;
    }

    public void addActionListenerAddProduct(ActionListener e){addProductButton.addActionListener(e);}
    public void addActionListenerUpdateProduct(ActionListener e){updateProductButton.addActionListener(e);}
    public void addActionListenerDeleteProduct(ActionListener e){deleteProductButton.addActionListener(e);}
    public void addActionListenerViewProduct(ActionListener e){viewTableButton.addActionListener(e);}
    public void itsError(String error){JOptionPane.showMessageDialog(this, error);}
}
