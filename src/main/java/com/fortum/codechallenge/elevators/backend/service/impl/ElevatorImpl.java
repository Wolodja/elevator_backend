package com.fortum.codechallenge.elevators.backend.service.impl;

import com.fortum.codechallenge.elevators.backend.service.DirectionEnum;
import com.fortum.codechallenge.elevators.backend.service.Elevator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Getter
@Service
@NoArgsConstructor
public class ElevatorImpl implements Elevator {

    private int id;
    private int totalNumberOfFloors;
    private int currentFloor;
    private DirectionEnum direction;
    private List<Integer> targetFloors=Collections.synchronizedList(new ArrayList<>());
    private List<Integer> servedFloors= Collections.synchronizedList(new ArrayList<>());

    public ElevatorImpl(int id, int totalNumberOfFloors) {
        this.id = id;
        this.totalNumberOfFloors = totalNumberOfFloors;
        this.currentFloor = 0;
        direction = DirectionEnum.NONE;
    }

    @Override
    public DirectionEnum getDirection() {
        return direction;
    }

    @Override
    public int getAddressedFloor() {
        int addressedFloor = -1;
        if (isBusy()) {
            switch (direction) {
                case UP:
                    addressedFloor = targetFloors.stream().mapToInt(Number::intValue).max().orElse(-1);
                    break;
                case DOWN:
                    addressedFloor = targetFloors.stream().mapToInt(Number::intValue).min().orElse(-1);
                    break;
                default:
                    break;
            }
        }
        return addressedFloor;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void moveElevator(int toFloor) {

    }

    @Override
    public boolean isBusy() {
        return !targetFloors.isEmpty();
    }

    @Override
    public int currentFloor() {
        return currentFloor;
    }
}
