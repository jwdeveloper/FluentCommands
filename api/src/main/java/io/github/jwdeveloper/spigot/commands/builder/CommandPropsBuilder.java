package io.github.jwdeveloper.spigot.commands.builder;

import io.github.jwdeveloper.spigot.commands.data.CommandProperties;

import java.util.Arrays;
import java.util.function.Consumer;

public interface CommandPropsBuilder<T> {

    CommandProperties properties();

    T withProperties(Consumer<CommandProperties> properties);

    T withProperties(CommandProperties properties);

    T self();

    default T withUsageMessage(String usageMessage) {
        properties().usageMessage(usageMessage);
        return self();
    }

    default T withAliases(String... aliases) {
        properties().aliases(aliases);
        return self();
    }

    default T withLabel(String label) {
        properties().label(label);
        return self();
    }

    default T withDebug(boolean isDebug) {
        properties().debug(isDebug);
        return self();
    }

    default T withShortDescription(String shortDescription) {
        properties().shortDescription(shortDescription);
        return self();
    }

    default T withDescription(String description) {
        properties().description(description);
        return self();
    }

    default T withName(String name) {
        properties().name(name);
        return self();
    }

    default T withPermissions(String... permissions) {
        properties().permissions(Arrays.stream(permissions).toList());
        return self();
    }

    default T withHideFromTab(boolean isHide) {
        properties().hideFromTabDisplay(isHide);
        return self();
    }


    default T withProperty(String name, Object value) {
        properties().customProperties().put(name, value);
        return self();
    }

}
