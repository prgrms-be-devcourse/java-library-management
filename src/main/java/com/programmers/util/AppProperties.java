package com.programmers.util;


import com.programmers.exception.unchecked.SystemErrorException;

import java.io.*;
import java.util.Properties;

public class AppProperties {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = AppProperties.class.getClassLoader()
            .getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new SystemErrorException();
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new SystemErrorException();
        }
    }

    public AppProperties() {
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key, "메세지 가져오기에 실패 하였습니다.");
        return value.replace("/", File.separator);
    }

    public static void setFileLastNumber(Long lastNumber) throws IOException {
        properties.setProperty("file.lastRowNumber", String.valueOf(lastNumber));
        properties.store(new FileOutputStream("application.properties"), null);
    }

}

