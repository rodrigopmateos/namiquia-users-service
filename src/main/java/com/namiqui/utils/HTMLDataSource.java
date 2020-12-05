package com.namiqui.utils;

import javax.activation.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HTMLDataSource implements DataSource {
    private String html;

    public HTMLDataSource(String htmlString){
        this.html = htmlString;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
