package TestPackage;

import com.gemini.generic.api.utils.ApiInvocation;
import com.gemini.generic.api.utils.Request;
import com.gemini.generic.api.utils.Response;
import com.gemini.generic.reporting.GemTestReporter;
import com.gemini.generic.reporting.STATUS;
import com.gemini.generic.tdd.GemjarTestngBase;
import com.gemini.generic.utils.ProjectConfigData;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.testng.annotations.Test;


public class Testex extends GemjarTestngBase {

    @Test
    public void Get_data_of_test_case() {
        GemTestReporter.addTestStep("Test Case", "Test to check the Get Test Exe API ", STATUS.INFO);
        String url = ProjectConfigData.getProperty("Gettest");

        GemTestReporter.addTestStep("Url for Get Request", url, STATUS.INFO);

        Response res = null;

        try {

            Request request = new Request();
            request.setURL(url);
            request.setMethod("get");
            Response response = ApiInvocation.handleRequest(request);
            res = response;
            GemTestReporter.addTestStep(" Get Request Verification ", "Get Request Executed Successfully", STATUS.PASS);
        } catch (Exception e) {
            GemTestReporter.addTestStep(" Get Request Verification ", "Get Request Did not Executed Successfully", STATUS.FAIL);
        }
        try {


            int status = res.getStatus();

            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);

            if (status == 200) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 200", STATUS.PASS);
                String bodo = res.getResponseBody();
                JsonParser parser = new JsonParser();
                JsonObject body = (JsonObject) parser.parse(bodo);
                GemTestReporter.addTestStep("Response After hitting the API ", String.valueOf(body), STATUS.INFO);

                String message = String.valueOf(body.get("message"));
                GemTestReporter.addTestStep("Final Message ", String.valueOf(message), STATUS.PASS);

            } else {
                String bo = res.getErrorMessage();
                GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.FAIL);


            }
        } catch (Exception e) {
            e.printStackTrace();
            GemTestReporter.addTestStep("ERROR!!", "Something went wrong", STATUS.FAIL);
        }


    }


    @Test
    public void Get_data_of_test_case_tcrunidnotvalid() {
        GemTestReporter.addTestStep("Test Case", "Test to check the Get Test Exe API when tc run id is not valid ", STATUS.INFO);
        String url = ProjectConfigData.getProperty("Gettest2");

        GemTestReporter.addTestStep("Url for Get Request", url, STATUS.INFO);

        Response res = null;

        try {


            Request request = new Request();
            request.setURL(url);
            request.setMethod("get");
            Response response = ApiInvocation.handleRequest(request);
            res = response;

            GemTestReporter.addTestStep(" Get Request Verification ", "Get Request Executed Successfully", STATUS.PASS);
        } catch (Exception e) {
            GemTestReporter.addTestStep(" Get Request Verification ", "Get Request Did not Executed Successfully", STATUS.FAIL);
        }

        try {
            int status = res.getStatus();

            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);


            if (status == 400) {
                String bo = res.getErrorMessage();

                GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.PASS);
            }
            else if (status == 200) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 200", STATUS.FAIL);
                String bodi = res.getResponseBody();
                GemTestReporter.addTestStep("Response After hitting the API ", String.valueOf(bodi), STATUS.INFO);
                JsonParser parser = new JsonParser();
                JsonObject body = (JsonObject) parser.parse(bodi);
                String message = String.valueOf(body.get("message"));
                GemTestReporter.addTestStep("Final Message ", String.valueOf(message), STATUS.FAIL);

            } else {
                String bo = res.getErrorMessage();
                GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.FAIL);


            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something Wrong happened ");
        }


    }
}
