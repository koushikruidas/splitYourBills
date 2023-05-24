package com.projectY.splitYourBills.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectY.splitYourBills.model.UserDTO;
import com.projectY.splitYourBills.service.UserService;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
    	// Initialize Mockito mocks
        MockitoAnnotations.openMocks(this);
        // Create an instance of UserController with the mocked UserService
        UserController userController = new UserController(userService);

        // Set up MockMvc using standalone configuration
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testCreateUser() throws Exception {
        // Create mock data for testing
        UserDTO mockUser = new UserDTO();
        mockUser.setId(1L);
        mockUser.setName("John Doe");

        // Mock the behavior of userService method
        Mockito.when(userService.createUser(any(UserDTO.class))).thenReturn(mockUser);

        // Perform the POST request and validate the response
        MvcResult mvcResult = mockMvc.perform(post("/api/users/create")
                .content(asJsonString(mockUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        // Verify the response
        String responseBody = mvcResult.getResponse().getContentAsString();
        UserDTO responseUser = new ObjectMapper().readValue(responseBody, UserDTO.class);
        assertEquals(mockUser, responseUser);

        // Verify that userService method was called with the correct argument
        Mockito.verify(userService).createUser(mockUser);
    }
    
    @Test
    public void testFindById() throws Exception {
    	UserDTO mockUser = UserDTO.builder()
    				.id(1L)
    				.name("John Doe")
    				.build();
        
    	Mockito.when(userService.findById(any(Long.class))).thenReturn(mockUser);
    	
    	mockMvc.perform(MockMvcRequestBuilders.get("/api/users/get/{id}", 1L))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"));
    }
    
    @Test
    public void testFindAll() throws Exception {
        List<UserDTO> users = Arrays.asList(
                UserDTO.builder().id(1L).name("John Doe").email("john.doe@example.com").build(),
                UserDTO.builder().id(2L).name("Jane Smith").email("jane.smith@example.com").build()
        );

        Mockito.when(userService.findAll()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/get/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(users.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Jane Smith"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email").value("jane.smith@example.com"));
    }

    @Test
    public void testDelete() throws Exception {
        long userId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/delete/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(userService, Mockito.times(1)).delete(userId);
    }

    @Test
    public void testUpdate() throws Exception {
        long userId = 1L;
        UserDTO userDto = UserDTO.builder().id(1L).name("John Doe").email("john.doe@example.com").build();
        UserDTO updatedUserDto = UserDTO.builder().id(userId).name("Updated User").email("updated@example.com").build();

        Mockito.when(userService.update(userDto, userId)).thenReturn(updatedUserDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/update/{id}", userId)
                .content(asJsonString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated User"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("updated@example.com"));
    }
    
    // Utility method to convert an object to JSON string
    private static String asJsonString(Object object) throws Exception {
        return new ObjectMapper().writeValueAsString(object);
    }

}
