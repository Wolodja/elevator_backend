package com.fortum.codechallenge.elevators.backend.listner;

import com.fortum.codechallenge.elevators.backend.event.InElevatorButtonPressEvent;
import com.fortum.codechallenge.elevators.backend.event.OnFloorButtonPressEvent;
import com.fortum.codechallenge.elevators.backend.service.Elevator;
import com.fortum.codechallenge.elevators.backend.service.ElevatorController;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledExecutorService;

@Slf4j
@Service
public class EventListener {

    private ElevatorController elevatorController;

    private ScheduledExecutorService executor;

    public EventListener(ElevatorController elevatorController, ScheduledExecutorService executor) {
        this.elevatorController = elevatorController;
        this.executor = executor;
    }

    @Subscribe
    @AllowConcurrentEvents
    public void floorButtonPressed(OnFloorButtonPressEvent floorPressRequest) {
        log.info("Request from floor " + floorPressRequest.getToFloor() + " in the direction " + floorPressRequest.getRequestedDirection());
        Elevator elevator = elevatorController.requestElevator(floorPressRequest.getToFloor(), floorPressRequest.getRequestedDirection());
        if (elevator != null) {
            elevator.addFloorToTargetList(floorPressRequest.getToFloor());
            executor.execute(elevator);
        }
    }

    @Subscribe
    @AllowConcurrentEvents
    public void requestInsideElevator(InElevatorButtonPressEvent elevatorPressEvent) {
        log.info("Elevator Event- Inside Elevator " + elevatorPressEvent.getElevatorId() + " moving to floor " + elevatorPressEvent.getToFloor());
        Elevator elevator = elevatorController.requestInsideElevator(elevatorPressEvent.getToFloor(), elevatorPressEvent.getElevatorId());
        if (elevator != null) {
            elevator.addFloorToTargetList(elevatorPressEvent.getToFloor());
            executor.execute(elevator);
        }
    }
}
