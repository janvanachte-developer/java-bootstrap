package io.vanachte.jan.bootstrap.jpa;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean // https://spring.io/blog/2011/07/27/fine-tuning-spring-data-repositories
public interface ReadOnlyRepositorySpringDataImpl<ENTITY, ID extends Serializable> extends Repository<ENTITY, ID> {

    Optional<ENTITY> findById(ID id);
    boolean existsById(ID id);
    List<ENTITY> findAll();
    List<ENTITY> findAll(Sort sort);
    Page<ENTITY> findAll(Pageable pageable);
    List<ENTITY> findAllById(Iterable<ID> iterable);
    long count();
    ENTITY getOne(ID id);
    <S extends ENTITY> List<S> findAll(Example<S> example);
    <S extends ENTITY> List<S> findAll(Example<S> example, Sort sort);
}
