package com.johnstok.http;

import static org.junit.Assert.*;
import org.junit.Test;

public class HeaderTest {
    
    @Test
    public void headerNamesAreCaseInsensitive() {
        
        // ACT
        Header h1 = Header.parse("Foo:bar");
        Header h2 = Header.parse("foO:bar");
        
        // ASSERT
        assertEquals(h1,h2);
    }

    @Test
    public void parseSimpleHeader() {

        // ACT
        Header h = Header.parse("Foo:bar");

        // ASSERT
        assertEquals("Foo", h.getName());
        assertEquals("bar", h.getValue());
    }


    @Test
    public void parseHeaderValueHasSingleLws() {

        // ACT
        Header h = Header.parse("Foo:bar\r\n baz");

        // ASSERT
        assertEquals("Foo", h.getName());
        assertEquals("bar\r\n baz", h.getValue());
    }


    @Test
    public void parseHeaderValueHasMultipleLws() {
        
        // ACT
        Header h = Header.parse("Foo:a\r\n b\r\n\tc");
        
        // ASSERT
        assertEquals("Foo", h.getName());
        assertEquals("a\r\n b\r\n\tc", h.getValue());
    }


    @Test
    public void parseHeaderValueHasLeadingWhitespace() {

        // ACT
        Header h = Header.parse("Foo: \tbar");

        // ASSERT
        assertEquals("Foo", h.getName());
        assertEquals("bar", h.getValue());
    }


    @Test
    public void parseHeaderValueHasTrailingWhitespace() {

        // ACT
        Header h = Header.parse("Foo:bar\t ");

        // ASSERT
        assertEquals("Foo", h.getName());
        assertEquals("bar", h.getValue());
    }


    @Test
    public void parseHeaderValueHasLeadingLws() {
        
        // ACT
        Header h = Header.parse("Foo:\r\n bar");
        
        // ASSERT
        assertEquals("Foo", h.getName());
        assertEquals("\r\n bar", h.getValue());
    }
    
    
    @Test
    public void parseHeaderValueHasTrailingLws() {
        
        // ACT
        Header h = Header.parse("Foo:bar\r\n\t");
        
        // ASSERT
        assertEquals("Foo", h.getName());
        assertEquals("bar\r\n\t", h.getValue());
    }


    @Test
    public void parseHeaderValueHasTrailingAndLeadingWhitespace() {

        // ACT
        Header h = Header.parse("Foo: bar\t");

        // ASSERT
        assertEquals("Foo", h.getName());
        assertEquals("bar", h.getValue());
    }


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
