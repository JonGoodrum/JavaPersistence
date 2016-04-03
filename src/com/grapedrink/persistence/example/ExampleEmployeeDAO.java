package com.grapedrink.persistence.example;

import java.util.List;

import javax.persistence.EntityManager;

import com.grapedrink.persistence.dao.AbstractDAO;

public class ExampleEmployeeDAO extends AbstractDAO {

    public ExampleEmployeeDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public void createEmployee(ExampleEmployeeEntity e) {
        super.create(e);
    }

    public void deleteEmployee(ExampleEmployeeEntity e) {
        super.delete(e);
    }

    public void deleteEverything() {
        for (ExampleEmployeeEntity e : getEverything()) {
            super.delete(e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<ExampleEmployeeEntity> getEverything() {
        return (List<ExampleEmployeeEntity>) super.executeQuery("SELECT e FROM ExampleEmployeeEntity e");
    }
}
