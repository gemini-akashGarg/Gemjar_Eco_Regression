package TestPackage;
import com.gemini.generic.api.utils.ApiInvocation;
import com.gemini.generic.api.utils.Request;
import com.gemini.generic.api.utils.Response;
import com.gemini.generic.reporting.GemTestReporter;
import com.gemini.generic.reporting.STATUS;
import com.gemini.generic.tdd.GemjarTestngBase;
import com.gemini.generic.utils.ProjectConfigData;
import com.google.gson.JsonObject;
import org.testng.annotations.Test;


public class GetUser extends GemjarTestngBase {


    @Test
    public void Validate_Username() {
        GemTestReporter.addTestStep("Test Case", "Test to check the Get API ", STATUS.INFO);
        String url = ProjectConfigData.getProperty("Gett");


        GemTestReporter.addTestStep("Url for Get Request", url, STATUS.INFO);

        Response response2 = null;
        try {

            Request request = new Request();
            request.setURL(url);
            request.setMethod("get");
            Response response = ApiInvocation.handleRequest(request);
            response2 = response;
        } catch (Exception e) {
            GemTestReporter.addTestStep(" Get Request Verification ", "Get Request Did not Executed Successfully", STATUS.FAIL);
        }
        int status = response2.getStatus();
        GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);

        GemTestReporter.addTestStep(" Get Request Verification ", "Get Request Executed Successfully", STATUS.PASS);


        if (status == 200) {
            GemTestReporter.addTestStep("Status Verification", "Expected Status : 200", STATUS.PASS);
            String body = response2.getResponseBody();
            GemTestReporter.addTestStep("Response After hitting the API ", String.valueOf(body), STATUS.INFO);


        } else {
            String bo = response2.getErrorMessage();
            GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.FAIL);


        }


    }




    @Test
    public void Validate_Username_username_doesnotexists() {
        GemTestReporter.addTestStep("Test Case", "Test to check the Get API when the username doesnot exists", STATUS.INFO);

        String url = ProjectConfigData.getProperty("Gett2");


        GemTestReporter.addTestStep("Url for Get  Request", url, STATUS.INFO);

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

        int status = res.getStatus();

        GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);

        if (status == 200) {
            GemTestReporter.addTestStep("Status Verification", "Expected Status : 200", STATUS.PASS);
            String body = res.getResponseBody();
            GemTestReporter.addTestStep("Response After hitting the API ", String.valueOf(body), STATUS.INFO);


        } else {
            String bo = res.getErrorMessage();
            GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.FAIL);


        }


    }


}


