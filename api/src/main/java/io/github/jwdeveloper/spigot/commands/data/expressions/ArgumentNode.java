package io.github.jwdeveloper.spigot.commands.data.expressions;

import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentProperties;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ArgumentNode {
    private ArgumentProperties argument;
    private Object value;
}