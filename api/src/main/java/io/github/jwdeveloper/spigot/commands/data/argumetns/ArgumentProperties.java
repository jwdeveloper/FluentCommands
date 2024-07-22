package io.github.jwdeveloper.spigot.commands.data.argumetns;

import lombok.Data;
import lombok.experimental.Accessors;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(fluent = true)
public class ArgumentProperties {
    private String name;
    private ArgumentType type = ArgumentType.TEXT;
    private ArgumentDisplay displayMode = ArgumentDisplay.TYPE;
    private List<ArgumentValidator> validators = new ArrayList<>();
    private boolean required;
    private Object defaultValue;
    private String description;
}
