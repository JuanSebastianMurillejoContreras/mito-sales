package com.mitocode.controller;

import com.mitocode.dto.CategoryDTO;
import com.mitocode.model.Category;
import com.mitocode.service.ICategoryService;
import com.mitocode.service.impl.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    //@Autowired
    private final ICategoryService service; // = new CategoryService();

    @Qualifier("categoryMapper")
    private final ModelMapper mapper;

    //MapStruct y ModelMapper son las librerias famosas de Java
    //La librería modelMapper no tiene soporte con los records

    @PreAuthorize("@authServiceImpl.hasAccess('/readAll')")
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> readAll() throws Exception{
        List<CategoryDTO> list = service.readAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /*@GetMapping
    public ResponseEntity<List<CategoryRecord>> readAll() throws Exception{
        List<CategoryRecord> list = service.readAll().stream().map( e -> new CategoryRecord(e.getIdCategory(), e.getName(), e.getDescription(), e.isEnable())).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }*/

    /*@GetMapping
    public ResponseEntity<List<CategoryDTO>> readAll() throws Exception{
        List<CategoryDTO> list = service.readAll().stream().map( e -> {
           CategoryDTO dto = new CategoryDTO();
           dto.setIdCategory(e.getIdCategory());
           dto.setNameCategory(e.getName());
           dto.setDescriptionCategory(e.getDescription());
           dto.setEnabledCategory(e.isEnable());
           return dto;
        }).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> readById(@PathVariable("id") Integer id) throws Exception{
        CategoryDTO obj = convertToDto(service.readById(id));
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO dto) throws Exception{
        Category obj = service.save(convertToEntity(dto));
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody CategoryDTO dto) throws Exception{
        dto.setIdCategory(id);
        Category obj = service.update(convertToEntity(dto),id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id)throws Exception{
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Queries
    /////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/find/name/{name}")
    public ResponseEntity<List<CategoryDTO>> findByName(@PathVariable("name") String name){
        List<CategoryDTO>  list = service.findByName(name).stream().map(this::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/find/name/like/{name}")
    public ResponseEntity<List<CategoryDTO>> findByNameLike(@PathVariable("name")String name){
        List<CategoryDTO> list = service.findByNameLike(name).stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/get/name/description")
    public ResponseEntity<List<CategoryDTO>> getNameAndDescription2(@RequestParam("name")String name, @RequestParam("description")String description){
        List<CategoryDTO> list = service.getNameAndDescriptions2(name, description).stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/get/name/sql/{name}")
    public ResponseEntity<List<CategoryDTO>> getNameSQL(@PathVariable("name") String name){
        List<CategoryDTO>  list = service.getNameSQL(name).stream().map(this::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //Pagination
    //////////////////////////////////////////////////////////////////////

    @GetMapping("/pagination")
    public ResponseEntity<Page<Category>> findPage(Pageable pageable){
        Page<Category> pageCategory = service.findPage(pageable);
        return new ResponseEntity<>(pageCategory, HttpStatus.OK);
    }

    @GetMapping("/pagination2")
    public ResponseEntity<Page<Category>> findPage(
            @RequestParam(name = "p", defaultValue = "0") int page,
            @RequestParam(name = "s", defaultValue = "3") int size
    ){
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Category> pageResponse = service.findPage(pageRequest);
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    ////  Ordenamiento con Spring Data JPA
    ///////////////////////////////////////////////////////////////////////////////
    @GetMapping("/order")
    public ResponseEntity<List<CategoryDTO>> findAllOrder(@RequestParam(name="param", defaultValue = "ASC") String param){
        List<CategoryDTO> list = service.findAllOrder(param).stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /////////////////////////////////////////////////////////////////////////////////////
    private CategoryDTO convertToDto(Category obj){
        return mapper.map(obj,CategoryDTO.class);
    }
    private Category convertToEntity(CategoryDTO dto) {
        return mapper.map(dto, Category.class);
    }
}
