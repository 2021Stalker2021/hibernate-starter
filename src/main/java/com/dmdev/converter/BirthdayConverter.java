package com.dmdev.converter;

import com.dmdev.entity.Birthday;
import jakarta.persistence.AttributeConverter;

import java.sql.Date;
import java.util.Optional;

public class BirthdayConverter implements AttributeConverter<Birthday, Date> {
    @Override
    public Date convertToDatabaseColumn(Birthday attribute) {
        return Optional.ofNullable(attribute)
                .map(Birthday::birthDate)
                .map(Date::valueOf) // преобразовываем localDate в Date
                .orElse(null);
    }

    @Override
    public Birthday convertToEntityAttribute(Date dbData) {
        return Optional.ofNullable(dbData)
                .map(Date::toLocalDate)
                .map(Birthday::new) // помещаем наш localDate в клсс Birthday
                .orElse(null);
    }
}
