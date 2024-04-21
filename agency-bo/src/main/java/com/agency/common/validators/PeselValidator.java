package com.agency.common.validators;

import java.time.DateTimeException;
import java.time.LocalDate;

public class PeselValidator {

    private static final int[] WEIGHTS = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};

    public boolean validate(String pesel) {
        if (pesel == null || pesel.length() != 11) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 10; i++) {
            int result = Integer.parseInt(pesel.substring(i, i + 1)) * WEIGHTS[i];
            sum += getOnlyLastDigitIfNumberIsHigherThanTen(result);
        }

        int checkDigit = 10 - (sum % 10);
        if (checkDigit == 10) {
            checkDigit = 0;
        }

        return checkDigit == Integer.parseInt(pesel.substring(10, 11)) && validateBirthDate(pesel);
    }

    private static int getOnlyLastDigitIfNumberIsHigherThanTen(int result) {
        if (result >= 10) {
            return result % 10;
        } else {
            return result;
        }
    }

    private boolean validateBirthDate(String pesel) {
        int year = Integer.parseInt(pesel.substring(0, 2), 10);
        int month = Integer.parseInt(pesel.substring(2, 4), 10);
        int day = Integer.parseInt(pesel.substring(4, 6), 10);

        if (month > 20) {
            year += 2000;
            month -= 20;
        } else if (month > 0) {
            year += 1900;
        } else {
            return false;
        }

        try {
            LocalDate birthDate = LocalDate.of(year, month, day);
            return birthDate.isBefore(LocalDate.now()) || birthDate.isEqual(LocalDate.now());
        } catch (DateTimeException e) {
            return false;
        }
    }
}
