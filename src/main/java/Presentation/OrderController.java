package Presentation;

import BusinessLogic.ClientBLL;
import BusinessLogic.OrderBLL;
import BusinessLogic.ProductBLL;
import DataAccess.OrderDAO;
import Model.Client;
import Model.Orders;
import Model.Product;
import com.sun.tools.javac.Main;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderController {
    protected static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private OrderView orderView;
    private MainView mainView;

    public OrderController(OrderView orderView, MainView mainView){
        this.mainView = mainView;
        this.orderView = orderView;

        orderView.addActionListenerAddOrder(new ActionListenerAddOrder());
        orderView.addActionListenerDeleteOrder(new ActionListenerDeleteOrder());
        orderView.addActionListenerUpdateOrder(new ActionListenerUpdateOrder());
        //orderView.addActionListenerAddOrder(new ActionListenerViewOrder());
    }
    public class ActionListenerAddOrder implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int quantity = 0, id_product = 0, id_client = 0;

                try {
                    quantity = Integer.parseInt(orderView.getIdQuantityOrdAdd());
                    id_product = Integer.parseInt(orderView.getIdProductOrdAdd());
                    id_client = Integer.parseInt(orderView.getIdClientOrdAdd());
                }catch (Exception ex){
                    orderView.itsError(ex.getMessage());
                }

            Orders orders = new Orders(id_client, id_product, quantity);
            OrderBLL orderBLL = new OrderBLL();

            ProductBLL productBLL = new ProductBLL();
            ClientBLL clientBLL = new ClientBLL();

            if(orderBLL.checkStock(orders)){
                orders = orderBLL.addOrder(orders);

                PrintWriter printWriter = null;
                try{
                    printWriter = new PrintWriter("order" + orders.getId() + " .txt");
                }catch(FileNotFoundException ex){
                    throw new RuntimeException(ex);
                }
                Product product = productBLL.findProductById(orders.getIdProduct());
                Client client = clientBLL.findClientById(orders.getIdClient());
                printWriter.print(orderBLL.orderTxt(orders, product, client));

                printWriter.close();
            }
            else orderView.itsError("Stock insuficient");

        }


    }
    public class ActionListenerDeleteOrder implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(orderView.getIdOrdDelete());
            OrderDAO orderDAO = new OrderDAO();
            Orders orders = orderDAO.findById(id);

            OrderBLL orderBLL = new OrderBLL();
            try {
                orderBLL.getValidators().get(0).validate(orders);
            }
            catch (Exception exception){
                orderView.itsError(exception.getMessage());
            }

            try{
                orders = orderBLL.deleteOrder(orders);
            } catch (Exception ex) {
                LOGGER.log(Level.INFO, ex.getMessage());
            }

        }
    }
    public class ActionListenerUpdateOrder implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int id =0,quantity;
            try{
                id = Integer.parseInt(orderView.getIdOrdUpdate());
            }catch(Exception ex){
                orderView.itsError(ex.getMessage());
            }
            quantity = Integer.parseInt(orderView.getQuantityOrdUpdate());

            Orders orders = new Orders(id,quantity);
            OrderBLL orderBLL = new OrderBLL();
            try{
                orderBLL.getValidators().get(0).validate(orders);
            }catch(Exception ex){
                LOGGER.log(Level.INFO, ex.getMessage());
            }

            try{
                orders = orderBLL.updateOrder(orders);
            }catch(Exception ex){
                LOGGER.log(Level.INFO, ex.getMessage());
            }


        }
    }
    public class ActionListenerViewOrder implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            OrderBLL ordersBLL = new OrderBLL();

            DefaultTableModel defaultTableModel = new DefaultTableModel(ordersBLL.getListOfOrders(), ordersBLL.getFieldNmaes()){
                @Override
                public boolean isCellEditable(int row, int column){
                    return false;
                }
            };
            orderView.getTable1().setModel(defaultTableModel);
        }
        }
    }

