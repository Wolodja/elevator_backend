package com.fortum.codechallenge.elevators.backend.domain.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TargetFloor {

    private int toFloor;
    private DirectionEnum nextDirection;
}
