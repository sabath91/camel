package pl.czyz.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Time {

    @JsonProperty
    private String message;
    @JsonProperty
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
