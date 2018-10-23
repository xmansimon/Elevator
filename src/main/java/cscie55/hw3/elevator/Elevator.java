package cscie55.hw3.elevator;

import java.util.*;


/**
 * A <code>Elevator</code> object represents an Elevator in
 * a building. 
 *
 * @author
 */

public class Elevator{
    //private static final int floorNumber = 7;
    public static final int CAPACITY = 10;
    private int currentFloor = 1; // elevator starts from 1st floor
    private int direction = 1; //going up
    private int[] numberDestined = new int[8]; // number of people destined to certain floor. numberDestined[1] -> first floor.
    private int passengerInElevator; //total number of passenger in the elevator
    private Building building;

    private Map<Integer, ArrayList<Passenger>> occupants = new HashMap<Integer,ArrayList<Passenger>>();

    /**
     * Constructor
     */
    public Elevator(Building building){
        this.building = building;

        //add passenger to each floor list
        for(int i = Building.GROUND_FLOOR; i <= Building.FLOORS; i++){
            occupants.put(i, new ArrayList<Passenger>());
        }

    }
    public Elevator(){
    }
    /**
     * move method
     */
    public void move(){
        currentFloor = currentFloor + this.direction;


        //change direction when reaches top or bottom.
        if(Building.GROUND_FLOOR == this.currentFloor
                || this.currentFloor == Building.FLOORS )
            this.direction *= -1;


        Floor floorObj = this.building.getFloor(currentFloor);

        if(this.occupants.get(currentFloor).size() > 0){
            this.elevatorStop( currentFloor, true, this.occupants.get(currentFloor) );
        }

        //board Passengers going up
        if( this.goingUp() && floorObj.upwardBound.size() > 0){
            this.elevatorStop(currentFloor, false, floorObj.upwardBound );
        }


            //board Passengers going down
        else if( this.goingDown()&& floorObj.downwardBound.size() > 0){
            this.elevatorStop(currentFloor, false, floorObj.downwardBound);
        }



//        if(direction == 1){ // go up
//            currentFloor++;
//        }else{
//            currentFloor--;
//        }
//        if(currentFloor == floorNumber){ // change direction if elevator reaches top
//            direction = true;
//        }else if(currentFloor == 1){ // change direction if elevator reaches 1st floor
//            direction = false;
//        }
//        try{
//            while(building.getFloor(currentFloor).passengersWaiting > 0 && passengerInElevator!=CAPACITY){
//                building.getFloor(currentFloor).passengersWaiting--;
//                boardPassenger(1);
//            }
//        }catch(ElevatorFullException e){
//            System.out.println("Capacity reached: " + e);
        //}
        //}
//        passengerInElevator = passengerInElevator - numberDestined[currentFloor];
//        numberDestined[currentFloor] = 0; // clears number of people destined for the current floor.
//
//        System.out.println(toString());
    }
    /**
     * @param destinationFloor Passenger destination floor
     */
    public void boardPassenger(int destinationFloor) throws ElevatorFullException{
        passengerInElevator++;
        numberDestined[destinationFloor] ++;

        if(passengerInElevator >= CAPACITY){
            throw new ElevatorFullException("!!");
        }
    }

    public String toString(){
        return "Floor " +currentFloor+ ": " + passengerInElevator + " Passengers";
    }


    public int getCurrentFloor(){
        return currentFloor;
    }

    public Set<Passenger> getPassengers(){
        Set<Passenger> returnSet = new HashSet<Passenger>();

        for(int floor : occupants.keySet() ){
            for(Passenger passenger : this.occupants.get(floor)){
                returnSet.add(passenger);
            }
        }

        return returnSet;

       // return passengerInElevator;
    }

    public void setDestination(int floor){
        numberDestined[floor] ++;
    }

    // public void stopElevator()throws ElevatorFullException{
    //     while(building.getFloor(currentFloor).getPassengersWaiting() > 0){
    //         try{
    //             boardPassenger(1);
    //         }catch (ElevatorFullException e) {
    //             throw new ElevatorFullException("!!");
    //         }
    //         building.getFloor(currentFloor).passengersWaiting--;
    //     }
    // }

    public boolean goingUp(){
        return (this.direction == 1);
    }

    public boolean goingDown(){
        return (this.direction == -1);
    }

    private void elevatorStop(int floor, boolean isUnloading, Collection<Passenger> passengers){

        if(isUnloading){

            //unload passengers onto floor
            for( Passenger passenger : this.occupants.get(floor)){
                passenger.arrive();
                this.building.getFloor(floor).residents.add(passenger);
            }

            //remove passengers from elevator collection by simple erasing previous list
            this.occupants.put(floor, new ArrayList<Passenger>() );


        } else {

            Collection<Passenger> justBoarded = new ArrayList<Passenger>();


            try{
                for( Passenger passenger :  passengers){

                    if(this.getPassengers().size() == Elevator.CAPACITY){
                        throw new ElevatorFullException("Reached Fulll Capacity");
                    }

                    passenger.boardElevator();
                    this.occupants.get( passenger.destinationFloor() ).add(passenger);
                    justBoarded.add(passenger);

                }
            } catch (ElevatorFullException e){ }


            //need to remove the passengers from the passed collection as, they were just added to
            // elevators collection, can't remove items as I iterate.. shame
            for(Passenger passenger : justBoarded){
                passengers.remove(passenger);
            }
            justBoarded = null;

        }
    }

}
