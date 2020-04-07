package com.fortum.codechallenge.elevators.backend;

import com.fortum.codechallenge.elevators.backend.controller.ElevatorControllerEndPoints;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Boiler plate test class to get up and running with a test faster.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class IntegrationTest {
    @Autowired
    private ElevatorControllerEndPoints endpoints;

}
