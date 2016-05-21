package at.tu.wmpm16.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.Date;

@Entity
public class SmartMeterConsumptions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int smartmeternumber;
    private Date date;
    private long coldwaterconsumption;
    private long electricityconsumption;
    private long gasconsumption;
    private long heatingconsumption;
    private long warmwaterconsumption;

    protected SmartMeterConsumptions() {
    }

	
	public SmartMeterConsumptions(long id, int smartmeternumber, Date date,
			long coldwaterconsumption, long electricityconsumption,
			long gasconsumption, long heatingconsumption,
			long warmwaterconsumption) {
		super();
		this.id = id;
		this.smartmeternumber = smartmeternumber;
		this.date = date;
		this.coldwaterconsumption = coldwaterconsumption;
		this.electricityconsumption = electricityconsumption;
		this.gasconsumption = gasconsumption;
		this.heatingconsumption = heatingconsumption;
		this.warmwaterconsumption = warmwaterconsumption;
	}


	@Override
	public String toString() {
		return "SmartMeterConsumptions [id=" + id + ", smartmeternumber="
				+ smartmeternumber + ", date=" + date
				+ ", coldwaterconsumption=" + coldwaterconsumption
				+ ", electricityconsumption=" + electricityconsumption
				+ ", gasconsumption=" + gasconsumption
				+ ", heatingconsumption=" + heatingconsumption
				+ ", warmwaterconsumption=" + warmwaterconsumption + "]";
	}


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

	public long getColdwaterconsumption() {
		return coldwaterconsumption;
	}

	public void setColdwaterconsumption(long coldwaterconsumption) {
		this.coldwaterconsumption = coldwaterconsumption;
	}

	public long getElectricityconsumption() {
		return electricityconsumption;
	}

	public void setElectricityconsumption(long electricityconsumption) {
		this.electricityconsumption = electricityconsumption;
	}

	public long getGasconsumption() {
		return gasconsumption;
	}

	public void setGasconsumption(long gasconsumption) {
		this.gasconsumption = gasconsumption;
	}

	public long getHeatingconsumption() {
		return heatingconsumption;
	}

	public void setHeatingconsumption(long heatingconsumption) {
		this.heatingconsumption = heatingconsumption;
	}

	public long getWarmwaterconsumption() {
		return warmwaterconsumption;
	}

	public void setWarmwaterconsumption(long warmwaterconsumption) {
		this.warmwaterconsumption = warmwaterconsumption;
	}

	public int getSmartmeternumber() {
		return smartmeternumber;
	}

	public void setSmartmeternumber(int smartmeternumber) {
		this.smartmeternumber = smartmeternumber;
	}

    
    
    
}
