package com.fortum.codechallenge.elevators.backend.domain;

import com.fortum.codechallenge.elevators.backend.domain.impl.DirectionEnum;

public interface ElevatorController {

    Elevator requestElevator(int toFloor, DirectionEnum requestedDirection);

    Elevator requestInsideElevator(int elevatorId);

    boolean validFloor(int toFloor);

    boolean validDirection(String direction);

    boolean validElevatorId(int elevatorId);

}