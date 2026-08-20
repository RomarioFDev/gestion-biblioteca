package dev.romario.proyecto.repositories;

import java.util.List;


public class RepositoryMemory<T extends Identifiable> implements Repositories<T> {
    private final List<T> entityList;

    public RepositoryMemory(List<T> entityList) {
        this.entityList = entityList;
    }

    @Override
    public void create(T entity) {
        this.entityList.add(entity);
    }

    @Override
    public List<T> findAll() {
        return this.entityList;
    }

    @Override
    public T findById(String id) {
        for (T data : this.entityList){
            if (data.getId().equals(id)){
                return data;
            }
        }
        return null;
    }

    @Override
    public void update(T entity) {
        for (int i = 0; i < this.entityList.size(); i++) {
            if (this.entityList.get(i).getId().equals(entity.getId())){
                this.entityList.set(i, entity);
                return;
            }
        }
    }

    @Override
    public void deleteById(String id) {
        this.entityList.removeIf(data -> data.getId().equals(id));
    }


}
