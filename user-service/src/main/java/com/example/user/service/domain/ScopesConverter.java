package com.example.user.service.domain;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ScopesConverter implements AttributeConverter<Set<String>, String> {
    @Override
    public String convertToDatabaseColumn(Set<String> attribute) {
        if (attribute != null && attribute.size() > 0) {
            return String.join(";", attribute);
        }
        return null;
    }

    @Override
    public Set<String> convertToEntityAttribute(String dbData) {
        if (dbData != null && dbData.length() > 0) {
            return Arrays.stream(dbData.split(";")).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }
}
