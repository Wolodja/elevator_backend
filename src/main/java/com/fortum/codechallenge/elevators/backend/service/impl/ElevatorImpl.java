package com.fortum.codechallenge.elevators.backend.service.impl;

import com.fortum.codechallenge.elevators.backend.service.DirectionEnum;
import com.fortum.codechallenge.elevators.backend.service.Elevator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Getter
@Service
@NoArgsConstructor
public class ElevatorImpl implements Elevator, Runnable {

    private int id;

    @Value("${com.fortum.codechallenge.numberOfFloors}")
    private int numberOfFloors;

    @Value("${com.fortum.codechallenge.onFloorTravelTime}")
    private int oneFloorTravelTime;

    @Value("${com.fortum.codechallenge.passengersGettingOfElevatorTime}")
    private int passengersGettingOffTime;

    private int currentFloor;

    private int targetFloor;

    private DirectionEnum direction;

    private List<Integer> targetFloors = Collections.synchronizedList(new ArrayList<>());

    public ElevatorImpl(int id) {
        this.id = id;
        this.currentFloor = 0;
        this.targetFloor = 0;
        direction = DirectionEnum.NONE;
    }

    @Override
    public void run() {
        while (isBusy()) {
            targetFloor = getAddressedFloor();
            moveElevator();
            waitForPassengersToGetInOrGetOff();
        }
    }

    @Override
    public int getAddressedFloor() {
        if (targetFloors.contains(currentFloor)) {
            return currentFloor;
        }
        return calculateAddressFloor();
    }

    private int calculateAddressFloor() {
        int addressedFloor;
        switch (direction) {
            case UP:
                addressedFloor = targetFloors.stream()
                        .filter(floor -> floor > currentFloor)
                        .mapToInt(Number::intValue)
                        .min()
                        .orElse(-1);
                if (addressedFloor == -1) {
                    addressedFloor = targetFloors.stream()
                            .filter(floor -> floor < currentFloor)
                            .mapToInt(Number::intValue)
                            .max()
                            .orElse(-1);
                }
                return addressedFloor;
            case DOWN:
                addressedFloor = targetFloors.stream()
                        .filter(floor -> floor < currentFloor)
                        .mapToInt(Number::intValue)
                        .max()
                        .orElse(-1);
                if (addressedFloor == -1) {
                    addressedFloor = targetFloors.stream()
                            .filter(floor -> floor > currentFloor)
                            .mapToInt(Number::intValue)
                            .min()
                            .orElse(-1);
                }
                return addressedFloor;
            default:
                addressedFloor = targetFloors.stream()
                        .min(Comparator.comparingInt(i -> Math.abs(i - currentFloor)))
                        .orElseThrow(() -> new RuntimeException("Target Floor List is empty!"));
                return addressedFloor;
        }
    }

    @Override
    public void moveElevator() {
        direction = calculateDirection();

        logElevatorMove();

        while (currentFloor != targetFloor) {
            moveOneFloor();
            addOneFloorTravelTime();
        }
        logElevatorFinishMove();
        targetFloors.removeIf(floor -> (currentFloor == floor));
    }


    private void addOneFloorTravelTime() {
        try {
            Thread.sleep(oneFloorTravelTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void waitForPassengersToGetInOrGetOff() {
        try {
            Thread.sleep(passengersGettingOffTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void moveOneFloor() {
        switch (direction) {
            case UP:
                currentFloor += 1;
                break;
            case DOWN:
                currentFloor -= 1;
                break;
            default:
                break;
        }
    }

    private DirectionEnum calculateDirection() {
        if (targetFloor > currentFloor) {
            return DirectionEnum.UP;
        } else {
            return targetFloor < currentFloor ? DirectionEnum.DOWN : DirectionEnum.NONE;
        }
    }

    @Override
    public boolean isBusy() {
        return !targetFloors.isEmpty();
    }

    @Override
    public void addFloorToTargetList(int floor) {
        if (floor > 0 && floor <= numberOfFloors) {
            targetFloors.add(floor);
        }
    }

    private void logElevatorMove() {
        log.info("Elevator " + id + " is on " + currentFloor + " floor and is moving to " + targetFloor + " floor");
    }

    private void logElevatorFinishMove() {
        log.info("Elevator  " + id + " reached " + targetFloor + " floor");
    }
}
