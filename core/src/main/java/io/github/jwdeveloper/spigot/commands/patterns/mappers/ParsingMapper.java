package io.github.jwdeveloper.spigot.commands.patterns.mappers;

import io.github.jwdeveloper.dependance.api.DependanceContainer;
import io.github.jwdeveloper.spigot.commands.builder.arguments.ArgumentBuilder;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.Ref;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentParseEvent;
import io.github.jwdeveloper.spigot.commands.patterns.PatterMapper;
import org.checkerframework.checker.units.qual.A;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.reflect.Modifier;

public class ParsingMapper implements PatterMapper {

    private final DependanceContainer container;

    public ParsingMapper(DependanceContainer container) {
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
        var reference = new Ref<>();
        var methodContainer = container.createChildContainer()
                .registerSingleton(ArgumentParseEvent.class, container1 -> reference.getValue())
                .build();

        builder.withParser(event ->
        {
            reference.setValue(event);
            var params = methodContainer.resolveParameters(method);
            try {
                Object result = null;
                if (isStatic)
                    result = method.invoke(null, params);
                else
                    result = method.invoke(object, params);
                return ActionResult.success(result);
            } catch (Exception e) {
                return ActionResult.failed(e.getMessage());
            }
        });

    }

}
