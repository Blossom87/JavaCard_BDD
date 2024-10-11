package fr.afpa.models;

import java.sql.Connection;
import java.util.List;

import fr.afpa.models.ConnectionPostgreSQL;


public abstract class DAO<T> {

    public Connection connect = ConnectionPostgreSQL.getInstance();

    public abstract T find(int id);

    public abstract List<T> findAll();

    public abstract T update(Contact updateContact);

    public abstract T add(Contact newContact);

    public abstract boolean delete(int id);

    public abstract boolean deleteID(Contact contact);
}
