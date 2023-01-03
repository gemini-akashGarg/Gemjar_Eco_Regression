package TestPackage;

import com.gemini.generic.api.utils.*;
import com.gemini.generic.reporting.GemTestReporter;
import com.gemini.generic.reporting.STATUS;
import com.gemini.generic.tdd.GemjarTestngBase;
import com.gemini.generic.utils.GemJarGlobalVar;
import com.gemini.generic.utils.ProjectConfigData;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

//
//import com.gemini.generic.tdd.GemjarTestngBase;
//import com.google.gson.JsonObject;
//
//public class posttoken extends GemjarTestngBase {
//
//
//
//    String TO;
//    String token;
//    String bt;
//
//    public String token() {
//        String Token;
////        String urlss = ProjectApiUrl.getUrl("Login");
////        JsonObject payloadss = ProjectSampleJson.getSampleData("Login_sampleJson").getAsJsonObject();
////        payloadss = (JsonObject) ApiHealthCheckUtils.result(payloadss);
////        JsonObject res = null;
////        res = ApiClientConnect.postRequest(urlss, String.valueOf(payloadss), "json");
////        JsonObject Boddy = (JsonObject) res.get("responseBody");
////        JsonObject to = (JsonObject) Boddy.get("data");
////        String tokenss = String.valueOf(to.get("token"));
////        Token = tokenss;
////        return Token;
//
//    }
//
//
//
//}
public class posttoken extends GemjarTestngBase {


    String TO;
    String token;
    String bt;


    public String token() throws Exception {
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

    }


    @Test
    public void changetoken() throws Exception {

        GemTestReporter.addTestStep("Test Case", "Test to check the Change Token API when the JWT is not expired", STATUS.INFO);
        String url = ProjectConfigData.getProperty("posttoken");


        GemTestReporter.addTestStep("Url for Post  Request", url, STATUS.INFO);


        String j = token();


        Map<String, String> headers = new HashMap<>();

        String jnew = j.replaceAll("^\"|\"$", "");


        headers.put("Authorization", "Bearer " + jnew);
        Request request = new Request();
        request.setURL(url);
        request.setMethod("Post");
        request.setHeaders(headers);
        request.setStep("Change Token");

        Response response = ApiInvocation.handleRequest(request);

        String ress = response.getResponseBody();

        JsonParser parser = new JsonParser();
        JsonObject res = (JsonObject) parser.parse(ress);


        JsonObject data = res.get("data").getAsJsonObject();
        String too = data.get("bridgeToken").getAsString();
        bt = too;


    }

    @Test
    public void changetoken_header_not_given() throws Exception {

        GemTestReporter.addTestStep("Test Case", "Test to check the Change Token API when the Auth header is not given", STATUS.INFO);
        String url = ProjectConfigData.getProperty("posttoken");
        GemTestReporter.addTestStep("Url for Post  Request", url, STATUS.INFO);


        Response res = null;
        try {
            try {
                String j = token();


                Map<String, String> headers = new HashMap<>();

                String jnew = j.replaceAll("^\"|\"$", "");

                headers.put("Authorization", "Bearer " + jnew);

                Request request = new Request();
                request.setURL(url);
                request.setMethod("Post");


                Response response = ApiInvocation.handleRequest(request);
                res = response;
            } catch (Exception e) {
                GemTestReporter.addTestStep("Request Execution", "POST req did not Executed Successfully", STATUS.FAIL);
            }

            // String resss = res.getResponseBody();

//            JsonParser parser = new JsonParser();
//            JsonObject rest = (JsonObject) parser.parse(resss);


            int status = res.getStatus();

            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);

            if (status == 200) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.FAIL);

