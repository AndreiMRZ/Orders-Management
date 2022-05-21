package BusinessLogic;

import BusinessLogic.Validators.EmailValidator;
import BusinessLogic.Validators.PhoneNrValidator;
import BusinessLogic.Validators.Validator;
import DataAccess.ClientDAO;
import Model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {
   private ClientDAO clientDAO;
    private List<Validator<Client>> validators;

    public ClientBLL(){
        validators = new ArrayList<Validator<Client>>();
        validators.add(new EmailValidator());
        validators.add(new PhoneNrValidator());
        clientDAO = new ClientDAO();
    }

    public Client findClientById(int id){
        Client cl = clientDAO.findById(id);

        if(cl == null){
            throw new NoSuchElementException("The client with id=" + id + " was not found!");

        }
       return cl;
    }

    public String[] getFieldNames(){
        String[] toReturn = clientDAO.takeFieldNames();
        return toReturn;
    }

    public String[][] getlistOfClients(){
        String[][] toReturn = clientDAO.listOfObject(clientDAO.findAll());
        return toReturn;
    }

    public Client addClient(Client client){
        Client toReturn = clientDAO.insert(client);
        if(toReturn == null)
            throw new NoSuchElementException("Clientul cu id-ul " + toReturn.getId() + " nu poate fi adaugat");
        return toReturn;
    }

    public Client deleteClient(Client client){
        Client toReturn = clientDAO.delete(client);
        if(toReturn == null)
            throw new NoSuchElementException("Clientul cu id-ul " + toReturn.getId() + " nu poate fi sters");
       return toReturn;
    }

    public Client updateClient(Client client){
        Client toReturn = clientDAO.update(client);
        if(toReturn == null)
            throw new NoSuchElementException("Clientul cu id-ul " + toReturn.getId() + " nu poate fi modificat");
        return toReturn;
    }

    public List<Validator<Client>> getValidators(){
        return validators;
    }

}
