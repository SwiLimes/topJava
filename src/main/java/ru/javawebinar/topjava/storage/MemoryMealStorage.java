package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMealStorage implements Storage<Meal> {
    private static final Logger logger = LoggerFactory.getLogger(MemoryMealStorage.class);
    private static final AtomicInteger ID_COUNTER = new AtomicInteger(0);
    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    public MemoryMealStorage() {
        Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        ).forEach(this::add);
    }

    @Override
    public Meal add(Meal meal) {
        logger.info("Add new meal: {}", meal);
        int id = ID_COUNTER.incrementAndGet();
        meal.setId(id);
        return meals.put(id, meal);
    }

    @Override
    public List<Meal> getAll() {
        logger.info("Get all meals");
        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal get(int id) {
        Meal meal = meals.get(id);
        logger.info("Get meal with id {}: {}", id, meal);
        return meal;
    }

    @Override
    public Meal update(Meal meal) {
        int id = meal.getId();
        Meal oldMeal = meals.get(id);
        if (oldMeal == null) {
            logger.error("Updated meal does not exist!");
            return null;
        }
        logger.info("Update meal with id {}. \nOld value: {}. \nNew value: {}", id, oldMeal, meal);
        return meals.put(id, meal);
    }

    @Override
    public void remove(int id) {
        logger.info("Remove meal with id {}", id);
        meals.remove(id);
    }
}
