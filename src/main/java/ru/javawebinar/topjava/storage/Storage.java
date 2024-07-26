package ru.javawebinar.topjava.storage;

import java.util.List;

public interface Storage<T> {
    void add(T t);

    List<T> getAll();

    T get(int id);

    void update(int id, T t);

    void remove(int id);
}
