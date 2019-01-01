package io.vanachte.jan.bootstrap.address;

import io.vanachte.jan.bootstrap.country.Country;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class AddressServiceJpaRepositoryImpl implements AddressService {

    private final AddressReadRepository readRepository;
    private final AddressWriteRepository writeRepository;

    public AddressServiceJpaRepositoryImpl(AddressReadRepository readRepository, AddressWriteRepository writeRepository) {
        this.readRepository = readRepository;
        this.writeRepository = writeRepository;
    }

    @Transactional
    @Override
    public Address create(Country country, String... lines){

        AddressJpaEntity entity = new AddressJpaEntity();

        for ( String line : lines) {
            AddressLineJpaEntity lineEntity = new AddressLineJpaEntity(line);
            entity.add(lineEntity);
        }

        if ( country != null ) {
            entity.setCountry(country.getCode());
        }

        return map(writeRepository.save(entity));
    }

    @Override
    public List<Address> findAll() {

        List<AddressJpaEntity> entities = readRepository.findAll();

        return entities.stream()
                .map(entity -> map(entity))
                .collect(Collectors.toList());
    }

    private Address map(AddressJpaEntity entity) {

        return Address.builder()
                .lines(new ArrayList(entity.getLines()))
                .country(Country.builder().code(entity.getCountry()).build())
                .build();
    }
}
