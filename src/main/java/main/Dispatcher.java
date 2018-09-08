package main;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Dispatcher {

    private Lock lock = new ReentrantLock();
    private Integer calls = 0;
    private Integer maxCalls = Integer.MAX_VALUE;
    private Integer averageWaitTime= new Random().nextInt(500)+500;
    private List<Call> inLine = new ArrayList<Call>();

    //Employees
    private List<Employee> employees = new ArrayList<Employee>();

    public Dispatcher(Integer maxCalls){
        this.maxCalls=maxCalls;
    }

    public abstract Boolean dispatchCall(Call call);

    void increment() {
        this.calls++;
        if(this.calls>this.maxCalls){
            throw new RuntimeException("Max Calls Exceeded");
        }
    }

    /**
     * Take an available employee and dispatch the to that employee.
     * @param call
     */
    protected void dispatchCallToEmployee(Call call) {
        Employee freeEmployee =
                this.employees.stream()
                .filter(employee -> employee.getClass().equals(Director.class))
                .findAny()
                .orElse(
                        this.employees.stream()
                        .filter(employee -> employee.getClass().equals(Supervisor.class))
                        .findAny()
                        .orElse(this.employees.get(0))
                );
        this.employees.remove(freeEmployee);
        freeEmployee.takeCall(call,this);
    }

    /**
     * Maximum Calls or Employee available.
     * @return
     */
    Boolean canTakeACall() {
        return !(this.calls>=this.maxCalls) &
                this.availableEmployees();
    }

    private Boolean availableEmployees() {
        return !this.employees.isEmpty();
    }

    private void free(){
        this.lock.lock();
        this.calls--;
        this.lock.unlock();
    }
    public void addFreeEmployee(Employee employee){
        this.employees.add(employee);
        this.free();
    }
    public void addNewEmployee(Employee employee){
        this.employees.add(employee);
    }

    //GETTERS
    public Lock getLock() {
        return lock;
    }

    public Integer getAverageWaitTime() {
        return averageWaitTime;
    }

    public Integer getCalls() {
        return calls;
    }
}
