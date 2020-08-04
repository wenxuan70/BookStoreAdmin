package com.bookstore.util;

import org.thymeleaf.TemplateEngine;

import javax.servlet.ServletContext;

public class ThymeleafUtils {
    private static final String THYMELEAF = "thymeleaf";

    public static void setTemplateEngine(ServletContext context, TemplateEngine engine) {
        context.setAttribute(THYMELEAF, engine);
    }

    public static TemplateEngine getTemplateEngine(ServletContext context) {
        return (TemplateEngine) context.getAttribute(THYMELEAF);
    }
}
