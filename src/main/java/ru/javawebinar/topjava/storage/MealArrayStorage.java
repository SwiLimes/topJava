package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MealArrayStorage implements Storage<Meal> {
    private static final Logger logger = LoggerFactory.getLogger(MealArrayStorage.class);
    private final List<Meal> meals = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void add(Meal meal) {
        logger.info("Add new meal: {}", meal);
        meal.setId(meals.size());
        meals.add(meal);
    }

    @Override
    public List<Meal> getAll() {
        logger.info("Get all meals");
        return meals;
    }

    @Override
    public Meal get(int id) {
        Meal meal = meals.stream().filter(m -> m.getId() == id).findFirst().orElse(null);
        logger.info("Get meal with id {}: {}", id, meal);
        return meal;
    }

    @Override
    public void update(int id, Meal meal) {
        Meal updateMeal = meals.stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
        if (updateMeal == null) {
            logger.error("Updated meal does not exist!");
            return;
        }

        int index = meals.indexOf(updateMeal);
        logger.info("Update meal at index {}. \nOld value: {}. \nNew value: {}", index, updateMeal, meal);
        updateMeal.setDateTime(meal.getDateTime());
        updateMeal.setDescription(meal.getDescription());
        updateMeal.setCalories(meal.getCalories());
        meals.set(index, updateMeal);
    }

    @Override
    public void remove(int id) {
        logger.info("Remove meal at index {}", id);
        meals.removeIf(meal -> meal.getId() == id);
    }
}
