package io.vanachte.jan.bootstrap.person;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

import javax.inject.Named;

@Named
public class PersonMapperImpl implements PersonMapper {


    private static ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
        Configuration builderConfiguration = modelMapper.getConfiguration().copy();
        builderConfiguration.setDestinationNamingConvention((name, type) -> true);
        builderConfiguration.setDestinationNameTransformer((name, type) -> name);
        modelMapper.createTypeMap(PersonType.class, Person.PersonBuilder.class, builderConfiguration);
    }

    @Override
    public Person map(PersonType personType, Class<? extends Person> clazz) {

        return Person.builder()
                .identifier(personType.getIdentifier())
                .firstName(personType.getFirstName())
                .lastName(personType.getLastName())
                .build();

//        return modelMapper.map(personType, clazz);
    }
}
