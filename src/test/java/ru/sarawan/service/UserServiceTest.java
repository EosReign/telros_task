package ru.sarawan.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static ru.eosreign.exception.NotFoundException.F_USER_EMAIL_MESSAGE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.eosreign.entity.UserCredential;
import ru.eosreign.entity.UserInfo;
import ru.eosreign.exception.NotFoundException;
import ru.eosreign.mapper.UserMapper;
import ru.eosreign.repo.UserInfoRepo;
import ru.eosreign.service.UserService;
import ru.eosreign.util.JwtService;
import ru.eosreign.web.dto.UserProfileRequest;
import ru.eosreign.web.dto.UserProfileResponse;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

class UserServiceTest {

    @Mock
    private UserInfoRepo repo;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserService userService;

    private UserInfo userInfo;
    private String jwtToken;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        var userCredential = new UserCredential();
        userCredential.setEmail("test@example.com");

        userInfo = new UserInfo();
        userInfo.setFirstName("John");
        userInfo.setLastName("Doe");
        userInfo.setCredential(userCredential);
        userInfo.setBirthDate(Timestamp.valueOf(LocalDate.parse("2025-04-18").atStartOfDay()));

        jwtToken = "valid.jwt.token";
    }

    @Test
    void testGetUser() {
        when(jwtService.extractUsernameFromAuthHeader(jwtToken)).thenReturn("test@example.com");
        when(repo.findByCredential_Email("test@example.com")).thenReturn(Optional.of(userInfo));
        when(mapper.entityToDto(userInfo)).thenReturn(new UserProfileResponse());

        UserProfileResponse response = userService.getUser(jwtToken);

        assertNotNull(response);
        verify(jwtService).extractUsernameFromAuthHeader(jwtToken);
        verify(repo).findByCredential_Email("test@example.com");
        verify(mapper).entityToDto(userInfo);
    }

    @Test
    void testGetByUsername_UserFound() {
        when(repo.findByCredential_Email("test@example.com")).thenReturn(Optional.of(userInfo));

        UserInfo result = userService.getByUsername("test@example.com");

        assertEquals(userInfo, result);
        verify(repo).findByCredential_Email("test@example.com");
    }

    @Test
    void testGetByUsername_UserNotFound() {
        when(repo.findByCredential_Email("notfound@example.com")).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            userService.getByUsername("notfound@example.com");
        });

        assertEquals(String.format(F_USER_EMAIL_MESSAGE, "notfound@example.com"), exception.getMessage());
        verify(repo).findByCredential_Email("notfound@example.com");
    }

    @Test
    void testUpdateUser() {
        UserProfileRequest request = new UserProfileRequest();
        request.setFirstName("Jane");
        request.setLastName("Doe");
        
        when(jwtService.extractUsernameFromAuthHeader(jwtToken)).thenReturn("test@example.com");
        when(repo.findByCredential_Email("test@example.com")).thenReturn(Optional.of(userInfo));

        userService.updateUser(jwtToken, request);

        assertEquals("Jane", userInfo.getFirstName());
        assertEquals("Doe", userInfo.getLastName());
        
        verify(repo).save(userInfo);
    }

    @Test
    void testDeleteUser() {
        when(jwtService.extractUsernameFromAuthHeader(jwtToken)).thenReturn("test@example.com");

        when(repo.findByCredential_Email("test@example.com")).thenReturn(Optional.of(userInfo));

        userService.deleteUser(jwtToken);

        verify(repo).delete(userInfo);
    }
}