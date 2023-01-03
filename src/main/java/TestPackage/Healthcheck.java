package TestPackage;

import com.gemini.generic.api.utils.ApiInvocation;
import com.gemini.generic.api.utils.ApiInvocationImpl;
import com.gemini.generic.api.utils.GemJarHealthCheckBase;
import com.gemini.generic.api.utils.ProjectSampleJson;
import com.gemini.generic.exception.GemException;
import org.testng.annotations.Test;

public class Healthcheck extends GemJarHealthCheckBase {


@Test
    public static void Test() throws GemException {
    String payload = ProjectSampleJson.getSampleDataString("healthcheck");
    ApiInvocationImpl.healthCheck("src/main/resources/healthcheck.json");
    ApiInvocationImpl.healthCheck("src/main/resources/healthcheck1.json");
    ApiInvocationImpl.healthCheck("src/main/resources/healthcheck2.json");




}

}
