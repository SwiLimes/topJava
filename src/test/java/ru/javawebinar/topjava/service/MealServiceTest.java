package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Arrays;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_1_ID;
import static ru.javawebinar.topjava.MealTestData.NOT_FOUND_MEAL;
import static ru.javawebinar.topjava.MealTestData.USER_MEAL_1_ID;
import static ru.javawebinar.topjava.MealTestData.adminMeal1;
import static ru.javawebinar.topjava.MealTestData.adminMeal2;
import static ru.javawebinar.topjava.MealTestData.adminMeal3;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.MealTestData.getNew;
import static ru.javawebinar.topjava.MealTestData.getUpdated;
import static ru.javawebinar.topjava.MealTestData.userMeal1;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USER_MEAL_1_ID, USER_ID);
        assertMatch(meal, userMeal1);
    }

    @Test
    public void getOther() {
        Assert.assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_1_ID, ADMIN_ID));
    }

    @Test
    public void getNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND_MEAL, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(ADMIN_MEAL_1_ID, ADMIN_ID);
        Assert.assertThrows(NotFoundException.class, () -> service.get(ADMIN_MEAL_1_ID, ADMIN_ID));
    }

    @Test
    public void deleteOther() {
        Assert.assertThrows(NotFoundException.class, () -> service.delete(ADMIN_MEAL_1_ID, USER_ID));
    }

    @Test
    public void deleteNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_MEAL, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        assertMatch(service.getBetweenInclusive(LocalDate.of(2024, 5, 1), LocalDate.of(2024, 6, 1), ADMIN_ID),
                Arrays.asList(adminMeal2, adminMeal1));
    }

    @Test
    public void getBetweenEmpty() {
        assertMatch(service.getBetweenInclusive(null, null, ADMIN_ID), Arrays.asList(adminMeal3, adminMeal2, adminMeal1));
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(ADMIN_ID), Arrays.asList(adminMeal3, adminMeal2, adminMeal1));
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(USER_MEAL_1_ID, USER_ID), getUpdated());
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), ADMIN_ID);
        Meal newMeal = getNew();
        newMeal.setId(created.getId());

        assertMatch(created, newMeal);
        assertMatch(service.get(created.getId(), ADMIN_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        Assert.assertThrows(DataAccessException.class, () ->
                service.create(new Meal(userMeal1.getDateTime(), "Duplicate", 10), USER_ID));
    }
}