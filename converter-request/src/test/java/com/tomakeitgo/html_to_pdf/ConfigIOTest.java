package com.tomakeitgo.html_to_pdf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

class ConfigIOTest {

    @Test
    void test() {

        Config config = new Config()
                .setVersion("1")
                .setOptions(new Config.PDFOptions()
                        .setAuthor("author")
                        .setCreator("creator")
                        .setKeywords("Keywords")
                        .setTitle("title")
                        .setSubject("subject")
                        .setAdditionalAttributes(Map.of("one", "one"))
                )
                .setEntries(List.of("one", "two", "three"));

        ConfigIO io = new ConfigIO();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        io.write(config, buffer);


        Config read = io.read(new ByteArrayInputStream(buffer.toByteArray()));

        Assertions.assertEquals(config, read);
    }
}