package com.fortum.codechallenge.elevators.backend.service.impl;

import com.fortum.codechallenge.elevators.backend.service.DirectionEnum;
import com.fortum.codechallenge.elevators.backend.service.Elevator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ElevatorImplTest {

    Elevator elevator;

    @BeforeEach
    void setUp() {
        elevator = new ElevatorImpl(1);
    }

    @Test
    public void checkInitialValues() {
        assertEquals(DirectionEnum.NONE, elevator.getDirection());
        assertFalse(elevator.isBusy());
        assertEquals(0, elevator.getCurrentFloor());
    }
}