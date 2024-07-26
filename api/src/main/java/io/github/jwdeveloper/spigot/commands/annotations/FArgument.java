package io.github.jwdeveloper.spigot.commands.annotations;

public @interface FArgument {

    String name();

    String type() default "Text";

    String suggestions() default "";
}
