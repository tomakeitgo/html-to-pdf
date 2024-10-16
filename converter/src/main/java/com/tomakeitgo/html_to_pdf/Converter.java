package com.tomakeitgo.html_to_pdf;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.styledxmlparser.resolver.resource.IResourceRetriever;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Converter {

    private final ProcessLogger logger = new ProcessLogger();

    public void convert(Zippy input, OutputStream out) {
        logger.info("Starting Convert.");
        ConfigIO io = new ConfigIO();
        Config config = io.read(new ByteArrayInputStream(input.getEntry("tomakeitgo.json")));

        ZipResolver resolver = new ZipResolver(input, logger);
        ZipOutputStream stream = new ZipOutputStream(out);

        for (String path : config.getEntries()) {
            try {
                logger.info("Starting conversion of: " + path);
                stream.putNextEntry(new ZipEntry(path + ".pdf"));
                stream.write(convert(
                        config,
                        resolver,
                        input.getEntry(path)
                ));
                stream.closeEntry();
                logger.info("finished conversion of: " + path);
            } catch (IOException e) {
                logger.error("Error in " + path + " " + e.getMessage());
            }
        }

        try {
            stream.finish();
        } catch (IOException e) {
            logger.error("Unable to finish zip file: " + e.getMessage());
        }

        logger.info("Finished Convert.");
        logger.print();
    }


    public byte[] convert(
            Config config,
            IResourceRetriever resourceRetriever,
            byte[] toConvert
    ) throws IOException{
        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();

        ConverterProperties properties = new ConverterProperties();
        properties.setResourceRetriever(resourceRetriever);

        HtmlConverter.convertToPdf(
                new ByteArrayInputStream(toConvert),
                pdfStream,
                properties
        );

        return setPdfProperties(config, pdfStream).toByteArray();
    }

    private static ByteArrayOutputStream setPdfProperties(Config config, ByteArrayOutputStream pdfStream) throws IOException {
        ByteArrayOutputStream pdfOut = new ByteArrayOutputStream();
        PdfDocument document = new PdfDocument(
                new PdfReader(new ByteArrayInputStream(pdfStream.toByteArray())),
                new PdfWriter(pdfOut)

        );

        Config.PDFOptions options = config.getOptions();
        document.getDocumentInfo().setTitle(options.getTitle());
        document.getDocumentInfo().setSubject(options.getSubject());
        document.getDocumentInfo().setAuthor(options.getAuthor());
        document.getDocumentInfo().setCreator(options.getCreator());
        document.getDocumentInfo().setKeywords(options.getKeywords());
        document.getDocumentInfo().setMoreInfo(options.getAdditionalAttributes());

        document.close();
        return pdfOut;
    }

    private static class ZipResolver implements IResourceRetriever {
        private final Zippy input;
        private final ProcessLogger logger;

        private ZipResolver(Zippy input, ProcessLogger logger) {
            this.input = input;
            this.logger = logger;
        }

        @Override
        public InputStream getInputStreamByUrl(URL url) throws IOException {
            return new ByteArrayInputStream(this.getByteArrayByUrl(url));
        }

        @Override
        public byte[] getByteArrayByUrl(URL url) throws IOException {
            if (!"file".equals(url.getProtocol())) {
                logger.error("Unable to resolve resource: " + url);
                return new byte[0];
            }

            Path fullPath = Path.of(url.getPath());
            Path normalize = Path.of("").toAbsolutePath();
            Path relativize = normalize.relativize(fullPath);

            return input.getEntry(relativize.toString());
        }
    }
}
