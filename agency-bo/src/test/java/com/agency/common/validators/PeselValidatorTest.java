package com.agency.common.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PeselValidatorTest {

    private PeselValidator peselValidator;

    @BeforeEach
    void setup(){
        peselValidator = new PeselValidator();
    }

    @ParameterizedTest
    @MethodSource("checkPeselValidator")
    void should_valid_correct_pesel(String pesel, boolean isValid){
        assertEquals(isValid, peselValidator.validate(pesel));
    }

    @Test
    public void should_throw_an_exception_when_theres_a_letter_in_pesel() {
        // given
        String invalidPesel = "1907017079M";

        // then
        assertThrows(NumberFormatException.class, () -> peselValidator.validate(invalidPesel));

    }

    private static Stream<Arguments> checkPeselValidator(){
        return Stream.of(
                Arguments.of("98022805848", true),
                Arguments.of("47121997280", true),
                Arguments.of("04220318015", true),
                Arguments.of("19070170793", true),
                Arguments.of("75032958134", false),
                Arguments.of("00000000000", false),
                Arguments.of("1907017079", false),
                Arguments.of("", false)
        );
    }

}
