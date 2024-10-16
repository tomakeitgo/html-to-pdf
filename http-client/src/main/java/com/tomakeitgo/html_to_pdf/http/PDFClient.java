package com.tomakeitgo.html_to_pdf.http;

import com.tomakeitgo.html_to_pdf.RequestBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PDFClient implements AutoCloseable{

    private final HttpClient client;
    private final String url;

    public PDFClient(HttpClient client, String url) {
        this.client = client;
        this.url = url;
    }


    public byte[] convert(RequestBuilder req) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(url))
                .method("POST", HttpRequest.BodyPublishers.ofByteArray(req.toByteArray()))
                .build();

        HttpResponse<byte[]> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofByteArray()
        );

        return response.body();
    }


    @Override
    public void close() throws Exception {
        //client.close();
    }
}
