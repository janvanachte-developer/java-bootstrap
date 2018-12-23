package io.vanachte.jan.bootstrap.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.internal.util.Strings;
import org.modelmapper.spi.PropertyType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration()
//                .setMatchingStrategy(MatchingStrategies.STRICT)
//                .setDestinationNamingConvention((propertyName, propertyType) -> PropertyType.METHOD.equals(propertyType))
//                .setDestinationNameTransformer((name, nameableType) -> Strings.decapitalize(name));

        return modelMapper;
    }
 }
