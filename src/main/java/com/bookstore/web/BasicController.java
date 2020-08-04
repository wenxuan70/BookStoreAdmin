package com.bookstore.web;

import com.bookstore.util.ThymeleafUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public abstract class BasicController extends HttpServlet {
    // Thymeleaf引擎
    protected TemplateEngine engine;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext sc = config.getServletContext();
        engine = ThymeleafUtils.getTemplateEngine(sc);

        initServlet(config);
    }

    /**
     * 如果子类需要初始化
     * @param config
     */
    protected abstract void initServlet(ServletConfig config);

    @Override
    protected abstract void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException;

    @Override
    protected abstract void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException;

    /**
     * 渲染模板
     * @param req
     * @param resp
     * @param name
     */
    protected void renderTemplate(HttpServletRequest req, HttpServletResponse resp, String name) throws IOException {
        WebContext webContext = new WebContext(req, resp, req.getServletContext());
        engine.process(name, webContext, resp.getWriter());
    }

    /**
     * 渲染模板
     * @param req
     * @param resp
     * @param viewName 模板名称
     * @param vars
     */
    protected void renderTemplate(HttpServletRequest req, HttpServletResponse resp, String viewName, Map<String, Object> vars) throws IOException {
        WebContext webContext = new WebContext(req, resp, req.getServletContext());
        webContext.setVariables(vars);
        engine.process(viewName, webContext, resp.getWriter());
    }

    /**
     * 渲染模板
     * @param req
     * @param resp
     * @param viewName 模板名称
     * @param varsName
     * @param vars
     */
    protected void renderTemplate(HttpServletRequest req, HttpServletResponse resp, String viewName, String[] varsName, Object[] vars) throws IOException {
        WebContext webContext = new WebContext(req, resp, req.getServletContext());
        for (int i = 0; i < varsName.length; i++) {
            webContext.setVariable(varsName[i], vars[i]);
        }
        engine.process(viewName, webContext, resp.getWriter());
    }

    /**
     * 渲染模板
     * @param req
     * @param resp
     * @param viewName 模板名称
     * @param varName
     * @param var
     */
    protected void renderTemplate(HttpServletRequest req, HttpServletResponse resp, String viewName, String varName, Object var) throws IOException {
        WebContext webContext = new WebContext(req, resp, req.getServletContext());
        webContext.setVariable(varName, var);
        engine.process(viewName, webContext, resp.getWriter());
    }
}
