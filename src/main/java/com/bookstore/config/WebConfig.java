package com.bookstore.config;

import com.bookstore.util.ThymeleafUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 用来加载Thymeleaf模板引擎和数据库配置
 */
public class WebConfig implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        // 把模板引擎加入ServletContext
        TemplateEngine engine = templateEngine(context);
        ThymeleafUtils.setTemplateEngine(context, engine);
        System.out.format("加载模板引擎配置...\n");

        // 加载数据库配置
        try {
            Class.forName("com.bookstore.util.DataSourceUtils");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.format("加载数据库配置...\n");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    /**
     * 设置模板引擎
     * @param context
     * @return
     */
    private TemplateEngine templateEngine(ServletContext context) {
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver(context));
        return templateEngine;
    }

    /**
     * 视图解析器
     * @param context
     * @return
     */
    private ITemplateResolver templateResolver(final ServletContext context) {
        ServletContextTemplateResolver templateResolver =
                new ServletContextTemplateResolver(context);

        templateResolver.setTemplateMode(TemplateMode.HTML);
        // 映射
        templateResolver.setPrefix("/WEB-INF/template/");
        templateResolver.setSuffix(".html");
        // 缓存时间
        templateResolver.setCacheTTLMs(Long.valueOf(3600000L));
        // 是否缓存
        templateResolver.setCacheable(false);
        return templateResolver;
    }
}
