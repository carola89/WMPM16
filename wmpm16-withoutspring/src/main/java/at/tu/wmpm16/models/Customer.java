package at.tu.wmpm16.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martina on 12.05.2016.
 */
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int customerNR;
    private String name;
    private String email;
    private String telNr;
    private String address;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "customer_operator", joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "operator_id", referencedColumnName = "id"))
    private List<Operator> operators;
    @OneToOne(mappedBy = "customer", orphanRemoval=true)
    private SmartMeter smartMeters;


    protected Customer() {
    }

    public Customer(int customerNR, String name, String email, String telNr, String address) {
        this.customerNR = customerNR;
        this.name = name;
        this.email = email;
        this.telNr = telNr;
        this.address = address;
    }

    public Customer(int customerNR, String name, String email, String telNr, String address, List<Operator> operators, SmartMeter smartMeters) {
        this.customerNR = customerNR;
        this.name = name;
        this.email = email;
        this.telNr = telNr;
        this.address = address;
        this.operators = operators;
        this.smartMeters = smartMeters;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCustomerNR() {
        return customerNR;
    }

    public void setCustomerNR(int customerNR) {
        this.customerNR = customerNR;
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

    public String getTelNr() {
        return telNr;
    }

    public void setTelNr(String telNr) {
        this.telNr = telNr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Operator> getOperators() {
        if (this.operators == null) {
            this.operators = new ArrayList<>();
        }
        return operators;
    }

    public void setOperators(List<Operator> operators) {
        this.operators = operators;
    }

    public SmartMeter getSmartMeters() {

        return smartMeters;
    }

    public void setSmartMeters(SmartMeter smartMeters) {
        this.smartMeters = smartMeters;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", customerNR=" + customerNR +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", telNr='" + telNr + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
