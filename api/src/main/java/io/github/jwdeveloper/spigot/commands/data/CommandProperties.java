package io.github.jwdeveloper.spigot.commands.data;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Accessors(fluent = true)
public class CommandProperties {
    private Map<String, Object> customProperties = new HashMap<>();

    private String name;

    private List<SenderType> excludedSenders = new ArrayList<>();

    private String shortDescription = "";

    private String description = "";

    private String usageMessage = "";

    private String[] aliases;

    private String permissionMessage = "";

    private String label = "";

    private String permission = "";

    private boolean hideFromCommands = false;

    private boolean hideFromDocumentation = false;

    private boolean active = true;

    public Object get(String propertyName) {
        return customProperties.get(propertyName);
    }

    public <T> T get(String propertyName, Class<T> returnType) {
        return (T) get(propertyName);
    }


}
