package com.fortum.codechallenge.elevators.backend.service.impl;

import com.fortum.codechallenge.elevators.backend.service.DirectionEnum;
import com.fortum.codechallenge.elevators.backend.service.Elevator;
import com.fortum.codechallenge.elevators.backend.service.ElevatorController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ElevatorControllerImpl implements ElevatorController {
    @Override
    public Elevator requestElevator(int toFloor) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public List<Elevator> getElevators() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void releaseElevator(Elevator elevator) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Elevator requestElevator(int toFloor, DirectionEnum requestedDirection) {
        return null;
    }

    @Override
    public Elevator requestInsideElevator(int toFloor, int elevatorId) {
        return null;
    }
}
