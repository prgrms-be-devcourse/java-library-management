package com.programmers.util;

import com.programmers.exception.unchecked.SystemErrorException;

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
        try (InputStream input = MessageProperties.class.getResourceAsStream(
            "/messages.properties")) {
            if (input != null) {
                properties.load(new InputStreamReader(input, StandardCharsets.UTF_8));
            } else {
                throw new SystemErrorException();
            }
        } catch (IOException e) {
            throw new SystemErrorException();
        }

        try (InputStream input = MessageProperties.class.getResourceAsStream(
            "/error_messages.properties")) {
            if (input != null) {
                errorProperties.load(new InputStreamReader(input, StandardCharsets.UTF_8));
            } else {
                throw new SystemErrorException();
            }
        } catch (IOException e) {
            throw new SystemErrorException();
        }
    }

    private MessageProperties() {
    }

    // TODO: 해당 class 로 메세지를 가져오기 때문에 여기서는 상수 사용 불가.
    public static String get(String key) {
        String value = properties.getProperty(key, "메세지 가져오기에 실패 하였습니다.");
        return value.replace("\n", System.lineSeparator());
    }

    public static String getError(String key) {
        String value = errorProperties.getProperty(key, "메세지 가져오기에 실패 하였습니다.");
        return value.replace("\n", System.lineSeparator());
    }
}

