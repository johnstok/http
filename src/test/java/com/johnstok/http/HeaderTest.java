package com.johnstok.http;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HeaderTest {

    @Test
    public void retryAfterIsAResponseHeader() {

        // ARRANGE

        // ACT

        // ASSERT
        assertTrue(Header.isResponseHeader(Header.RETRY_AFTER));
    }

    @Test
    public void hostIsNotAResponseHeader() {

        // ARRANGE

        // ACT

        // ASSERT
        assertFalse(Header.isResponseHeader(Header.HOST));
    }

    @Test
    public void retryAfterIsNotARequestHeader() {

        // ARRANGE

        // ACT

        // ASSERT
        assertFalse(Header.isRequestHeader(Header.RETRY_AFTER));
    }

    @Test
    public void hostIsARequestHeader() {

        // ARRANGE

        // ACT

        // ASSERT
        assertTrue(Header.isRequestHeader(Header.HOST));
    }
}
