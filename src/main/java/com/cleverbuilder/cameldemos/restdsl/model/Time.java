package com.cleverbuilder.cameldemos.restdsl.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Time {

    private String message;
    private LocalDateTime time;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Time() {
    }

    public Time(long id) {
        this.message = "Hello Fabric8";
        this.time = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time.format(formatter);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }


}
