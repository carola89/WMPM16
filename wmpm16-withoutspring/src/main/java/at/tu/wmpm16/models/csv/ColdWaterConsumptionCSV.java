package at.tu.wmpm16.models.csv;

import java.util.Date;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;


/**
 * 
 * model class for CSV files CSV-Input Files
 * Called by Camel-Bindy
 * 
 */
@CsvRecord(separator=",", generateHeaderColumns=true)
public class ColdWaterConsumptionCSV {
	
	//Data fields are for the columns
	@DataField(pos=1)
	private long id;
	
	@DataField(pos=2)
	private long standardValue;
	
	@DataField(pos=3)
	private long measuredValue;

	@DataField(pos=4)
	private String date;
	
	@DataField(pos=5)
	private int smartMeterNr;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
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
