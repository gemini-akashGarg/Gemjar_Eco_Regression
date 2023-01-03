package TestPackage;

import com.gemini.generic.api.utils.ApiInvocation;
import com.gemini.generic.api.utils.ProjectSampleJson;
import com.gemini.generic.api.utils.Request;
import com.gemini.generic.api.utils.Response;
import com.gemini.generic.reporting.GemTestReporter;
import com.gemini.generic.reporting.STATUS;
import com.gemini.generic.tdd.GemjarTestngBase;
import com.gemini.generic.utils.ProjectConfigData;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class gettoken extends GemjarTestngBase {

    String TO;

    public String token() {
        try {
            String Token;
            String urlss = ProjectConfigData.getProperty("Login");
            String payload = ProjectSampleJson.getSampleDataString("Login_sampleJson");
            Request request = new Request();
            request.setURL(urlss);
            request.setMethod("Post");
            request.setRequestPayload(payload);
            Response response = ApiInvocation.handleRequest(request);
            String Bodd = response.getResponseBody();
            JsonParser parser = new JsonParser();
            JsonObject Boddy = (JsonObject) parser.parse(Bodd);
            JsonObject to = (JsonObject) Boddy.get("data");
            String tokenss = String.valueOf(to.get("token"));
            Token = tokenss;
            return Token;

        } catch (Exception e) {
            GemTestReporter.addTestStep("Final token", "Some error occured while fetching token", STATUS.FAIL);
            e.printStackTrace();
            return null;

        }
    }

    @Test
    public void Gettoken() throws Exception {
        GemTestReporter.addTestStep("Test Case", "Test to check the Get Token API when the JWT is not expired", STATUS.INFO);
        String j = token();
        Map<String, String> headers = new HashMap<>();
        String jnew = j.replaceAll("^\"|\"$", "");
        headers.put("Authorization", "Bearer " + jnew);
        Request request = new Request();
        String url = ProjectConfigData.getProperty("Gettoken");
        GemTestReporter.addTestStep("Url for Get Request", url, STATUS.INFO);
        request.setURL(url);
        request.setMethod("get");
        request.setHeaders(headers);
        request.setStep("Test to check the Get Company API");
        try {
            Response response = ApiInvocation.handleRequest(request);
            GemTestReporter.addTestStep(" Get Request Verification ", "Get Request Executed Successfully", STATUS.PASS);
        } catch (Exception e) {
            GemTestReporter.addTestStep(" Get Request Verification ", "Get Request Did not Executed Successfully", STATUS.FAIL);
        }


        ////////////////////////////////////////////////


    }


    @Test
    public void Gettoken_wrong_auth() {
/////////////////////////////new one/////////////////////////////////////


        GemTestReporter.addTestStep("Test Case", "Test to check the Get Token API when the JWT is not expired", STATUS.INFO);
        String j = token();
        Map<String, String> headers = new HashMap<>();
        String jnew = j.replaceAll("^\"|\"$", "");
        headers.put("Authorization", "Bearer " + jnew + "op");
        Request request = new Request();
        String url = ProjectConfigData.getProperty("Gettoken");
        GemTestReporter.addTestStep("Url for Get Request", url, STATUS.INFO);
        request.setURL(url);
        request.setMethod("get");
        request.setHeaders(headers);

        try {
            Response response = ApiInvocation.handleRequest(request);

            GemTestReporter.addTestStep(" Get Request Verification ", "Get Request Executed Successfully", STATUS.PASS);

            int status = response.getStatus();
            GemTestReporter.addTestStep(" Status ", String.valueOf(status), STATUS.INFO);

            if (status == 200) {
                GemTestReporter.addTestStep(" Status Verification", "Expected status:403", STATUS.FAIL);
                GemTestReporter.addTestStep("Response ", response.getResponseBody(), STATUS.INFO);
            } else {
                GemTestReporter.addTestStep(" Status Verification", "Expected status:403", STATUS.PASS);
                GemTestReporter.addTestStep("Response ", response.getResponseBody(), STATUS.INFO);
            }

        } catch (Exception e) {
            GemTestReporter.addTestStep(" Get Request Verification ", "Get Request Did not Executed Successfully", STATUS.FAIL);
        }


    }


    @Test
    public void Gettoken_Empty_auth() {

        GemTestReporter.addTestStep("Test Case", "Test to check the Get Token API when Auth header is not given", STATUS.INFO);


        String url = ProjectConfigData.getProperty("Gettoken");
        GemTestReporter.addTestStep("Url for Get Request", url, STATUS.INFO);
        try {


            String j = token();

            Map<String, String> headers = new HashMap<>();

            String jnew = j.replaceAll("^\"|\"$", "");

            headers.put("Authorization", "Bearer " + jnew + "as");
            Response res=null;
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
            //  System.out.println(res);
            int status = res.getStatus();

            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);

            if (status == 200) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.FAIL);
                String body = res.getResponseBody();
                GemTestReporter.addTestStep("Response After hitting the API ", String.valueOf(body), STATUS.INFO);


            } else if (status==403){
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.PASS);

                GemTestReporter.addTestStep("Final response", "No response", STATUS.PASS);


            }
            else {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.FAIL);

                GemTestReporter.addTestStep("Final response", "No response", STATUS.FAIL);

            }
        } catch (Exception e) {
            e.printStackTrace();


        }


    }
//    @Test(dataProvider = "GemjarDataProvider", dataProviderClass = GemjarDataProvider.class)
//    public void Gettoken_JWT_expired(JsonObject inputData) throws InterruptedException {
//
//
//        GemTestReporter.addTestStep("Test Case", "Test to check the Get Token API when the JWT is  expired", STATUS.INFO);
//
//        String url = ProjectApiUrl.getUrl("Gettoken");
//
//        GemTestReporter.addTestStep("Url for Get Request", url, STATUS.INFO);
//        try {
//
//
//            String j = token();
//
//            JsonObject res = null;
//            Map<String, String> headers = new HashMap<>();
//
//            String jnew = j.replaceAll("^\"|\"$", "");
//            System.out.println(jnew);
//            headers.put("Authorization", "Bearer " + jnew);
//            Thread.sleep(400001);
//            try {
//                res = ApiClientConnect.createRequest("Get", url, "", headers);
//                GemTestReporter.addTestStep(" Get Request Verification ", "Get Request Executed Successfully", STATUS.PASS);
//            } catch (Exception e) {
//                GemTestReporter.addTestStep(" Get Request Verification ", "Get Request Did not Executed Successfully", STATUS.FAIL);
//            }
//
//            int status = res.get("status").getAsInt();
//
//            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);
//
//            if (status == 403) {
//                GemTestReporter.addTestStep("Status Verification", "Expected Status :403", STATUS.PASS);
//
//                GemTestReporter.addTestStep("Final response", "No response", STATUS.PASS);
//
//
//            } else {
//
//                GemTestReporter.addTestStep("Final response", "No response", STATUS.FAIL);
//
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//
//        }
//
//    }


}
