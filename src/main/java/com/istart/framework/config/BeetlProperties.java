package com.istart.framework.config;

import org.springframework.boot.autoconfigure.template.AbstractTemplateViewResolverProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.MimeType;

import java.nio.charset.Charset;

/**
 * Annotation for externalized configuration. Add this to a class definition or a @Bean method in a @Configuration class if you want to bind and validate some external Properties (e.g. from a .properties file). 

Note that contrary to @Value, SpEL expressions are not evaluated since property values are externalized.
作用范围：Class 或@Bean方法（在@Configuration class里面）
 * @author 进军
 *
 */
@ConfigurationProperties(prefix = "spring.beetl")
public class BeetlProperties extends AbstractTemplateViewResolverProperties {

    public static final Charset DEFAULT_ENCODING = Charset.forName("UTF-8");
    public static final MimeType DEFAULT_CONTENT_TYPE = MimeType.valueOf("text/html");
    private boolean checkTemplateLocation = true;


    public static final String DEFAULT_TEMPLATE_LOADER_PATH = "classpath:/templates/";
    public static final String DEFAULT_PREFIX = "";
    public static final String DEFAULT_SUFFIX = ".html";

    private String prefix = "";
    private String suffix = ".html";
    private int order = 0;
    private String root = DEFAULT_TEMPLATE_LOADER_PATH;
    private String config;


    protected BeetlProperties() {
        super("/", ".html");
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String getSuffix() {
        return suffix;
    }

    @Override
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}
