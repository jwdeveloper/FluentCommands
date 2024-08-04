package io.github.jwdeveloper.spigot.commands.data;

import lombok.Data;

@Data
public class Ref<T> {

    T value;

    public Ref(T value) {
        this.value = value;
    }

    public Ref() {
        value = null;
    }



}
