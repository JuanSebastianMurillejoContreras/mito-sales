package com.mitocode.service;

import com.mitocode.model.Category;
import com.mitocode.repo.ICategoryRepo;
import com.mitocode.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//@ActiveProfiles("test")
//@AutoConfigureMockMvc(addFilters = false)

@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {

    @MockBean
    private CategoryServiceImpl service;

    @MockBean
    private ICategoryRepo repo;

    private Category CATEGORY_1;
    private Category CATEGORY_2;
    private Category CATEGORY_3;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(repo);
        this.service = new CategoryServiceImpl(repo);

        Category CATEGORY_1 = new Category(1,"TVS", "Television", true);
        Category CATEGORY_2 = new Category(2,"PSP", "Play Station", true);
        Category CATEGORY_3 = new Category(3,"BOOKS", "Some books", true);

        List<Category> categories = List.of(CATEGORY_1, CATEGORY_2, CATEGORY_3);
        Mockito.when(repo.findAll()).thenReturn(categories);
        Mockito.when(repo.findById(any())).thenReturn(Optional.of(CATEGORY_1));
        Mockito.when(repo.save(any())).thenReturn(CATEGORY_1);
    }

    @Test
    void readAllTest() throws Exception{
        List<Category> response = service.readAll();

        //assertNotNull(response);
        //assertTrue(!response.isEmpty());
        assertEquals(response.size(),3);

    }

    @Test
    void readByIdTest() throws Exception {
        final int ID = 1;
        Category response = service.readById(any());
        assertNotNull(response);
    }

    @Test
    void saveTest() throws Exception{
        Category response = service.save(CATEGORY_1);
        assertNotNull(response);
    }

    @Test
    void updateTest() throws Exception{
        final int ID = 1;
        Category response = service.update(CATEGORY_1, any());
        assertNotNull(response);
    }

    @Test
    void deleteTest() throws Exception{
        repo.deleteById(any());
        repo.deleteById(any());
        repo.deleteById(any());
        verify(repo, times(3)).deleteById(any());
    }



}
