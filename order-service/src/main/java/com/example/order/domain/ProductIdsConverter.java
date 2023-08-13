package com.example.order.domain;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductIdsConverter implements AttributeConverter<Set<Long>, String> {
    @Override
    public String convertToDatabaseColumn(Set<Long> attribute) {
        if (attribute != null && attribute.size() > 0) {
            return attribute.stream().map(String::valueOf).collect(Collectors.joining(";"));
        }
        return null;
    }

    @Override
    public Set<Long> convertToEntityAttribute(String dbData) {
        if (dbData != null && dbData.length() > 0) {
            return Arrays.stream(dbData.split(";")).map(Long::parseLong).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }
}
