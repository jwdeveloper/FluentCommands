package io.github.jwdeveloper.spigot.commands.argumetns;

import io.github.jwdeveloper.spigot.commands.data.DisplayAttribute;
import io.github.jwdeveloper.spigot.commands.functions.ArgumentParser;
import io.github.jwdeveloper.spigot.commands.functions.ArgumentSuggestions;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@Accessors(fluent = true)
public class ArgumentProperties {
    private int index = -1;
    private String name = "";
    private String type = "";
    private String defaultValue = "";
    private String description = "";
    private boolean required;

    private ArgumentParser parser;
    private ArgumentSuggestions suggestion;
    private Set<DisplayAttribute> displayAttributes = new HashSet<>();
    private Map<String, Object> properties = new HashMap<>();

    public boolean hasDisplayAttribute(DisplayAttribute attribute) {
        return displayAttributes.contains(attribute);
    }
}
