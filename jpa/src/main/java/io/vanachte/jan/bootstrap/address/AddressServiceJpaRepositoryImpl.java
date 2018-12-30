package io.vanachte.jan.bootstrap.address;

import io.vanachte.jan.bootstrap.country.Country;

import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class AddressServiceJpaRepositoryImpl implements AddressService {

    private final AddressReadOnlyRepository readOnlyRepository;

    public AddressServiceJpaRepositoryImpl(AddressReadOnlyRepository readOnlyRepository) {
        this.readOnlyRepository = readOnlyRepository;
    }

    @Override
    public List<Address> findAll() {

        List<AddressJpaEntity> entities = readOnlyRepository.findAll();

        return entities.stream()
                .map(entity -> map(entity))
                .collect(Collectors.toList());
    }

    private Address map(AddressJpaEntity entity) {

        return Address.builder().country(Country.builder().code("BE").build()).build();
    }
}
