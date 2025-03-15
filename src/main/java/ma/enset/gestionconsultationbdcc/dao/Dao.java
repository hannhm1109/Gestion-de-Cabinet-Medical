package ma.enset.gestionconsultationbdcc.dao;

import java.util.List;

public interface Dao<E, U> {
    void create(E e);
    void delete(E e);
    void update(E e);
    List<E> findAll();
    E findById(U id);
}