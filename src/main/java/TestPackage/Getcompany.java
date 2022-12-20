package TestPackage;


import com.gemini.generic.api.utils.ApiInvocation;
import com.gemini.generic.api.utils.Request;
import com.gemini.generic.api.utils.Response;
import com.gemini.generic.reporting.GemTestReporter;
import com.gemini.generic.reporting.STATUS;
import com.gemini.generic.tdd.GemjarTestngBase;
import com.gemini.generic.utils.ProjectConfigData;
import org.testng.annotations.Test;

public class Getcompany extends GemjarTestngBase {
    @Test
    public void Getcompany() throws Exception {
        GemTestReporter.addTestStep("Test Case", "Test to check the Get Company API ", STATUS.INFO);


/////////////////////////////////////////////////////////


        Request request = new Request();
        String url = ProjectConfigData.getProperty("Getc");
        request.setURL(url);
        System.out.println(url);
        request.setMethod("get");
        request.setStep("Test to check the Get Company API");
        Response response = ApiInvocation.handleRequest(request);
        System.out.println(response.getStatus());
//        GemTestReporter.addTestStep("","",STATUS.FAIL);
        //////////////////////////////////////////////////////////////
    }
}

