package michael.linker.msr.web.util.parsing;

import michael.linker.msr.web.exception.BadRequestResponseStatusException;

/**
 * Custom wrapper for the String to Long values parsing.
 */
public class LongParser {

    /**
     * Parse String to Long.
     *
     * @param longAsString String containing Long value.
     * @return parsed Long value.
     * @throws BadRequestResponseStatusException if String cannot be parsed or null.
     */
    public static Long valueOf(String longAsString) throws BadRequestResponseStatusException {
        try {
            return Long.valueOf(longAsString);
        } catch (NumberFormatException e) {
            throw new BadRequestResponseStatusException(e.getMessage());
        }
    }

    /**
     * Parse Long to Long.
     *
     * @param longValue Long value.
     * @return Long value.
     * @throws BadRequestResponseStatusException if input Long is null.
     */
    public static Long value(Long longValue) throws BadRequestResponseStatusException {
        if (longValue == null) {
            throw new BadRequestResponseStatusException();
        }
        return longValue;
    }
}
