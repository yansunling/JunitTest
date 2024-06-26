package com.module.log;

import com.other.thread.JobThreadExecutor;
import com.yd.utils.common.StringUtils;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Plugin(name = "FileAppender", category = "Core", elementType = "appender", printObject = true)
public class FileAppender extends AbstractAppender {
    private String fileName;

    /* 构造函数 */
    public FileAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, String fileName) {
        super(name, filter, layout, ignoreExceptions);
        this.fileName = fileName;
    }

    private Object lock=new Object();
    @Override
    public void append(LogEvent event) {
        synchronized(lock){
            final byte[] bytes = getLayout().toByteArray(event);
            writerFile(bytes);
        }
    }

    /*  接收配置文件中的参数 */
    @PluginFactory
    public static FileAppender createAppender(@PluginAttribute("name") String name,
                                              @PluginAttribute("fileName") String fileName,
                                              @PluginElement("Filter") final Filter filter,
                                              @PluginElement("Layout") Layout<? extends Serializable> layout,
                                              @PluginAttribute("ignoreExceptions") boolean ignoreExceptions) {
        if (name == null) {
            LOGGER.error("no name defined in conf.");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }

        return new FileAppender(name, filter, layout, ignoreExceptions, fileName);
    }

    private static boolean createFile(String fileName) {
        Path filePath = Paths.get(fileName);
        try {
            // 文件不存在，新建文件
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }

        } catch (IOException e) {
            LOGGER.error("create file exception", e);
            return false;
        }
        return true;
    }

    private void writerFile(byte[] log) {
        try {
            String threadFileNmae = JobThreadExecutor.contextHolder.get();
            if(StringUtils.isBlank(threadFileNmae)){
                return;
            }
            fileName=threadFileNmae;
            createFile(fileName);
            Files.write(Paths.get(fileName), log, StandardOpenOption.APPEND);
        } catch (IOException e) {
            LOGGER.error("write file exception", e);
        }
    }
}