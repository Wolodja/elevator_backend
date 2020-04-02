package com.fortum.codechallenge.elevators.backend.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InElevatorButtonPressEvent {

    private final int toFloor;
    private final int elevatorId;

}
