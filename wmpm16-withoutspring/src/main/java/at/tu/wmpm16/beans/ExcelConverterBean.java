package at.tu.wmpm16.beans;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.camel.Body;
import org.apache.camel.Header;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;

import at.tu.wmpm16.models.ColdWaterConsumption;

@Component
public class ExcelConverterBean {

    public ColdWaterConsumption readExcelColdWaterConsumption(@Body InputStream body) {
        try {
        	
        	List<ColdWaterConsumption> cwclist = new ArrayList<ColdWaterConsumption>();
        	
            HSSFWorkbook workbook = new HSSFWorkbook(body);
            HSSFSheet sheet = workbook.getSheetAt(0);
            boolean headersFound = false;
            int colNum;
            for(Iterator rit = sheet.rowIterator(); rit.hasNext();) {
                HSSFRow row = (HSSFRow) rit.next();
                if(!headersFound) {  // Skip the first row with column headers
                    headersFound = true;
                    continue;
                }
                colNum = 0;
                ColdWaterConsumption cwc = new ColdWaterConsumption();
                for(Iterator cit = row.cellIterator(); cit.hasNext(); ++colNum) {
                    HSSFCell cell = (HSSFCell) cit.next();
                    if(headersFound)
                    switch(colNum) {
//                    id,standardValue,measuredValue,date,smartMeterNr
//                    108,10,0,2016-01-04 00:05:00.0,3

                        case 0: // ID (Long)
                           cwc.setId((long) cell.getNumericCellValue());
                           break;
                        case 1: // standard value
                        	cwc.setStandardValue((long) cell.getNumericCellValue());
                        	break;
                        case 2: // measured value
                            cwc.setMeasuredValue((long) cell.getNumericCellValue()); 
                            break;
                        case 3: // date
                            cwc.setDate(cell.getDateCellValue());
                            break;
                        case 4: // smartMeterNr
                        	cwc.setSmartMeterNr((int) cell.getNumericCellValue());
                        	break;
                    }
                }
                System.out.println(cwc);
                return cwc;
            }
            
        } catch (Exception e) {
    
            throw new RuntimeException("Unable to import Excel", e);
        }
        
        return null;
    }
	
}
