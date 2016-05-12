package smartHomeManagement.models;

import java.util.List;

/**
 * Created by Martina on 12.05.2016.
 */
public class Operator {

    private long id;
    private int operatorNr;
    private String name;
    private String address;
    private String email;
    private List<Customer> customers;
    private List<SmartMeter> smartMeters;


    protected Operator() {
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

    public String getAddresse() {
        return address;
    }

    public void setAddresse(String addresse) {
        this.address = addresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


