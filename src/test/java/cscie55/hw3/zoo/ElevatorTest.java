package cscie55.hw3.zoo;

import cscie55.hw3.elevator.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ElevatorTest
{
    // Don't board any getPassengers. Just check that the getElevator moves up and down correctly.
    @Test
    public void elevatorMotion()
    {
        Building building = new Building();
        Elevator elevator = building.getElevator();
        int expectedFloorNumber = 1;
        // Go to the top
        while (expectedFloorNumber < Building.FLOORS) {
            checkElevator(elevator, expectedFloorNumber);
            elevator.move();
            expectedFloorNumber++;
            checkElevator(elevator, expectedFloorNumber);
        }
        assertEquals(Building.FLOORS, expectedFloorNumber);
        // Go back to the bottom
        while (expectedFloorNumber > 1) {
            checkElevator(elevator, expectedFloorNumber);
            elevator.move();
            expectedFloorNumber--;
            checkElevator(elevator, expectedFloorNumber);
        }
        assertEquals(1, expectedFloorNumber);
    }


    // Check that getPassengers get on and off correctly.
    @Test
    public void disembark() throws ElevatorFullException
    {
        Building building = new Building();
        // There are six getPassengers.
        Passenger p1 = new Passenger(1);
        Passenger p2 = new Passenger(2);
        Passenger p3 = new Passenger(3);
        Passenger p4 = new Passenger(4);
        Passenger p5 = new Passenger(5);
        Passenger p6 = new Passenger(6);
        // They enter the building (and become resident on the ground getFloor)
        building.enter(p1);
        building.enter(p2);
        building.enter(p3);
        building.enter(p4);
        building.enter(p5);
        building.enter(p6);
        Floor groundFloor = building.getFloor(1);
        assertTrue(groundFloor.isResident(p1));
        assertTrue(groundFloor.isResident(p2));
        assertTrue(groundFloor.isResident(p3));
        assertTrue(groundFloor.isResident(p4));
        assertTrue(groundFloor.isResident(p5));
        assertTrue(groundFloor.isResident(p6));
        Elevator elevator = building.getElevator();
        // Everyone wants to go up, to various floors.
        groundFloor.waitForElevator(p1, 3);
        groundFloor.waitForElevator(p2, 4);
        groundFloor.waitForElevator(p3, 4);
        groundFloor.waitForElevator(p4, 6);
        groundFloor.waitForElevator(p5, 6);
        groundFloor.waitForElevator(p6, 6);
        // No one is on the getElevator yet
        checkElevator(elevator, 1);
        // The getElevator goes up to the top and then down. It should stop on the ground getFloor and
        // board everyone.
        roundTrip(elevator);
        checkElevator(elevator, 1, p1, p2, p3, p4, p5, p6);
        elevator.move();
        checkElevator(elevator, 2, p1, p2, p3, p4, p5, p6);
        // p1 wanted to get off on 3
        elevator.move();
        checkElevator(elevator, 3, p2, p3, p4, p5, p6);
        // p2 and p3 wanted to get off on 4
        elevator.move();
        checkElevator(elevator, 4, p4, p5, p6);
        elevator.move();
        checkElevator(elevator, 5, p4, p5, p6);
        // Remaining getPassengers get off on 6
        elevator.move();
        checkElevator(elevator, 6);
        elevator.move();
        checkElevator(elevator, 7);
        // Check that everyone is where they should be
        assertTrue(building.getFloor(3).isResident(p1));
        assertTrue(building.getFloor(4).isResident(p2));
        assertTrue(building.getFloor(4).isResident(p3));
        assertTrue(building.getFloor(6).isResident(p4));
        assertTrue(building.getFloor(6).isResident(p5));
        assertTrue(building.getFloor(6).isResident(p6));
    }

    // Check that getPassengers on higher floors can call and board the getElevator, and then
    // disembark on the ground getFloor.
    @Test
    public void call() throws ElevatorFullException
    {
        Building building = new Building();
        // There are five getPassengers
        Passenger p1 = new Passenger(1);
        Passenger p2 = new Passenger(2);
        Passenger p3 = new Passenger(3);
        Passenger p4 = new Passenger(4);
        Passenger p5 = new Passenger(5);
        // They enter the building (and become resident on the ground getFloor)
        building.enter(p1);
        building.enter(p2);
        building.enter(p3);
        building.enter(p4);
        building.enter(p5);
        Floor groundFloor = building.getFloor(1);
        assertTrue(groundFloor.isResident(p1));
        assertTrue(groundFloor.isResident(p2));
        assertTrue(groundFloor.isResident(p3));
        assertTrue(groundFloor.isResident(p4));
        assertTrue(groundFloor.isResident(p5));
        Elevator elevator = building.getElevator();
        // p1, p2 go to getFloor 3; p3, p4, p5 go to 6.
        groundFloor.waitForElevator(p1, 3);
        groundFloor.waitForElevator(p2, 3);
        groundFloor.waitForElevator(p3, 6);
        groundFloor.waitForElevator(p4, 6);
        groundFloor.waitForElevator(p5, 6);
        roundTrip(elevator); // Now they enter the getElevator
        roundTrip(elevator); // Get them to their floors
        assertTrue(building.getFloor(3).isResident(p1));
        assertTrue(building.getFloor(3).isResident(p2));
        assertTrue(building.getFloor(6).isResident(p3));
        assertTrue(building.getFloor(6).isResident(p4));
        assertTrue(building.getFloor(6).isResident(p5));
        // p1 wants to go up (3 -> 5)
        // p2 wants to go down (3 -> 1)
        // p3 wants to go down (6 -> 1)
        building.getFloor(3).waitForElevator(p1, 5);
        building.getFloor(3).waitForElevator(p2, 1);
        building.getFloor(6).waitForElevator(p3, 1);
        System.out.println(building.getFloor(3).isResident(p1));
        System.out.println(building.getFloor(3).isResident(p3));

        //The getPassengers moving are no longer resident (they are waiting for the getElevator)
        assertTrue(!building.getFloor(3).isResident(p1));
        assertTrue(!building.getFloor(3).isResident(p2));
        assertTrue(!building.getFloor(6).isResident(p3));
        assertTrue(building.getFloor(6).isResident(p4));
        assertTrue(building.getFloor(6).isResident(p5));
        // Start moving up
        elevator.move();
        checkElevator(elevator, 2);
        elevator.move();
        checkElevator(elevator, 3, p1); // p2 wants to go down but getElevator is going up
        elevator.move();
        checkElevator(elevator, 4, p1);
        elevator.move();
        checkElevator(elevator, 5);
        elevator.move();
        checkElevator(elevator, 6); // p3 doesn't board, wants to go down
        elevator.move();
        checkElevator(elevator, 7);
        elevator.move();
        checkElevator(elevator, 6, p3);
        elevator.move();
        checkElevator(elevator, 5, p3);
        elevator.move();
        checkElevator(elevator, 4, p3);
        elevator.move();
        checkElevator(elevator, 3, p2, p3);
        elevator.move();
        checkElevator(elevator, 2, p2, p3);
        elevator.move();
        checkElevator(elevator, 1);
        assertTrue(building.getFloor(5).isResident(p1));
        assertTrue(building.getFloor(1).isResident(p2));
        assertTrue(building.getFloor(1).isResident(p3));
        assertTrue(building.getFloor(6).isResident(p4));
        assertTrue(building.getFloor(6).isResident(p5));
    }

    // Check handling of a full getElevator.
    @Test
    public void elevatorFull() throws ElevatorFullException
    {
        Building building = new Building();
        // Create 15 getPassengers. (Capacity is 10, so not everyone will fit).
        // They all want to go to 4.
        Floor groundFloor = building.getFloor(1);
        assertEquals(10, Elevator.CAPACITY);
        assertEquals(10, Elevator.CAPACITY);
        final int PASSENGERS = 15;
        Passenger[] p = new Passenger[PASSENGERS];
        for (int id = 0; id < PASSENGERS; id++) {
            p[id] = new Passenger(id);
            building.enter(p[id]);
            groundFloor.waitForElevator(p[id], 4);
        }
        // Load to getElevator capacity
        Elevator elevator = building.getElevator();
        roundTrip(elevator); // Passengers board after getElevator GOES to first getFloor.
        // Starting on the ground getFloor won't do it.
        checkElevator(elevator, 1, p[0], p[1], p[2], p[3], p[4], p[5], p[6], p[7], p[8], p[9]);
        // After a round trip (1 -> 7 -> 1), the first getPassengers who boarded should be on 4,
        // and the getElevator should have the remaining getPassengers.
        roundTrip(elevator);
        Floor floor4 = building.getFloor(4);
        checkElevator(elevator, 1, p[10], p[11], p[12], p[13], p[14]);
        assertTrue(floor4.isResident(p[0]));
        assertTrue(floor4.isResident(p[1]));
        assertTrue(floor4.isResident(p[2]));
        assertTrue(floor4.isResident(p[3]));
        assertTrue(floor4.isResident(p[4]));
        assertTrue(floor4.isResident(p[5]));
        assertTrue(floor4.isResident(p[6]));
        assertTrue(floor4.isResident(p[7]));
        assertTrue(floor4.isResident(p[8]));
        assertTrue(floor4.isResident(p[9]));
        // After one more round trip, everyone should be on 4.
        roundTrip(elevator);
        checkElevator(elevator, 1);
        assertTrue(floor4.isResident(p[0]));
        assertTrue(floor4.isResident(p[1]));
        assertTrue(floor4.isResident(p[2]));
        assertTrue(floor4.isResident(p[3]));
        assertTrue(floor4.isResident(p[4]));
        assertTrue(floor4.isResident(p[5]));
        assertTrue(floor4.isResident(p[6]));
        assertTrue(floor4.isResident(p[7]));
        assertTrue(floor4.isResident(p[8]));
        assertTrue(floor4.isResident(p[9]));
        assertTrue(floor4.isResident(p[10]));
        assertTrue(floor4.isResident(p[11]));
        assertTrue(floor4.isResident(p[12]));
        assertTrue(floor4.isResident(p[13]));
        assertTrue(floor4.isResident(p[14]));
    }

    private void roundTrip(Elevator elevator)
    {
        assert elevator.getCurrentFloor() == 1;
        while (elevator.getCurrentFloor() < Building.FLOORS) {
            elevator.move();
        }
        while (elevator.getCurrentFloor() > 1) {
            elevator.move();
        }
    }

    private void checkElevator(Elevator elevator, int floorNumber, Passenger ... expectedPassengers)
    {
        assertEquals(floorNumber, elevator.getCurrentFloor());
        assertEquals(new HashSet<Passenger>(Arrays.asList(expectedPassengers)),
                elevator.getPassengers());
    }
}