package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class AppTest {

	@Test
    void testLogic() {
        String greeting = "Hello World";
        assertEquals("Hello World", greeting);
    }
}