/**
 * Exception for license plate length violation.
 * 
 * @author Giuseppe Campanelli
 * @author Michael McMahon
 *
 */
public class LicensePlateLengthViolationException extends Exception {

    LicensePlateLengthViolationException(String message)
    {
        super(message);
    }
}