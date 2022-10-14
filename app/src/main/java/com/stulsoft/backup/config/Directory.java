package com.stulsoft.backup.config;

import com.typesafe.config.Optional;

import java.util.List;
import java.util.Objects;

public class Directory {
    private String name;
    private String source;
    private String destination;

    private Integer maxBackupDirectories;

    @Optional
    private List<String> directoriesToSkip;

    public Directory() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<String> getDirectoriesToSkip() {
        return directoriesToSkip;
    }

    public void setDirectoriesToSkip(List<String> directoriesToSkip) {
        this.directoriesToSkip = directoriesToSkip;
    }

    public Integer getMaxBackupDirectories() {
        return maxBackupDirectories;
    }

    public void setMaxBackupDirectories(Integer maxBackupDirectories) {
        this.maxBackupDirectories = maxBackupDirectories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Directory directory = (Directory) o;
        return Objects.equals(getName(), directory.getName()) && Objects.equals(getSource(),
                directory.getSource()) && Objects.equals(getDestination(), directory.getDestination());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSource(), getDestination());
    }

    @Override
    public String toString() {
        return "Directory{" +
                "name='" + name + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", maxBackupDirectories=" + maxBackupDirectories +
                ", directoriesToSkip=" + directoriesToSkip +
                '}';
    }
}