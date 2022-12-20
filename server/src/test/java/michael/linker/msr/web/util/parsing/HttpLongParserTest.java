package michael.linker.msr.web.util.parsing;

import michael.linker.msr.ParentTest;
import michael.linker.msr.web.exception.BadRequestResponseStatusException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpLongParserTest extends ParentTest {

    @Test
    void valueOf() {
        assertEquals(1L, HttpLongParser.valueOf("1"));
    }

    @Test
    void valueOfNotLong() {
        assertThrows(BadRequestResponseStatusException.class,
                () -> HttpLongParser.valueOf("L"));
    }

    @Test
    void valueOfNull() {
        assertThrows(BadRequestResponseStatusException.class,
                () -> HttpLongParser.valueOf(null));
    }

    @Test
    void value() {
        assertEquals(1L, HttpLongParser.value(1L));
    }

    @Test
    void valueNull() {
        assertThrows(BadRequestResponseStatusException.class,
                () -> HttpLongParser.value(null));
    }
}