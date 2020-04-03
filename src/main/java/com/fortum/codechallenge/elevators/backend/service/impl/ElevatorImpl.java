package com.fortum.codechallenge.elevators.backend.service.impl;

import com.fortum.codechallenge.elevators.backend.service.DirectionEnum;
import com.fortum.codechallenge.elevators.backend.service.Elevator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Service
@NoArgsConstructor
public class ElevatorImpl implements Elevator {

    private int id;
    private int totalNumberOfFloors;
    private int currentFloor;
    private DirectionEnum direction;

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
        return 0;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void moveElevator(int toFloor) {

    }

    @Override
    public boolean isBusy() {
        return false;
    }

    @Override
    public int currentFloor() {
        return currentFloor;
    }
}
