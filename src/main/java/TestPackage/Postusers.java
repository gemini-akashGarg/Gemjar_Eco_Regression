package TestPackage;

import com.gemini.generic.api.utils.*;
import com.gemini.generic.reporting.GemTestReporter;
import com.gemini.generic.reporting.STATUS;
import com.gemini.generic.tdd.GemjarTestngBase;
import com.gemini.generic.utils.ProjectConfigData;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.testng.annotations.Test;

public class Postusers extends GemjarTestngBase {

    @Test
    public void Create_New_User() {

        GemTestReporter.addTestStep("Test Case", "Test to check the Post function by creating new user ", STATUS.INFO);

        String url = ProjectConfigData.getProperty("Post");

        GemTestReporter.addTestStep("Url for Post Request", url, STATUS.INFO);

        String payloads = ProjectSampleJson.getSampleDataString("Postgemini_sampleJson");

        JsonParser parser = new JsonParser();
        JsonElement pay = parser.parse(payloads);

        String payload = String.valueOf(ApiHealthCheckUtils.result(pay));

        System.out.println(payload);
        GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);


        try {

            Request request = new Request();
            request.setURL(url);
            request.setMethod("Post");
            request.setRequestPayload(payload);

            Response response = ApiInvocation.handleRequest(request);

            GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Executed Successfully", STATUS.PASS);

            if (response.getResponseBody() == null) {
                GemTestReporter.addTestStep("Final Response", "Null", STATUS.FAIL);
            } else {
                int status = response.getStatus();

                GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);

                if (status == 201) {
                    GemTestReporter.addTestStep("Status Verification", "Expected Status : 201", STATUS.PASS);

                    String bodi = response.getResponseBody();
                    JsonParser parser1 = new JsonParser();
                    JsonObject body = (JsonObject) parser.parse(bodi);
                    GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);

                    JsonObject token = body.get("data").getAsJsonObject();
                    String Token = token.get("bridgeToken").getAsString();
                    GemTestReporter.addTestStep("Bridge Token", String.valueOf(Token), STATUS.INFO);

                    String message = body.get("message").toString();

                    GemTestReporter.addTestStep("Final Message", message, STATUS.PASS);

                } else {

                    String bo = response.getErrorMessage();
                    GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.FAIL);


                }
            }


        } catch (Exception e) {
            GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Did not Executed Successfully", STATUS.FAIL);
        }


    }

    @Test
    public void Create_New_User_username_alreadyexists() {


        GemTestReporter.addTestStep("Test Case", "Test to check the Post function by creating new user when the username already exists ", STATUS.INFO);

        String url = ProjectConfigData.getProperty("Post");


        GemTestReporter.addTestStep("Url for Post Request", url, STATUS.INFO);

        String payloads = ProjectSampleJson.getSampleDataString("Postgemini2_sampleJson");
        JsonParser parser = new JsonParser();
        JsonObject pay = (JsonObject) parser.parse(payloads);
        String payload = String.valueOf(ApiHealthCheckUtils.result(pay));

        GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);


        try {

            Request request = new Request();
            request.setURL(url);
            request.setMethod("Post");
            request.setRequestPayload(payload);

            Response response = ApiInvocation.handleRequest(request);


            GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Executed Successfully", STATUS.PASS);
            int status = response.getStatus();

            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);

            if (status == 409) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 409", STATUS.PASS);


                String Error = response.getErrorMessage();
                GemTestReporter.addTestStep("Response Body", String.valueOf(Error), STATUS.PASS);


            } else if (status == 400) {
                String bo = response.getErrorMessage();
                GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.FAIL);


            } else if (status == 201) {
                String body = response.getResponseBody();
                GemTestReporter.addTestStep("Final response", String.valueOf(body), STATUS.FAIL);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    @Test
    public void Create_New_User_compulsory_field_not_present() {

        GemTestReporter.addTestStep("Test Case", "Test to check the Post function by creating new user when the compulsory fields are not present", STATUS.INFO);
        String url = ProjectConfigData.getProperty("Post");


        GemTestReporter.addTestStep("Url for Post Request", url, STATUS.INFO);
        String payloads = ProjectSampleJson.getSampleDataString("Postgemini3_sampleJson");
        JsonParser parser = new JsonParser();
        JsonObject pay = (JsonObject) parser.parse(payloads);
        String payload = String.valueOf(ApiHealthCheckUtils.result(pay));

        GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);

        Response res = null;

        try {

            Request request = new Request();
            request.setURL(url);
            request.setMethod("Post");
            request.setRequestPayload(payload);

            Response response = ApiInvocation.handleRequest(request);
            res = response;

            GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Executed Successfully", STATUS.PASS);

        } catch (Exception e) {
            GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Did not Executed Successfully", STATUS.FAIL);
        }
        // System.out.println(res);
        try {


            int status = res.getStatus();

            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);

            if (status == 500) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 500", STATUS.PASS);


                String Error = res.getErrorMessage();
                GemTestReporter.addTestStep("Response Body", String.valueOf(Error), STATUS.PASS);


            } else if (status == 400) {
                String bo = res.getErrorMessage();
                GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.FAIL);


            } else {
                String bo = res.getErrorMessage();
                GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.FAIL);

            }
        } catch (Exception e) {
            e.printStackTrace();
            GemTestReporter.addTestStep("ERROR!", "Something Wrong happened", STATUS.FAIL);
        }
    }


    @Test
    public void Create_New_User_bygiving_empty_body() {

        GemTestReporter.addTestStep("Test Case", "Test to check the Post function by creating new user when the body is empty", STATUS.INFO);


        String url = ProjectConfigData.getProperty("Post");

        GemTestReporter.addTestStep("Url for Post Request", url, STATUS.INFO);
        String payloads = ProjectSampleJson.getSampleDataString("Postgemini4_sampleJson");
        JsonParser parser = new JsonParser();
        JsonObject pay = (JsonObject) parser.parse(payloads);
        String payload = String.valueOf(ApiHealthCheckUtils.result(pay));
       GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);

        Response res = null;

        try {

            Request request = new Request();
            request.setURL(url);
            request.setMethod("Post");
            request.setRequestPayload(payload);

            Response response = ApiInvocation.handleRequest(request);
            res = response;


            GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Executed Successfully", STATUS.PASS);

        } catch (Exception e) {
            GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Did not Executed Successfully", STATUS.FAIL);
        }

