package io.github.jwdeveloper.spigot.commands.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FCommand {
    String pattern() default "";

    String name() default "";
}