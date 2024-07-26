package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealArrayStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

public class MealServlet extends HttpServlet {

    private static final Storage<Meal> mealStorage = new MealArrayStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        mealStorage.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        mealStorage.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        mealStorage.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        mealStorage.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        mealStorage.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        mealStorage.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        mealStorage.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        Meal meal = new Meal(
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories")));
        if (id == null || id.isEmpty()) {
            mealStorage.add(meal);
        } else {
            int i = Integer.parseInt(id);
            mealStorage.update(i, meal);
        }
        showList(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        String action = req.getParameter("action");
        if (action == null) {
            showList(req, resp);
            return;
        }

        switch (action) {
            case "create", "edit":
                if (!(id == null || id.isEmpty())) {
                    req.setAttribute("meal", mealStorage.get(Integer.parseInt(id)));
                }
                break;
            case "delete":
                mealStorage.remove(Integer.parseInt(id));
                resp.sendRedirect("meals");
                return;
            default:
                throw new IllegalArgumentException("Action " + action + " does not exist!");
        }
        req.getRequestDispatcher("meals/edit.jsp").forward(req, resp);
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("meals", MealsUtil.filteredByStreams(mealStorage.getAll()));
        req.getRequestDispatcher("meals/meals.jsp").forward(req, resp);
    }
}
