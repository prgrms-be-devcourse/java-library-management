package com.programmers.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.programmers.infrastructure.IO.ConsoleInteractionAggregator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class DependencyInjectorTest {
    private AutoCloseable closeable;

    @Mock
    private ConsoleInteractionAggregator mockConsoleInteractionAggregator;

    @InjectMocks
    private DependencyInjector dependencyInjector = DependencyInjector.getInstance();

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        when(mockConsoleInteractionAggregator.collectModeInput()).thenReturn("1");
    }

    @AfterEach
    void tearDown() throws Exception {
        if (closeable != null) {
            closeable.close();
        }
    }

    @Test
    @DisplayName("객체 초기화 테스트")
    void testObjectInitialization() {
        dependencyInjector.init();

        assertNotNull(dependencyInjector.getRequestProcessor());
        assertNotNull(dependencyInjector.getGlobalExceptionHandler());
        assertNotNull(dependencyInjector.getAppExceptionHandler());
        assertNotNull(dependencyInjector.getIdGenerator());
    }

//    @Test
//    @DisplayName("ModeFactory 테스트")
//    void testGetModeFactory() {
//        when(mockConsoleInteractionAggregator.collectModeInput()).thenReturn("1");
//        assertNotNull(dependencyInjector.getModeFactory(mockConsoleInteractionAggregator));
//    }

}



