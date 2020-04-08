package com.fortum.codechallenge.elevators.backend.domain.impl;

import com.fortum.codechallenge.elevators.backend.domain.Elevator;
import com.fortum.codechallenge.elevators.backend.domain.ElevatorController;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Getter
@NoArgsConstructor
@Service
public class ElevatorControllerImpl implements ElevatorController {

    @Value("${com.fortum.codechallenge.numberOfElevators}")
    private int numberOfElevators;

    @Value("${com.fortum.codechallenge.numberOfFloors}")
    private int numberOfFloors;

    @Value("${com.fortum.codechallenge.onFloorTravelTime}")
    private int oneFloorTravelTime;

    @Value("${com.fortum.codechallenge.passengersGettingOfElevatorTime}")
    private int passengersTime;

    private final List<Elevator> elevators = Collections.synchronizedList(new ArrayList<>());

    @PostConstruct
    public void init() {
        IntStream.rangeClosed(1, numberOfElevators).forEach(elevatorId -> elevators.add(new ElevatorImpl(elevatorId, numberOfFloors, oneFloorTravelTime, passengersTime)));
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
                elevator = elevators.stream()
                        .filter(el -> !el.isBusy()).findAny()
                        .orElse(elevators.stream()
                                .min(Comparator.comparingInt(e -> Math.abs(e.getCurrentFloor() - toFloor)))
                                .orElse(null));
            }
        }
        return elevator;
    }

    public Elevator findElevatorBasedOnDirection(int toFloor, DirectionEnum reqDirection) {
        switch (reqDirection) {
            case UP:
                return elevators.stream()
                        .filter(elevator -> directionUpCondition(toFloor, elevator))
                        .max(Comparator.comparing(Elevator::getCurrentFloor))
                        .orElse(null);
            case DOWN:
                return elevators.stream()
                        .filter(elevator -> directionDownCondition(toFloor, elevator))
                        .min(Comparator.comparing(Elevator::getCurrentFloor))
                        .orElse(null);
            default:
                return null;
        }

    }

    private boolean directionDownCondition(int toFloor, Elevator elevator) {
        return elevator.getCurrentFloor() > toFloor &&
                elevator.getDirection().equals(DirectionEnum.DOWN) &&
                !DirectionEnum.UP.equals(elevator.getNextDirection());
    }

    private boolean directionUpCondition(int toFloor, Elevator elevator) {
        return elevator.getCurrentFloor() < toFloor &&
                elevator.getDirection().equals(DirectionEnum.UP) &&
                !DirectionEnum.DOWN.equals(elevator.getNextDirection());
    }

    private Elevator findElevatorOnCurrentFloor(int toFloor) {
        return elevators.stream()
                .filter(elevator -> (!elevator.isBusy() && elevator.getCurrentFloor() == toFloor))
                .findAny()
                .orElse(null);
    }

    @Override
    public boolean validFloor(int toFloor) {
        return toFloor >= 0 && toFloor <= numberOfFloors;
    }

    @Override
    public boolean validDirection(String direction) {
        return Arrays.stream(DirectionEnum.values()).anyMatch(directionEnum -> directionEnum.name().equalsIgnoreCase(direction))
                && !direction.equalsIgnoreCase(DirectionEnum.NONE.name());
    }

    @Override
    public boolean validElevatorId(int elevatorId) {
        return elevators.stream().anyMatch(elevator -> elevator.getId() == elevatorId);
    }
}
