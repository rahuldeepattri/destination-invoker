package org.example;

import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.testutil.MockDestination;
import com.sap.cloud.sdk.testutil.MockUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * Hello world!
 */
//mvn exec:java -Dexec.mainClass="org.example.App"
////mvn clean package exec:java -Dexec.mainClass="org.example.App"
public class App {
    public static void main(String[] args) throws Exception {
        noAuth();
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

    private static void noAuth() throws IOException {
        HttpDestination httpDestination = DestinationAccessor.getDestination("NoAuthentication").asHttp();
        HttpClient httpClient = HttpClientAccessor.getHttpClient(httpDestination);

        HttpResponse execute = httpClient.execute(new HttpGet());
        String result = IOUtils.toString(execute.getEntity().getContent(), StandardCharsets.UTF_8);

        System.out.println(result);
    }
}
