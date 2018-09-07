package main;

import model.Call;

public class DispatcherAwaitOnLine extends Dispatcher {
    public DispatcherAwaitOnLine(Integer maxCalls) {
        super(maxCalls);
    }

    @Override
    public Boolean dispatchCall(Call call) {
        this.getLock().lock();
        while(!canTakeACall()){
            try {
                Thread.sleep(this.getAverageWaitTime());
            } catch (InterruptedException e) {
                //DO Nothing.
            }
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
