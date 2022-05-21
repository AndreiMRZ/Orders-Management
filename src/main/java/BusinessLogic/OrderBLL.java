package BusinessLogic;

import BusinessLogic.Validators.OrderValidatorForClient;
import BusinessLogic.Validators.Validator;
import DataAccess.OrderDAO;
import Model.Client;
import Model.Orders;
import Model.Product;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class OrderBLL {
        private List<Validator<Orders>> validators;
        private OrderDAO orderDAO;

        public OrderBLL(){
          validators = new ArrayList<Validator<Orders>>();
          validators.add(new OrderValidatorForClient());
          orderDAO = new OrderDAO();
        }

        public String orderTxt(Orders orders, Product product, Client client){
            return orderDAO.orderTxt(orders, product, client);
        }

        public boolean checkStock(Orders orders){
            return orderDAO.checkStock(orders);
        }

        public String[] getFieldNmaes(){
            String[] toReturn = orderDAO.takeFieldNames();
            return toReturn;
        }

        public String[][] getListOfOrders(){
            String[][] toReturn = orderDAO.listOfObject(orderDAO.findAll());
            return toReturn;
        }
        public Orders updateOrder(Orders orders){
            Orders toReturn = orderDAO.update(orders);

            if(toReturn == null)
                throw new NoSuchElementException("Clientul cu id-ul: " + toReturn.getId() + " nu poate fi modificat");
            return toReturn;
        }

        public Orders deleteOrder(Orders orders){
            Orders toReturn = orderDAO.delete(orders);

            if(toReturn == null)
                throw new NoSuchElementException("Clientul cu id-ul: " + toReturn.getId() + " nu poate fi sters");
            return toReturn;
        }

        public Orders addOrder(Orders orders){
            Orders toReturn = orderDAO.insert(orders);

            if(toReturn == null)
                throw new NoSuchElementException("Clientul cu id-ul: " + toReturn.getId() + " nu poate fi adaugat");
            return toReturn;
        }

        public List<Validator<Orders>> getValidators(){
            return validators;
        }
}
