package io.vanachte.jan.bootstrap.address;

import org.springframework.context.annotation.Primary;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Named
@Primary
public class AddressReadOnlyRepositoryJpaImpl implements AddressReadOnlyRepository {

    private final EntityManager entityManager;

    public AddressReadOnlyRepositoryJpaImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public AddressJpaEntity findByIdentifier(String identifier) {
        Query query = entityManager.createQuery("select a from AddressJpaEntity a where a.identifier = :identifier");
        query.setParameter("identifier", identifier);
        return (AddressJpaEntity) query.getSingleResult();
    }

    @Override
    public List<AddressJpaEntity> findAll() {
        Query query = entityManager.createQuery("select a from AddressJpaEntity a");
        return query.getResultList();
    }
}
