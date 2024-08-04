package io.github.jwdeveloper.spigot.commands.templates;

import io.github.jwdeveloper.dependance.api.DependanceContainer;
import io.github.jwdeveloper.spigot.commands.CommandsRegistry;
import io.github.jwdeveloper.spigot.commands.TemplateCommand;
import io.github.jwdeveloper.spigot.commands.annotations.FCommand;
import io.github.jwdeveloper.spigot.commands.annotations.FCommandBuilder;
import io.github.jwdeveloper.spigot.commands.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.patterns.PatternService;
import org.bukkit.command.CommandSender;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FluentTemplateCommand implements TemplateCommand {

    private final DependanceContainer container;
    private final PatternService patternService;
    private final CommandsRegistry commandsRegistry;
    private final Map<String, CommandBuilder> builders;

    public FluentTemplateCommand(PatternService patternService,
                                 DependanceContainer container,
                                 CommandsRegistry commandsRegistry) {
        this.container = container;
        this.patternService = patternService;
        this.commandsRegistry = commandsRegistry;
        this.builders = new HashMap<>();
    }

    @Override
    public CommandBuilder templateToBuilder(Object template, CommandBuilder builder) {
        var model = getModel(template);
        var templateType = template.getClass();
        if (templateType.isAnnotationPresent(FCommand.class)) {
            decorateBuilder(template, templateType.getAnnotation(FCommand.class), builder);
        }

        for (var method : model.getCommandMethods()) {
            method.setAccessible(true);
            handleCommandMethod(template, method, builder);
        }

        for (var method : model.getCommandBuilderMethods()) {
            method.setAccessible(true);
            handleBuilderMethod(template, method, builder);
        }

        return builder;
    }

    private CommandBuilder decorateBuilder(Object template, FCommand fCommand, CommandBuilder builder) {
        var pattern = fCommand.pattern();
        var name = fCommand.name();


        if (!pattern.isEmpty() && !name.isEmpty()) {
            throw new RuntimeException("You need to choose Pattern or Name, they can not be use both");
        }

        if (!pattern.isEmpty()) {
            var patternResult = patternService.getCommandBuilder(template, pattern, builder);
            if (patternResult.isFailed()) {
                throw new RuntimeException(patternResult.getMessage());
            }
            builder = patternResult.getValue();
        }

        if (!fCommand.description().isEmpty())
            builder.withDescription(fCommand.description());

        if (!fCommand.shortDescription().isEmpty())
            builder.withShortDescription(fCommand.shortDescription());

        if (!fCommand.permission().isEmpty())
            builder.withPermission(fCommand.permission());

        if (!fCommand.label().isEmpty())
            builder.withLabel(fCommand.label());

        if (!fCommand.usageMessage().isEmpty())
            builder.withUsageMessage(fCommand.usageMessage());

        builder.withAliases(fCommand.aliases());
        builder.withHideFromCommands(fCommand.hideFromCommands());

        return builder;
    }


    private void handleBuilderMethod(Object target, Method method, CommandBuilder builder) {
        var targetCommand = "";
        if (method.isAnnotationPresent(FCommandBuilder.class)) {
            var annotation = method.getAnnotation(FCommandBuilder.class);
            targetCommand = annotation.name();
        }
        builder = getBuilder(targetCommand, builder);
        try {
            var methodContainer = container.createChildContainer()
                    .registerSingleton(CommandBuilder.class, builder)
                    .build();
            var params = methodContainer.resolveParameters(method);
            method.invoke(target, params);
        } catch (Exception e) {
            throw new RuntimeException("Error while invoking builder", e);
        }
    }

    private CommandBuilder getBuilder(String targetCommand, CommandBuilder mainBuilder) {
        if (targetCommand.isEmpty()) {
            return mainBuilder;
        }
        if (targetCommand.equalsIgnoreCase(mainBuilder.properties().name()))
            return mainBuilder;

        return mainBuilder.subCommand(targetCommand);
    }


    private void handleCommandMethod(Object target, Method method, CommandBuilder builder) {
        if (method.isAnnotationPresent(FCommand.class)) {
            var annotation = method.getAnnotation(FCommand.class);
            builder = decorateBuilder(target, annotation, builder);
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
