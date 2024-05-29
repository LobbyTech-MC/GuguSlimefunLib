package me.ddggdd135.guguslimefunlib.script;

@FunctionalInterface
public interface CiConsumer<A, B, C> {
    void apply(A a, B b, C c);
}
