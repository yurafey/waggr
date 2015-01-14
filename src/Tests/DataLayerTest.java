package Tests;

import BusinessLogic.User;
import DataAccessLayer.UserGateway;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataLayerTest {
    private UserGateway userGateway = new UserGateway();

    @Test
    public void testNewGetDeleteUser() {
        String login = "testUser";
        String password = "testUserPassword";
        String name = "testUserName";
        String surname = "testUserSurname";
        String cityName = "testUserCity";
        String countryName = "testUserCountry";
        assertTrue(userGateway.newUser(login, password, name, surname, cityName, countryName));
        User checkUser = userGateway.getUser(login);
        assertEquals("testUser", checkUser.getUserLogin());
        assertEquals("testUserPassword", checkUser.getUserPassword());
        assertEquals("testUserName", checkUser.getUserName());
        assertEquals("testUserSurname",checkUser.getUserSurname());
        assertEquals("testUserCity",checkUser.getUserCity());
        assertEquals("testUserCountry", checkUser.getUserCountry());
        assertTrue(userGateway.deleteUser(login));
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

        assertTrue(userGateway.newUser("testUser", "1", "2", "3", "4", "5"));
        assertTrue(userGateway.updateUser("testUser", newUser));
        assertNull(userGateway.getUser("testUser"));

        User checkUser = userGateway.getUser("testUser1");
        assertEquals("testUser1", checkUser.getUserLogin());
        assertEquals("testUserPassword1", checkUser.getUserPassword());
        assertEquals("testUserName1", checkUser.getUserName());
        assertEquals("testUserSurname1", checkUser.getUserSurname());
        assertEquals("testUserCity1",checkUser.getUserCity());
        assertEquals("testUserCountry1",checkUser.getUserCountry());
        assertTrue(userGateway.deleteUser("testUser1"));
    }
}