package cscie55.hw3.elevator;

/**
 * A <code>Building</code> object represents Building. A building
 * has an Elevator objects as well as a number of FLOORS as defined
 * in a static int. A building knows the state of each Floor object as
 * well as it's Elevator.
 *
 *
 */

public class Building{

    public static final int FLOORS = 7;
    public final static int GROUND_FLOOR = 1;

    //private int currentFloor;
    private Elevator elevator;
    private Floor[] floors = new Floor[8];


    public Building(){
        this.elevator = new Elevator(this);
        for(int i = 1; i <=7 ; i++){
            floors[i] = new Floor(this,i);
        }
    }

    public Elevator getElevator(){
        return elevator;
    }

    public Floor getFloor(int floorNumber){
        return floors[floorNumber];
    }

    public void enter(Passenger passenger){
        floors[GROUND_FLOOR].enterGroundFloor(passenger);
    }


}