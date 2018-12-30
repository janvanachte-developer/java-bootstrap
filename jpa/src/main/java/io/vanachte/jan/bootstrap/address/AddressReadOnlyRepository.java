package io.vanachte.jan.bootstrap.address;

import java.util.List;

public interface AddressReadOnlyRepository {
    AddressJpaEntity findByIdentifier(String junit5);

    List<AddressJpaEntity> findAll();
}
