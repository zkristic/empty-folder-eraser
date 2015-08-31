package com.zkristic.eraser.service;

import java.nio.file.NoSuchFileException;

/**
 * Created by Zoran Kristić (zkristic).
 */
public interface FolderService {
    void deleteEmptyFolders(String pathname) throws NoSuchFileException;
}
