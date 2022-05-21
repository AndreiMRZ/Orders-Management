package BusinessLogic.Validators;

import Model.Product;

public class ProductValidator implements Validator<Product> {
    @Override
    public void validate(Product product) {
        if(product.getId()< 0)
            throw new IllegalArgumentException("Id- ul nu este corect");
    }
}
