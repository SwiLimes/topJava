package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MealsUtil {
    public static final int CALORIES_PER_DATE = 2000;

    public static List<MealTo> filteredByStreams(List<Meal> meals) {
        return filteredByStreams(meals, null, null);
    }

    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        Stream<Meal> stream = meals.stream();
        if (startTime != null && endTime != null) {
            stream = stream.filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime));
        }
        return stream.map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > CALORIES_PER_DATE))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
