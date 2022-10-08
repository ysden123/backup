package com.stulsoft.backup.config;

import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;

import java.util.List;

public class AppConfig {
    private List<Directory> directories;

    private static final AppConfig appConfig;

    static {
        appConfig = ConfigBeanFactory.create(ConfigFactory.load(), AppConfig.class);
    }

    public AppConfig(){}

    public static AppConfig getAppConfig(){
        return appConfig;
    }

    public List<Directory> getDirectories() {
        return directories;
    }

    public void setDirectories(List<Directory> directories) {
        this.directories = directories;
    }

    @Override
    public String toString() {
        return "AppConfig{" +
                "directories=" + directories +
                '}';
    }
}
