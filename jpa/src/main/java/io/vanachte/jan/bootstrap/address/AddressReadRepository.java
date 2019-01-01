package io.vanachte.jan.bootstrap.address;

import java.util.List;

public interface AddressReadRepository {

    List<AddressJpaEntity> findAll();
}
