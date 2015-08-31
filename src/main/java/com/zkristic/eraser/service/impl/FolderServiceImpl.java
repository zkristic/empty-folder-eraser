package com.zkristic.eraser.service.impl;

import com.zkristic.eraser.service.FolderService;

import java.io.File;
import java.nio.file.NoSuchFileException;

/**
 * Created by Zoran Kristić (zkristic).
 */
public class FolderServiceImpl implements FolderService {

    private boolean shouldBeDeleted(File file) {
        return file.isDirectory() && file.listFiles().length == 0;
    }

    private void deleteEmptyFolders(File root) {
        if (root.isFile()) {
            return;
        }

        File[] files = root.listFiles();
        for (File file : files) {
            deleteEmptyFolders(file);
        }

        if (shouldBeDeleted(root)) {
            root.delete();
        }
    }

    public void deleteEmptyFolders(String pathname) throws IllegalArgumentException, NoSuchFileException {
        if (pathname == null) {
            throw new IllegalArgumentException("Specified path shouldn't be null!");
        }
        if (pathname.isEmpty()) {
            throw new IllegalArgumentException("Specified path shouldn't be empty string!");
        }

        File root = new File(pathname);

        if (!root.exists()) {
            throw new NoSuchFileException("Specified path doesn't exist!");
        }

        if (!root.isDirectory()) {
            throw new IllegalArgumentException("Specified path should be a directory!");
        }

        deleteEmptyFolders(root);
    }

}
