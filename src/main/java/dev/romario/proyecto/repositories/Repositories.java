package dev.romario.proyecto.repositories;

import java.util.List;

public interface Repositories<T extends Identifiable> {
    void create(T entity);
    List<T> findAll();
    T findById(String id);
    void update(T entity);
    void deleteById(String id);
}
