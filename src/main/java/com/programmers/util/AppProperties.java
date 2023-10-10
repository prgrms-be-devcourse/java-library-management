package com.programmers.util;


import com.programmers.exception.unchecked.SystemErrorException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    private AppProperties() {}

    public static String getProperty(String key) {
        String value = properties.getProperty(key, "메세지 가져오기에 실패 하였습니다.");
        return value.replace("/", File.separator);
    }

    public static void setFileLastNumber(Long lastNumber) throws IOException {
        properties.setProperty("file.lastRowNumber", String.valueOf(lastNumber));
        try (OutputStream output = new FileOutputStream("src/main/resources/application.properties")) {
            properties.store(output, null);
        }
    }

}