                GemTestReporter.addTestStep("Response After hitting the API ", String.valueOf(res.getResponseBody()), STATUS.INFO);


            } else {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.PASS);

                GemTestReporter.addTestStep("Final response", "No response", STATUS.PASS);


            }

        } catch (Exception e) {
            GemTestReporter.addTestStep("Final token", "Some error occured while Changing the token", STATUS.FAIL);
            e.printStackTrace();


        }
    }


    @Test
    public void changetoken_Wrong_auth() {

        GemTestReporter.addTestStep("Test Case", "Test to check the Change Token API when the Authorisation is wrong", STATUS.INFO);

        String url = ProjectConfigData.getProperty("posttoken");

        GemTestReporter.addTestStep("Url for Post  Request", url, STATUS.INFO);
        try {
            String j = token();
            JsonObject res = null;
            Map<String, String> headers = new HashMap<>();

            String jnew = j.replaceAll("^\"|\"$", "");
            ////System.out.println(j);

            ////System.out.println(jnew);
            headers.put("Authorization", "Bearer " + jnew + "lolpas");
            Response resp = null;
            try {

                Request request = new Request();
                request.setURL(url);
                request.setMethod("Post");
                request.setHeaders(headers);

                Response response = ApiInvocation.handleRequest(request);
                resp = response;
                GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Executed Successfully", STATUS.PASS);
            } catch (Exception e) {
                GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Did not Executed Successfully", STATUS.FAIL);
            }

            int status = resp.getStatus();


            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);

            if (status == 200) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 200", STATUS.FAIL);
                String bodi = resp.getResponseBody();
                JsonParser parser = new JsonParser();
                JsonObject body = (JsonObject) parser.parse(bodi);

                GemTestReporter.addTestStep("Response After hitting the API ", String.valueOf(body), STATUS.INFO);

                String message = String.valueOf(body.get("message"));
                GemTestReporter.addTestStep("Final Message ", String.valueOf(message), STATUS.FAIL);

            } else {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.PASS);

                GemTestReporter.addTestStep("Final response", "No response", STATUS.PASS);


            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Final token", "Some error occured while fetching token", STATUS.FAIL);
            e.printStackTrace();


        }

    }

    public void Gettoken2() throws Exception {

        String url = ProjectConfigData.getProperty("Gettoken");


        String j = token();
        Response res = null;
        Map<String, String> headers = new HashMap<>();

        String jnew = j.replaceAll("^\"|\"$", "");

        headers.put("Authorization", "Bearer " + jnew);
        try {

            Request request = new Request();
            request.setURL(url);
            request.setMethod("get");
            request.setHeaders(headers);

            Response response = ApiInvocation.handleRequest(request);
            res = response;

        } catch (Exception e) {
            GemTestReporter.addTestStep(" Get Request Verification ", "Get Request Did not Executed Successfully", STATUS.FAIL);
        }

        int status = res.getStatus();


        if (status == 200) {

            String bodi = res.getResponseBody();
            JsonParser parser = new JsonParser();
            JsonObject body = (JsonObject) parser.parse(bodi);

            JsonObject data = body.get("data").getAsJsonObject();
            String too = data.get("bridgeToken").getAsString();
            bt = too;

        }
    }

    @Test
    public void Insertsuitess() {
        try {
            Gettoken2();

            GemTestReporter.addTestStep("Test Case", "Test to insert the suite using Post API  ", STATUS.INFO);
            String url = ProjectConfigData.getProperty("pospo");

            GemTestReporter.addTestStep("Url for Post Request", url, STATUS.INFO);

            String payloads = ProjectSampleJson.getSampleDataString("psuite_sampleJson");

            JsonParser parser = new JsonParser();
            JsonObject pay = (JsonObject) parser.parse(payloads);

            String payload = String.valueOf(ApiHealthCheckUtils.result(pay));

            GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);

            //headers

            //  ////System.out.println(bridgeToken);

            String username = ProjectConfigData.getProperty("username");
            //String bridgeToken = (String) projectProperty.get("bridgeToken");
            Map<String, String> headers = new HashMap<>();
            // \ ////System.out.println(bridgeToken);
            headers.put("username", username);
            headers.put("bridgeToken", bt);
            Response res = null;

            try {


                Request request = new Request();
                request.setURL(url);
                request.setMethod("post");
                request.setHeaders(headers);
                request.setRequestPayload(payload);
                Response response = ApiInvocation.handleRequest(request);
                res = response;
                GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Executed Successfully", STATUS.PASS);
            } catch (Exception e) {
                GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Did not Executed Successfully", STATUS.FAIL);
            }
            //  ////System.out.println(res);

            int status = res.getStatus();
            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);
            if (status == 201) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 201", STATUS.PASS);
                String body = res.getResponseBody();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);

            } else if (status == 500) {
                GemTestReporter.addTestStep("Response Body", "Internal seerver error", STATUS.FAIL);

            } else {
                String body = res.getErrorMessage();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 201", STATUS.FAIL);


            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Final token", "Some error occured while fetching token", STATUS.FAIL);
            e.printStackTrace();


        }

    }

    @Test
    public void Insertsuitess_s_run_idpresent() {
        try {
            Gettoken2();

            GemTestReporter.addTestStep("Test Case", "Test to insert the suite using Post API when S-run-id is already present", STATUS.INFO);

            String url = ProjectConfigData.getProperty("pospo");
            GemTestReporter.addTestStep("Url for Post Request", url, STATUS.INFO);

            String payloads = ProjectSampleJson.getSampleDataString("psuite1_sampleJson");

            JsonParser parser = new JsonParser();
            JsonObject pay = (JsonObject) parser.parse(payloads);

            String payload = String.valueOf(ApiHealthCheckUtils.result(pay));


            GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);

            //headers

            String username = ProjectConfigData.getProperty("username");

            Map<String, String> headers = new HashMap<>();
            headers.put("username", username);
            headers.put("bridgeToken", bt);
            Response res = null;

            try {

                Request request = new Request();
                request.setURL(url);
                request.setMethod("post");
                request.setHeaders(headers);
                request.setRequestPayload(payload);
                Response response = ApiInvocation.handleRequest(request);
                res = response;


                GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Executed Successfully", STATUS.PASS);

            } catch (Exception e) {

                GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Did not Executed Successfully", STATUS.FAIL);
            }
            //  ////System.out.println(res);

            int status = res.getStatus();
            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);
            if (status == 400) {
                String body = res.getErrorMessage();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 400", STATUS.PASS);


            } else if (status == 201) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 201", STATUS.FAIL);
                String bodi = res.getResponseBody();
                GemTestReporter.addTestStep("Response Body", String.valueOf(bodi), STATUS.INFO);

                JsonParser parser1 = new JsonParser();
                JsonObject body = (JsonObject) parser.parse(bodi);


                String message = body.get("message").toString();
                GemTestReporter.addTestStep("Final Message", message, STATUS.FAIL);

            } else if (status == 500) {
                GemTestReporter.addTestStep("Response Body", "Internal seerver error", STATUS.FAIL);

            } else {
                String body = res.getErrorMessage();

                GemTestReporter.addTestStep("Status Verification", "Expected Status : 201", STATUS.FAIL);

                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);

            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Final token", "Some error occured while fetching token", STATUS.FAIL);
            e.printStackTrace();


        }

    }

    @Test
    public void Insertsuitess_s_run_id_not_presentinthepayload() {
        try {
            Gettoken2();

            GemTestReporter.addTestStep("Test Case", "Test to insert the suite using Post API when S-run-id is not given in the payload", STATUS.INFO);

            String url = ProjectConfigData.getProperty("pospo");

            GemTestReporter.addTestStep("Url for Post Request", url, STATUS.INFO);

//            JsonObject payload = ProjectSampleJson.getSampleData("psuite2_sampleJson").getAsJsonObject();


            String payloads = ProjectSampleJson.getSampleDataString("psuite2_sampleJson");

            JsonParser parser = new JsonParser();
            JsonObject pay = (JsonObject) parser.parse(payloads);

            String payload = String.valueOf(ApiHealthCheckUtils.result(pay));

            GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);
            //headers
            String username = ProjectConfigData.getProperty("username");
//            String username = (String) projectProperty.get("username");
            Map<String, String> headers = new HashMap<>();
            headers.put("username", username);
            headers.put("bridgeToken", bt);
            Response res = null;

            try {


                Request request = new Request();
                request.setURL(url);
                request.setMethod("post");
                request.setHeaders(headers);
                request.setRequestPayload(payload);
                Response response = ApiInvocation.handleRequest(request);
                res = response;


                GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Executed Successfully", STATUS.PASS);
            } catch (Exception e) {
                GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Did not Executed Successfully", STATUS.FAIL);
            }


            int status = res.getStatus();
            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);
            if (status == 201) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 201", STATUS.PASS);
                String body = res.getResponseBody();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);


            } else if (status == 500) {
                GemTestReporter.addTestStep("Response Body", "Internal server error", STATUS.FAIL);

            } else {
                String body = res.getErrorMessage();

                GemTestReporter.addTestStep("Status Verification", "Expected Status : 201", STATUS.FAIL);


                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);

            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Final token", "Some error occured while fetching token", STATUS.FAIL);
            e.printStackTrace();


        }
    }


    @Test
    public void Insertsuitess_wrong_auth() {
        try {
            Gettoken2();

            GemTestReporter.addTestStep("Test Case", "Test to insert the suite using Post API when Wrong Auth is given", STATUS.INFO);
            String url = ProjectConfigData.getProperty("pospo");
            GemTestReporter.addTestStep("Url for Post Request", url, STATUS.INFO);


            String payloads = ProjectSampleJson.getSampleDataString("psuite2_sampleJson");

            JsonParser parser = new JsonParser();
            JsonObject pay = (JsonObject) parser.parse(payloads);

            String payload = String.valueOf(ApiHealthCheckUtils.result(pay));


            GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);
            //headers
            //  String bridgeToken = (String) projectProperty.get("bridgeToken");
            String username = ProjectConfigData.getProperty("username");
//            String username = (String) projectProperty.get("username");
            Map<String, String> headers = new HashMap<>();
            headers.put("username", username);
            headers.put("bridgeToken", bt + "sssss");
            Response res = null;

            try {


                Request request = new Request();
                request.setURL(url);
                request.setMethod("post");
                request.setHeaders(headers);
                request.setRequestPayload(payload);
                Response response = ApiInvocation.handleRequest(request);
                res = response;


                GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Executed Successfully", STATUS.PASS);
            } catch (Exception e) {
                GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Did not Executed Successfully", STATUS.FAIL);
            }
            //  ////System.out.println(res);

            int status = res.getStatus();
            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);
            if (status == 201) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.FAIL);
                String body = res.getResponseBody();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);


            } else if (status == 500) {
                GemTestReporter.addTestStep("Response Body", "Internal server error", STATUS.FAIL);

            } else if (status == 403) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.PASS);

                String body = res.getErrorMessage();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.PASS);


            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Final token", "Some error occured while fetching token", STATUS.FAIL);
            e.printStackTrace();


        }
    }

    @Test
    public void Insertsuitess_auth_not_given() {
        try {
            Gettoken2();

            GemTestReporter.addTestStep("Test Case", "Test to insert the suite using Post API when Wrong Auth is given", STATUS.INFO);
            String url = ProjectConfigData.getProperty("pospo");
            GemTestReporter.addTestStep("Url for Post Request", url, STATUS.INFO);


            String payloads = ProjectSampleJson.getSampleDataString("psuite2_sampleJson");

            JsonParser parser = new JsonParser();
            JsonObject pay = (JsonObject) parser.parse(payloads);

            String payload = String.valueOf(ApiHealthCheckUtils.result(pay));


//            JsonObject payload = ProjectSampleJson.getSampleData("psuite2_sampleJson").getAsJsonObject();
            GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);
            //headers
            //  String bridgeToken = (String) projectProperty.get("bridgeToken");
//            String username = (String) projectProperty.get("username");
            String username = ProjectConfigData.getProperty("username");
            Map<String, String> headers = new HashMap<>();
            headers.put("username", username);
            headers.put("bridgeToken", bt + "sssss");
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
            ////System.out.println(res);

            int status = res.getStatus();
            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);
            if (status == 201) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.FAIL);
                String body = res.getResponseBody();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.FAIL);


            } else if (status == 500) {
                GemTestReporter.addTestStep("Response Body", "Internal server error", STATUS.FAIL);

            } else if (status == 403) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.PASS);

                String body = res.getErrorMessage();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.PASS);


            } else {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.FAIL);

                String body = res.getErrorMessage();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.FAIL);

            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Final token", "Some error occured while fetching token", STATUS.FAIL);
            e.printStackTrace();


        }
    }


    @Test
    public void Updatesuite() {
        try {
            Gettoken2();

            GemTestReporter.addTestStep("Test Case", "Test to Update the suite using Put API  ", STATUS.INFO);

            String url = ProjectConfigData.getProperty("putu");

            GemTestReporter.addTestStep("Url for Put Request", url, STATUS.INFO);


            String payloads = ProjectSampleJson.getSampleDataString("put_1_sampleJson");

//            JsonParser parser = new JsonParser();
//            JsonObject pay = (JsonObject) parser.parse(payloads);
//
//            String payload = String.valueOf(ApiHealthCheckUtils.result(pay));


            JsonParser parser = new JsonParser();
            JsonObject pay = (JsonObject) parser.parse(payloads);

//            JsonObject payload = ProjectSampleJson.getSampleData("put_1_sampleJson").getAsJsonObject();

            if (GemJarGlobalVar.environment.equalsIgnoreCase("prod")) {
                pay.addProperty("s_run_id", "GEMPYP_TEST_PROD_63700467-4D93-46AB-A46E-727B2E85DC3F");
            }


            String payload = String.valueOf(ApiHealthCheckUtils.result(pay));
            GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);

            //headers
            //  String bridgeToken= (String) projectProperty.get("bridgeToken");

            String username = ProjectConfigData.getProperty("username");

            Map<String, String> headers = new HashMap<>();
            headers.put("username", username);
            headers.put("bridgeToken", bt);
            Response res = null;

            try {


                Request request = new Request();
                request.setURL(url);
                request.setMethod("put");
                request.setHeaders(headers);
                request.setRequestPayload(payload);
                Response response = ApiInvocation.handleRequest(request);
                res = response;


                GemTestReporter.addTestStep(" Put Request Verification ", "Put Request Executed Successfully", STATUS.PASS);
            } catch (Exception e) {
                GemTestReporter.addTestStep(" Put Request Verification ", "Put Request Did not Executed Successfully", STATUS.FAIL);
            }
            ////System.out.println(res);

            int status = res.getStatus();

            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);
            if (status == 200) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 200", STATUS.PASS);
                String body = res.getResponseBody();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);


            } else {
                String body = res.getErrorMessage();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 201", STATUS.FAIL);

            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Final token", "Some error occurred while fetching token", STATUS.FAIL);
            e.printStackTrace();


        }

    }

    @Test
    public void Updatesuite_srunidnotpresent() {
        try {
            Gettoken2();

            GemTestReporter.addTestStep("Test Case", "Test to Update the suite using Put API and the s-run_id is not present in the database ", STATUS.INFO);
            String url = ProjectConfigData.getProperty("putu");


            GemTestReporter.addTestStep("Url for Put Request", url, STATUS.INFO);

//payload
            String payloads = ProjectSampleJson.getSampleDataString("put_2_sampleJson");
            JsonParser parser = new JsonParser();
            JsonObject pay = (JsonObject) parser.parse(payloads);

///////


            if (GemJarGlobalVar.environment.equalsIgnoreCase("prod")) {
                pay.addProperty("s_run_id", "b2f779e7-a4f2-44d8-a557-b3426ea520c14");
            }
            String payload = String.valueOf(ApiHealthCheckUtils.result(pay));

            GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);

            //headers
            String username = ProjectConfigData.getProperty("username");

            Map<String, String> headers = new HashMap<>();
            headers.put("username", username);
            headers.put("bridgeToken", bt);

            Response res = null;

            try {


                Request request = new Request();
                request.setURL(url);
                request.setMethod("put");
                request.setHeaders(headers);
                request.setRequestPayload(payload);
                Response response = ApiInvocation.handleRequest(request);
                res = response;


                GemTestReporter.addTestStep(" Put Request Verification ", "Put Request Executed Successfully", STATUS.PASS);
            } catch (Exception e) {
                GemTestReporter.addTestStep(" Put Request Verification ", "Put Request Did not Executed Successfully", STATUS.FAIL);
            }
            ////System.out.println(res);

            int status = res.getStatus();
            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);
            if (status == 400) {

                String body = res.getErrorMessage();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 400", STATUS.PASS);

            } else if (status == 200) {


                GemTestReporter.addTestStep("Status Verification", "Expected Status : 400", STATUS.FAIL);
                String body = res.getResponseBody();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);


            } else {

                String body = res.getErrorMessage();

                GemTestReporter.addTestStep("Status Verification", "Expected Status : 400", STATUS.FAIL);
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);


            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Final token", "Some error occurred while fetching token", STATUS.FAIL);
            e.printStackTrace();


        }

    }


    @Test
    public void Updatesuite_wrong_auth() {
        try {
            Gettoken2();

            GemTestReporter.addTestStep("Test Case", "Test to Update the suite using Put API and Auth is not correct ", STATUS.INFO);
            String url = ProjectConfigData.getProperty("putu");


            GemTestReporter.addTestStep("Url for Put Request", url, STATUS.INFO);


            //payload

            String payloads = ProjectSampleJson.getSampleDataString("put_2_sampleJson");
            JsonParser parser = new JsonParser();
            JsonObject pay = (JsonObject) parser.parse(payloads);

            /////


            if (GemJarGlobalVar.environment.equalsIgnoreCase("prod")) {
                pay.addProperty("s_run_id", "b2f779e7-a4f2-44d8-a557-b3426ea520c1");
            }
            String payload = String.valueOf(ApiHealthCheckUtils.result(pay));

            GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);

            //headers
            //   String bridgeToken= (String) projectProperty.get("bridgeToken");

            String username = ProjectConfigData.getProperty("username");
            Map<String, String> headers = new HashMap<>();
            headers.put("username", username);
            headers.put("bridgeToken", bt + "er");

            Response res = null;

            try {


                Request request = new Request();
                request.setURL(url);
                request.setMethod("put");
                request.setHeaders(headers);
                request.setRequestPayload(payload);
                Response response = ApiInvocation.handleRequest(request);
                res = response;

                GemTestReporter.addTestStep(" Put Request Verification ", "Put Request Executed Successfully", STATUS.PASS);
            } catch (Exception e) {
                GemTestReporter.addTestStep(" Put Request Verification ", "Put Request Did not Executed Successfully", STATUS.FAIL);
            }
            ////System.out.println(res);

            int status = res.getStatus();
            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);
            if (status == 403) {

                String body = res.getErrorMessage();

                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.PASS);

                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);

            } else if (status == 200) {


                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.FAIL);
                String body = res.getResponseBody();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);


            } else {

                String body = res.getErrorMessage();

                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.FAIL);
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);


            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Final token", "Some error occurred while fetching token", STATUS.FAIL);
            e.printStackTrace();


        }

    }

    @Test
    public void Updatesuite_auth_not_given() {
        try {
            Gettoken2();

            GemTestReporter.addTestStep("Test Case", "Test to Update the suite using Put API and Auth header is not given ", STATUS.INFO);
            String url = ProjectConfigData.getProperty("putu");

            GemTestReporter.addTestStep("Url for Put Request", url, STATUS.INFO);
//payload

            String payloads = ProjectSampleJson.getSampleDataString("put_2_sampleJson");
            JsonParser parser = new JsonParser();
            JsonObject pay = (JsonObject) parser.parse(payloads);


            ////////


            if (GemJarGlobalVar.environment.equalsIgnoreCase("prod")) {
                pay.addProperty("s_run_id", "b2f779e7-a4f2-44d8-a557-b3426ea520c1");
            }

            String payload = String.valueOf(ApiHealthCheckUtils.result(pay));


            GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);

            //headers
            String username = ProjectConfigData.getProperty("username");

            Map<String, String> headers = new HashMap<>();
            headers.put("username", username);
            headers.put("bridgeToken", bt);

            Response res = null;

            try {


                Request request = new Request();
                request.setURL(url);
                request.setMethod("put");

                request.setRequestPayload(payload);
                Response response = ApiInvocation.handleRequest(request);
                res = response;

                GemTestReporter.addTestStep(" Put Request Verification ", "Put Request Executed Successfully", STATUS.PASS);
            } catch (Exception e) {
                GemTestReporter.addTestStep(" Put Request Verification ", "Put Request Did not Executed Successfully", STATUS.FAIL);
            }

            int status = res.getStatus();
            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);
            if (status == 400) {

                String body = res.getErrorMessage();

                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.FAIL);
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);


            } else if (status == 200) {


                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.FAIL);
                String body = res.getResponseBody();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);


            } else if (status == 403) {


                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.PASS);

                String body = res.getErrorMessage();
                GemTestReporter.addTestStep("Final Body", body, STATUS.PASS);

            } else {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.FAIL);

            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Final token", "Some error occurred while fetching token", STATUS.FAIL);
            e.printStackTrace();


        }

    }

    @Test
    public void Create_new_record_for_test_case() {
        try {
            Gettoken2();

            GemTestReporter.addTestStep("Test Case", "To create a new testcase in the database using Post function  ", STATUS.INFO);

            String url = ProjectConfigData.getProperty("pospos");


            GemTestReporter.addTestStep("Url for Post Request", url, STATUS.INFO);


            String payloads = ProjectSampleJson.getSampleDataString("ptest_sampleJson");
            JsonParser parser = new JsonParser();
            JsonObject pay = (JsonObject) parser.parse(payloads);


//            JsonObject payload = ProjectSampleJson.getSampleData("ptest_sampleJson").getAsJsonObject();
            if (GemJarGlobalVar.environment.equalsIgnoreCase("prod")) {
                pay.addProperty("s_run_id", "GEMPYP_TEST_PROD_63700467-4D93-46AB-A46E-727B2E85DC3F");
            }

            String payload = String.valueOf(ApiHealthCheckUtils.result(pay));

            GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);


            //headers
            String username = ProjectConfigData.getProperty("username");

            Map<String, String> headers = new HashMap<>();
            headers.put("username", username);
            headers.put("bridgeToken", bt);

            GemTestReporter.addTestStep("Headers", String.valueOf(headers), STATUS.INFO);
            Response res = null;
            try {


                Request request = new Request();
                request.setURL(url);
                request.setMethod("post");
                request.setHeaders(headers);
                request.setRequestPayload(payload);
                Response response = ApiInvocation.handleRequest(request);
                res = response;

                GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Executed Successfully", STATUS.PASS);
            } catch (Exception e) {
                GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Did not Executed Successfully", STATUS.FAIL);
            }
            ////System.out.println(res);

            int status = res.getStatus();
            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);
            if (status == 201) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 201", STATUS.PASS);
                String body = res.getResponseBody();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);


            } else {
                String body = res.getErrorMessage();

                GemTestReporter.addTestStep("Status Verification", "Expected Status : 201", STATUS.FAIL);
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);


            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Final token", "Some error occurred while fetching token", STATUS.FAIL);
            e.printStackTrace();


        }


    }


    @Test
    public void Create_new_record_for_test_case_srunidnotexists() {
        try {
            Gettoken2();

            GemTestReporter.addTestStep("Test Case", "To create a new testcase in the database using Post function S_run_id  Does not Exist in the database ", STATUS.INFO);
            String url = ProjectConfigData.getProperty("pospos");

            GemTestReporter.addTestStep("Url for Post Request", url, STATUS.INFO);


            String payloads = ProjectSampleJson.getSampleDataString("ptest1_sampleJson");
            JsonParser parser = new JsonParser();
            JsonObject pay = (JsonObject) parser.parse(payloads);


            if (GemJarGlobalVar.environment.equalsIgnoreCase("prod")) {
                pay.addProperty("s_run_id", "b2f779e7-a4f2-44d8-a557-b3426ea520c1");
            }
            String payload = String.valueOf(ApiHealthCheckUtils.result(pay));

            GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);

            //headers
            String username = ProjectConfigData.getProperty("username");

            Map<String, String> headers = new HashMap<>();
            headers.put("username", username);
            headers.put("bridgeToken", bt);

            GemTestReporter.addTestStep("Headers", String.valueOf(headers), STATUS.INFO);

            Response res = null;

            try {


                Request request = new Request();
                request.setURL(url);
                request.setMethod("post");
                request.setHeaders(headers);
                request.setRequestPayload(payload);
                Response response = ApiInvocation.handleRequest(request);
                res = response;

                GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Executed Successfully", STATUS.PASS);
            } catch (Exception e) {
                GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Did not Executed Successfully", STATUS.FAIL);
            }


            int status = res.getStatus();
            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);
            if (status == 400) {
                String body = res.getErrorMessage();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 400", STATUS.PASS);


            } else if (status == 201) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 400", STATUS.FAIL);
                String body = res.getResponseBody();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);


            } else {
                String body = res.getErrorMessage();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 400", STATUS.FAIL);

            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Final token", "Some error occured while fetching token", STATUS.FAIL);
            e.printStackTrace();


        }

    }


    @Test
    public void Create_new_record_for_test_case_srunidnotgiven() {

        try {
            Gettoken2();

            GemTestReporter.addTestStep("Test Case", "To create a new testcase in the database using Post function S_run_id  is not given by the user ", STATUS.INFO);
            String url = ProjectConfigData.getProperty("pospos");

            GemTestReporter.addTestStep("Url for Post Request", url, STATUS.INFO);


            String payloads = ProjectSampleJson.getSampleDataString("ptest2_sampleJson");
            JsonParser parser = new JsonParser();
            JsonObject pay = (JsonObject) parser.parse(payloads);


            if (GemJarGlobalVar.environment.equalsIgnoreCase("prod")) {
                pay.addProperty("s_run_id", "b2f779e7-a4f2-44d8-a557-b3426ea520c1");
            }
            String payload = String.valueOf(ApiHealthCheckUtils.result(pay));

            GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);

            //headers
            String username = ProjectConfigData.getProperty("username");

            Map<String, String> headers = new HashMap<>();
            headers.put("username", username);
            headers.put("bridgeToken", bt);

            GemTestReporter.addTestStep("Headers", String.valueOf(headers), STATUS.INFO);

            Response res = null;

            try {

                Request request = new Request();
                request.setURL(url);
                request.setMethod("post");
                request.setHeaders(headers);
                request.setRequestPayload(payload);
                Response response = ApiInvocation.handleRequest(request);
                res = response;


                GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Executed Successfully", STATUS.PASS);
            } catch (Exception e) {
                GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Did not Executed Successfully", STATUS.FAIL);
            }


            int status = res.getStatus();
            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);
            if (status == 400) {
                String body = res.getErrorMessage();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 400", STATUS.PASS);

            } else if (status == 201) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 400", STATUS.FAIL);
                String body = res.getResponseBody();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);

            } else {
                String body = res.getErrorMessage();

                GemTestReporter.addTestStep("Status Verification", "Expected Status : 400", STATUS.FAIL);
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);


            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Final token", "Some error occurred while fetching token", STATUS.FAIL);
            e.printStackTrace();
        }
    }

    @Test

    public void Create_new_record_for_test_case_tc_run_id_not_given() {
        try {
            Gettoken2();
            String url = ProjectConfigData.getProperty("pospos");
            GemTestReporter.addTestStep("Test Case", "To create a new testcase in the database using Post function when user doesnot give tc run id in the payload  ", STATUS.INFO);

            GemTestReporter.addTestStep("Url for Post Request", url, STATUS.INFO);


            String payloads = ProjectSampleJson.getSampleDataString("ptest3_sampleJson");
            JsonParser parser = new JsonParser();
            JsonObject pay = (JsonObject) parser.parse(payloads);

            if (GemJarGlobalVar.environment.equalsIgnoreCase("prod")) {
                pay.addProperty("s_run_id", "GEMPYP_TEST_PROD_63700467-4D93-46AB-A46E-727B2E85DC3F");
            }
            String payload = String.valueOf(ApiHealthCheckUtils.result(pay));

            GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);

            Response res = null;

            //headers
            String username = ProjectConfigData.getProperty("username");

            Map<String, String> headers = new HashMap<>();
            headers.put("username", username);
            headers.put("bridgeToken", bt);

            GemTestReporter.addTestStep("Headers", String.valueOf(headers), STATUS.INFO);
            try {

                Request request = new Request();
                request.setURL(url);
                request.setMethod("post");
                request.setHeaders(headers);
                request.setRequestPayload(payload);
                Response response = ApiInvocation.handleRequest(request);
                res = response;


                GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Executed Successfully", STATUS.PASS);
            } catch (Exception e) {
                GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Did not Executed Successfully", STATUS.FAIL);
            }


            int status = res.getStatus();
            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);
            if (status == 201) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 201", STATUS.PASS);
                String body = res.getResponseBody();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);


            } else {
                String body = res.getErrorMessage();
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 201", STATUS.FAIL);

                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);


            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Final token", "Some error occured while fetching token", STATUS.FAIL);
            e.printStackTrace();


        }

    }


    @Test

    public void Create_new_record_for_test_case_Wrong_auth() {
        try {
            Gettoken2();

            GemTestReporter.addTestStep("Test Case", "To create a new testcase in the database using Post function when user gives the wrong auth  ", STATUS.INFO);

            String url = ProjectConfigData.getProperty("pospos");

            GemTestReporter.addTestStep("Url for Post Request", url, STATUS.INFO);


            String payloads = ProjectSampleJson.getSampleDataString("ptest3_sampleJson");


            JsonParser parser = new JsonParser();
            JsonObject pay = (JsonObject) parser.parse(payloads);

            if (GemJarGlobalVar.environment.equalsIgnoreCase("prod")) {
                pay.addProperty("s_run_id", "b2f779e7-a4f2-44d8-a557-b3426ea520c1");
            }
            String payload = String.valueOf(ApiHealthCheckUtils.result(pay));

            GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);

            Response res = null;

            //headers
            String username = ProjectConfigData.getProperty("username");

            Map<String, String> headers = new HashMap<>();
            headers.put("username", username);
            headers.put("bridgeToken", bt + "kkll");

            GemTestReporter.addTestStep("Headers", String.valueOf(headers), STATUS.INFO);
            try {


                Request request = new Request();
                request.setURL(url);
                request.setMethod("post");
                request.setHeaders(headers);
                request.setRequestPayload(payload);
                Response response = ApiInvocation.handleRequest(request);
                res = response;


                GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Executed Successfully", STATUS.PASS);
            } catch (Exception e) {
                GemTestReporter.addTestStep(" Post Request Verification ", "Post Request Did not Executed Successfully", STATUS.FAIL);
            }


            int status = res.getStatus();
            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);
            if (status == 201) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.FAIL);
                String body = res.getResponseBody();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);

            } else if (status == 403) {
                String body = res.getErrorMessage();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.PASS);


            } else {
                String body = res.getErrorMessage();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.FAIL);

            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Final token", "Some error occured while fetching token", STATUS.FAIL);
            e.printStackTrace();


        }

    }


    @Test

    public void Create_new_record_for_test_case_auth_not_given() {
        try {
            Gettoken2();
            String url = ProjectConfigData.getProperty("pospos");
            GemTestReporter.addTestStep("Test Case", "To create a new testcase in the database using Post function when user Does not gives auth  ", STATUS.INFO);

            GemTestReporter.addTestStep("Url for Post Request", url, STATUS.INFO);

            String payloads = ProjectSampleJson.getSampleDataString("ptest3_sampleJson");


            JsonParser parser = new JsonParser();
            JsonObject pay = (JsonObject) parser.parse(payloads);


            if (GemJarGlobalVar.environment.equalsIgnoreCase("prod")) {
                pay.addProperty("s_run_id", "b2f779e7-a4f2-44d8-a557-b3426ea520c1");
            }

            String payload = String.valueOf(ApiHealthCheckUtils.result(pay));
            GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);

            Response res = null;

            //headers
            String username = ProjectConfigData.getProperty("username");

            Map<String, String> headers = new HashMap<>();
            headers.put("username", username);
            headers.put("bridgeToken", bt + "kkll");

            //   GemTestReporter.addTestStep("Headers", String.valueOf(headers), STATUS.INFO);
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
            if (status == 201) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.FAIL);
                String body = res.getResponseBody();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);


            } else if (status == 403) {
                String body = res.getErrorMessage();

                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.PASS);
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);


            } else {
                String body = res.getErrorMessage();

                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.FAIL);
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);

            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Final token", "Some error occurred while fetching token", STATUS.FAIL);
            e.printStackTrace();


        }
    }


    @Test
    public void Update_testcase_data() {
        try {
            Gettoken2();

            GemTestReporter.addTestStep("Test Case", "Test to check the Put function ", STATUS.INFO);

            String url = ProjectConfigData.getProperty("putexe");

            GemTestReporter.addTestStep("Url for Put Request", url, STATUS.INFO);


            //headers
            String username = ProjectConfigData.getProperty("username");

            Map<String, String> headers = new HashMap<>();
            headers.put("username", username);
            headers.put("bridgeToken", bt);

            GemTestReporter.addTestStep("Headers", String.valueOf(headers), STATUS.INFO);

            //payload


            String payloads = ProjectSampleJson.getSampleDataString("puter1_sampleJson");
            JsonParser parser = new JsonParser();
            JsonObject pay = (JsonObject) parser.parse(payloads);


            if (GemJarGlobalVar.environment.equalsIgnoreCase("prod")) {
                pay.addProperty("tc_run_id", "Test_functions_1_bbab0912-c8b8-4d0f-861d-4f5e2b151146");
                pay.addProperty("s_run_id", "GEMPYP_TEST_PROD_63700467-4D93-46AB-A46E-727B2E85DC3F");

            }
            String payload = String.valueOf(ApiHealthCheckUtils.result(pay));


            GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);

            Response res = null;

            try {


                Request request = new Request();
                request.setURL(url);
                request.setMethod("put");
                request.setHeaders(headers);
                request.setRequestPayload(payload);
                Response response = ApiInvocation.handleRequest(request);
                res = response;

                GemTestReporter.addTestStep(" Put Request Verification ", "Put Request Executed Successfully", STATUS.PASS);
            } catch (Exception e) {
                GemTestReporter.addTestStep(" Put Request Verification ", "Put Request Did not Executed Successfully", STATUS.FAIL);
            }


            int status = res.getStatus();

            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);

            if (status == 200) {

                GemTestReporter.addTestStep("Status Verification", "Expected Status : 200", STATUS.PASS);
                String body = res.getResponseBody();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);

            } else {
                String bo = res.getErrorMessage();
                GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.FAIL);


            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Final token", "Some error occurred while fetching token", STATUS.FAIL);
            e.printStackTrace();


        }

    }

    @Test
    public void Update_testcase_data_srunidnotgiven() {
        try {
            Gettoken2();

            GemTestReporter.addTestStep("Test Case", "To update data of particular testcase using tc_run_id when s run id is not given by the user ", STATUS.INFO);

            String url = ProjectConfigData.getProperty("putexe");

            GemTestReporter.addTestStep("Url for Put Request", url, STATUS.INFO);

            //headers
            String username = ProjectConfigData.getProperty("username");

            Map<String, String> headers = new HashMap<>();
            headers.put("username", username);
            headers.put("bridgeToken", bt);

            GemTestReporter.addTestStep("Headers", String.valueOf(headers), STATUS.INFO);

            //payload


            String payloads = ProjectSampleJson.getSampleDataString("puter2_sampleJson");
            JsonParser parser = new JsonParser();
            JsonObject pay = (JsonObject) parser.parse(payloads);
            String payload = String.valueOf(ApiHealthCheckUtils.result(pay));
            GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);

            Response res = null;

            try {


                Request request = new Request();
                request.setURL(url);
                request.setMethod("put");
                request.setHeaders(headers);
                request.setRequestPayload(payload);
                Response response = ApiInvocation.handleRequest(request);
                res = response;

                GemTestReporter.addTestStep(" Put Request Verification ", "Put Request Executed Successfully", STATUS.PASS);
            } catch (Exception e) {
                GemTestReporter.addTestStep(" Put Request Verification ", "Put Request Did not Executed Successfully", STATUS.FAIL);
            }


            int status = res.getStatus();
            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);
            if (status == 400) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 400", STATUS.PASS);

                String bo = res.getErrorMessage();
                GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.PASS);

            } else if (status == 200) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 400", STATUS.FAIL);
                String body = res.getResponseBody();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);


            } else {
                String bo = res.getErrorMessage();
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 400", STATUS.FAIL);

                GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.FAIL);


            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Final token", "Some error occurred while fetching token", STATUS.FAIL);
            e.printStackTrace();


        }

    }

    @Test
    public void Update_testcase_data_tcrunidnotgiven() {
        try {
            Gettoken2();

            GemTestReporter.addTestStep("Test Case", "To update data of particular testcase using tc_run_id when Tc run id is not given by the user ", STATUS.INFO);

            String url = ProjectConfigData.getProperty("putexe");

            GemTestReporter.addTestStep("Url for Put Request", url, STATUS.INFO);

            //headers
            String username = ProjectConfigData.getProperty("username");
            Map<String, String> headers = new HashMap<>();
            headers.put("username", username);
            headers.put("bridgeToken", bt);

            GemTestReporter.addTestStep("Headers", String.valueOf(headers), STATUS.INFO);

            //payload


            String payloads = ProjectSampleJson.getSampleDataString("puter3_sampleJson");


            JsonParser parser = new JsonParser();
            JsonObject pay = (JsonObject) parser.parse(payloads);
            String payload = String.valueOf(ApiHealthCheckUtils.result(pay));

            GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);

            Response res = null;

            try {


                Request request = new Request();
                request.setURL(url);
                request.setMethod("put");
                request.setHeaders(headers);
                request.setRequestPayload(payload);
                Response response = ApiInvocation.handleRequest(request);
                res = response;

                GemTestReporter.addTestStep(" Put Request Verification ", "Put Request Executed Successfully", STATUS.PASS);
            } catch (Exception e) {
                GemTestReporter.addTestStep(" Put Request Verification ", "Put Request Did not Executed Successfully", STATUS.FAIL);
            }


            int status = res.getStatus();
            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);
            if (status == 400) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 400", STATUS.PASS);

                String bo = res.getErrorMessage();
                GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.PASS);

            } else if (status == 200) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 400", STATUS.FAIL);
                String body = res.getResponseBody();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);

            } else {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 400", STATUS.FAIL);
                String bo = res.getErrorMessage();
                GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.FAIL);
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Final token", "Some error occurred while fetching token", STATUS.FAIL);
            e.printStackTrace();


        }
    }

    @Test
    public void Update_testcase_data_wrong_auth() {
        try {
            Gettoken2();
            GemTestReporter.addTestStep("Test Case", "To update data of particular testcase using tc_run_id when user gives the wrong authorization ", STATUS.INFO);

//url
            String url = ProjectConfigData.getProperty("putexe");

            GemTestReporter.addTestStep("Url for Put Request", url, STATUS.INFO);


            //headers
            String username = ProjectConfigData.getProperty("username");

            Map<String, String> headers = new HashMap<>();
            headers.put("username", username);
            headers.put("bridgeToken", bt + "ddddd");

            GemTestReporter.addTestStep("Headers", String.valueOf(headers), STATUS.INFO);

            //payload


            String payloads = ProjectSampleJson.getSampleDataString("puter3_sampleJson");
            JsonParser parser = new JsonParser();
            JsonObject pay = (JsonObject) parser.parse(payloads);
            String payload = String.valueOf(ApiHealthCheckUtils.result(pay));

            GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);

            Response res = null;

            try {

                Request request = new Request();
                request.setURL(url);
                request.setMethod("put");
                request.setHeaders(headers);
                request.setRequestPayload(payload);
                Response response = ApiInvocation.handleRequest(request);
                res = response;

                GemTestReporter.addTestStep(" Put Request Verification ", "Put Request Executed Successfully", STATUS.PASS);
            } catch (Exception e) {
                GemTestReporter.addTestStep(" Put Request Verification ", "Put Request Did not Executed Successfully", STATUS.FAIL);
            }


            int status = res.getStatus();
            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);
            if (status == 400) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.FAIL);

                String bo = res.getErrorMessage();
                GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.INFO);

            } else if (status == 200) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.FAIL);
                String body = res.getResponseBody();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);

            } else if (status == 403) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.PASS);

                String bo = res.getErrorMessage();
                GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.PASS);


            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Final token", "Some error occurred while fetching token", STATUS.FAIL);
            e.printStackTrace();


        }
    }

    @Test
    public void Update_testcase_data_auth_not_given() {
        try {
            Gettoken2();
            GemTestReporter.addTestStep("Test Case", "To update data of particular testcase using tc_run_id when user does not gives the authorization ", STATUS.INFO);
            String url = ProjectConfigData.getProperty("putexe");
            GemTestReporter.addTestStep("Url for Put Request", url, STATUS.INFO);

            //headers
            String username = ProjectConfigData.getProperty("username");

            Map<String, String> headers = new HashMap<>();
            headers.put("username", username);
            headers.put("bridgeToken", bt + "ddddd");

            //  GemTestReporter.addTestStep("Headers", String.valueOf(headers), STATUS.INFO);

            //payload



            String payloads = ProjectSampleJson.getSampleDataString("puter3_sampleJson");


            JsonParser parser = new JsonParser();
            JsonObject pay = (JsonObject) parser.parse(payloads);
            String payload = String.valueOf(ApiHealthCheckUtils.result(pay));

            GemTestReporter.addTestStep("Payload ", String.valueOf(payload), STATUS.INFO);

            Response res = null;

            try {

                Request request = new Request();
                request.setURL(url);
                request.setMethod("put");

                request.setRequestPayload(payload);
                Response response = ApiInvocation.handleRequest(request);
                res = response;


                GemTestReporter.addTestStep(" Put Request Verification ", "Put Request Executed Successfully", STATUS.PASS);
            } catch (Exception e) {
                GemTestReporter.addTestStep(" Put Request Verification ", "Put Request Did not Executed Successfully", STATUS.FAIL);
            }


            int status = res.getStatus();
            GemTestReporter.addTestStep("Status ", String.valueOf(status), STATUS.INFO);
            if (status == 400) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.FAIL);

                String bo = res.getErrorMessage();
                GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.FAIL);

            } else if (status == 200) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.FAIL);
                String body = res.getResponseBody();
                GemTestReporter.addTestStep("Response Body", String.valueOf(body), STATUS.INFO);


            } else if (status == 403) {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.PASS);

                String bo = res.getErrorMessage();
                GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.PASS);


            } else {
                GemTestReporter.addTestStep("Status Verification", "Expected Status : 403", STATUS.FAIL);

                String bo = res.getErrorMessage();
                GemTestReporter.addTestStep("Final response", String.valueOf(bo), STATUS.FAIL);
            }
        } catch (Exception e) {
            GemTestReporter.addTestStep("Final token", "Some error occurred while fetching token", STATUS.FAIL);
            e.printStackTrace();


        }
    }

}




