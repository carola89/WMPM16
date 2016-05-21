package at.tu.wmpm16.beans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.tu.wmpm16.models.ColdWaterConsumption;
import at.tu.wmpm16.models.ElectricityConsumption;
import at.tu.wmpm16.models.GasConsumption;
import at.tu.wmpm16.models.HeatingConsumption;
import at.tu.wmpm16.models.SmartMeterConsumptions;
import at.tu.wmpm16.models.WarmWaterConsumption;

@Component
public class SplitSmartMeterConsumptionsToSingleConsumptionsBean {

	public List<Object> splitBody(Object body) {
        // since this is based on an unit test you can of cause
        // use different logic for splitting as Camel have out
        // of the box support for splitting a String based on comma
        // but this is for show and tell, since this is java code
        // you have the full power how you like to split your messages
        System.out.println("split body in: " + body);
        
        SmartMeterConsumptions smc = (SmartMeterConsumptions) body;
        
        ColdWaterConsumption cwc = new ColdWaterConsumption(10l, smc.getColdwaterconsumption(), smc.getDate(), smc.getSmartmeternumber());
        ElectricityConsumption ec = new ElectricityConsumption(10l, smc.getElectricityconsumption(), smc.getDate(), smc.getSmartmeternumber());
        GasConsumption gc = new GasConsumption(10l, smc.getGasconsumption(), smc.getDate(), smc.getSmartmeternumber());
        HeatingConsumption hc = new HeatingConsumption(10l, smc.getHeatingconsumption(), smc.getDate(), smc.getSmartmeternumber());
        WarmWaterConsumption wwc = new WarmWaterConsumption(10, smc.getWarmwaterconsumption(), smc.getDate(), smc.getSmartmeternumber());
        
        List<Object> result = new ArrayList<Object>();
        result.add(cwc);
        result.add(ec);
        result.add(gc);
        result.add(hc);
        result.add(wwc);
        
        System.out.println("split result: " + result);
        
        return result;
    }
}
