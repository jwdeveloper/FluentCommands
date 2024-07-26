package io.github.jwdeveloper.spigot.commands.builder;

import io.github.jwdeveloper.spigot.commands.builder.arguments.ArgumentBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;

import java.util.function.Consumer;

public interface CommandArgumentsBuilder<T> {

    ArgumentBuilder argument(String name);

    T addArgument(String name, Consumer<ArgumentBuilder> action);

    default T addArgument(String name) {
        return addArgument(name, "Text", (x) -> {
        });
    }

    default T addArgument(String name, String argumentType) {
        return addArgument(name, argumentType, (x) -> {
        });
    }


    default T addArgument(String name, String argumentType, Consumer<ArgumentBuilder> action) {
        return addArgument(name, argumentBuilder ->
        {
            argumentBuilder.withType(argumentType);
            action.accept(argumentBuilder);
        });
    }

    default T addNumberArgument(String argumentName) {
        return addArgument(argumentName, "Number");
    }

    default T addNumberArgument(String argumentName, Consumer<ArgumentBuilder> action) {
        return addArgument(argumentName, "Number", action);
    }

    default T addTextArgument(String argumentName) {
        return addArgument(argumentName, "Text");
    }

    default T addTextArgument(String argumentName, Consumer<ArgumentBuilder> action) {
        return addArgument(argumentName, "Text", action);
    }


    default T addEnumArgument(Class<? extends Enum> type, Consumer<ArgumentBuilder> action) {
        return addArgument(type.getSimpleName(), "Enum", argumentBuilder ->
        {
            argumentBuilder.withProperty(argumentProperties ->
            {
                argumentProperties.properties().put("enum-type", type);
            });
            action.accept(argumentBuilder);
        });
    }

    default T addEnumArgument(Class<? extends Enum> type) {
        return addEnumArgument(type, x -> {
        });
    }

    default T addBoolArgument(String argumentName) {
        return addArgument(argumentName, "Bool");
    }

    default T addBoolArgument(String argumentName, Consumer<ArgumentBuilder> action) {
        return addArgument(argumentName, "Bool", action);
    }

    default T addPlayerArgument(String argumentName) {
        return addArgument(argumentName, "Player");
    }

    default T addPlayerArgument(String argumentName, Consumer<ArgumentBuilder> action) {
        return addArgument(argumentName, "Player", action);
    }

    default T addLocationArgument() {
        return addArgument("location", "Location");
    }

    default T addLocationArgument(String name) {
        return addArgument(name, "Location");
    }

    default T addEntityArgument() {
        return addEnumArgument(EntityType.class);
    }

    default T addSoundArgument() {
        return addEnumArgument(Sound.class);
    }

    default T addColorArgument() {
        return addEnumArgument(ChatColor.class);
    }

    default T addParticleArgument() {
        return addEnumArgument(Particle.class);
    }
}