try {
    int status = res.getStatus();

    GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);

    if (status == 500) {
        GemTestReporter.addTestStep("Status Verification", "Expected Status : 500", STATUS.PASS);


        String Error = res.getErrorMessage();
        GemTestReporter.addTestStep("Response Body", String.valueOf(Error), STATUS.PASS);



    } else if (status == 400) {
        String bo = res.getErrorMessage();
        GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.FAIL);


    } else if (status == 201) {
        String bo = res.getResponseBody();
        GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.FAIL);

    } else {
        String bo = res.getErrorMessage();
        GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.FAIL);

    }
}
catch (Exception e)
{
    e.printStackTrace();
    GemTestReporter.addTestStep("Error!!","Something wrong happened",STATUS.FAIL);
}
    }


    @Test
    public void Create_New_User_bygiving_wrong_email() {

        GemTestReporter.addTestStep("Test Case", "Test to check the Post function by creating new user when the Email format is not correct", STATUS.INFO);
        String url = ProjectConfigData.getProperty("Post");


        GemTestReporter.addTestStep("Url for Post Request", url, STATUS.INFO);
        String payloads= ProjectSampleJson.getSampleDataString("Postgemini5_sampleJson");
        JsonParser parser = new JsonParser();
        JsonObject pay = (JsonObject) parser.parse(payloads);
        String payload = String.valueOf(ApiHealthCheckUtils.result(pay));

        GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);

        Response res = null;

        try {
            Request request = new Request();
            request.setURL(url);
            request.setMethod("Post");
            request.setRequestPayload(payload);

            Response response = ApiInvocation.handleRequest(request);
            res = response;

            GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Executed Successfully", STATUS.PASS);

        } catch (Exception e) {
            GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Did not Executed Successfully", STATUS.FAIL);
        }
        // System.out.println(res);

        try {


            int status = res.getStatus();

            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);

            if (status == 500) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 500", STATUS.FAIL);


                String Error = res.getErrorMessage();
                GemTestReporter.addTestStep("Response Body", String.valueOf(Error), STATUS.FAIL);


            } else if (status == 400) {
                String bo = res.getErrorMessage();
                GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.PASS);


            } else if (status == 201) {
                String bo = res.getResponseBody();
                GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.FAIL);

            } else {
                String bo = res.getErrorMessage();
                GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.FAIL);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            GemTestReporter.addTestStep("ERROR!!","Somwthing Wrong Happened",STATUS.FAIL);
        }
        }

}
