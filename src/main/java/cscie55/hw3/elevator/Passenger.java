package cscie55.hw3.elevator;

/**
 * A <code>Passenger</code> object represents a person to ride the Elevator.
 * a passengers has a ID number, knows its current Floor and its destination Floor.
 *

 */

public class Passenger {

    private static final int undefinedFloor = -1;
    private int destinationFloor;
    private int currentFloor;
    private int ID;

    public Passenger(int id){
        this.ID = id;
        currentFloor = 1;
        destinationFloor = undefinedFloor;
    }

    public int currentFloor(){
	    return currentFloor;
    }

    public int destinationFloor(){
	    return destinationFloor;
    }

    public void waitForElevator(int newDestinationFloor){
        this.destinationFloor = newDestinationFloor;
    }

    public void boardElevator(){
        this.currentFloor = undefinedFloor;
    }


    public void arrive(){
        this.currentFloor = destinationFloor;
        this.destinationFloor = undefinedFloor;
    }

    public String toString(){
	    return "PASSENGER: current: " + currentFloor + "  destined: " + destinationFloor;

    }
}