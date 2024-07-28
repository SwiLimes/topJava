package ru.javawebinar.topjava.storage;

import java.util.List;

public interface Storage<T> {
    T add(T t);

    List<T> getAll();

    T get(int id);

    T update(T t);

    void remove(int id);
}
