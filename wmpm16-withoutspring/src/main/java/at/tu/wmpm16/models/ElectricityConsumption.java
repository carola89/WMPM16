package at.tu.wmpm16.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Martina on 14.05.2016.
 */
@Entity
public class ElectricityConsumption {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long standardValue;
    private long measuredValue;
    private Date date;
    private int smartMeterNr;

    public ElectricityConsumption() {
    }

    public ElectricityConsumption(long standardValue, long measuredValue, Date date, int smartMeter) {
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

    public int getSmartMeterNr() {
        return smartMeterNr;
    }

    public void setSmartMeterNr(int smartMeterNr) {
        this.smartMeterNr = smartMeterNr;
    }
    
    @Override
	public String toString() {
		return "ElectricityConsumption [id=" + id + ", standardValue="
				+ standardValue + ", measuredValue=" + measuredValue
				+ ", date=" + date + ", smartMeterNr=" + smartMeterNr + "]";
	}
}
