package io.vanachte.jan.bootstrap.address;

public interface AddressWriteRepository {
    AddressJpaEntity save(AddressJpaEntity entity);
}
