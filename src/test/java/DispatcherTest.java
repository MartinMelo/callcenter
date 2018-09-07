import main.Dispatcher;
import modelTest.Caller;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public abstract class DispatcherTest{

    private Dispatcher dispatcher;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void consoleOutToStream(){
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanConsoleOutSaved(){
        this.outContent.reset();
    }

    @Test
    public void oneCall(){
        new Caller(this.dispatcher).start();
    }
    @Test
    public void nCalls(){
        for(int i=0;i<1000;i++){
            new Caller(this.dispatcher).start();
        }
    }

    //GETTERS && SETTERS
    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public ByteArrayOutputStream getOutContent() {
        return outContent;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
}
