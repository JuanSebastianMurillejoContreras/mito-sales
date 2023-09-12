package com.mitocode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitocode.dto.CategoryDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Category;
import com.mitocode.service.ICategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

//@ActiveProfiles("test")
//@AutoConfigureMockMvc(addFilters = false)

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean//I want a false instance, only for themes of testing
    private ICategoryService service;

    @MockBean(name = "categoryMapper")
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;


    Category CATEGORY_1 = new Category(1,"TVS", "Television", true);
    Category CATEGORY_2 = new Category(2,"PSP", "Play Station Portable", true);
    Category CATEGORY_3 = new Category(3,"BOOKS", "Some books", true);
    CategoryDTO CATEGORYDTO_1 = new CategoryDTO(1,"TVS", "Television", true);
    CategoryDTO CATEGORYDTO_2 = new CategoryDTO(2,"PSP", "Play Station Portable", true);
    CategoryDTO CATEGORYDTO_3 = new CategoryDTO(3,"BOOKS", "Some books", true);
    CategoryDTO CATEGORYDTO_99 = new CategoryDTO(99,"ERROR", "Some error", false);

    @Test//Never can to be private, always is public
    public void readAllTest() throws Exception {

        List<Category> categories = List.of(CATEGORY_1, CATEGORY_2, CATEGORY_3);

        Mockito.when(service.readAll()).thenReturn(categories);
        Mockito.when(modelMapper.map(CATEGORY_1, CategoryDTO.class)).thenReturn(CATEGORYDTO_1);
        Mockito.when(modelMapper.map(CATEGORY_2, CategoryDTO.class)).thenReturn(CATEGORYDTO_2);
        Mockito.when(modelMapper.map(CATEGORY_3, CategoryDTO.class)).thenReturn(CATEGORYDTO_3);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/categories")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(3)))
                        .andExpect(jsonPath("$[1].nameofCategory", is("PSP")));
    }

    @Test
    void readByIdTest() throws Exception{
        final int ID = 1;

        Mockito.when(service.readById(any())).thenReturn(CATEGORY_1);
        Mockito.when(modelMapper.map(CATEGORY_1, CategoryDTO.class)).thenReturn(CATEGORYDTO_1);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/categories/" + ID)
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nameofCategory", is("TVS")));
    }

    @Test
    void createTest() throws Exception{

        Mockito.when(service.save(any())).thenReturn(CATEGORY_3);
        Mockito.when(modelMapper.map(CATEGORY_3, CategoryDTO.class)).thenReturn(CATEGORYDTO_3);

        MockHttpServletRequestBuilder mockRequest =  MockMvcRequestBuilders
                .post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CATEGORYDTO_3));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nameofCategory", is("BOOKS")))
                .andExpect(jsonPath("$.enabledCategory", is(true)));
    }

    @Test
    void updateTest() throws Exception{

        int ID = 2;

        Mockito.when(service.update(any(),any())).thenReturn(CATEGORY_2);
        Mockito.when(modelMapper.map(CATEGORY_2, CategoryDTO.class)).thenReturn(CATEGORYDTO_2);

        MockHttpServletRequestBuilder mockRequest =  MockMvcRequestBuilders
                .put("/categories/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CATEGORYDTO_2));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nameofCategory", is("PSP")))
                .andExpect(jsonPath("$.enabledCategory", is(true)));
    }

    @Test
    void updateErrorTest() throws Exception{
        int ID = 99;

        Mockito.when(service.update(any(), any())).thenThrow(new ModelNotFoundException("ID NOT VALID:" + ID));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/categories/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CATEGORYDTO_99));

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ModelNotFoundException));
    }

    @Test
    void deleteTest() throws Exception{
        final int ID_CATEGORY = 1;

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/categories/" + ID_CATEGORY)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());

    }

    @Test
    void deleteErrorTest() throws Exception{
        final int ID_CATEGORY = 999;

        Mockito.doThrow(new ModelNotFoundException("ID: " + ID_CATEGORY + " NOT FOUND")).when(service).delete(ID_CATEGORY);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/categories/" + ID_CATEGORY)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());

    }

}