package com.tomakeitgo.html_to_pdf;

import java.util.ArrayList;
import java.util.List;

public class ProcessLogger {
    List<String> entries = new ArrayList<>();

    public void info(String message){
        entries.add("INFO: " + message);
    }

    public void error(String message){
        entries.add("ERROR: " + message);
    }

    public void print(){
        entries.forEach(System.out::println);
    }
}
