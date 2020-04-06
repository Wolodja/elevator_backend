package com.fortum.codechallenge.elevators.backend.service;

import java.util.List;

public interface ElevatorController {

    List<Elevator> getElevators();

    Elevator requestElevator(int toFloor, DirectionEnum requestedDirection);

    Elevator requestInsideElevator(int toFloor, int elevatorId);

}
