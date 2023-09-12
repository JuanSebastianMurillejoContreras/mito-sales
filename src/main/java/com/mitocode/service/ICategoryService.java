package com.mitocode.service;

import com.mitocode.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService extends ICRUD<Category, Integer> {

    List<Category> findByName(String name);
    List<Category>findByNameLike(String name);
    List<Category>getNameAndDescriptions1(String name, String description);
    List<Category>getNameAndDescriptions2(String name, String description);
    List<Category>getNameSQL(String name);
    Page<Category> findPage(Pageable pageable);
    List<Category> findAllOrder(String param);
}
