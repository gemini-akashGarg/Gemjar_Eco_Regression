package TestPackage;


import com.gemini.generic.api.utils.ApiInvocation;
import com.gemini.generic.api.utils.Request;
import com.gemini.generic.api.utils.Response;
import com.gemini.generic.reporting.GemTestReporter;
import com.gemini.generic.reporting.STATUS;
import com.gemini.generic.tdd.GemjarTestngBase;

import com.gemini.generic.utils.ProjectConfigData;
import org.testng.annotations.Test;

import java.util.HashMap;

public class get_suite extends GemjarTestngBase {

    @Test
    public void sample() throws Exception {


        Request request = new Request();
        String url = ProjectConfigData.getProperty("Gettt");
        request.setURL(url);
        request.setMethod("get");
        request.setStep("Test to check the Get API");
        Response response = ApiInvocation.handleRequest(request);
        System.out.println(response.getStatus());
//        HashMap<String,String> mapi=new HashMap<>();
//        mapi.put("Key","Value");

//        GemTestReporter.addTestStep("Title", "This is a sample test case", STATUS.INFO,mapi);


    }

    @Test
    public void Get_data_of_suite_srunidnotpresent() throws Exception {
        Request request = new Request();
        String url = ProjectConfigData.getProperty("Gettte");
        GemTestReporter.addTestStep("Url of the test case", url, STATUS.INFO);
        request.setURL(url);
        request.setMethod("get");
        // request.setStep("Test to check the Get API when s run id not present");
        Response response = ApiInvocation.handleRequest(request);
        int status = response.getStatus();
        GemTestReporter.addTestStep("Status", String.valueOf(status), STATUS.INFO);
        response.getResponseBody();

        //  GemTestReporter.addTestStep("Response Body",response.getResponseBody(),STATUS.INFO);


        if (status == 400) {
            GemTestReporter.addTestStep("Status verification", String.valueOf(status), STATUS.PASS);
        }
    }
}
