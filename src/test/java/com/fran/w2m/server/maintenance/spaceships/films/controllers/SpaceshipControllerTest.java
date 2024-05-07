package com.fran.w2m.server.maintenance.spaceships.films.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fran.w2m.server.maintenance.spaceships.films.dto.SpaceshipRQ;
import com.fran.w2m.server.maintenance.spaceships.films.security.SpringSecurityConfig;
import com.fran.w2m.server.maintenance.spaceships.films.services.SpaceshipService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static com.fran.w2m.server.maintenance.spaceships.films.utils.BeanUtils.DESCRIPTION;
import static com.fran.w2m.server.maintenance.spaceships.films.utils.BeanUtils.ID;
import static com.fran.w2m.server.maintenance.spaceships.films.utils.BeanUtils.MAX_SPEED;
import static com.fran.w2m.server.maintenance.spaceships.films.utils.BeanUtils.MODEL;
import static com.fran.w2m.server.maintenance.spaceships.films.utils.BeanUtils.NAME;
import static com.fran.w2m.server.maintenance.spaceships.films.utils.BeanUtils.SPACESHIP_RQ;
import static com.fran.w2m.server.maintenance.spaceships.films.utils.BeanUtils.SPACESHIP_RS;
import static com.fran.w2m.server.maintenance.spaceships.films.utils.BeanUtils.SPACESHIP_RSPAGE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SpaceshipController.class)
@Import(SpringSecurityConfig.class)
class SpaceshipControllerTest {

    public static final String FIELD_NAME = "name";

    public static final String API_SPACESHIPS_SEARCH = "/api/spaceships/search";

    public static final String API_SPACESHIPS = "/api/spaceships";

    public static final String API_SPACESHIPS_ID = "/api/spaceships/";

    public static final String API_SPACESHIPS_PAGE_0_SIZE_10 = "/api/spaceships?page=0&size=10";

    public static final String ROLE_USER = "USER";

    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpaceshipService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = ROLE_USER)
    void whenFindAll_shouldReturnAllSpaceshipRS() throws Exception {

        when(service.findAll(any(PageRequest.class))).thenReturn(SPACESHIP_RSPAGE);

        mockMvc.perform(MockMvcRequestBuilders.get(API_SPACESHIPS_PAGE_0_SIZE_10))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value(NAME))
                .andExpect(jsonPath("$.content[0].model").value(MODEL))
                .andExpect(jsonPath("$.content[0].description").value(DESCRIPTION))
                .andExpect(jsonPath("$.content[0].maxSpeed").value(MAX_SPEED));
    }

    @Test
    @WithMockUser(roles = ROLE_USER)
    void whenFindByIdWithId_shouldReturnSpaceshipRS() throws Exception {

        when(service.findById(any(Long.class))).thenReturn(SPACESHIP_RS);

        mockMvc.perform(MockMvcRequestBuilders.get(API_SPACESHIPS_ID + 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.model").value(MODEL))
                .andExpect(jsonPath("$.description").value(DESCRIPTION))
                .andExpect(jsonPath("$.maxSpeed").value(MAX_SPEED));

    }

    @Test
    @WithMockUser(roles = ROLE_USER)
    void whenFindByNameWithName_shouldReturnSpaceshipRS() throws Exception {

        when(service.findByName(any(String.class))).thenReturn(Collections.singletonList(SPACESHIP_RS));

        mockMvc.perform(MockMvcRequestBuilders.get(API_SPACESHIPS_SEARCH)
                        .param(FIELD_NAME, SPACESHIP_RS.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(NAME))
                .andExpect(jsonPath("$[0].model").value(MODEL))
                .andExpect(jsonPath("$[0].description").value(DESCRIPTION))
                .andExpect(jsonPath("$[0].maxSpeed").value(MAX_SPEED));

    }

    @Test
    @WithMockUser(authorities = ROLE_ADMIN)
    void whenCreateSpaceshipWithBody_shouldReturnSpaceshipRS() throws Exception {

        when(service.save(any(SpaceshipRQ.class))).thenReturn(SPACESHIP_RS);

        mockMvc.perform(MockMvcRequestBuilders.post(API_SPACESHIPS)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(SPACESHIP_RQ))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.model").value(MODEL))
                .andExpect(jsonPath("$.description").value(DESCRIPTION))
                .andExpect(jsonPath("$.maxSpeed").value(MAX_SPEED));

    }

    @Test
    @WithMockUser(authorities = ROLE_ADMIN)
    void whenUpdateSpaceshipWithIdAndBody_shouldReturnSpaceshipRSUpdated() throws Exception {

        when(service.update(any(SpaceshipRQ.class), any(Long.class))).thenReturn(SPACESHIP_RS);

        mockMvc.perform(MockMvcRequestBuilders.put(API_SPACESHIPS_ID + ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(SPACESHIP_RQ))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.model").value(MODEL))
                .andExpect(jsonPath("$.description").value(DESCRIPTION))
                .andExpect(jsonPath("$.maxSpeed").value(MAX_SPEED));

    }

    @Test
    @WithMockUser(authorities = ROLE_ADMIN)
    void whenDeleteSpaceshipWithId_shouldReturnSpaceshipRSUpdated() throws Exception {

        when(service.delete(any(Long.class))).thenReturn(SPACESHIP_RS);

        mockMvc.perform(MockMvcRequestBuilders.delete(API_SPACESHIPS_ID + ID)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.model").value(MODEL))
                .andExpect(jsonPath("$.description").value(DESCRIPTION))
                .andExpect(jsonPath("$.maxSpeed").value(MAX_SPEED));

    }

}