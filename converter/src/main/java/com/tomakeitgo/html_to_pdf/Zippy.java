package com.tomakeitgo.html_to_pdf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zippy {
    private final byte[] content;

    public Zippy(byte[] content) {
        this.content = content;
    }

    public byte[] getEntry(String name) {
        try {
            ZipInputStream stream = new ZipInputStream(new ByteArrayInputStream(content));
            ZipEntry entry;
            while ((entry = stream.getNextEntry()) != null) {
                if (entry.getName().equals(name))
                    return stream.readAllBytes();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new byte[0];
    }
}
