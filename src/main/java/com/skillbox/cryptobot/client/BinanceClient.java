package com.skillbox.cryptobot.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BinanceClient {
    private final HttpGet httpGet;
    private final ObjectMapper mapper;
    private final HttpClient httpClient;

    public BinanceClient(@Value("${binance.api.getPrice}") String uri) {
        httpGet = new HttpGet(uri);
        mapper = new ObjectMapper();
        httpClient = HttpClientBuilder.create()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
    }
    public double getBitcoinPrice() throws IOException {
        System.out.println("Performing client call to binanceApi to get bitcoin price");
        try {
            return mapper.readTree(EntityUtils.toString(httpClient.execute(httpGet).getEntity()))
                    .path("price").asDouble();
        } catch (IOException e) {
            System.out.println("Error while getting price from binance " + e);
            throw e;
        }
    }
}
