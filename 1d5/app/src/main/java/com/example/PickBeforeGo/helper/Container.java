package com.example.PickBeforeGo.helper;

public class Container<T> {
    T value;
    public Container(T v) { this.value = v; }
    public void set(T v) { this.value = v; }
    public T get() { return this.value; }
}
