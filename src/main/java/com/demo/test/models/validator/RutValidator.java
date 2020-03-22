package com.demo.test.models.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Felipe González Alfaro on 22-03-20.
 */
public class RutValidator implements ConstraintValidator<RutConstraint, String> {
    @Override
    public void initialize(RutConstraint constraintAnnotation) {

    }

    /**
     * Method to validate chliean DNI (rut).
     *
     * @param rut                        Chilean rut
     * @param constraintValidatorContext
     * @return is valid
     */
    @Override
    public boolean isValid(String rut, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = false;
        try {
            rut = rut.toUpperCase();
            rut = rut.replace(".", "").replace("-", "");

            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                isValid = true;
            }

        } catch (Exception ignored) {
        }
        return isValid;
    }
}
