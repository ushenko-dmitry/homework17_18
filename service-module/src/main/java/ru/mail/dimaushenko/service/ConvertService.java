package ru.mail.dimaushenko.service;

import java.util.List;

public interface ConvertService<T, M> {

    T getDTOFromObject(M m);

    List<T> getDTOFromObject(List<M> m);

    M getObjectFromDTO(T t);

    List<M> getObjectFromDTO(List<T> t);

}
