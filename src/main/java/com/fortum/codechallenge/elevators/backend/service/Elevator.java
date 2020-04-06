package com.fortum.codechallenge.elevators.backend.service;

public interface Elevator extends Runnable{

    DirectionEnum getDirection();

    int getId();

    boolean isBusy();

    int getCurrentFloor();

    void addFloorToTargetList(int floor);

}
