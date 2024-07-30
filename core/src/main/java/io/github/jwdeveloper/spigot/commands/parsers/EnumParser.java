package io.github.jwdeveloper.spigot.commands.parsers;

import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentProperties;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentType;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentParseEvent;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentSuggestionEvent;

import java.util.Arrays;
import java.util.List;

public class EnumParser implements ArgumentType {

    @Override
    public String name() {
        return "Enum";
    }

    @Override
    public ActionResult<Object> onParse(ArgumentParseEvent event) {
        try {
            var iterator = event.iterator();
            var type = getEnumType(event.argument());
            if (type.isFailed()) {
                return type.cast();
            }
            var entityName = iterator.next();
            var value = Enum.valueOf(type.getValue(), entityName);
            return ActionResult.success(value);
        } catch (Exception e) {

            return ActionResult.failed(e.getMessage());
        }
    }

    @Override
    public ActionResult<List<String>> onSuggestion(ArgumentSuggestionEvent event) {
        var enumResult = getEnumType(event.argument());
        if (enumResult.isFailed()) {
            return enumResult.cast();
        }
        var _enum = enumResult.getValue();
        var value = event.rawValue();
        var enums = Arrays.stream(_enum.getEnumConstants())
                .map(Enum::name)
                .filter(e -> e.contains(value))
                .toList();
        return ActionResult.success(enums);
    }

    private ActionResult<Class<? extends Enum>> getEnumType(ArgumentProperties argument) {
        if (!argument.properties().containsKey("enum-type")) {
            return ActionResult.failed("enum-type property is empty!");
        }
        var type = (Class<? extends Enum>) argument.properties().get("enum-type");
        return ActionResult.success(type);
    }
}
