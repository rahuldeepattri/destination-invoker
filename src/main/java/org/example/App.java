package org.example;

import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.testutil.MockDestination;
import com.sap.cloud.sdk.testutil.MockUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 */
//mvn exec:java -Dexec.mainClass="org.example.App"
////mvn clean package exec:java -Dexec.mainClass="org.example.App"
@Slf4j
public class App {
    public static void main(String[] args) throws Exception {
        List<String> destinations = Arrays.asList("NoAuthentication", "BasicAuthentication", "OAuth2ClientCredentials");

        for (String destination : destinations) {
            String result = callHttpDestination(destination);
            log.info(result);
        }


    }

    private static void mockExample() throws IOException {
        final MockUtil mockUtil = new MockUtil();

        MockDestination destination = MockDestination
                .builder("my-destination", URI.create("https://services.odata.org/V2/Northwind/Northwind.svc"))
                .build();

        mockUtil.mockDestination(destination);


        HttpDestination httpDestination = DestinationAccessor.getDestination("my-destination").asHttp();
//        HttpDestination httpDestination = DestinationAccessor.getDestination("NoAuthentication").asHttp();
        HttpClient httpClient = HttpClientAccessor.getHttpClient(httpDestination);

        HttpResponse execute = httpClient.execute(new HttpGet());
        execute.getEntity().writeTo(System.out);
    }

    private static String callHttpDestination(String destinationName) throws IOException {
        HttpDestination httpDestination = DestinationAccessor.getDestination(destinationName).asHttp();
        HttpClient httpClient = HttpClientAccessor.getHttpClient(httpDestination);

        HttpResponse execute = httpClient.execute(new HttpGet());
        log.debug(execute.toString());
        return IOUtils.toString(execute.getEntity().getContent(), StandardCharsets.UTF_8);
    }
}
