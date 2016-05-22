package at.tu.wmpm16.models;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Martina on 12.05.2016.
 */
@Entity
public class SmartMeter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int smartMeterNr;
    private String location;
    @OneToOne(orphanRemoval=true)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToMany(mappedBy = "smartMeters")
    private List<Operator> operators;

    protected SmartMeter() {
    }

    public SmartMeter(int smartMeterNr, String location, Customer customer, List<Operator> operators) {
        this.smartMeterNr = smartMeterNr;
        this.location = location;
        this.customer = customer;
        this.operators = operators;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSmartMeterNr() {
        return smartMeterNr;
    }

    public void setSmartMeterNr(int smartMeterNr) {
        this.smartMeterNr = smartMeterNr;
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
