package com.stulsoft.backup;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Optional;

public class VersionService {
    static class CreationTimeComparator implements Comparator<Path> {
        @Override
        public int compare(Path p1, Path p2) {
            try {
                var attr1 = Files.readAttributes(p1, BasicFileAttributes.class);
                var attr2 = Files.readAttributes(p2, BasicFileAttributes.class);
                return attr1.creationTime().compareTo(attr2.creationTime());
            } catch (Exception exception) {
                exception.printStackTrace();
                return 0;
            }
        }
    }

    private final String destinationDirectory;
    private final Integer maxBackupDirectories;

    public VersionService(String destinationDirectory, Integer maxBackupDirectories) {
        this.destinationDirectory = destinationDirectory;
        this.maxBackupDirectories = maxBackupDirectories;
    }

    public String buildOutputDirectoryName() {
        if (maxBackupDirectories != null) {
            if (findNumberOfExistingBackups() >= maxBackupDirectories) {
                findOldestBackupDirectory()
                        .ifPresent(p -> {
                            try {
                                System.out.printf("going to delete %s%n", p);
                                FileUtils.deleteQuietly(p.toFile());
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        });
            }
        }
        return Paths.get(destinationDirectory, buildOutputBackupDirectoryName()).toString();
    }

    private Optional<Path> findOldestBackupDirectory() {
        try (var l = Files.list(new File(destinationDirectory).toPath())) {
            return l.filter(Files::isDirectory).min(new CreationTimeComparator());
        } catch (Exception exception) {
            exception.printStackTrace();
            return Optional.empty();
        }
    }

    private long findNumberOfExistingBackups() {
        try (var l = Files.list(new File(destinationDirectory).toPath())) {
            return l.filter(Files::isDirectory).count();
        } catch (NoSuchFileException ignore) {
            return 0;
        } catch (Exception exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    private String buildOutputBackupDirectoryName() {
        Calendar calendar = Calendar.getInstance();
        var formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        return formatter.format(calendar.getTimeInMillis());
    }
}
