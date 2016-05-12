package smartHomeManagement.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
//    private List<Operator> operators;
//    private List<SmartMeter>smartMeters;


    protected Customer() {
    }

    public Customer(int customerNR, String name, String email, String telNr, String address) {
        this.customerNR = customerNR;
        this.name = name;
        this.email = email;
        this.telNr = telNr;
        this.address = address;
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
