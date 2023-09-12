package com.mitocode.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean//Como T no es un @Entity no se pueden generar Beans, porque no tengo el tipo
//Y en algun momento se lo proporcionará. Si no coloca esta anotación se cae la aplicacion porque
// Spring Boot no sabe que hacer con IGenericRepo. Entonces esta anotación le dice que no cree un Bean
// de esta interface. Y este comportamiento se hereda internamente en las otras interfaces.
public interface IGenericRepo<T, ID> extends JpaRepository<T, ID> {
}
