package at.tu.wmpm16.models;

import java.util.Date;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;


/**
 * 
 * Modellklasse für CSV-Input Files, welche die unten definierten DataFields als Spalten enhalten
 * Wird von Camel-Bindy benutzt.
 * 
 * @author gabriel
 */
@CsvRecord(separator=",")
public class ColdWaterConsumptionCSV {
	
	@DataField(pos=1)
	private long id;
	
	@DataField(pos=2)
	private long standardValue;
	
	@DataField(pos=3)
	private long measuredValue;

	@DataField(pos=4)
	private Date date;
	
	@DataField(pos=5)
	private int smartMeterNr;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getMeasuredValue() {
		return measuredValue;
	}

	public void setMeasuredValue(long measuredValue) {
		this.measuredValue = measuredValue;
	}

	public long getStandardValue() {
		return standardValue;
	}

	public void setStandardValue(long standardValue) {
		this.standardValue = standardValue;
	}

	public int getSmartMeterNr() {
		return smartMeterNr;
	}

	public void setSmartMeterNr(int smartMeterNr) {
		this.smartMeterNr = smartMeterNr;
	}

	@Override
	public String toString() {
		return "ColdWaterConsumptionCSV [id=" + id + ", standardValue="
				+ standardValue + ", measuredValue=" + measuredValue
				+ ", date=" + date + ", smartMeterNr=" + smartMeterNr + "]";
	}
	
	
}
