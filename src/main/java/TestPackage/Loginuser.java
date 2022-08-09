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

public class Loginuser extends GemjarTestngBase {


    @Test
    public void Loginusers() throws Exception {


        GemTestReporter.addTestStep("Test Case", "Test to check the Login User Api  ", STATUS.INFO);
        Request request = new Request();
        String url = ProjectConfigData.getProperty("Login");
        request.setURL(url);
        request.setMethod("Post");
        request.setStep("Login user");

        String payload = ProjectSampleJson.getSampleDataString("Login_sampleJson");
        request.setRequestPayload(payload);

        Response response = ApiInvocation.handleRequest(request);
        System.out.println(response.getStatus());

    }


    @Test
    public void Loginuser_wrongcredentials() throws Exception {


        GemTestReporter.addTestStep("Test Case", "Test to check the Login User Api by wrong credentials ", STATUS.INFO);
        String url = ProjectConfigData.getProperty("Login");
        GemTestReporter.addTestStep("Url for Post Request", url, STATUS.INFO);
        String payload = ProjectSampleJson.getSampleDataString("Login2_sampleJson");
        Request request = new Request();
        request.setURL(url);
        request.setMethod("Post");
        request.setRequestPayload(payload);
        Response response = ApiInvocation.handleRequest(request);
         int status = response.getStatus();
        System.out.println(status);
        GemTestReporter.addTestStep("Status", String.valueOf(status), STATUS.INFO);
        System.out.println(response.getErrorMessage());
        if (status == 400) {
            GemTestReporter.addTestStep("Status Verification", "Expected status:400", STATUS.PASS);


        } else {
            GemTestReporter.addTestStep("Status Verification", "Expected status:400", STATUS.FAIL);

        }
        GemTestReporter.addTestStep("Response Error", response.getErrorMessage(), STATUS.INFO);


    }

    @Test
    public void Loginuser_fieldsnotpresent() {

        GemTestReporter.addTestStep("Test Case", "Test to check the Login User Api when some fields are not present ", STATUS.INFO);


        String url = ProjectConfigData.getProperty("Login");


        GemTestReporter.addTestStep("Url for Post Request", url, STATUS.INFO);

        String payload = ProjectSampleJson.getSampleDataString("Login3_sampleJson");

        GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);

        Response res = null;

        try {


            Request request = new Request();
            request.setURL(url);
            request.setMethod("post");
            request.setRequestPayload(payload);
            Response response = ApiInvocation.handleRequest(request);
            res = response;


            GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Executed Successfully", STATUS.PASS);
        } catch (Exception e) {
            GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Did not Executed Successfully", STATUS.FAIL);
        }


        int status = res.getStatus();

        GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);

        if (status == 500) {
            GemTestReporter.addTestStep("Status Verification", "Expected Status : 500", STATUS.PASS);
            String bo = res.getErrorMessage();
            GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.PASS);


        } else if (status == 200) {
            GemTestReporter.addTestStep("Status Verification", "Expected Status : 500", STATUS.FAIL);
            String bio = res.getResponseBody();

            JsonParser parser = new JsonParser();
            JsonObject body = (JsonObject) parser.parse(bio);


            GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);

            JsonObject token = body.get("data").getAsJsonObject();
            String Token = token.get("token").getAsString();
            GemTestReporter.addTestStep("Bridge Token", String.valueOf(token), STATUS.INFO);

            String message = body.get("message").toString();

            GemTestReporter.addTestStep("Final Message", message, STATUS.FAIL);
        } else {
            String bo = res.getErrorMessage();
            GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.FAIL);

        }

    }


    @Test
    public void Loginuser_empty_body() {

        GemTestReporter.addTestStep("Test Case", "Test to check the Login User Api when body is empty ", STATUS.INFO);
        String url = ProjectConfigData.getProperty("Login");

        GemTestReporter.addTestStep("Url for Post Request", url, STATUS.INFO);

        String payload = ProjectSampleJson.getSampleDataString("Login4_sampleJson");


        GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);

        Response res = null;

        try {



            Request request = new Request();
            request.setURL(url);
            request.setMethod("post");
            request.setRequestPayload(payload);
            Response response = ApiInvocation.handleRequest(request);
            res = response;
            GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Executed Successfully", STATUS.PASS);
        }

        catch (Exception e) {
            GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Did not Executed Successfully", STATUS.FAIL);
        }
        // System.out.println(res);

        int status = res.getStatus();

        GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);

        if (status == 500) {
            GemTestReporter.addTestStep("Status Verification", "Expected Status : 500", STATUS.PASS);
            String bo = res.getErrorMessage();
            GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.PASS);


        } else if (status == 200) {
            GemTestReporter.addTestStep("Status Verification", "Expected Status : 500", STATUS.FAIL);

            String bod = res.getResponseBody();
            JsonParser parser = new JsonParser();
            JsonObject body = (JsonObject) parser.parse(bod);
            GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);

            JsonObject token = body.get("data").getAsJsonObject();
            String Token = token.get("token").getAsString();
            GemTestReporter.addTestStep("Bridge Token", String.valueOf(token), STATUS.INFO);

            String message = body.get("message").toString();

            GemTestReporter.addTestStep("Final Message", message, STATUS.FAIL);
        } else {
            String bo = res.getErrorMessage();
            GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.FAIL);

        }

    }


}


