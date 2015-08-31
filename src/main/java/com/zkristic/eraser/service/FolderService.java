package com.zkristic.eraser.service;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.List;

/**
 * Created by Zoran Kristić (zkristic).
 */
public interface FolderService {
    void deleteEmptyFolders(String pathname) throws NoSuchFileException;
}
