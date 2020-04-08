package com.fortum.codechallenge.elevators.backend.domain.impl;

import com.fortum.codechallenge.elevators.backend.domain.Elevator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Getter
@ToString
@Service
@NoArgsConstructor
public class ElevatorImpl implements Elevator, Runnable {

    private static final String TARGET_FLOOR_LIST_IS_EMPTY = "Target Floor List is empty!";
    private int id;

    private int numberOfFloors;

    private int oneFloorTravelTime;

    private int passengersTime;

    private int currentFloor;

    private int targetFloor;

    private DirectionEnum direction;

    private DirectionEnum nextDirection;

    private List<TargetFloor> targetFloors = Collections.synchronizedList(new ArrayList<>());

    public ElevatorImpl(int id, int numberOfFloors, int oneFloorTravelTime, int passengersTime) {
        this.id = id;
        this.numberOfFloors = numberOfFloors;
        this.oneFloorTravelTime = oneFloorTravelTime;
        this.passengersTime = passengersTime;
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
    public void addFloorToTargetList(int floor, DirectionEnum direction) {
        if (floor >= 0 && floor <= numberOfFloors) {
            targetFloors.add(new TargetFloor(floor, direction));
        }
    }

    @Override
    public boolean isBusy() {
        return !targetFloors.isEmpty();
    }

    private int getAddressedFloor() {
        if (targetFloors.stream().anyMatch(floor -> floor.getToFloor() == currentFloor)) {
            return currentFloor;
        }
        TargetFloor targetFloor = calculateAddressFloor();
        nextDirection = targetFloor.getNextDirection();
        return targetFloor.getToFloor();
    }

    private TargetFloor calculateAddressFloor() {
        TargetFloor addressedFloor;
        switch (direction) {
            case UP:
                addressedFloor = targetFloors.stream()
                        .filter(floor -> floor.getToFloor() > currentFloor)
                        .min(Comparator.comparingInt(TargetFloor::getToFloor))
                        .orElse(null);
                if (addressedFloor == null) {
                    addressedFloor = targetFloors.stream()
                            .filter(floor -> floor.getToFloor() < currentFloor)
                            .max(Comparator.comparingInt(TargetFloor::getToFloor))
                            .orElse(new TargetFloor(currentFloor, null));
                }
                return addressedFloor;
            case DOWN:
                addressedFloor = targetFloors.stream()
                        .filter(floor -> floor.getToFloor() < currentFloor)
                        .max(Comparator.comparingInt(TargetFloor::getToFloor))
                        .orElse(null);
                if (addressedFloor == null) {
                    addressedFloor = targetFloors.stream()
                            .filter(floor -> floor.getToFloor() > currentFloor)
                            .min(Comparator.comparingInt(TargetFloor::getToFloor))
                            .orElse(new TargetFloor(currentFloor, null));
                }
                return addressedFloor;
            default:
                addressedFloor = targetFloors.stream()
                        .min(Comparator.comparingInt(floor -> Math.abs(floor.getToFloor() - currentFloor)))
                        .orElse(new TargetFloor(currentFloor, null));
                return addressedFloor;
        }
    }

    private void moveElevator() {
        direction = calculateDirection();

        logElevatorMove();

        while (currentFloor != targetFloor) {
            moveOneFloor();
            addOneFloorTravelTime();
        }
        logElevatorFinishMove();
        targetFloors.removeIf(floor -> (currentFloor == floor.getToFloor()));
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
            Thread.sleep(passengersTime);
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

    private void logElevatorMove() {
        log.info("Elevator " + id + " is on " + currentFloor + " floor and is moving to " + targetFloor + " floor");
    }

    private void logElevatorFinishMove() {
        log.info("Elevator  " + id + " reached " + targetFloor + " floor");
    }
}
