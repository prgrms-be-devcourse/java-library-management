package com.programmers.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class MessageProperties {
    private static final Properties errorProperties = new Properties();
    private static final Properties properties = new Properties();

    //TODO: 파일이름 환경변수 설정
    static {
        try (InputStream input = MessageProperties.class.getResourceAsStream("/messages.properties")) {
            if (input != null) {
                properties.load(new InputStreamReader(input, StandardCharsets.UTF_8));
            } else {
                System.err.println("messages.properties 파일이 존재하지 않습니다.");
                System.exit(-1);
            }
        } catch (IOException e) {
            //TODO: 예외처리
        }

        try (InputStream input = MessageProperties.class.getResourceAsStream("/error_messages.properties")) {
            if (input != null) {
                errorProperties.load(new InputStreamReader(input, StandardCharsets.UTF_8));
            } else {
                System.err.println("error_messages.properties 파일이 존재하지 않습니다.");
                System.exit(-1);
            }
        } catch (IOException e) {
            //TODO: 예외처리
        }
    }

    public static String get(String key) {
        return properties.getProperty(key, "해당 키가 존재하지 않습니다: " + key);
    }

    public static String getError(String key) {
        return errorProperties.getProperty(key, "해당 키가 존재하지 않습니다: " + key);
    }
}

