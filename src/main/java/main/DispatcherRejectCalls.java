package main;

import model.Call;
import model.Director;
import model.Employee;
import model.Supervisor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DispatcherRejectCalls extends Dispatcher {


    public DispatcherRejectCalls(Integer maxCalls) {
        super(maxCalls);
    }

    public Boolean dispatchCall(Call call){
        this.getLock().lock();
        if(!canTakeACall()){
            return Boolean.FALSE;
        }
        this.dispatchCallToEmployee(call);
        try {
            this.increment();
        } finally {
            this.getLock().unlock();
            return Boolean.TRUE;
        }
    }


}
