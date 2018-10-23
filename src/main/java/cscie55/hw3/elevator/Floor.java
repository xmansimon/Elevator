package cscie55.hw3.elevator;

import java.util.*;

/**
 * A <code>Floor</code> object represents a getFloor in a building.
 * Each Floor object has a Floor number and tracks how many people
 * are waiting for the Elevator.
 *
 */

public class Floor{
    public int floorNumber;
    public int passengersWaiting;
    private Building building;
    Collection<Passenger> residents = new ArrayList<Passenger>();
    Collection<Passenger> upwardBound = new ArrayList<Passenger>();
    Collection<Passenger> downwardBound = new ArrayList<Passenger>();

    public Floor(Building building, int floorNumber){
        this.floorNumber = floorNumber;
        this.building = building;
    };



    public void waitForElevator(Passenger passenger, int destinationFloor){
        passengersWaiting++;
        passenger.waitForElevator(destinationFloor);

        if(destinationFloor < floorNumber){
            downwardBound.add(passenger);
        } else if(destinationFloor > floorNumber) {
            upwardBound.add(passenger);
        }
        residents.remove(passenger);

    }


    public  boolean isResident(Passenger passenger){
        return residents.contains(passenger);
    }
    public void enterGroundFloor(Passenger passenger){
        if(floorNumber == Building.GROUND_FLOOR) {
            residents.add(passenger);
        }
    }


}
