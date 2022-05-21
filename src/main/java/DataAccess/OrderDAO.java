package DataAccess;

import Model.Client;
import Model.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import Connection.ConnectionFactory;
import Model.Product;

public class OrderDAO extends AbstractDAO<Orders>{

    public boolean checkStock(Orders orders){
        String string = "SELECT stock FROM product WHERE product.id =  " + orders.getIdProduct();

        int stock = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(string);
            resultSet = statement.executeQuery();

            if(resultSet.next()){
                stock = Integer.parseInt(resultSet.getString("stock"));
            }
        }catch(SQLException e){
            LOGGER.log(Level.WARNING, "DAO:Order " + e.getMessage());
        }finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return stock >= orders.getQuantity();
    }


    public String orderTxt(Orders orders, Product product, Client client){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Order");
        stringBuilder.append("\nClient Name:" + client.getName());
        stringBuilder.append("\nProduct Name:" + product.getName());
        stringBuilder.append("\nCantitate :"+ orders.getQuantity());
        stringBuilder.append("\nPrice:"+ product.getPrice());
        stringBuilder.append("\nTotal Price:" + product.getPrice()*orders.getQuantity());
        return stringBuilder.toString();
    }
}
