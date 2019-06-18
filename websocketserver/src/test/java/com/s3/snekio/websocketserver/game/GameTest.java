package com.s3.snekio.websocketserver.game;

import com.s3.snekio.websocketshared.models.Orb;
import com.s3.snekio.websocketshared.models.Player;
import com.s3.snekio.websocketshared.models.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private GameMock gameMock;

    // Create two players
    private static final String NAME_1 = "henk";
    private static final String SESSIONID_1 = "session1";
    private static final String UUID_1 = "UUID1";
    private static final String COLOR_1 = "#ffffff";
    private static final float X_1 = 100f;
    private static final float Y_1 = 100f;
    private static final Position POSITION_1 = new Position(X_1, Y_1);
    private static Player PLAYER_1;

    private static final String NAME_2 = "john doe";
    private static final String SESSIONID_2 = "session2";
    private static final String UUID_2 = "UUID2";
    private static final String COLOR_2 = "#dddddd";
    private static final float X_2 = 150f;
    private static final float Y_2 = 150f;
    private static final Position POSITION_2 = new Position(X_2, Y_2);
    private static Player PLAYER_2;

    private static final String NAME_3 = "jan";
    private static final String SESSIONID_3 = "session3";
    private static final String UUID_3 = "UUID3";
    private static final String COLOR_3 = "#eeeeee";
    private static final float X_3 = 100f;
    private static final float Y_3 = 100f;
    private static final Position POSITION_3 = new Position(X_3, Y_3);
    private static Player PLAYER_3;

    private static final float X_orb1 = 100f;
    private static final float Y_orb1 = 100f;
    private static final float X_orb2 = 200f;
    private static final float Y_orb2 = 200f;
    private static final float orbValue = 10f;
    private static final Orb ORB_1 = new Orb(new Position(X_orb1, Y_orb1), orbValue);
    private static final Orb ORB_2 = new Orb(new Position(X_orb2, Y_orb2), orbValue);

    private static final SnekLogic snekLogic = new SnekLogic();

    @BeforeEach
    void setUp() {
        // Arrange before each test is executed
        gameMock = new GameMock();

        PLAYER_1 = new Player(NAME_1, SESSIONID_1, UUID_1, COLOR_1, POSITION_1);
        gameMock.addPlayer(PLAYER_1);
        PLAYER_2 = new Player(NAME_2, SESSIONID_2, UUID_2, COLOR_2, POSITION_2);
        gameMock.addPlayer(PLAYER_2);
        PLAYER_3 = new Player(NAME_3, SESSIONID_3, UUID_3, COLOR_3, POSITION_3);
        gameMock.addPlayer(PLAYER_3);

        gameMock.addOrb(ORB_1);
        gameMock.addOrb(ORB_2);

        // Simulate 5 game ticks for each player
        SnekLogic snekLogic = new SnekLogic();
        for (int i = 0; i < 4; i++) {
            snekLogic.updateSnek(PLAYER_1.getSnek(), 0,0);
            snekLogic.updateSnek(PLAYER_2.getSnek(), 0,0);
            snekLogic.updateSnek(PLAYER_3.getSnek(), 0,0);
        }
    }

    @DisplayName("Find player by SessionId")
    @Test
    void findPlayer_SuccessRequest_ReturnPlayer() {
        // Arrange
        Player expected = new Player(NAME_1, SESSIONID_1, UUID_1, COLOR_1, POSITION_1);

        // Act
        Player actual = gameMock.findPlayer(PLAYER_1.getSessionId());

        // Assert
        assertEquals(expected.getName(), actual.getName(), "Compare name");
        assertEquals(expected.getSessionId(), actual.getSessionId(), "Compare sessionid");
        assertEquals(expected.getUuid(), actual.getUuid(), "Compare uuid");
        assertEquals(expected.getPosition().getX(), actual.getPosition().getX(), "Compare position, x value");
        assertEquals(expected.getPosition().getY(), actual.getPosition().getY(), "Compare position, y value");
    }

    @DisplayName("Does player 1 intersects (collide) with player 3 Test")
    @Test
    void playerIntersect_SuccessRequest_ReturnBoolean() {
        // Arrange
        boolean expected = true;
        List<Position> playerSnek = PLAYER_1.getSnek().getTail();
        Position playerSnekHead = playerSnek.get(0);

        // Act
        boolean actual = gameMock.playerIntersect(PLAYER_1, playerSnekHead, playerSnek, PLAYER_3);

        // Assert
        assertEquals(expected, actual, "Player_1 is colliding with player_3");
    }

    @DisplayName("Does player 1 does not intersect (collide) with player 2 Test")
    @Test
    void playerIntersect_FailRequest_ReturnBoolean() {
        // Arrange
        boolean expected = false;
        List<Position> playerSnek = PLAYER_1.getSnek().getTail();
        Position playerSnekHead = playerSnek.get(0);

        // Act
        boolean actual = gameMock.playerIntersect(PLAYER_1, playerSnekHead, playerSnek, PLAYER_2);

        // Assert
        assertEquals(expected, actual, "Player_1 doesn't collide with player_2");
    }

    @DisplayName("Does player 1 intersect with orb 1 Test")
    @Test
    void orbIntersectAndCollect_SuccessRequest() {
        // Arrange
        int expectedSnekSize = PLAYER_1.getSnek().getSize() + 1;
        int expectedOrbCount = 2;
        List<Position> playerSnek = PLAYER_1.getSnek().getTail();
        Position playerSnekHead = playerSnek.get(0);

        // Act
        gameMock.orbIntersect(playerSnekHead, PLAYER_1);
        int actualSnekSize = PLAYER_1.getSnek().getSize();
        int actualOrbCount = gameMock.getOrbs().size();

        // Assert
        assertEquals(expectedSnekSize, actualSnekSize, "Player_1 increased one in size");
        assertEquals(expectedOrbCount, actualOrbCount, "Game added back one orb");
    }

    @DisplayName("Does player 2 does not intersect with orb 2 Test")
    @Test
    void orbIntersectAndCollect_FailRequest() {
        // Arrange
        int expectedSnekSize = PLAYER_2.getSnek().getSize();
        int expectedOrbCount = 2;
        List<Position> playerSnek = PLAYER_2.getSnek().getTail();
        Position playerSnekHead = playerSnek.get(0);

        // Act
        gameMock.orbIntersect(playerSnekHead, PLAYER_2);
        int actualSnekSize = PLAYER_2.getSnek().getSize();
        int actualOrbCount = gameMock.getOrbs().size();

        // Assert
        assertEquals(expectedSnekSize, actualSnekSize, "Player_2 didn't increase in size");
        assertEquals(expectedOrbCount, actualOrbCount, "Game has same amount of orbs");
    }

    @DisplayName("Player 1s snek gets destroyed and orbs added back into the game Test")
    @Test
    void explodeSnekAndReAddOrbsIntoGame_SuccessRequest() {
        // Arrange
        int expectedOrbCount = gameMock.getOrbs().size() + PLAYER_2.getSnek().getSize() - 1;
        List<Position> playerSnek = PLAYER_2.getSnek().getTail();

        // Act
        gameMock.explodeSnek(playerSnek);
        int actualOrbCount = gameMock.getOrbs().size();

        // Assert
        assertEquals(expectedOrbCount, actualOrbCount, "Game has more Orbs");
    }
}