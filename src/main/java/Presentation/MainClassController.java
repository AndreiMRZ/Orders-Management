package Presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainClassController {
    private MainView mainView;
    private ClientView clientView;
    private ProductView productView;
    private OrderView orderView;

    public MainClassController(MainView mainView, ClientView clientView, ProductView productView, OrderView orderView){
        this.mainView = mainView;
        this.clientView = clientView;
        this.productView = productView;
        this.orderView = orderView;

        mainView.addActionListenerClient(new ActionListenerClient());
        mainView.addActionListenerProduct(new ActionListenerProduct());
        mainView.addActionListenerOrder(new ActionListenerOrder());
    }

    public MainClassController(MainView mainView, ClientView clientView) {
        this.clientView = clientView;
        this.mainView = mainView;
        mainView.addActionListenerClient(new ActionListenerClient());
    }

    public class ActionListenerClient implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            mainView.getFrame().setVisible(false);
            clientView.getFrame().setVisible(true);
        }
    }
    public class ActionListenerProduct implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            mainView.getFrame().setVisible(false);
            productView.getFrame().setVisible(true);
        }
    }
    public class ActionListenerOrder implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            mainView.getFrame().setVisible(false);
            orderView.getFrame().setVisible(true);
        }
    }
}
