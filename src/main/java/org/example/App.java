package org.example;

import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

/**
 * Hello world!
 */
//mvn exec:java -Dexec.mainClass="org.example.App"
public class App {
    public static void main(String[] args) throws IOException {
        HttpDestination httpDestination = DestinationAccessor.getDestination("NoAuthentication").asHttp();
        HttpClient httpClient = HttpClientAccessor.getHttpClient(httpDestination);

        HttpResponse execute = httpClient.execute(new HttpGet());
        System.out.println(execute);

    }
}
