package smartHomeManagement.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Martina on 14.05.2016.
 */
@Entity
public class GasConsumption {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long standardValue;
    private long measuredValue;
    private Date date;
    private int smartMeterNr;

    protected GasConsumption() {
    }

    public GasConsumption(long standardValue, long measuredValue, Date date, int smartMeter) {
        this.standardValue = standardValue;
        this.measuredValue = measuredValue;
        this.date = date;
        this.smartMeterNr = smartMeter;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStandardValue() {
        return standardValue;
    }

    public void setStandardValue(long standardValue) {
        this.standardValue = standardValue;
    }

    public long getMeasuredValue() {
        return measuredValue;
    }

    public void setMeasuredValue(long measuredValue) {
        this.measuredValue = measuredValue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSmartMeter() {
        return smartMeterNr;
    }

    public void setSmartMeter(int smartMeter) {
        this.smartMeterNr = smartMeter;
    }
}
