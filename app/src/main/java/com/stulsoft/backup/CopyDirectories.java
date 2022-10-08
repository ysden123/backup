package com.stulsoft.backup;

import com.stulsoft.backup.config.Directory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.io.File;
import java.util.Set;

public class CopyDirectories {
    private final Set<Directory> directories;

    public CopyDirectories(Set<Directory> directories) {
        this.directories = directories;
    }

    public void makeCopy() {
        for (var directory : directories) {
            System.out.printf("Copying %s%n", directory.getName());
            System.out.printf("   source     : %s%n", directory.getSource());
            System.out.printf("   destination: %s%n", directory.getDestination());
            long start = System.currentTimeMillis();
            var directoryFilter = new DirectoryFilter(directory.getDirectoriesToSkip());
            try {
                FileUtils.copyDirectory(new File(directory.getSource()),
                        new File(directory.getDestination()), directoryFilter, true);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            System.out.printf("%s copied in %s%n", directory.getName(),
                    DurationFormatUtils.formatDuration(System.currentTimeMillis() - start, "HH:mm:ss,SSS",
                            true));
            System.out.printf("Handled %d directories, %d files.%n",
                    directoryFilter.getHandledDirectories(), directoryFilter.getHandledFiles());
            System.out.printf("Skipped %d directories, %d files.%n",
                    directoryFilter.getSkippedDirectories(), directoryFilter.getSkippedFiles());
        }
    }
}
