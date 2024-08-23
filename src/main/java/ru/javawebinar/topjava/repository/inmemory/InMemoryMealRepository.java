package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    public InMemoryMealRepository() {
        //admin meals
        MealsUtil.meals.forEach(meal -> save(meal, 1));
        //user meals
        save(new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS), "user meal1", 500), 2);
        save(new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS), "user meal2", 2000), 2);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        meal.setUserId(repository.get(meal.getId()).getUserId());
        return userId == meal.getUserId() ? repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal) : null;
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);
        AtomicBoolean isExist = new AtomicBoolean(false);
        repository.computeIfPresent(id, (i, m) -> {
            isExist.set(m.getUserId() == userId);
            return isExist.get() ? null : m;
        });
        return isExist.get();
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {}", id);
        Meal meal = repository.get(id);
        return meal != null && meal.getUserId() == userId ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll");
        return getAllFiltered(userId, null, null);
    }

    @Override
    public List<Meal> getBetween(int userId, LocalDateTime start, LocalDateTime end) {
        log.info("getBetween");
        return getAllFiltered(userId, start, end);
    }

    private List<Meal> getAllFiltered(int userId, LocalDateTime start, LocalDateTime end) {
        Stream<Meal> stream = repository.values().stream().filter(meal -> meal.getUserId() == userId);
        if (start != null && end != null) {
            stream = stream.filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(), start, end));
        }
        return stream.sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
    }
}

