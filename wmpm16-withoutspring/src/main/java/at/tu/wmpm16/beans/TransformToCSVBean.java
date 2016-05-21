package at.tu.wmpm16.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import at.tu.wmpm16.models.ColdWaterConsumption;
import at.tu.wmpm16.models.ColdWaterConsumptionCSV;

@Component
public class TransformToCSVBean {

	@SuppressWarnings("unchecked")
	public Collection<ColdWaterConsumptionCSV> transform(Exchange exchange) {
		List<Map<String, Object>> cwcs = (List<Map<String, Object>>) exchange
				.getIn().getBody();
		
		Collection<ColdWaterConsumptionCSV> cwccoll = new ArrayList<ColdWaterConsumptionCSV>();

		for (Map<String, Object> cmap : cwcs) {
			for (Entry<String, Object> cobj : cmap.entrySet()) {
				ColdWaterConsumption c = (ColdWaterConsumption) cobj.getValue();
				
				System.out.println(c);
				if (null != c) {
					cwccoll.add(mapToCWCCSV(c));
				}
			}
		}

		return cwccoll;
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

}
