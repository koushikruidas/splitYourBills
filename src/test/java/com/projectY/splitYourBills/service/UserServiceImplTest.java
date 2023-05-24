package com.projectY.splitYourBills.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import com.projectY.splitYourBills.entity.User;
import com.projectY.splitYourBills.exception.ResourceNotFoundException;
import com.projectY.splitYourBills.model.UserDTO;
import com.projectY.splitYourBills.repo.UserRepository;

public class UserServiceImplTest {

    private UserRepository userRepository;
    private ModelMapper mapper;
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        mapper = mock(ModelMapper.class);
        userService = new UserServiceImpl(userRepository, mapper);
    }

    @Test
    public void testFindAll() {
        // Arrange
        List<User> users = new ArrayList<>();
        users.add(User.builder().Id(1L).name("John").email("john@example.com").phone("1234567890").build());
        users.add(User.builder().Id(2L).name("jane").email("jane@example.com").phone("9876543210").build());
        when(userRepository.findAll()).thenReturn(users);

        List<UserDTO> expectedUserDTOs = new ArrayList<>();
        expectedUserDTOs.add(new UserDTO(1L, "John", "john@example.com", "1234567890"));
        expectedUserDTOs.add(new UserDTO(2L, "Jane", "jane@example.com", "9876543210"));
        when(mapper.map(any(User.class), eq(UserDTO.class))).thenReturn(new UserDTO());

        // Act
        List<UserDTO> result = userService.findAll();

        // Assert
        assertEquals(expectedUserDTOs.size(), result.size());
        verify(userRepository, times(1)).findAll();
        verify(mapper, times(2)).map(any(User.class), eq(UserDTO.class));
    }

    @Test
    public void testCreateUser() {
        // Arrange
        UserDTO userDto = new UserDTO(1L, "John", "john@example.com", "1234567890");
        User user = User.builder().Id(1L).name("John").email("john@example.com").phone("1234567890").build();
        when(mapper.map(userDto, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(mapper.map(user, UserDTO.class)).thenReturn(userDto);

        // Act
        UserDTO result = userService.createUser(userDto);

        // Assert
        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getName(), result.getName());
        assertEquals(userDto.getEmail(), result.getEmail());
        assertEquals(userDto.getPhone(), result.getPhone());
        verify(mapper, times(1)).map(userDto, User.class);
        verify(userRepository, times(1)).save(user);
        verify(mapper, times(1)).map(user, UserDTO.class);
    }

    @Test
    public void testFindById_ExistingId() {
        // Arrange
        long id = 1L;
        User user = User.builder().Id(1L).name("John").email("john@example.com").phone("1234567890").build();
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findById(id)).thenReturn(optionalUser);
        when(mapper.map(user, UserDTO.class))
        .thenReturn(UserDTO.builder()
        		.id(1)
        		.name("John")
        		.email("john@example.com")
        		.phone("1234567890")
        		.build());

        // Act
        UserDTO result = userService.findById(id);

        // Assert
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPhone(), result.getPhone());
        verify(userRepository, times(1)).findById(id);
        verify(mapper, times(1)).map(user, UserDTO.class);
    }

    @Test
    public void testFindById_NonExistingId() {
        // Arrange
        long id = 1L;
        Optional<User> optionalUser = Optional.empty();
        when(userRepository.findById(id)).thenReturn(optionalUser);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.findById(id));
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    public void testDelete() {
        // Arrange
        long id = 1L;

        // Act
        userService.delete(id);

        // Assert
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdate_ExistingId() {
        // Arrange
        long id = 1L;
        UserDTO userDto = new UserDTO(1L, "John", "john@example.com", "1234567890");
        User user = User.builder().Id(1L).name("John").email("john@example.com").phone("1234567890").build();
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findById(id)).thenReturn(optionalUser);
        when(userRepository.save(user)).thenReturn(user);
        when(mapper.map(user, UserDTO.class)).thenReturn(userDto);

        // Act
        UserDTO result = userService.update(userDto, id);

        // Assert
        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getName(), result.getName());
        assertEquals(userDto.getEmail(), result.getEmail());
        assertEquals(userDto.getPhone(), result.getPhone());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(user);
        verify(mapper, times(1)).map(user, UserDTO.class);
    }

    @Test
    public void testUpdate_NonExistingId() {
        // Arrange
        long id = 1L;
        UserDTO userDto = new UserDTO(1L, "John", "john@example.com", "1234567890");
        Optional<User> optionalUser = Optional.empty();
        when(userRepository.findById(id)).thenReturn(optionalUser);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.update(userDto, id));
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, never()).save(any(User.class));
        verify(mapper, never()).map(any(UserDTO.class), eq(User.class));
        verify(mapper, never()).map(any(User.class), eq(UserDTO.class));
    }
}
