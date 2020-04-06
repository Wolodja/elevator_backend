package com.fortum.codechallenge.elevators.backend.service;

/**
 * Interface for an elevator object.
 */
public interface Elevator extends Runnable{

    /**
     * Tells which direction is the elevator going in.
     *
     * @return Direction Enumeration value describing the direction.
     */
    DirectionEnum getDirection();

    /**
     * If the elevator is moving. This is the target floor.
     *
     * @return primitive integer number of floor
     */
    int getAddressedFloor();

    /**
     * Get the Id of this elevator.
     *
     * @return primitive integer representing the elevator.
     */
    int getId();

    /**
     * Command to move the elevator to the given floor.
     */
    void moveElevator();

    /**
     * Check if the elevator is occupied at the moment.
     *
     * @return true if busy.
     */
    boolean isBusy();

    /**
     * Reports which floor the elevator is at right now.
     *
     * @return int actual floor at the moment.
     */
    int getCurrentFloor();

    void addFloorToTargetList(int floor);

}
