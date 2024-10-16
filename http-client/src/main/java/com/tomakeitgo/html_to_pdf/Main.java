package com.tomakeitgo.html_to_pdf;

import com.tomakeitgo.html_to_pdf.http.PDFClient;

import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        var request = new RequestBuilder()
                .setAuthor("Author")
                .setCreator("Creator")
                .setTitle("My Title")
                .setSubject("My Subject")
                .setKeywords("Keyword string")
                .addResource("a.css", " * { color: red; }".getBytes(StandardCharsets.UTF_8))
                .addEntry("entry1.html", "<html><head><link href='a.css' rel='stylesheet'></head><body>Hi!!!</body></html>".getBytes(StandardCharsets.UTF_8))
                .addEntry("entry2.html", "<html><body>by</body></html>".getBytes(StandardCharsets.UTF_8));

        try (var httpClient = new PDFClient(HttpClient.newBuilder().build(), "http://localhost:8080/test")) {
            ConfigIO.unzipIt(
                    httpClient.convert(request),
                    Path.of("/Users/willlowery/IdeaProjects/html-to-pdf/http-client/test")
            );
        }
    }
}