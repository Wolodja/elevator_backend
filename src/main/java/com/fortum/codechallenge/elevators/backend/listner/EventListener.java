package com.fortum.codechallenge.elevators.backend.listner;

import com.fortum.codechallenge.elevators.backend.domain.Elevator;
import com.fortum.codechallenge.elevators.backend.domain.ElevatorController;
import com.fortum.codechallenge.elevators.backend.event.InElevatorButtonPressEvent;
import com.fortum.codechallenge.elevators.backend.event.OnFloorButtonPressEvent;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Service
public class EventListener {

    private ElevatorController elevatorController;

    private ThreadPoolExecutor executor;

    public EventListener(ElevatorController elevatorController, ThreadPoolExecutor executor) {
        this.elevatorController = elevatorController;
        this.executor = executor;
    }

    @Subscribe
    @AllowConcurrentEvents
    public void requestOnFloor(OnFloorButtonPressEvent floorPressRequest) {
        log.info("On  " + floorPressRequest.getToFloor() + " floor elevator is requested in " + floorPressRequest.getRequestedDirection() + " direction.");
        Elevator elevator = elevatorController.requestElevator(floorPressRequest.getToFloor(), floorPressRequest.getRequestedDirection());
        if (elevator != null) {
            if (elevator.isBusy()) {
                elevator.addFloorToTargetList(floorPressRequest.getToFloor(), floorPressRequest.getRequestedDirection());
            } else {
                elevator.addFloorToTargetList(floorPressRequest.getToFloor(), floorPressRequest.getRequestedDirection());
                executor.execute(elevator);
            }
        }
    }

    @Subscribe
    @AllowConcurrentEvents
    public void requestInsideElevator(InElevatorButtonPressEvent elevatorPressEvent) {
        log.info("Inside Elevator " + elevatorPressEvent.getElevatorId() + " pressed button to move to " + elevatorPressEvent.getToFloor() + " floor.");
        Elevator elevator = elevatorController.requestInsideElevator(elevatorPressEvent.getElevatorId());
        if (elevator != null) {
            if (elevator.isBusy()) {
                elevator.addFloorToTargetList(elevatorPressEvent.getToFloor(), null);
            } else {
                elevator.addFloorToTargetList(elevatorPressEvent.getToFloor(), null);
                executor.execute(elevator);
            }
        }
    }
}
