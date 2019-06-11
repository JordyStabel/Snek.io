import com.s3.SnekIO.restserver.User;
import com.s3.SnekIO.restserver.exception.ConflictException;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserResourceControllerTest {

    private UserRepositoryMock userRepositoryMock;

    private static final ObjectId OBJECT_ID_1 = new ObjectId("5cf683f0125f0a66a9ef6911");
    private static final String USERNAME_1 = "henk";
    private static final String EMAIL_1 = "henk@gmail.com";
    private static final String PASSWORD_1 = "password1";

    private static final ObjectId OBJECT_ID_2 = new ObjectId("5cf683f0125f0a66a9ef6912");
    private static final String USERNAME_2 = "piet";
    private static final String EMAIL_2 = "piet@gmail.com";
    private static final String PASSWORD_2 = "password2";

    private static final ObjectId OBJECT_ID_3 = new ObjectId("5cf683f0125f0a66a9ef6913");
    private static final String USERNAME_3 = "truus";
    private static final String EMAIL_3 = "truus@gmail.com";
    private static final String PASSWORD_3 = "password3";

    private static final ObjectId REGISTER_OBJECT_ID = new ObjectId("5cf683f0125f0a66a9ef6914");

    @BeforeEach
    void setUp() {
        // Arrange before each test is executed
        List<User> users = new ArrayList<>();
        users.add(new User(OBJECT_ID_1, USERNAME_1, EMAIL_1, PASSWORD_1));
        users.add(new User(OBJECT_ID_2, USERNAME_2, EMAIL_2, PASSWORD_2));
        users.add(new User(OBJECT_ID_3, USERNAME_3, EMAIL_3, PASSWORD_3));
        userRepositoryMock = new UserRepositoryMock(users);
    }

    @DisplayName("Get All Users Request Success Test")
    @Test
    final void getAllUsers_SuccessRequest_ReturnUserArray() {
        // Arrange
        int expectedSize = 3;
        int expectedCount = 3;

        // Act
        List<User> temp = userRepositoryMock.findAll();
        int actualSize = temp.size();
        int actualCount = 0;
        for (User user : temp) {
            actualCount++;
        }

        // Assert
        assertEquals(expectedSize, actualSize, "The amount of users that should be present");
        assertEquals(expectedCount, actualCount, "The amount of users after counting all in a loop");
    }

    @DisplayName("Get All Users Request Fail Test")
    @Test
    final void getAllUsers_FailRequest_ReturnUserArray() {
        // Arrange
        List<User> temp = userRepositoryMock.findAll();
        // Externally being removed
        temp.removeAll(temp);
        int expected = 0;

        // Act
        int actual = temp.size();

        // Assert
        assertEquals(expected, actual, "The amount of users that should be present");
    }

    @DisplayName("Get All Users Request Fail Test")
    @Test
    final void getAllUsers_FailRequest_ReturnNull() {
        // Arrange
        int expected = 0;
        userRepositoryMock = new UserRepositoryMock(new ArrayList<>());
        List<User> temp = userRepositoryMock.findAll();

        // Act
        int actual = temp.size();

        // Assert
        assertEquals(expected, actual, "The amount of users that should be present");
    }

    @DisplayName("Get Single User By Id Request Success Test")
    @Test
    final void getUserById_SuccessRequest_ReturnUser() {
        // Arrange
        ObjectId objectId = OBJECT_ID_1;
        User expected = new User(OBJECT_ID_1, USERNAME_1, EMAIL_1, PASSWORD_1);

        // Act
        User actual = userRepositoryMock.findBy_id(objectId);

        // Assert
        assertEquals(expected.userName, actual.userName, "Compare username");
        assertEquals(expected.email, actual.email, "Compare email");
        assertEquals(expected.password, actual.password, "Compare password");
        assertEquals(expected._id, actual._id, "Compare id");
    }

    @DisplayName("Get Single User By Id Request Fail Test")
    @Test
    final void getUserById_FailRequest_NullId_ReturnUser() {
        // Arrange
        ObjectId objectId = null;
        User expected = null;

        // Act
        User actual = userRepositoryMock.findBy_id(objectId);

        // Assert
        assertEquals(expected, actual, "Both should be null");
    }

    @DisplayName("Get Single User By Id Request Fail Test")
    @Test
    final void getUserById_FailRequest_NonExistingId_ReturnUser() {
        // Arrange
        ObjectId objectId = new ObjectId("1aa111a1111a1a11a1aa1111");
        User expected = null;

        // Act
        User actual = userRepositoryMock.findBy_id(objectId);

        // Assert
        assertEquals(expected, actual, "Both should be null");
    }

    @DisplayName("Update Single User By Id Request Success Test")
    @Test
    final void updateUser_SuccessRequest_ReturnUser() {
        // Arrange
        ObjectId objectId = OBJECT_ID_2;
        String updatedName = "John Cena";
        User expected = new User(OBJECT_ID_2, updatedName, EMAIL_2, PASSWORD_2);

        // Act
        User actual = userRepositoryMock.findBy_id(objectId);
        actual.userName = "John Cena";
        userRepositoryMock.save(actual);
        actual = userRepositoryMock.findBy_id(objectId);

        // Assert
        assertEquals(expected.userName, actual.userName, "Both user names should be: John Cena");
    }

    @DisplayName("Update Single User By Id Request Fail Test")
    @Test
    final void updateUser_FailRequest_NullForUsername_ReturnUser() {
        // Arrange
        ObjectId objectId = OBJECT_ID_2;
        String updatedName = null;
        User expected = new User(OBJECT_ID_2, updatedName, EMAIL_2, PASSWORD_2);

        // Act
        User actual = userRepositoryMock.findBy_id(objectId);
        actual.userName = null;
        userRepositoryMock.save(actual);
        actual = userRepositoryMock.findBy_id(objectId);

        // Assert
        assertEquals(expected.userName, actual.userName, "Both user names should be: null");
    }

    @DisplayName("Register Single User Request Success Test")
    @Test
    final void register_SuccessRequest_ReturnUser() {
        // Arrange
        String username = "Martin Shkreli";
        String email = "scum@bag.com";
        String password = "*=>O#jail";
        User expected = new User(REGISTER_OBJECT_ID, username, email, password);
        int expectedUserCount = 4;

        // Act
        User actual = userRepositoryMock.register(new User(REGISTER_OBJECT_ID, username, email, password));

        // Assert
        assertEquals(expected.userName, actual.userName, "Compare user names");
        assertEquals(expected.email, actual.email, "Compare emails");
        assertEquals(expected.password, actual.password, "Compare passwords");
        assertEquals(REGISTER_OBJECT_ID, actual._id, "Compare ids");
        assertEquals(expectedUserCount, userRepositoryMock.findAll().size(), "Comparing expected amount of users with the actual amount");
    }

    @DisplayName("Register Single User Request Fail Test")
    @Test
    final void register_FailRequest_DuplicateEmail_ReturnUser() {
        // Arrange & act
        String username = "John Doe";
        String email = EMAIL_1;
        String password = "12345";
        int expectedUserCount = 3;

        Throwable throwable = catchThrowable(() -> userRepositoryMock.register(new User(REGISTER_OBJECT_ID, username, email, password)));

        // Assert
        assertEquals("This email address is already in use.", throwable.getMessage());
        assertEquals(ConflictException.class, throwable.getClass());
        assertEquals(expectedUserCount, userRepositoryMock.findAll().size(), "Comparing expected amount of users with the actual amount");
    }
}