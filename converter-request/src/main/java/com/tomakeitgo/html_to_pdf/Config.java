package com.tomakeitgo.html_to_pdf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Config {
    private String version = "1";
    private List<String> entries = new ArrayList<>();
    private PDFOptions options = new PDFOptions();

    public String getVersion() {
        return version;
    }

    public Config setVersion(String version) {
        this.version = version;
        return this;
    }

    public void addEntry(String entry) {
        this.entries.add(entry);
    }

    public List<String> getEntries() {
        return entries;
    }

    public Config setEntries(List<String> entries) {
        this.entries = entries;
        return this;
    }

    public PDFOptions getOptions() {
        return options;
    }

    public Config setOptions(PDFOptions options) {
        this.options = options;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Config config = (Config) o;
        return Objects.equals(version, config.version) && Objects.equals(entries, config.entries) && Objects.equals(options, config.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, entries, options);
    }

    public static class PDFOptions {
        private String author;
        private String creator;
        private String title;
        private String subject;
        private String keywords;
        private Map<String, String> additionalAttributes;

        public String getAuthor() {
            return author;
        }

        public PDFOptions setAuthor(String author) {
            this.author = author;
            return this;
        }

        public String getCreator() {
            return creator;
        }

        public PDFOptions setCreator(String creator) {
            this.creator = creator;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public PDFOptions setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getSubject() {
            return subject;
        }

        public PDFOptions setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public String getKeywords() {
            return keywords;
        }

        public PDFOptions setKeywords(String keywords) {
            this.keywords = keywords;
            return this;
        }

        public Map<String, String> getAdditionalAttributes() {
            return additionalAttributes;
        }

        public PDFOptions setAdditionalAttributes(Map<String, String> additionalAttributes) {
            this.additionalAttributes = additionalAttributes;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PDFOptions that = (PDFOptions) o;
            return Objects.equals(author, that.author) && Objects.equals(creator, that.creator) && Objects.equals(title, that.title) && Objects.equals(subject, that.subject) && Objects.equals(keywords, that.keywords) && Objects.equals(additionalAttributes, that.additionalAttributes);
        }

        @Override
        public int hashCode() {
            return Objects.hash(author, creator, title, subject, keywords, additionalAttributes);
        }
    }


}
