package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.web.meal.MealRestController;

public class SpringMain {
    public static void main(String[] args) {
//        System.setProperty("spring.profiles.active", String.join(",",
//                Profiles.getActiveDbProfile(),
//                Profiles.REPOSITORY_IMPLEMENTATION));

        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext(new String[]{"spring/spring-app.xml", "spring/spring-db.xml"}, false)) {
            appCtx.getEnvironment().setActiveProfiles(
                    Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION);
            appCtx.refresh();

            MealRestController mealController = appCtx.getBean(MealRestController.class);
            System.out.println(mealController.getAll());

        }
    }
}
