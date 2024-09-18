package ru.javawebinar.topjava;

import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class TimingRules {
    private static final Logger logger = LoggerFactory.getLogger("results");
    private static final StringBuilder results = new StringBuilder();

    public static final Stopwatch STOPWATCH = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("%-95s %7dms", description.getDisplayName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result).append("\n");
            logger.info("{}ms\n", result);
        }
    };

    private static final String DELIMITER = String.join("", Collections.nCopies(105, "-"));

    public static final ExternalResource SUMMARY = new ExternalResource() {
        @Override
        protected void before() {
            results.setLength(0);
        }

        @Override
        protected void after() {
            logger.info("\n{}\nTest results\n{}\n{}{}\n", DELIMITER, DELIMITER, results, DELIMITER);
        }
    };
}
