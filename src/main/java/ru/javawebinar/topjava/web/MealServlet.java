package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MemoryMealStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class MealServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(MealServlet.class);
    private Storage<Meal> mealStorage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.info("Init MealServlet");
        super.init(config);
        mealStorage = new MemoryMealStorage();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        logger.info("Process POST request");
        String id = req.getParameter("id");
        Meal meal = new Meal(
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories")));
        if (id == null || id.isEmpty()) {
            mealStorage.add(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            mealStorage.update(meal);
        }
        redirectToRoot(resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Process GET request");
        String id = req.getParameter("id");
        String action = req.getParameter("action");
        if (action == null) {
            showList(req, resp);
            return;
        }

        switch (action) {
            case "create":
            case "edit":
                if (!(id == null || id.isEmpty())) {
                    req.setAttribute("meal", mealStorage.get(Integer.parseInt(id)));
                }
                break;
            case "delete":
                mealStorage.remove(Integer.parseInt(id));
                redirectToRoot(resp);
                return;
            default:
                logger.error("Action {} does not exist!", action);
                redirectToRoot(resp);
                return;
        }
        logger.info("Forward to {} page", action.equals("create") ? "create" : "edit");
        req.getRequestDispatcher("meals/editMeal.jsp").forward(req, resp);
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("meals", MealsUtil.filteredByStreams(mealStorage.getAll(), MealsUtil.CALORIES_PER_DATE));
        logger.info("Forward to meals");
        req.getRequestDispatcher("meals/meals.jsp").forward(req, resp);
    }

    private void redirectToRoot(HttpServletResponse resp) throws IOException {
        logger.info("Redirect to meals");
        resp.sendRedirect("meals");
    }
}
