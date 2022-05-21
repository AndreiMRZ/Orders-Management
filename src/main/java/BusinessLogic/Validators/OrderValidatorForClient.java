package BusinessLogic.Validators;

import Model.Orders;

public class OrderValidatorForClient implements Validator<Orders>{

    @Override
    public void validate(Orders orders) {
        if(orders.getId() < 0)
            throw new IllegalArgumentException("Id-ul nu este corect");
    }
}
