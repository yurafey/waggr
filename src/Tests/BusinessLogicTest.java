package Tests;

import BusinessLogic.User;
import BusinessLogic.UserWorker;
import org.junit.Test;

import static org.junit.Assert.*;

public class BusinessLogicTest {

    @Test
    public void testNewUser() throws Exception {
        UserWorker userWorker = new UserWorker();
        String login = "testUser";
        String password = "testUserPassword";
        String name = "testUserName";
        String surname = "testUserSurname";
        String cityName = "testUserCity";
        String countryName = "testUserCountry";
        assertTrue(userWorker.newUser(login, password, name, surname, cityName, countryName));
        assertFalse(userWorker.newUser(login, password, name, surname, cityName, countryName));
        assertTrue(userWorker.deleteUser(login));
    }

    @Test
    public void testAuthorization() throws Exception {
        UserWorker userWorker = new UserWorker();
        String login = "testUser";
        String password = "testUserPassword";
        String name = "testUserName";
        String surname = "testUserSurname";
        String cityName = "testUserCity";
        String countryName = "testUserCountry";
        assertTrue(userWorker.newUser(login, password, name, surname, cityName, countryName));
        assertTrue(userWorker.checkAuthorization(login,password));
        assertTrue(userWorker.deleteUser(login));
    }

    @Test
    public void testUpdateUser() throws Exception {
        UserWorker userWorker = new UserWorker();
        String login = "testUser";
        String password = "testUserPassword";
        String name = "testUserName";
        String surname = "testUserSurname";
        String cityName = "testUserCity";
        String countryName = "testUserCountry";
        assertTrue(userWorker.newUser(login, password, name, surname, cityName, countryName));

        User newUser = new User();
        newUser.setUserLogin("testUser1");
        newUser.setUserPassword("testUserPassword1");
        newUser.setUserName("testUserName1");
        newUser.setUserSurname("testUserSurname1");
        newUser.setUserCity("testUserCity1");
        newUser.setUserCountry("testUserCountry1");
        assertTrue(userWorker.updateUser(login, newUser));
        assertNotNull(userWorker.getUser("testUser1"));
        assertTrue(userWorker.deleteUser("testUser1"));
    }

}