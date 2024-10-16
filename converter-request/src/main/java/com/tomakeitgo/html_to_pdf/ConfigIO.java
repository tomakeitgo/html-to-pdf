package com.tomakeitgo.html_to_pdf;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ConfigIO {

    public Config read(InputStream inputStream){
        return new Gson().fromJson(new InputStreamReader(inputStream), Config.class);
    }

    public void write(Config config, OutputStream outputStream){
        PrintStream writer = new PrintStream(outputStream);
        new Gson().toJson(config, writer);
        writer.flush();
    }

    public static byte[] zipIt(Path directory) throws IOException{
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        zipIt(directory, buffer);
        return buffer.toByteArray();
    }

    public static void zipIt(Path directory, Path out) throws IOException {
        zipIt(directory, new FileOutputStream(out.toFile()));
    }

    public static void zipIt(Path directory, OutputStream outputStream) throws IOException {
        try (
                Stream<Path> walk = Files.walk(directory);
                ZipOutputStream zip = new ZipOutputStream(outputStream)
        ) {
            walk.forEach(i -> {
                if (i.toFile().isDirectory()) return;
                try {
                    zip.putNextEntry(new ZipEntry(directory.relativize(i).toString()));
                    zip.write(Files.readAllBytes(i));
                    zip.closeEntry();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    public static void unzipIt(byte[] data, Path out) throws IOException {
        try (ZipInputStream stream = new ZipInputStream(new ByteArrayInputStream(data))) {
            ZipEntry entry;
            while ((entry = stream.getNextEntry()) != null) {
                if (entry.isDirectory()) continue;
                Path toWriteTo = out.resolve(entry.getName());
                Files.createDirectories(toWriteTo.getParent());
                Files.write(toWriteTo, stream.readAllBytes());
            }
        }
    }
}
