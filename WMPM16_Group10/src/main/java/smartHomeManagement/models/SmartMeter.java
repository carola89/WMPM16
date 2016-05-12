package smartHomeManagement.models;

import java.util.List;

/**
 * Created by Martina on 12.05.2016.
 */
public class SmartMeter {

    private long id;
    private int SmartMeterNr;
    private String location;
    private Customer customer;
    private List<Operator> operators;

    protected SmartMeter() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSmartMeterNr() {
        return SmartMeterNr;
    }

    public void setSmartMeterNr(int smartMeterNr) {
        SmartMeterNr = smartMeterNr;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Operator> getOperators() {
        return operators;
    }

    public void setOperators(List<Operator> operators) {
        this.operators = operators;
    }
}
