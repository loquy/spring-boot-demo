package com.example.codegenerator.utils;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.NullCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * FreeMarker模版工具类
 * @author loquy
 */
public class FreeMarkerTemplateUtils {
    private FreeMarkerTemplateUtils() {
    }

    private static final Configuration CONFIGURATION = new Configuration(Configuration.VERSION_2_3_22);

    static {
        //ClassTemplateLoader方式：需要将模版放在FreeMarkerTemplateUtils类所在的包，加载模版时会从该包下加载
        try {
            String path = java.net.URLDecoder.decode(Objects.requireNonNull(
                    FreeMarkerTemplateUtils.class.getClassLoader().getResource("")).getPath(), "utf-8");
            //FileTemplateLoader方式：需要将模版放置在classpath目录下 目录有中文也可以
            CONFIGURATION.setTemplateLoader(new FileTemplateLoader(new File(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        CONFIGURATION.setDefaultEncoding("UTF-8");
        CONFIGURATION.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        CONFIGURATION.setCacheStorage(NullCacheStorage.INSTANCE);
    }

    public static Template getTemplate(String templateName) throws IOException {
        return CONFIGURATION.getTemplate(templateName);
    }
}
