package io.vanachte.jan.bootstrap.person;

import io.vanachte.jan.bootstrap.jpa.ReadOnlyRepositorySpringDataImpl;

public interface PersonReadOnlyRepositorySpringDataImpl extends ReadOnlyRepositorySpringDataImpl<PersonJpaEntity, Long> {
}
