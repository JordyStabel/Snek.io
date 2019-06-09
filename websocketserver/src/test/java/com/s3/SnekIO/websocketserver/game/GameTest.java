package com.s3.SnekIO.websocketserver.game;

import com.s3.SnekIO.websocketshared.models.InputMouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private InputMouse inputMouse;

    @BeforeEach
    void setUp() {
        // Arrange for each test
        inputMouse = new InputMouse(150, 230);
    }

    @Test
    void setInputMouse() {
        // Arrange
        float expectedX = 150;
        float expectedY = 230;

        // Act
        float actualX = inputMouse.getX();
        float actualY = inputMouse.getY();

        // Assert
        assertEquals(expectedX, actualX);
        assertEquals(expectedY, actualY);
    }
}