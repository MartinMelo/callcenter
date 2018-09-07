package model;

import main.Dispatcher;

public abstract class Employee extends Thread {


    private Call call;
    private Dispatcher dispatcher;
    private Boolean hasCall = Boolean.FALSE;

    public void takeCall(Call call, Dispatcher dispatcher) {
        this.setCall(call);
        this.setDispatcher(dispatcher);
        this.hasCall = Boolean.TRUE;
    }

    @Override
    public void run() {
        while(true){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(this.hasCall){
                try {
                    sleep(this.getCall().getDuration());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    this.setCall(null);
                    this.hasCall = Boolean.FALSE;
                    this.getDispatcher().addFreeEmployee(this);

                }
            }
        }
    }

    public Call getCall() {
        return call;
    }

    public void setCall(Call call) {
        this.call = call;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
}
