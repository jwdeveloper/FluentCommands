package io.github.jwdeveloper.spigot.commands.data.expressions;

import io.github.jwdeveloper.spigot.commands.data.argumetns.ArgumentProperties;
import lombok.Data;


@Data
public class ArgumentNode {
    private ArgumentProperties argument;
    private String rawValue;
    private Object value;
}