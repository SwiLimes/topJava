package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private static final String USER_ID = "userId";

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        Integer id = meal.getId();
        if (id != null && get(id, userId) == null) {
            // Not own
            return null;
        }

        meal.setUser(entityManager.getReference(User.class, userId));
        if (meal.isNew()) {
            entityManager.persist(meal);
            return meal;
        }
        return entityManager.merge(meal);
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return entityManager.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter(USER_ID, userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = entityManager.createNamedQuery(Meal.GET, Meal.class)
                .setParameter("id", id)
                .setParameter(USER_ID, userId)
                .getResultList();
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return entityManager.createNamedQuery(Meal.GET_ALL, Meal.class)
                .setParameter(USER_ID, userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return entityManager.createNamedQuery(Meal.GET_BETWEEN, Meal.class)
                .setParameter(USER_ID, userId)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .getResultList();
    }
}