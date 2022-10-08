/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.stulsoft.backup;

import com.stulsoft.backup.config.AppConfig;

import java.util.HashSet;

public class App {
    public static void main(String[] args) {
        var appConfig = AppConfig.getAppConfig();
        System.out.println(appConfig);

        var copyDirectories = new CopyDirectories(new HashSet<>(appConfig.getDirectories()));
        copyDirectories.makeCopy();
    }
}