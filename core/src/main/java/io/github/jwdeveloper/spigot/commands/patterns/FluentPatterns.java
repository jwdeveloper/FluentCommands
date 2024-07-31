package io.github.jwdeveloper.spigot.commands.patterns;

import io.github.jwdeveloper.spigot.commands.builder.arguments.ArgumentBuilder;

import java.util.HashMap;
import java.util.Map;

public class FluentPatterns implements Patterns {

    private final Map<String, PatterMapper> propertyMappers = new HashMap<>();
    private final Map<String, PatterMapper> symbolsMappers = new HashMap<>();

    @Override
    public Patterns mapProperty(String property, PatterMapper mapper) {
        propertyMappers.put(property, mapper);
        return this;
    }


    @Override
    public boolean applyMapping(String key, String value, ArgumentBuilder argumentBuilder) {
        if (!propertyMappers.containsKey(key)) {
            return false;
        }
        var mapper = propertyMappers.get(key);
        mapper.map(value, argumentBuilder);
        return true;
    }
}
