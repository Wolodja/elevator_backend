package com.fortum.codechallenge.elevators.backend.service;

import java.util.List;

public interface ElevatorController {

    List<Elevator> getElevators();

    Elevator requestElevator(int toFloor, DirectionEnum requestedDirection);

    Elevator requestInsideElevator(int elevatorId);

    boolean validFloor(int toFloor);

    boolean validDirection(String direction);

    boolean validElevatorId(int elevatorId);

}
