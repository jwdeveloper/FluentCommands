package io.github.jwdeveloper.spigot.commands.templates;

import io.github.jwdeveloper.dependance.api.DependanceContainer;
import io.github.jwdeveloper.dependance.implementation.DependanceContainerBuilder;
import io.github.jwdeveloper.dependance.injector.api.events.events.OnInjectionEvent;
import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.data.events.CommandEvent;
import io.github.jwdeveloper.spigot.commands.data.expressions.CommandExpression;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;

public class TemplateMethodHandler {

    private final Object target;
    private final Method method;
    private final DependanceContainer container;
    private CommandEvent commandEvent;
    private int argumentIndex;

    public TemplateMethodHandler(Object target, Method method, DependanceContainerBuilder containerBuilder) {
        this.container = createContainer(containerBuilder);
        this.target = target;
        this.method = method;
    }

    public void invokeEvent(Command command, CommandEvent event) {
        argumentIndex = 0;
        this.commandEvent = event;
        try {
            var params = container.resolveParameters(method);
            method.invoke(target,params);
        } catch (Exception e) {
            throw new RuntimeException("Error while invoking command: " + command.name(), e);
        }
    }


    private DependanceContainer createContainer(DependanceContainerBuilder builder) {
        builder.registerSingleton(CommandEvent.class, container1 -> commandEvent);
        builder.registerSingleton(Command.class, container1 -> commandEvent.command());
        builder.registerSingleton(CommandSender.class, container1 -> commandEvent.sender());
        builder.registerSingleton(CommandExpression.class, container1 -> commandEvent.expression());
        builder.registerSingleton(String[].class, container1 -> commandEvent.arguments());
        builder.configure(configuration ->
        {
            configuration.onInjection(this::handleArguments);
            configuration.onInjection(this::handleSender);
        });
        return builder.build();
    }

    private Object handleSender(OnInjectionEvent event)
    {
        if(event.input().equals(CommandSender.class))
        {
            return event.output();
        }
        if (CommandSender.class.isAssignableFrom(event.input())) {
            return event.container().find(CommandSender.class);
        }
        return event.output();
    }

    private Object handleArguments(OnInjectionEvent event) {

        if (event.output() != null) {
            return event.output();
        }

        if (argumentIndex > commandEvent.argumentCount()) {
            throw new RuntimeException("Command arguments out of the range");
        }

        return commandEvent.getArgument(argumentIndex++, Object.class);
    }
}
