package io.github.jwdeveloper.spigot.commands.patterns.mappers;

import io.github.jwdeveloper.dependance.api.DependanceContainer;
import io.github.jwdeveloper.spigot.commands.builder.arguments.ArgumentBuilder;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.Ref;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentParseEvent;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentSuggestionEvent;
import io.github.jwdeveloper.spigot.commands.patterns.PatterMapper;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

public class SuggestionsMapper implements PatterMapper {

    private final DependanceContainer container;

    public SuggestionsMapper(DependanceContainer container) {
        this.container = container;
    }

    @Override
    public void map(String value, ArgumentBuilder builder, Object object) throws Exception {
        var methodName = value;
        if (methodName.endsWith("()"))
            methodName = methodName.replace("()", "");


        var type = object.getClass();
        var method = type.getDeclaredMethod(methodName);
        method.setAccessible(true);

        final boolean isStatic = Modifier.isStatic(method.getModifiers());
        var reference = new Ref<ArgumentSuggestionEvent>();
        var methodContainer = container.createChildContainer()
                .registerSingleton(ArgumentSuggestionEvent.class, container1 -> reference.getValue())
                .build();

        builder.withSuggestions(event ->
        {
            reference.setValue(event);
            var params = methodContainer.resolveParameters(method);
            try {
                Object result = null;
                if (isStatic)
                    result = method.invoke(null, params);
                else
                    result = method.invoke(object, params);

                if (result instanceof String[] args) {
                    return ActionResult.success(Arrays.stream(args).toList());
                }

                var res = new ArrayList<String>();
                res.add(result.toString());
                return ActionResult.success(res);
            } catch (Exception e) {
                return ActionResult.failed(e.getMessage());
            }
        });

    }
}
