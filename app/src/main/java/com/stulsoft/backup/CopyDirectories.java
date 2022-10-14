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
            var versionService = new VersionService(directory.getDestination(), directory.getMaxBackupDirectories());
            var odn = versionService.buildOutputDirectoryName();
            System.out.printf("%nCopying %s%n", directory.getName());
            System.out.printf("   source     : %s%n", directory.getSource());
            System.out.printf("   destination: %s%n", odn);
            long start = System.currentTimeMillis();
            var directoryFilter = new DirectoryFilter(directory.getDirectoriesToSkip());
            try {
                FileUtils.deleteDirectory(new File(odn));
                FileUtils.copyDirectory(new File(directory.getSource()),
                        new File(odn), directoryFilter, false);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            System.out.printf("%n%s copied in %s%n", directory.getName(),
                    DurationFormatUtils.formatDuration(System.currentTimeMillis() - start, "HH:mm:ss,SSS",
                            true));
            System.out.printf("Handled %d directories, %d files.%n",
                    directoryFilter.getHandledDirectories(), directoryFilter.getHandledFiles());
            System.out.printf("Skipped %d directories.%n", directoryFilter.getSkippedDirectories());
        }
    }
}
