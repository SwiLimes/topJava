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

    public static List<MealTo> filteredByStreams(List<Meal> meals, int caloriesPerDate) {
        return filteredByStreams(meals, null, null, caloriesPerDate);
    }

    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDate) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        Stream<Meal> stream = meals.stream();
        int i = (startTime == null ? 0 : 1) + (endTime == null ? 0 : 2);
        switch (i) {
            case 1:
                stream = stream.filter(meal -> TimeUtil.isTimeAfter(meal.getTime(), startTime));
                break;
            case 2:
                stream = stream.filter(meal -> TimeUtil.isTimeBefore(meal.getTime(), endTime));
                break;
            case 3:
                stream = stream.filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime));
                break;
            default:
                break;
        }
        return stream.map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDate))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
