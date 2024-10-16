package com.tomakeitgo.html_to_pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class RequestBuilder {

    private final Config config = new Config();
    private final HashMap<String, InputStream> inputs = new HashMap<>();

    public RequestBuilder addEntry(String entry, InputStream inputStream) {
        config.addEntry(entry);
        inputs.put(entry, inputStream);
        return this;
    }

    public RequestBuilder addEntry(String entry, byte[] data) {
        addEntry(entry, new ByteArrayInputStream(data));
        return this;
    }

    public RequestBuilder addResource(String resource, byte[] data) {
        inputs.put(resource, new ByteArrayInputStream(data));
        return this;
    }

    public RequestBuilder setAuthor(String author) {
        config.getOptions().setAuthor(author);
        return this;
    }

    public RequestBuilder setCreator(String creator) {
        config.getOptions().setCreator(creator);
        return this;
    }

    public RequestBuilder setTitle(String title) {
        config.getOptions().setTitle(title);
        return this;
    }

    public RequestBuilder setSubject(String subject) {
        config.getOptions().setSubject(subject);
        return this;
    }

    public RequestBuilder setKeywords(String keywords) {
        config.getOptions().setKeywords(keywords);
        return this;
    }

    public RequestBuilder setAdditionalAttributes(Map<String, String> additionalAttributes) {
        config.getOptions().setAdditionalAttributes(additionalAttributes);
        return this;
    }

    public byte[] toByteArray() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try (ZipOutputStream outputStream = new ZipOutputStream(buffer)) {
            outputStream.putNextEntry(new ZipEntry("tomakeitgo.json"));
            new ConfigIO().write(config, outputStream);
            outputStream.closeEntry();

            for (Map.Entry<String, InputStream> entry : this.inputs.entrySet()) {
                outputStream.putNextEntry(new ZipEntry(entry.getKey()));
                entry.getValue().transferTo(outputStream);
                outputStream.closeEntry();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return buffer.toByteArray();
    }

    public void toPath(Path path) throws IOException{
        Files.write(path, toByteArray());
    }
}
