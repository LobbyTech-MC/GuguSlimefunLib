package me.ddggdd135.guguslimefunlib.script;

@FunctionalInterface
public interface CiFunction<A, B, C, R> {
    R apply(A a, B b, C c);
}
