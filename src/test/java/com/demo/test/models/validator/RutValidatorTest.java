package com.demo.test.models.validator;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Felipe González Alfaro on 22-03-20.
 */
class RutValidatorTest {

    @Test
    void isValid() {
        RutValidator rutValidator = new RutValidator();
        String rut = "16660706-k";

        assertTrue(rutValidator.isValid(rut, null));
    }

    @Test
    void isValid2() {
        RutValidator rutValidator = new RutValidator();
        String rut = "16660706-K";

        assertTrue(rutValidator.isValid(rut, null));
    }

    @Test
    void isError() {
        RutValidator rutValidator = new RutValidator();
        String rut = "1asd0706-k";

        assertFalse(rutValidator.isValid(rut, null));
    }

    @Test
    void isError2() {
        RutValidator rutValidator = new RutValidator();
        String rut = "16660706-1";

        assertFalse(rutValidator.isValid(rut, null));
    }
}