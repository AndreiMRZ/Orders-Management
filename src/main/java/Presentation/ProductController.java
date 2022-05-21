package Presentation;

import BusinessLogic.ProductBLL;
import Model.Product;
import Main.MainClass;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductController {
    protected static final Logger LOGGER = Logger.getLogger(MainClass.class.getName());

    private ProductView productView;
    private MainView mainView;

    public ProductController(ProductView productView, MainView mainView){
        this.productView = productView;
        this.mainView = mainView;

        productView.addActionListenerAddProduct(new ActionListenerAddProduct());
        productView.addActionListenerDeleteProduct(new ActionListenerDeleteProduct());
        productView.addActionListenerUpdateProduct(new ActionListenerUpdateProduct());
        productView.addActionListenerViewProduct(new ActionListenerViewProduct());
    }
    public class ActionListenerAddProduct implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String name;
            double price = 0;
            int stock = 0;

            name = productView.getNameProdAdd();
            price = Double.parseDouble(productView.getPriceProdAdd());
            stock = Integer.parseInt(productView.getStockProdAdd());

            ProductBLL productBLL = new ProductBLL();
            Product product = new Product(name, price, stock);

            try{
                productBLL.getValidators().get(0).validate(product);

            }catch(Exception ex){
                productView.itsError(ex.getMessage());
            }
            try{
                product = productBLL.addProduct(product);
            }catch(Exception er){
                LOGGER.log(Level.INFO, er.getMessage());
            }
            /*try{
                productBLL.getValidators().get(0).validate(product);
            }catch(Exception er){
                productView.itsError(er.getMessage());
            }*/

        }
    }
    public class ActionListenerDeleteProduct implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int id = 0;
            try{
                id = Integer.parseInt(productView.getIdProdDelete());
            }catch(Exception ex){
                productView.itsError(ex.getMessage());
            }

            Product product = new Product(id);
            ProductBLL productBLL = new ProductBLL();

            try{
                productBLL.getValidators().get(0).validate(product);
            }catch(Exception ex){
               productView.itsError(ex.getMessage());
            }

            try{
                product = productBLL.deleteProduct(product);
            }catch(Exception ex){
                LOGGER.log(Level.INFO, ex.getMessage());
            }
        }
    }

    public class ActionListenerUpdateProduct implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String name;
            int id = 0, stock = 0;
            double price = 0;
            try{
                id = Integer.parseInt(productView.getIdProdUpdate());
            }catch(Exception ex){
                productView.itsError(ex.getMessage());
            }
            name = productView.getNameProdUpdate();
            stock = Integer.parseInt(productView.getStockProdUpdate());
            price = Double.parseDouble(productView.getPriceProdUpdate());
            ProductBLL productBLL = new ProductBLL();

            Product product = new Product(id, name,price,stock);
            try{
                productBLL.getValidators().get(0).validate(product);
            }catch(Exception ex){
                productView.itsError(ex.getMessage());
            }
            try{
                product = productBLL.updateProduct(product);
            }catch(Exception ex){
                LOGGER.log(Level.INFO, ex.getMessage());
            }

        }
    }
    public class ActionListenerViewProduct implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
           ProductBLL productBLL = new ProductBLL();

            DefaultTableModel defaultTableModel = new DefaultTableModel(productBLL.getListOfClients(), productBLL.getFieldNames()){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            productView.getTable1().setModel(defaultTableModel);

        }
    }
}
