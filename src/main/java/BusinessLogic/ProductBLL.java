package BusinessLogic;

import BusinessLogic.Validators.ProductValidator;
import BusinessLogic.Validators.Validator;
import DataAccess.ProductDAO;
import Model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {

    private List<Validator<Product>> validators;
    private ProductDAO productDAO;

    public ProductBLL(){

        validators = new ArrayList<Validator<Product>>();
        validators.add(new ProductValidator());
        productDAO = new ProductDAO();
    }

    public Product findProductById(int id){
        Product pr = productDAO.findById(id);
        if(pr == null){
            throw new NoSuchElementException("The product with id " + id + " was not found");
        }
    return pr;
    }

    public String[] getFieldNames(){
        String[] toReturn = productDAO.takeFieldNames();
        return toReturn;
    }

    public String[][] getListOfClients(){
        String[][] toReturn = productDAO.listOfObject(productDAO.findAll());
        return toReturn;
    }

    public Product updateProduct(Product product){
        Product toReturn = productDAO.update(product);

        if(product == null)
            throw new NoSuchElementException("Produsul cu id-ul : " + toReturn.getId() + " nu poate fi modificat");
        return toReturn;
    }

    public Product deleteProduct(Product product){
        Product toReturn = productDAO.delete(product);

        if(product == null)
            throw new NoSuchElementException("Produsul cu id-ul : " + toReturn.getId() + " nu poate fi sters");
        return toReturn;
    }

    public Product addProduct(Product product){
        Product toReturn = productDAO.insert(product);

        if(product == null)
            throw new NoSuchElementException("Produsul cu id-ul : " + toReturn.getId() + " nu poate fi adaugat");
        return toReturn;
    }

    public List<Validator<Product>> getValidators(){
        return validators;
    }
}
