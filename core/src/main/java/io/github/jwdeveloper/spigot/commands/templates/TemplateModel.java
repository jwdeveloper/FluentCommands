package io.github.jwdeveloper.spigot.commands.templates;

import io.github.jwdeveloper.spigot.commands.annotations.FCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.List;

@AllArgsConstructor
@Getter
public class TemplateModel {
    private Object target;

    private Class<?> targetType;

    private FCommand commandAnnotation;

    private List<Method> commandMethods;

    private List<Method> commandBuilderMethods;
}
