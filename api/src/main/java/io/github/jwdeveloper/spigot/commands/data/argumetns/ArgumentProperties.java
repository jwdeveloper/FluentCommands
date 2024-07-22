package io.github.jwdeveloper.spigot.commands.data.argumetns;

import io.github.jwdeveloper.spigot.commands.data.argumetns.parsing.ArgumentParser;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Data
@Accessors(fluent = true)
public class ArgumentProperties {
    private int index = -1;
    private String name = "";
    private ArgumentType type = ArgumentType.TEXT;
    private ArgumentDisplay displayMode = ArgumentDisplay.TYPE;
    private List<ArgumentParser> parsers = new ArrayList<>();
    private boolean required;
    private Object defaultValue;
    private String description;
    private Function<String, List<String>> suggestions;
}
