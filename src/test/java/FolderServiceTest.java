import com.zkristic.eraser.service.FolderService;
import com.zkristic.eraser.service.impl.FolderServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

import static org.junit.Assert.assertTrue;

/**
 * Created by Zoran Kristić (zkristic).
 */
public class FolderServiceTest {

    FolderService service = new FolderServiceImpl();
    private static String home = System.getProperty("user.home");
    private static String separator = System.getProperty("file.separator");

    private static String root;
    private static String empty;
    private static String notEmpty;
    private static String emptyInNotEmpty;
    private static String notEmptyButShouldBeDeleted;
    private static String emptyInNotEmptyButShouldBeDeleted;

    private static String file;
    private static String doesNotExist;

    @BeforeClass
    public static void setTestEnvironment() {
        root = home + separator + "Test";
        empty = root + separator + "Empty";
        notEmpty = root + separator + "NotEmpty";
        emptyInNotEmpty = notEmpty + separator + "Empty";
        notEmptyButShouldBeDeleted = root + separator + "NotEmptyButShouldBeDeleted";
        emptyInNotEmptyButShouldBeDeleted = notEmptyButShouldBeDeleted + separator + "Empty";

        file = notEmpty + separator + "file.txt";
        doesNotExist = home + separator + "ThisIsATestFolderAndShouldNeverExist";


        new File(empty).mkdirs();
        new File(emptyInNotEmpty).mkdirs();
        new File(emptyInNotEmptyButShouldBeDeleted).mkdirs();

        try {
            new File(file).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotDirectory() throws Exception {
        service.deleteEmptyFolders(file);
    }

    @Test(expected = NoSuchFileException.class)
    public void testDoesNotExist() throws Exception {
        service.deleteEmptyFolders(doesNotExist);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullPointer() throws Exception {
        service.deleteEmptyFolders(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyString() throws Exception {
        service.deleteEmptyFolders("");
    }

    @Test
    public void testEraser() throws Exception {
        service.deleteEmptyFolders(root);

        File existingFile = new File(file);
        File existingFolder = new File(notEmpty);
        File existingRoot = new File(root);

        assertTrue(existingFile.exists());
        assertTrue(existingFolder.list().length == 1);
        assertTrue(existingRoot.list().length == 1);

        assertTrue(existingFolder.listFiles()[0].equals(existingFile));
        assertTrue(existingRoot.listFiles()[0].equals(existingFolder));

        assertTrue(existingFile.delete());
        assertTrue(existingFolder.delete());
        assertTrue(existingRoot.delete());
    }

}
