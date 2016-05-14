package smartHomeManagement.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martina on 12.05.2016.
 */
@Entity
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int operatorNr;
    private String name;
    private String address;
    private String email;
    @ManyToMany(mappedBy = "operators")
    private List<Customer> customers;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "operator_smartMeter", joinColumns = @JoinColumn(name = "operator_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "smartMeter_id", referencedColumnName = "id"))
    private List<SmartMeter> smartMeters;


    protected Operator() {
    }

    public Operator(int operatorNr, String name, String address, String email, List<Customer> customers, List<SmartMeter> smartMeters) {
        this.operatorNr = operatorNr;
        this.name = name;
        this.address = address;
        this.email = email;
        this.customers = customers;
        this.smartMeters = smartMeters;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getOperatorNr() {
        return operatorNr;
    }

    public void setOperatorNr(int operatorNr) {
        this.operatorNr = operatorNr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Customer> getCustomers() {
        if (this.customers == null) {
            this.customers = new ArrayList<>();
        }
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<SmartMeter> getSmartMeters() {
        if (this.smartMeters == null) {
            this.smartMeters = new ArrayList<>();
        }
        return smartMeters;
    }

    public void setSmartMeters(List<SmartMeter> smartMeters) {
        this.smartMeters = smartMeters;
    }
}


