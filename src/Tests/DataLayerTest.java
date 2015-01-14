package Tests;

import BusinessLogic.User;
import DataAccessLayer.DBConnector;
import org.junit.Test;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class DataLayerTest {
    private DBConnector dbConnector = new DBConnector();

    @Test
    public void testNewGetDeleteUser() {
        String login = "testUser";
        String password = "testUserPassword";
        String name = "testUserName";
        String surname = "testUserSurname";
        String cityName = "testUserCity";
        String countryName = "testUserCountry";
        assertTrue(dbConnector.newUser(login, password, name, surname, cityName, countryName));
        User checkUser = dbConnector.getUser(login);
        assertEquals("testUser", checkUser.getUserLogin());
        assertEquals("testUserPassword", checkUser.getUserPassword());
        assertEquals("testUserName", checkUser.getUserName());
        assertEquals("testUserSurname",checkUser.getUserSurname());
        assertEquals("testUserCity",checkUser.getUserCity());
        assertEquals("testUserCountry",checkUser.getUserCountry());
        assertTrue(dbConnector.deleteUser(login));
    }

    @Test
    public void testUpdateUser() throws Exception {
        User newUser = new User();
        newUser.setUserLogin("testUser1");
        newUser.setUserPassword("testUserPassword1");
        newUser.setUserName("testUserName1");
        newUser.setUserSurname("testUserSurname1");
        newUser.setUserCity("testUserCity1");
        newUser.setUserCountry("testUserCountry1");

        assertTrue(dbConnector.newUser("testUser", "1", "2", "3", "4", "5"));
        assertTrue(dbConnector.updateUser("testUser", newUser));
        assertNull(dbConnector.getUser("testUser"));

        User checkUser = dbConnector.getUser("testUser1");
        assertEquals("testUser1", checkUser.getUserLogin());
        assertEquals("testUserPassword1", checkUser.getUserPassword());
        assertEquals("testUserName1", checkUser.getUserName());
        assertEquals("testUserSurname1",checkUser.getUserSurname());
        assertEquals("testUserCity1",checkUser.getUserCity());
        assertEquals("testUserCountry1",checkUser.getUserCountry());
        assertTrue(dbConnector.deleteUser("testUser1"));
    }
}