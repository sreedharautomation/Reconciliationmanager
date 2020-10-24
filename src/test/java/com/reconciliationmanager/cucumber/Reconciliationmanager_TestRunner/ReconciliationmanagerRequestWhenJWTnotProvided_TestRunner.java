package com.reconciliationmanager.cucumber.Reconciliationmanager_TestRunner;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features ={"src/test/resources/features/Reconciliationmanager/ReconciliationmanagerRequestWhenJWTnotProvided.feature"
},
        glue = {"com.reconciliationmanager.cucumber.steps_Reconciliationmanager"},
        plugin={"html:target/cucumber","html:target/reports/htmlreport"}

)

public class ReconciliationmanagerRequestWhenJWTnotProvided_TestRunner {
}
