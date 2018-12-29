package io.vanachte.jan.bootstrap.jpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true) // since JPA 2.1
public class BooleanToVarcharAttributeConverter implements AttributeConverter<Boolean, Character> {

    @Override
    public Character convertToDatabaseColumn(Boolean attribute) {
        return attribute != null && attribute ? 'Y' : 'N';
    }

    @Override
    public Boolean convertToEntityAttribute(Character column) {
        return column != null && column.equals('Y');
    }
}
