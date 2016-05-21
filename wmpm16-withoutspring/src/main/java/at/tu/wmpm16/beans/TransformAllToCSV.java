package at.tu.wmpm16.beans;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import at.tu.wmpm16.models.ColdWaterConsumption;
import at.tu.wmpm16.models.ElectricityConsumption;
import at.tu.wmpm16.models.GasConsumption;
import at.tu.wmpm16.models.HeatingConsumption;
import at.tu.wmpm16.models.WarmWaterConsumption;
import at.tu.wmpm16.models.csv.ColdWaterConsumptionCSV;
import at.tu.wmpm16.models.csv.ElectricityConsumptionCSV;
import at.tu.wmpm16.models.csv.GasConsumptionCSV;
import at.tu.wmpm16.models.csv.HeatingConsumptionCSV;
import at.tu.wmpm16.models.csv.WarmWaterConsumptionCSV;

@Component
public class TransformAllToCSV {

	public Collection<Object> transform(Exchange exchange) {

		System.out.println("TransformAllToCSV In: "
				+ exchange.getIn().getBody());
		Object object = (Object) exchange.getIn().getBody();

		if (object instanceof ColdWaterConsumption) {
			Collection<Object> coll = new ArrayList<Object>();

			ColdWaterConsumption c = (ColdWaterConsumption) object;
			System.out.println("object to transform: " + c);
			if (null != c) {
				coll.add(mapToCWCCSV(c));
			}
			System.out.println("Transform Result: " + coll);

			return coll;
		}
		
		if (object instanceof ElectricityConsumption) {
			Collection<Object> coll = new ArrayList<Object>();

			ElectricityConsumption c = (ElectricityConsumption) object;
			System.out.println("object to transform: " + c);
			if (null != c) {
				coll.add(mapToEcCsv(c));
			}
			System.out.println("Transform Result: " + coll);

			return coll;
		}
		
		if (object instanceof GasConsumption) {
			Collection<Object> coll = new ArrayList<Object>();

			GasConsumption c = (GasConsumption) object;
			System.out.println("object to transform: " + c);
			if (null != c) {
				coll.add(mapToGcCsv(c));
			}
			System.out.println("Transform Result: " + coll);

			return coll;
		}
		
		if (object instanceof HeatingConsumption) {
			Collection<Object> coll = new ArrayList<Object>();

			HeatingConsumption c = (HeatingConsumption) object;
			System.out.println("object to transform: " + c);
			if (null != c) {
				coll.add(mapToHcCsv(c));
			}
			System.out.println("Transform Result: " + coll);

			return coll;
		}
		
		if (object instanceof WarmWaterConsumption) {
			Collection<Object> coll = new ArrayList<Object>();

			WarmWaterConsumption c = (WarmWaterConsumption) object;
			System.out.println("object to transform: " + c);
			if (null != c) {
				coll.add(mapToWwcCsv(c));
			}
			System.out.println("Transform Result: " + coll);
			return coll;
		}
		
		

		return null;
	}

	private ColdWaterConsumptionCSV mapToCWCCSV(ColdWaterConsumption cwc) {
		if (null != cwc) {
			ColdWaterConsumptionCSV csv = new ColdWaterConsumptionCSV();
			csv.setDate(cwc.getDate().toString());
			csv.setId(cwc.getId());
			csv.setMeasuredValue(cwc.getMeasuredValue());
			csv.setSmartMeterNr(cwc.getSmartMeterNr());
			csv.setStandardValue(cwc.getStandardValue());

			return csv;
		}
		return null;
	}
	
	private ElectricityConsumptionCSV mapToEcCsv(ElectricityConsumption cwc) {
		if (null != cwc) {
			ElectricityConsumptionCSV csv = new ElectricityConsumptionCSV();
			csv.setDate(cwc.getDate().toString());
			csv.setId(cwc.getId());
			csv.setMeasuredValue(cwc.getMeasuredValue());
			csv.setSmartMeterNr(cwc.getSmartMeterNr());
			csv.setStandardValue(cwc.getStandardValue());

			return csv;
		}
		return null;
	}
	
	private GasConsumptionCSV mapToGcCsv(GasConsumption cwc) {
		if (null != cwc) {
			GasConsumptionCSV csv = new GasConsumptionCSV();
			csv.setDate(cwc.getDate().toString());
			csv.setId(cwc.getId());
			csv.setMeasuredValue(cwc.getMeasuredValue());
			csv.setSmartMeterNr(cwc.getSmartMeter());
			csv.setStandardValue(cwc.getStandardValue());

			return csv;
		}
		return null;
	}
	
	private HeatingConsumptionCSV mapToHcCsv(HeatingConsumption cwc) {
		if (null != cwc) {
			HeatingConsumptionCSV csv = new HeatingConsumptionCSV();
			csv.setDate(cwc.getDate().toString());
			csv.setId(cwc.getId());
			csv.setMeasuredValue(cwc.getMeasuredValue());
			csv.setSmartMeterNr(cwc.getSmartMeter());
			csv.setStandardValue(cwc.getStandardValue());

			return csv;
		}
		return null;
	}
	
	private WarmWaterConsumptionCSV mapToWwcCsv(WarmWaterConsumption cwc) {
		if (null != cwc) {
			WarmWaterConsumptionCSV csv = new WarmWaterConsumptionCSV();
			csv.setDate(cwc.getDate().toString());
			csv.setId(cwc.getId());
			csv.setMeasuredValue(cwc.getMeasuredValue());
			csv.setSmartMeterNr(cwc.getSmartMeterNr());
			csv.setStandardValue(cwc.getStandardValue());

			return csv;
		}
		return null;
	}

}
