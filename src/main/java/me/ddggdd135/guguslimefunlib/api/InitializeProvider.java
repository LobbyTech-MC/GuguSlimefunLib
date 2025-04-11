package me.ddggdd135.guguslimefunlib.api;

import java.util.function.Supplier;

public class InitializeProvider<T> {
    T value;

    public InitializeProvider(Supplier<T> provider) {
        value = provider.get();
    }

    public T v() {
        return value;
    }
}
