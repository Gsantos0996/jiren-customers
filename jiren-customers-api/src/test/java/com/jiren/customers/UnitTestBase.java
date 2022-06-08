package com.jiren.customers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public class UnitTestBase {

    private AutoCloseable closeable;

    @BeforeEach
    public void openMocks() {
        this.closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void releaseMocks() throws Exception {
        this.closeable.close();
    }

}
