package com.fortum.codechallenge.elevators.backend.event;

import com.fortum.codechallenge.elevators.backend.domain.impl.DirectionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OnFloorButtonPressEvent {

    private int toFloor;
    private DirectionEnum requestedDirection;

}
