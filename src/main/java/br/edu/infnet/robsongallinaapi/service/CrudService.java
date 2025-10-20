package br.edu.infnet.robsongallinaapi.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {
    T create(T entity);
    Optional<T> findById(ID id);
    void delete(ID id);
    List<T> findAll();
    T update(ID id, T entity);
}