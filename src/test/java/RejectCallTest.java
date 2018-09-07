import main.Dispatcher;
import main.DispatcherRejectCalls;
import model.Director;
import model.Operator;
import model.Supervisor;
import modelTest.Caller;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class RejectCallTest  extends DispatcherTest{
    @Before
    public void create() {
        this.setDispatcher(new DispatcherRejectCalls(10));
        for(int i=0;i<10;i++){
            if(i%5==0){
                this.getDispatcher().addNewEmployee(new Director());
            }else if(i%3==0){
                this.getDispatcher().addNewEmployee(new Supervisor());
            }
            else{
                this.getDispatcher().addNewEmployee(new Operator());
            }
        }

    }

    @Test
    public void oneCall(){
        new Caller(this.getDispatcher()).start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(this.getOutContent().toString().contains("Accepted"));
    }
    @Test
    public void nCalls(){
        for(int i=0;i<100;i++){
            new Caller(this.getDispatcher()).start();
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(this.getOutContent().toString().contains("Rejected"));
    }
}
