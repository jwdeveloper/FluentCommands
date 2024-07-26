package io.github.jwdeveloper.spigot.commands.parsers;

import io.github.jwdeveloper.spigot.commands.ArgumentType;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentProperties;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Set;


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
    public ActionResult<Object> onParse(ArgumentEvent event) {
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
    public ActionResult<List<String>> suggest(ArgumentEvent event) {
        var value = event.iterator().current();
        var enums = Arrays.stream(enumType.getEnumConstants())
                .map(Enum::name)
                .filter(e -> e.contains(value))
                .toList();
        return ActionResult.success(enums);
    }

}