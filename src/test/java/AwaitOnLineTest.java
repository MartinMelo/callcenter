
import main.DispatcherAwaitOnLine;
import model.Director;
import model.Operator;
import model.Supervisor;
import modelTest.Caller;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class AwaitOnLineTest extends DispatcherTest{

    @Before
    public void setUp() throws Exception {
        this.setDispatcher(new DispatcherAwaitOnLine(10));
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
        Assert.assertFalse(this.getOutContent().toString().contains("Rejected"));
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
        Assert.assertFalse(this.getOutContent().toString().contains("Rejected"));
        Assert.assertTrue(this.getOutContent().toString().contains("Accepted"));
    }

}
