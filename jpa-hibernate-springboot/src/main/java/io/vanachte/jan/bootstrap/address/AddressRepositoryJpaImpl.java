package io.vanachte.jan.bootstrap.address;

import org.springframework.context.annotation.Primary;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Named
@Primary
public class AddressRepositoryJpaImpl implements AddressReadRepository, AddressWriteRepository {

    private final EntityManager entityManager;

    public AddressRepositoryJpaImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<AddressJpaEntity> findAll() {
        Query query = entityManager.createQuery("select a from AddressJpaEntity a");
        return query.getResultList();
    }


    @Override
    public AddressJpaEntity save(AddressJpaEntity entity) {

        entityManager.persist(entity);
        return entity;
    }
}
