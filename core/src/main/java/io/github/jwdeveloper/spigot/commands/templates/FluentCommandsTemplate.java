package io.github.jwdeveloper.spigot.commands.templates;

import io.github.jwdeveloper.dependance.api.DependanceContainer;
import io.github.jwdeveloper.spigot.commands.CommandsTemplate;
import io.github.jwdeveloper.spigot.commands.annotations.FCommand;
import io.github.jwdeveloper.spigot.commands.annotations.FCommandBuilder;
import io.github.jwdeveloper.spigot.commands.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.argumetns.ArgumentType;
import org.bukkit.command.CommandSender;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;

public class FluentCommandsTemplate implements CommandsTemplate {

    private final PatternService patternService;
    private final DependanceContainer container;

    public FluentCommandsTemplate(PatternService patternService, DependanceContainer container) {
        this.patternService = patternService;
        this.container = container;
    }

    @Override
    public CommandBuilder templateToBuilder(Object template, CommandBuilder builder) {
        var model = getModel(template);
        var commandA = model.getCommandAnnotation();

        var patternResult = patternService.resolvePattern(commandA.pattern());
        if (patternResult.isFailed()) {
            return builder;
        }
        var patternData = patternResult.getValue();
        builder.withName(patternData.commandName());
        for (var argument : patternData.arguments()) {
            builder.argument(argument.name())
                    .withRequired(argument.required())
                    .withType(ArgumentType.valueOf(argument.type().toUpperCase()));
        }

        for (var method : model.getCommandMethods()) {
            method.setAccessible(true);
            handleSingleMethod(template, method, builder);
        }

        for (var method : model.getCommandBuilderMethods()) {
            method.setAccessible(true);
            handleBuilderMethod(template, method, builder);
        }

        return builder;
    }

    private void handleBuilderMethod(Object target, Method method, CommandBuilder builder) {
        var targetCommand = "";
        if (method.isAnnotationPresent(FCommandBuilder.class)) {
            var annotation = method.getAnnotation(FCommandBuilder.class);
            targetCommand = annotation.name();
        }

        if (!targetCommand.isEmpty()) {
            builder = builder.subCommand(targetCommand);
        }
        try {
            var methodContainer = container.createChildContainer()
                    .registerSingleton(CommandBuilder.class, builder)
                    .build();
            var params = methodContainer.resolveParameters(method);
            method.invoke(target, params);
        } catch (Exception e) {
            throw new RuntimeException("Error whiile invoking builder", e);
        }
    }

    private void handleSingleMethod(Object target, Method method, CommandBuilder builder) {

        var targetCommand = "";
        if (method.isAnnotationPresent(FCommand.class)) {
            var annotation = method.getAnnotation(FCommand.class);
            targetCommand = annotation.name();
        }

        if (!targetCommand.isEmpty()) {
            builder = builder.subCommand(targetCommand);
        }


        var senderTypeOptional = Arrays.stream(method.getParameterTypes())
                .filter(e -> e.isAssignableFrom(CommandSender.class))
                .findFirst();

        var senderType = senderTypeOptional.orElse(CommandSender.class);
        var handler = new TemplateMethodHandler(target, method, container.createChildContainer());
        builder.onEvent(senderType, handler::invokeEvent);
    }


    private TemplateModel getModel(Object template) {
        var commandAnnotation = getAnnotation(template.getClass(), FCommand.class);
        var commandMethods = Arrays.stream(template.getClass().getMethods())
                .filter(e -> e.isAnnotationPresent(FCommand.class))
                .toList();
        var builderMethods = Arrays.stream(template.getClass().getMethods())
                .filter(e -> e.isAnnotationPresent(FCommandBuilder.class))
                .toList();

        return new TemplateModel(template, template.getClass(),
                commandAnnotation.getValue(),
                commandMethods,
                builderMethods);
    }


    private <T extends Annotation> ActionResult<T> getAnnotation(AnnotatedElement target, Class<T> annotationType) {
        if (target.isAnnotationPresent(annotationType)) {
            return ActionResult.success(target.getAnnotation(annotationType));
        }
        return ActionResult.failed();
    }


}
