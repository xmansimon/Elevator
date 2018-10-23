package cscie55.hw3.elevator;

/**
 * A <code>ElevatorFullException</code> a custom Exception class that
 * extends Exception. When an Elevator object is already at Capacity and
 * another passenger is attempting to board this exception is called and
 * prints an error message. In the code that calls this exception aside from
 * the print message the code continues on it's way and does not board the
 * passenger.
 *
 * 
 */


public class ElevatorFullException extends java.lang.Exception {
    public ElevatorFullException(String message) {
        super(message);
    }
}
