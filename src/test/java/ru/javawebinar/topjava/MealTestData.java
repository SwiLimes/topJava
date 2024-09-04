package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int USER_MEAL_1_ID = START_SEQ + 3;
    public static final int ADMIN_MEAL_1_ID = START_SEQ + 11;
    public static final int ADMIN_MEAL_2_ID = START_SEQ + 12;
    public static final int ADMIN_MEAL_3_ID = START_SEQ + 13;

    public static final Meal userMeal1 = new Meal(USER_MEAL_1_ID, LocalDateTime.of(2024, 1, 30, 10,0), "Завтрак", 500);
    public static final Meal adminMeal1 = new Meal(ADMIN_MEAL_1_ID, LocalDateTime.of(2024, 6, 1, 14,0), "Админ ланч", 510);
    public static final Meal adminMeal2 = new Meal(ADMIN_MEAL_2_ID, LocalDateTime.of(2024, 6, 1, 21,0), "Админ ужин", 1500);
    public static final Meal adminMeal3 = new Meal(ADMIN_MEAL_3_ID, LocalDateTime.of(2024, 6, 2, 21,0), "Тест индекса по времени пищи у админа", 1500);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2025, 5, 15, 15, 0), "New meal", 2001);
    }

    public static Meal getUpdated() {
        Meal updatedMeal = new Meal(userMeal1);
        updatedMeal.setDateTime(LocalDateTime.of(3000, 1, 1, 10, 0));
        updatedMeal.setDescription("updatedDescription");
        updatedMeal.setCalories(5000);
        return updatedMeal;
    }

    public static void assertMatch(Meal actual, Meal excepted) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(excepted);
    }
}