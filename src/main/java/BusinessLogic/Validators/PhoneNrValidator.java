package BusinessLogic.Validators;

import Model.Client;

public class PhoneNrValidator implements Validator<Client>{
    @Override
    public void validate(Client client) {
        char[] nr =  client.getPhoneNumber().toCharArray();
        for(char c : nr){
            if(!Character.isDigit(c))
                throw new IllegalArgumentException("Numar de telefon invalid");
        }

    }
}
