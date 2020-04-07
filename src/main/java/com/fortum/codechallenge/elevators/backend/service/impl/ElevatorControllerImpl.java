package com.fortum.codechallenge.elevators.backend.service.impl;

import com.fortum.codechallenge.elevators.backend.service.DirectionEnum;
import com.fortum.codechallenge.elevators.backend.service.Elevator;
import com.fortum.codechallenge.elevators.backend.service.ElevatorController;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Getter
@Service
public class ElevatorControllerImpl implements ElevatorController {

    @Value("${com.fortum.codechallenge.numberOfElevators}")
    private int numberOfElevators;

    @Value("${com.fortum.codechallenge.numberOfFloors}")
    private int numberOfFloors;

    private final List<Elevator> elevators = Collections.synchronizedList(new ArrayList<>());

    public ElevatorControllerImpl() {
        IntStream.rangeClosed(1, numberOfElevators).forEach(elevatorId -> elevators.add(new ElevatorImpl(elevatorId)));
    }

    @Override
    public Elevator requestInsideElevator(int elevatorId) {
        return elevators.stream().filter(elevator -> elevator.getId() == elevatorId).findAny().orElse(null);
    }

    @Override
    public Elevator requestElevator(int toFloor, DirectionEnum requestedDirection) {
        Elevator elevator = findElevatorOnCurrentFloor(toFloor);
        if (elevator == null) {
            elevator = findElevatorBasedOnDirection(toFloor, requestedDirection);
            if (elevator == null) {
                elevator = elevators.stream().findFirst().orElse(null);
            }
        }
        return elevator;
    }

    public Elevator findElevatorBasedOnDirection(int toFloor, DirectionEnum reqDirection) {
        switch (reqDirection) {
            case UP:
                return elevators.stream()
                        .filter(elevator -> elevator.getCurrentFloor() < toFloor && elevator.getDirection().equals(DirectionEnum.UP))
                        .max(Comparator.comparing(Elevator::getCurrentFloor))
                        .orElse(null);
            case DOWN:
                return elevators.stream()
                        .filter(elevator -> elevator.getCurrentFloor() > toFloor && elevator.getDirection().equals(DirectionEnum.DOWN))
                        .min(Comparator.comparing(Elevator::getCurrentFloor))
                        .orElse(null);
            case NONE:
            default:
                return elevators.stream()
                        .min(Comparator.comparingInt(elevator -> Math.abs(elevator.getCurrentFloor() - toFloor)))
                        .orElse(null);
        }

    }

    private Elevator findElevatorOnCurrentFloor(int toFloor) {
        return getElevators().stream()
                .filter(elevator -> (!elevator.isBusy() && elevator.getCurrentFloor() == toFloor))
                .findAny()
                .orElse(null);
    }

    @Override
    public boolean validFloor(int toFloor) {
        return toFloor > 0 && toFloor < numberOfFloors;
    }

    @Override
    public boolean validDirection(String direction) {
        for (DirectionEnum directionEnum : DirectionEnum.values()) {
            if (directionEnum.name().equalsIgnoreCase(direction)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean validElevatorId(int elevatorId) {
        return elevators.stream().anyMatch(elevator -> elevator.getId() == elevatorId);
    }
}
