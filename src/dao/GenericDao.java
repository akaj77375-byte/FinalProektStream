package dao;

public interface GenericDao<T> {
    String add(Long hospitalId, T t);

    void removeById(Long hospital, Long id);

    String updateById(Long hospital, Long id, T t);
}
