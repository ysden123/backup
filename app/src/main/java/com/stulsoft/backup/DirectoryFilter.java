package com.stulsoft.backup;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class DirectoryFilter implements FileFilter {
    private final Set<String> skipFolders;
    private final AtomicInteger handledDirectories = new AtomicInteger(0);
    private final AtomicInteger skippedDirectories = new AtomicInteger(0);
    private final AtomicInteger handledFiles = new AtomicInteger(0);
    private final AtomicInteger skippedFiles = new AtomicInteger(0);

    public DirectoryFilter(Collection<String> skipFolders) {
        if (skipFolders == null){
            this.skipFolders = Collections.emptySet();
        }else {
            this.skipFolders = new HashSet<>(skipFolders);
        }
    }

    public int getHandledDirectories() {
        return handledDirectories.get();
    }

    public int getSkippedDirectories() {
        return skippedDirectories.get();
    }

    public int getHandledFiles() {
        return handledFiles.get();
    }

    public int getSkippedFiles() {
        return skippedFiles.get();
    }

    /**
     * Tests whether the specified abstract pathname should be
     * included in a pathname list.
     *
     * @param pathname The abstract pathname to be tested
     * @return {@code true} if and only if {@code pathname}
     * should be included
     */
    @Override
    public boolean accept(File pathname) {
        if (pathname.isDirectory()) {
            handledDirectories.incrementAndGet();
        } else {
            handledFiles.incrementAndGet();
        }
//        System.out.printf("Handling %s file%n", pathname.toPath());
        for (var skipFolder : skipFolders) {
            if (pathname.toPath().toString().contains(skipFolder)) {
                if (pathname.isDirectory()) {
                    skippedDirectories.incrementAndGet();
                } else {
                    skippedFiles.incrementAndGet();
                }
                return false;
            }
        }
        return true;
    }
}
