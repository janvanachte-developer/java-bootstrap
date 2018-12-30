package io.vanachte.jan.bootstrap.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PersonRepositorySpringDataImpl extends JpaRepository<PersonJpaEntity, Long> {
}
