package com.yosrabroug.banking.services;

import java.util.List;

public interface AbstractServices<T> {

    Integer save(T dto);
    List<T> findAll();

    T findById(Integer id);

    void delete(Integer id);


}
