package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository mealCrudRepository;
    private final CrudUserRepository userCrudRepository;

    @Autowired
    public DataJpaMealRepository(CrudMealRepository crudRepository, CrudUserRepository userCrudRepository) {
        this.mealCrudRepository = crudRepository;
        this.userCrudRepository = userCrudRepository;
    }

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && get(meal.id(), userId) == null) {
            // Not own
            return null;
        }
        meal.setUser(userCrudRepository.getReferenceById(userId));
        return mealCrudRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return mealCrudRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return mealCrudRepository.findById(id)
                .filter(m -> m.getUser().getId() == userId)
                .orElse(null);
    }

    @Override
    public Meal getWithUser(int id, int userId) {
        return mealCrudRepository.getWithUser(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return mealCrudRepository.getAll(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return mealCrudRepository.getBetween(startDateTime, endDateTime, userId);
    }
}
