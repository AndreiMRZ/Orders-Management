package DataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Connection.ConnectionFactory;

public class AbstractDAO<T> {

    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public String[] takeFieldNames(){
        int k = 0;
        String [] toReturn = new String[type.getDeclaredFields().length];
        for(Field field : type.getDeclaredFields())
            toReturn[k++] = field.getName();
        return toReturn;
    }

    public String[][] listOfObject(List<T> t){
        String[][] toReturn = new String[t.size()][t.get(0).getClass().getDeclaredFields().length];
        int i = 0, j = 0;

        for(T x : t){
            for(Field field : x.getClass().getDeclaredFields()){
                field.setAccessible(true);
                try{
                    toReturn[i][j++] = String.valueOf(field.get(x));
                }catch ( IllegalAccessException e){
                    throw new RuntimeException(e);
                }
            }
            i++;
            j = 0;
        }
        return toReturn;
    }

    private String createSelectAll(String field){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM ");
        stringBuilder.append(field);

        return stringBuilder.toString();
    }
    public List<T> findAll(){
        String string = createSelectAll(type.getSimpleName());

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(string);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findALL " + e.getMessage());

        }finally{
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private String createSelectQuery(String field){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("SELECT ");
        stringBuilder.append(" * ");
        stringBuilder.append(" FROM ");
        stringBuilder.append(type.getSimpleName());
        stringBuilder.append(" WHERE " + field + " =? ");

        return stringBuilder.toString();
    }

    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    System.out.println(fieldName + " " );

                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    private String createAddQuery(){
        StringBuilder stringBuilder =new StringBuilder();
        stringBuilder.append("CALL add");
        stringBuilder.append(type.getSimpleName());
        stringBuilder.append("(");

        return stringBuilder.toString();
    }

    private String createSelectLast(String field){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM ");
        stringBuilder.append(field);
        stringBuilder.append(" ORDER BY id DESC Limit 1");

        return stringBuilder.toString();
    }
    public T insert(T t){

        String query = createAddQuery();
        int i = 1;
        Field[] fields = type.getDeclaredFields();
        while(i < fields.length - 1){
            fields[i].setAccessible(true);
            try{
                query +="'" +  fields[i].get(t) + "',";
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            fields[i].setAccessible(false);
            i++;
        }
        try{
            fields[i].setAccessible(true);
            query += "'" + fields[i].get(t) + "');";
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
        fields[i].setAccessible(false);

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            System.out.println(query);
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeQuery();
            statement = connection.prepareStatement(createSelectLast(t.getClass().getSimpleName()));
            System.out.println(createSelectLast(t.getClass().getSimpleName()));
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e){
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());

        }finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }
    private String createUpdateQuery(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CALL modify");
        stringBuilder.append(type.getSimpleName());
        stringBuilder.append("(");

        return stringBuilder.toString();
    }
    public T update(T t){

        String query = createUpdateQuery();
        //query += "";
        int i = 0;
        Field[] fields = type.getDeclaredFields();

        while(i < fields.length - 1){
            fields[i].setAccessible(true);
            try{

                query += "'" + fields[i].get(t) + "',";
            }catch(IllegalArgumentException e){
                e.printStackTrace();
            }catch(IllegalAccessException e){
                e.printStackTrace();
            }
                fields[i].setAccessible(false);
            i++;
        }
        try{
            fields[i].setAccessible(true);
            query += "'" + fields[i].get(t) + "');";
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }catch(IllegalAccessException e){
            e.printStackTrace();
        }
        int idclient;
        fields[0].setAccessible(true);
        try{
            idclient = (Integer) fields[0].get(t);
        }catch(IllegalAccessException e){
            throw new RuntimeException(e);
        }
        fields[0].setAccessible(false);

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeQuery();
            statement = connection.prepareStatement(createSelectQuery("id"));
            statement.setInt(1, idclient);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        }catch(SQLException e){
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        }finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private String createDeleteQuery(String field){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DELETE FROM ");
        stringBuilder.append(type.getSimpleName());
        stringBuilder.append(" WHERE " + field + " =?");

        return stringBuilder.toString();
    }
    public T delete(T t){
        String query = createDeleteQuery("id");

        int id;
        Field[] fields = type.getDeclaredFields();

        fields[0].setAccessible(true);

        try{
            id = (int) fields[0].get(t);
        }catch(IllegalAccessException e){
            throw new RuntimeException(e);
        }
        fields[0].setAccessible(false);

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(createSelectQuery("id"));
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
}
