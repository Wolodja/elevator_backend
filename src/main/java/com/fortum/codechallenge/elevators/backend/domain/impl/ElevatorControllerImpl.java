package com.fortum.codechallenge.elevators.backend.domain.impl;

import com.fortum.codechallenge.elevators.backend.domain.Elevator;
import com.fortum.codechallenge.elevators.backend.domain.ElevatorController;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
