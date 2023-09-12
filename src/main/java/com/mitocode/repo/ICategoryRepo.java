package com.mitocode.repo;

import com.mitocode.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICategoryRepo extends IGenericRepo<Category, Integer> {

    /////////////DerivedQueries////////////////
    //SELECT * FROM Category c Where c.name = '';
    List<Category>findByName(String name);

    //SELECT * FROM Category c Where c.name LIKE '%aaa%';
    List<Category>findByNameLike(String name);
    //If I want all %//
    //  % name % -> findByNameContains
    //  % name -> findByNameStarWith
    //    name % -> findByNameEndsWith


    //SELECT * FROM Category c Where c.name = ? AND enabled = ?
   /** List<Category>findByNameAndEnable(String name, boolean enabled);
    List<Category>findByNameOrEnable(String name, boolean enabled);
    List<Category>findByEnable(boolean enabled);
    List<Category>findByEnableTrue();
    List<Category>findByEnableFalse();
    Category findOneByName(String name);
    List<Category> findTop3ByName(String name);
    List<Category> findByNameIs(String name);
    List<Category>findByNameIsNot(String name);
    List<Category>findByNameIsNull(String name);
    List<Category>findByNameEqualsIgnoreCase(String name);
    List<Category>findByIdCategoryLessThan(Integer id);
    List<Category>findByIdCategoryLessThanEqual(Integer id);
    List<Category>findByIdCategoryGreaterThan(Integer id);
    List<Category>findByIdCategoryGreaterThanEqual(Integer id);
    List<Category>findByIdCategoryBetween(Integer id1, Integer id2);
    List<Category>findByNameOrderByDescriptionAsc(String name);
    List<Category>findByNameOrderByDescriptionDesc(String name);**/


/////////////JPQL///////////////
// Java persistence Query Language

    //@Query("SELECT * FROM Category c WHERE c.name = ? AND c.description = ?")
    //@Query("FROM CategoryEntity c WHERE c.name = ? AND c.description = ?")
    @Query("FROM Category c WHERE c.name = :name AND c.description LIKE  %:description%")
    List<Category>getNameAndDescriptions1(@Param("name") String name, @Param("description") String description);

    @Query("SELECT new com.mitocode.model.Category(c.name, c.description) FROM Category c WHERE c.name = :name AND c.description LIKE  %:description%")
    List<Category>getNameAndDescriptions2(@Param("name") String name, @Param("description") String description);


/////////////SQL////////////////
    // SQL: NativeQuery // Exactly like in a DB motor
    @Query(value = "SELECT * FROM category WHERE name = :name", nativeQuery = true)
    List<Category>getNameSQL(@Param("name")String name);


    @Modifying//Se utiliza cuando el Query hace algo distinto a SELECT * FROM. Para cambiar
    // el comportamiento y que no espere un registro como resultado.
    @Query(value = "UPDATE category SET name = :name", nativeQuery = true)
    //Para devover el número de columnas afectadas.
    Integer updateNames(@Param("name")String name);










}
