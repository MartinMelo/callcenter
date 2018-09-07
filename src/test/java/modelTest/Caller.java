package modelTest;

import main.Dispatcher;
import model.Call;

import java.util.Random;

public class Caller  extends Thread{

    private Dispatcher dispatcher;
    public Caller(Dispatcher dispatcher) {
        super();
        this.dispatcher= dispatcher;
    }

    @Override
    public void run() {
        Boolean accepted = this.dispatcher.dispatchCall(new Call(new Random().nextInt(2000)+2000));
        System.out.println("Phone Call " + (accepted  ? "Accepted" : "Rejected"));
    }
}
