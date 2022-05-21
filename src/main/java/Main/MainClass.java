package Main;

import Presentation.*;

import java.sql.SQLException;
import java.util.logging.Logger;

public class MainClass {
    protected static final Logger LOGGER = Logger.getLogger(MainClass.class.getName());
    public static void main(String [] args) throws SQLException {
        MainView obj = new MainView();
        ClientView clientView = new ClientView();
        ProductView productView = new ProductView();
        OrderView orderView = new OrderView();

        MainClassController mainClassController = new MainClassController(obj,clientView, productView, orderView);
        ClientController clientController = new ClientController(clientView, obj);
        ProductController productController = new ProductController(productView, obj);
        OrderController orderController = new OrderController(orderView, obj);

    }
}
