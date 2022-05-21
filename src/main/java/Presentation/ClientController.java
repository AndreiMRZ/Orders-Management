package Presentation;

import BusinessLogic.ClientBLL;
import Model.Client;
import com.sun.tools.javac.Main;
import Main.MainClass;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientController {
    private ClientView clientView;
    private MainView mainView;
    protected static final Logger LOGGER = Logger.getLogger(MainClass.class.getName());

    public ClientController(ClientView clientView, MainView mainView){
        this.clientView = clientView;
        this.mainView = mainView;


        clientView.addActionListenerAddClient(new ActionListenerAddClient());
        clientView.addActionListenerDeleteClient(new ActionListenerDeleteClient());
        clientView.addActionListenerUpdateClient(new ActionListenerUpdateClient());
        clientView.addActionListenerViewTableButton(new ActionListenerViewTableClient());
    }

   public class ActionListenerAddClient implements ActionListener{

       @Override
       public void actionPerformed(ActionEvent e) {
           String name,address,email,phoneNumber;

           name = clientView.getNameAdd();
           address =clientView.getAddressAdd();
           email = clientView.getEmailAdd();
           phoneNumber = clientView.getPhoneNumberAdd();

           ClientBLL clientBLL = new ClientBLL();
           Client client = new Client(name, address, email, phoneNumber);

          /* try{
               clientBLL.getValidators().get(0).validate(client);
               clientBLL.getValidators().get(1).validate(client);
           }catch(Exception er){
               clientView.itsError(er.getMessage());
               return;
           }*/
           try{
               client = clientBLL.addClient(client);
           }catch(Exception er){
               LOGGER.log(Level.INFO, er.getMessage());
           }
           /*try{
               clientBLL.getValidators().get(0).validate(client);
           }catch(Exception er){
               clientView.itsError(er.getMessage());
           }*/
       }
   }

   public class ActionListenerDeleteClient implements ActionListener{

       @Override
       public void actionPerformed(ActionEvent e) {
           int id = 0;
           try{
               id = Integer.parseInt(clientView.getIdDelete());
           }catch(Exception ex){
               clientView.itsError(ex.getMessage());
           }
           Client client = new Client(id);
           ClientBLL clientBLL = new ClientBLL();
           try {
               clientBLL.getValidators().get(0).validate(client);
           }catch (Exception ex){
               clientView.itsError(ex.getMessage());
           }
           try{
               client = clientBLL.deleteClient(client);
           } catch(Exception ex){
               LOGGER.log(Level.INFO, ex.getMessage());
           }
       }
   }
   public class ActionListenerUpdateClient implements ActionListener{

       @Override
       public void actionPerformed(ActionEvent e) {
           String name, email, address, phoneNumber;
           int id = 0;
           try{
               id = Integer.parseInt(clientView.getIdUpdate());
           }catch(Exception ex){
               clientView.itsError(ex.getMessage());
           }
           name = clientView.getNameUpdate();
           email = clientView.getEmailUpdate();
           address = clientView.getAddressUpdate();
           phoneNumber = clientView.getPhoneNumberUpdate();

           Client client = new Client(id, name, address, email, phoneNumber);
           ClientBLL clientBLL = new ClientBLL();

           try{
               clientBLL.getValidators().get(1).validate(client);
               //clientBLL.getValidators().get(1).validate(client);
               //clientBLL.getValidators().get(2).validate(client);
           }catch (Exception ex){
               clientView.itsError(ex.getMessage());
           }
           try{
               client = clientBLL.updateClient(client);
           }catch(Exception ex){
               LOGGER.log(Level.INFO, ex.getMessage());
           }
       }
   }

   public class ActionListenerViewTableClient implements ActionListener{

       @Override
       public void actionPerformed(ActionEvent e) {
           ClientBLL clientBLL = new ClientBLL();

           DefaultTableModel defaultTableModel = new DefaultTableModel(clientBLL.getlistOfClients(), clientBLL.getFieldNames()){
               @Override
               public boolean isCellEditable(int row, int column) {
                   return false;
               }
           };
           clientView.getTable1().setModel(defaultTableModel);

       }
   }
}
