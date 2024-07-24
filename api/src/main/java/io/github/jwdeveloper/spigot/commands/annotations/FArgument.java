package io.github.jwdeveloper.spigot.commands.annotations;

import io.github.jwdeveloper.spigot.commands.data.argumetns.ArgumentType;

public @interface FArgument {

    String name();

    ArgumentType type() default ArgumentType.TEXT;

    String suggestions() default "";
}
