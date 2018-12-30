package io.vanachte.jan.bootstrap.address;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepositorySpringDataImpl extends AddressRepository, JpaRepository<AddressJpaEntity, Long> {
}
