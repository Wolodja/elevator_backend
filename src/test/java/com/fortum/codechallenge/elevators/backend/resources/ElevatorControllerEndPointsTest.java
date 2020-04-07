package com.fortum.codechallenge.elevators.backend.resources;

import com.fortum.codechallenge.elevators.backend.controller.ElevatorControllerEndPoints;
import com.fortum.codechallenge.elevators.backend.service.ElevatorController;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ElevatorControllerEndPoints.class)
public class ElevatorControllerEndPointsTest {

    @Autowired
    private WebTestClient webClient;

    private static final String INVALID_INPUT = "Invalid input";

    @MockBean
    private ElevatorController elevatorController;

/*    @Test
    public void testInvalidFloor() {
        webClient.get().uri("/rest/v1/requestElevator/-5/up").exchange().expectBody(String.class).isEqualTo(INVALID_INPUT);
    }

    @Test
    public void testInvalidDirection() {
        webClient.get().uri("/rest/v1/requestElevator/5/left").exchange().expectBody(String.class).isEqualTo(INVALID_INPUT);
    }*/
}
