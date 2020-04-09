package com.fortum.codechallenge.elevators.backend.controller;

import com.fortum.codechallenge.elevators.backend.domain.ElevatorController;
import com.fortum.codechallenge.elevators.backend.domain.impl.DirectionEnum;
import com.fortum.codechallenge.elevators.backend.event.InElevatorButtonPressEvent;
import com.fortum.codechallenge.elevators.backend.event.OnFloorButtonPressEvent;
import com.fortum.codechallenge.elevators.backend.exception.InvalidRequestParametersException;
import com.google.common.eventbus.EventBus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/rest/v1")
public final class ElevatorControllerEndPoints {

    private EventBus eventBus;
    private ElevatorController elevatorController;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(value = "/requestElevator/{floor}/{direction}")
    public String requestElevator(@NotNull @PathVariable Integer floor, @NotNull @PathVariable String direction) {
        if (elevatorController.validFloor(floor) && elevatorController.validDirection(direction)) {
            eventBus.post(new OnFloorButtonPressEvent(floor, DirectionEnum.valueOf(direction.toUpperCase())));
            return "Success";
        } else {
            throw new InvalidRequestParametersException("Invalid input - Floor : " + floor + ", direction: " + direction);
        }
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(value = "/requestInsideElevator/{elevatorId}/{floor}")
    public String requestInsideElevator(@NotNull @PathVariable Integer elevatorId, @NotNull @PathVariable Integer floor) {
        if (elevatorController.validElevatorId(elevatorId) && elevatorController.validFloor(floor)) {
            eventBus.post(new InElevatorButtonPressEvent(floor, elevatorId));
            return "Success";
        } else {
            throw new InvalidRequestParametersException("Invalid input - Floor : " + floor + ", elevatorId: " + elevatorId);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidRequestParametersException.class)
    public String handleInvalidRequestParameters(Exception exception) {
        log.error(exception.getMessage());
        return exception.getMessage();
    }

}



