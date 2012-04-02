package com.johnstok.http;

import static org.junit.Assert.*;
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

    @Test
    public void retryAfterIsNotAGeneralHeader() {

        // ARRANGE

        // ACT

        // ASSERT
        assertFalse(Header.isGeneralHeader(Header.RETRY_AFTER));
    }

    @Test
    public void dateIsAGeneralHeader() {

        // ARRANGE

        // ACT

        // ASSERT
        assertTrue(Header.isGeneralHeader(Header.DATE));
    }
}
