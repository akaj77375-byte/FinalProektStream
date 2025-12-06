package service;

public interface GenericService<T> {
    String add(Long  hospitalId, T t);

    String removeById(Long hospital,Long id);

    String updateById(Long hospital,Long id, T t);
}