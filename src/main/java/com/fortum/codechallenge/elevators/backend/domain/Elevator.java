package com.fortum.codechallenge.elevators.backend.domain;

import com.fortum.codechallenge.elevators.backend.domain.impl.DirectionEnum;

public interface Elevator extends Runnable{

    int getId();

    boolean isBusy();

    int getCurrentFloor();

    DirectionEnum getDirection();

    DirectionEnum getNextDirection();

    void addFloorToTargetList(int floor, DirectionEnum nextDirection);

}
