package Java.com.forloop.controller;

import com.forloop.controller.UserController;
import com.forloop.exceptions.NameAlreadyTakenException;
import com.forloop.model.User;
import com.forloop.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @Mock
    private HttpSession session;
    @Mock
    private UserService service;

    UserController userController;

    @BeforeEach
    public void setUp(){
        service = mock(UserService.class);
        session = mock(HttpSession.class);
        }

    @Test
    public void registrationThrowsException() throws NameAlreadyTakenException {

        when(service.registration(any(User.class))).thenThrow(new NameAlreadyTakenException("name already taken"));
        userController = new UserController(service);

        Assertions.assertEquals(userController.registration("a", "b", "c", session),
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("response", "name already taken")));
    }

    @Test
    public void registrationTest() throws NameAlreadyTakenException {

        when(service.registration(any(User.class))).thenReturn(new User());
        userController = new UserController(service);
        Assertions.assertEquals(userController.registration("a", "b", "c", session),
                ResponseEntity.ok(Collections.singletonMap("redirect", "/index")));
    }

    @Test
    public void loginTest(){

        doNothing().when(session).setAttribute(any(String.class), any(long.class));
        when(service.login(any(String.class), any(String.class))).thenReturn(new User());

        userController = new UserController(service);
        Assertions.assertEquals(ResponseEntity.ok(Collections.singletonMap("redirect", "/index")),
                userController.login("username", "password", session));

    }
    @Test
    public void loginNullTest(){

        when(service.login(any(String.class), any(String.class))).thenReturn(null);

        userController = new UserController(service);
        Assertions.assertEquals(userController.login("username", "password", session),
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("response", "Wrong username or password.")));
    }


    @Test
    public void logoutTest(){
        userController = new UserController(service);

        Assertions.assertTrue(userController.logout(session).isRedirectView());

    }

}
