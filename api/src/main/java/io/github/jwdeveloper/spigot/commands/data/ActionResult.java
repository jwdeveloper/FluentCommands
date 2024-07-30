package io.github.jwdeveloper.spigot.commands.data;

import lombok.Data;

import java.util.Optional;
import java.util.function.Function;

@Data
public class ActionResult<T> {
    private boolean success = true;
    private T value;
    private String message;

    public ActionResult() {
    }

    protected ActionResult(T object) {
        this.value = object;
    }

    protected ActionResult(T object, boolean success) {
        this(object);
        this.success = success;
    }

    protected ActionResult(T object, boolean status, String message) {
        this(object, status);
        this.message = message;
    }

    public T getOrThrow(String message) {
        if (value == null) {
            throw new RuntimeException(message);
        }
        return value;
    }

    public boolean isFailed() {
        return !isSuccess();
    }

    public boolean hasMessage() {
        return message != null;
    }

    public boolean hasObject() {
        return value != null;
    }

    public static <T> ActionResult<T> success() {
        return new ActionResult<>(null, true);
    }

    public <Output> ActionResult<Output> cast(Output output) {
        return new ActionResult<>(output, this.isSuccess(), this.getMessage());
    }

    public <Output> ActionResult<Output> cast() {
        return new ActionResult<>(null, this.isSuccess(), this.getMessage());
    }

    public <Output> ActionResult<Output> cast(Function<T, Output> converter) {
        var value = this.value != null ? converter.apply(this.value) : null;
        return new ActionResult<>(value, this.isSuccess(), this.getMessage());
    }


    public static <Input, Output> ActionResult<Output> cast(ActionResult<Input> action, Output output) {
        return new ActionResult<>(output, action.isSuccess(), action.getMessage());
    }

    public static <T> ActionResult<T> success(T payload) {
        return new ActionResult<>(payload, true);
    }

    public static <T> ActionResult<T> success(T payload, String message) {
        return new ActionResult<>(payload, true, message);
    }

    public static <T> ActionResult<T> failed() {
        return new ActionResult<>(null, false);
    }

    public static <T> ActionResult<T> failed(String message) {
        return new ActionResult<>(null, false, message);
    }

    public static <T> ActionResult<T> failed(T target, String message) {
        return new ActionResult<>(target, false, message);
    }


    public static <T> ActionResult<T> fromOptional(Optional<T> optional) {
        return optional.map(ActionResult::success).orElseGet(ActionResult::failed);
    }

}
