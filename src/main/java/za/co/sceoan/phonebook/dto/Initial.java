package za.co.sceoan.phonebook.dto;

import java.io.Serializable;

public class Initial implements Serializable {
    private String initial;
    private String path;

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
