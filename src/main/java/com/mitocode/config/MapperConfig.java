package com.mitocode.config;

import com.mitocode.dto.CategoryDTO;
import com.mitocode.dto.ProductDTO;
import com.mitocode.model.Category;
import com.mitocode.model.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean("categoryMapper")
    public ModelMapper categoryMapper(){
        ModelMapper mapper = new ModelMapper();
        TypeMap<Category, CategoryDTO> typeMap1 = mapper.createTypeMap(Category.class, CategoryDTO.class);
        typeMap1.addMapping(Category::getName, (dest, v) -> dest.setNameofCategory((String) v));

        TypeMap<CategoryDTO, Category> typeMap2 = mapper.createTypeMap(CategoryDTO.class, Category.class);
        typeMap2.addMapping(CategoryDTO::getNameofCategory, (dest, v)-> dest.setName((String) v));
        return mapper;
    }

    @Bean("productMapper")
    public ModelMapper productMapper(){
        ModelMapper mapper = new ModelMapper();
        TypeMap<ProductDTO, Product> typeMap1 = mapper.createTypeMap(ProductDTO.class, Product.class);
        typeMap1.addMapping(ProductDTO::getIdCategory, (dest, v) -> dest.getCategory().setIdCategory((Integer) v));

        TypeMap<Product, ProductDTO> typeMap2 = mapper.createTypeMap(Product.class, ProductDTO.class);
        typeMap2.addMapping(p -> p.getCategory().getIdCategory(), (dest,v)-> dest.setIdCategory((Integer) v));

        return mapper;
    }

    @Bean
    public ModelMapper defaultMapper(){
        return new ModelMapper();
    }
}