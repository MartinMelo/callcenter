package main;

import model.Call;

public class DispatcherRejectCalls extends Dispatcher {


    public DispatcherRejectCalls(Integer maxCalls) {
        super(maxCalls);
    }

    /**
     * this will reject the call is there is no available employee
     * or if the maximum call is reached.
     * @param call
     * @return
     */
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
