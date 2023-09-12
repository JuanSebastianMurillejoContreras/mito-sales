package com.mitocode.service.impl;

import com.mitocode.model.Category;
import com.mitocode.repo.ICategoryRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends CRUDImpl<Category, Integer> implements ICategoryService {

    //@Autowired
    private final ICategoryRepo repo;
    @Override
    protected IGenericRepo<Category, Integer> getRepo() {
        return repo;
    }

    @Override
    public List<Category> findByName(String name) {
        return repo.findByName(name);
    }

    @Override
    public List<Category> findByNameLike(String name) {
        return repo.findByNameLike("%"+name+"%");
    }

    @Override
    public List<Category> getNameAndDescriptions1(String name, String description) {
        return repo.getNameAndDescriptions1(name, description);
    }

    @Override
    public List<Category> getNameAndDescriptions2(String name, String description) {
        return repo.getNameAndDescriptions2(name, description);
    }

    @Override
    public List<Category> getNameSQL(String name) {
        return repo.getNameSQL(name);
    }

    @Override
    public Page<Category> findPage(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public List<Category> findAllOrder(String param) {
        Sort.Direction direction = param.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        return repo.findAll(Sort.by(direction,"name"));
    }


}
