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
public class HeatingConsumption {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long standardValue;
    private long measuredValue;
    private Date date;
    private SmartMeter smartMeter;

    protected HeatingConsumption() {
    }

    public HeatingConsumption(long standardValue, long measuredValue, Date date, SmartMeter smartMeter) {
        this.standardValue = standardValue;
        this.measuredValue = measuredValue;
        this.date = date;
        this.smartMeter = smartMeter;
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

    public SmartMeter getSmartMeter() {
        return smartMeter;
    }

    public void setSmartMeter(SmartMeter smartMeter) {
        this.smartMeter = smartMeter;
    }
}
