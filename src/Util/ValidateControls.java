package Util;

import java.net.PortUnreachableException;

public class ValidateControls {

    public static final String SPECIAL_CHARACTERS = "|!\\\"#$%&/_()=.:,;{}[]+*/¬@¨~?";
    public static final String NUMERICAL_CHARACTERS = "0123456789";

    public static boolean validateName(String name) {
        if (name.isEmpty())
            return false;
        char actualCharacter;
        for (int i = 0; i < name.length(); i++) {
            actualCharacter = name.charAt(i);
            if (name.charAt(i) == '-')
                continue;
            if (isSpecialCharacter(actualCharacter) || isNumericalCharacter(actualCharacter)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSpecialCharacter(char character) {
        for (int k = 0; k < SPECIAL_CHARACTERS.length(); k++) {
            if (SPECIAL_CHARACTERS.charAt(k) == character)
                return true;
        }
        return false;
    }

    public static boolean isNumericalCharacter(char character) {
        for (int k = 0; k < NUMERICAL_CHARACTERS.length(); k++) {
            if (NUMERICAL_CHARACTERS.charAt(k) == character)
                return true;
        }
        return false;
    }

    public static boolean validateDriverLicense(String driverLicense) {
        if (driverLicense.isEmpty())
            return false;
        return true;
    }


    public static boolean validateNameAddress(String name) {
        if (name.isEmpty())
            return false;
        return true;
    }

    public static boolean validateLicensePlate(String plate) {

        if(plate.isEmpty())
            return false;
        return true;
}

}
