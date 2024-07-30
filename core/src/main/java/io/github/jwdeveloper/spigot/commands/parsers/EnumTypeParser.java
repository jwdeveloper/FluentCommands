package io.github.jwdeveloper.spigot.commands.parsers;

import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentType;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentParseEvent;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentSuggestionEvent;

import java.util.Arrays;
import java.util.List;


public class EnumTypeParser implements ArgumentType {


    private final Class<? extends Enum> enumType;
    private final String name;

    public EnumTypeParser(Class<? extends Enum> enumType, String name) {
        this.enumType = enumType;
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public ActionResult<Object> onParse(ArgumentParseEvent event) {
        try {
            var iterator = event.iterator();
            var enumName = iterator.next().toUpperCase();
            var value = Enum.valueOf(enumType, enumName);
            return ActionResult.success(value);
        } catch (Exception e) {

            return ActionResult.failed(e.getMessage());
        }
    }

    @Override
    public ActionResult<List<String>> onSuggestion(ArgumentSuggestionEvent event) {
        var value = event.rawValue();
        var enums = Arrays.stream(enumType.getEnumConstants())
                .map(Enum::name)
                .filter(e -> e.contains(value))
                .toList();
        return ActionResult.success(enums);
    }



}