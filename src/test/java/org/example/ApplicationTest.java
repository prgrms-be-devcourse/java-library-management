package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ApplicationTest {

    @Test
    @DisplayName("애플리케이션 생성 테스트")
    void create() {
        Application application = new Application();
        assertNotNull(application);
    }
}