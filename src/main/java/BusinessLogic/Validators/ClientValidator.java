package BusinessLogic.Validators;

import Model.Client;

public class ClientValidator implements Validator<Client> {
    @Override
    public void validate(Client client) {
        if(client.getId()<1)
            throw new IllegalArgumentException("The id is invalid");
    }
}
