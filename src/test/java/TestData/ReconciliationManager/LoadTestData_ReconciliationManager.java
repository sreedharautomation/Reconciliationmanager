package TestData.ReconciliationManager;

import TestData.ReconciliationManager.TestDataVariables_ReconciliationManager;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class LoadTestData_ReconciliationManager {
    public static TestDataVariables_ReconciliationManager testData_ReconciliationManager = new TestDataVariables_ReconciliationManager();

    static {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            InputStream dataFile =  LoadTestData_ReconciliationManager.class.getClassLoader()
                    .getResourceAsStream("testdata/ReconciliationManager/ReconciliationManager.yaml");

            testData_ReconciliationManager = mapper.readValue(dataFile, TestDataVariables_ReconciliationManager.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}