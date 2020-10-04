package serverstat.file;


import log.GameLogger;
import org.junit.jupiter.api.*;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileManagerTest {
    FileManager fileManager;
    String path = "testFile/";
    File directory = new File("testFile");

    @BeforeEach
    public void init(){
        GameLogger.verbose = false;
        fileManager = new FileManager(path+"testFile");
        directory.mkdir();
    }

    @Test
    public void existsTest(){
        //cas de base le fichier n'existe pas
        assertEquals(false,fileManager.exists());

        //creation d'un fichier avec un nom different
        File otherFile = new File(path+"testOtherFile");
        try {
            otherFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(false,fileManager.exists());

        //Si le fichier existe:
        try {
            fileManager.getFile().createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(true,fileManager.exists());
    }



    @Test
    public void writeAndGetFileContentTest(){
        String content = "hello world";
        fileManager.create();
        fileManager.write(content);
        assertEquals(content,fileManager.getRaw());
    }

    @Test
    public void clearFileTest(){
        fileManager.create();
        //le fichier est vide par defaut
        assertEquals("",fileManager.getRaw());
        fileManager.clearFile();
        //il reste vide si il etait vide
        assertEquals("",fileManager.getRaw());

        //on ecrit dans le fichier
        fileManager.write("the file is not empty");
        assertEquals(true,fileManager.getRaw().length()>0);

        //on vide le fichier
        fileManager.clearFile();
        assertEquals("",fileManager.getRaw());
    }

    @Test
    public void getRawTest(){
        try {
            fileManager.getFile().createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Le fichier est vide
        assertEquals("",fileManager.getRaw());

        //On ecrit quelque chose dedans
        String text = "Bonjour j'ecrit quelque chose\nSur plusieurs lignes...\nEt voila.";
        fileManager.write(text);

        //Le fichier est autorisé en lecture
        fileManager.getFile().setReadable(true);
        assertEquals(text,fileManager.getRaw());

        //Un autre texte
        String text2 = "Bonjour j'ecrit quelque chose sur une seule ligne";
        fileManager.write(text2);
        assertEquals(text2,fileManager.getRaw());
    }

    @Test
    public void listToFileTest(){
        List<String> listNames = new ArrayList<>();
        listNames.add("file1");
        listNames.add("file2");
        List<FileManager> list = fileManager.ListToFileManager(path , listNames);

        String sep;
        if (System.getProperty("os.name").toLowerCase().contains("win"))
            sep = "\\";
        else
            sep = "/";
        assertEquals(list.get(0).getFile().getName() , "file1");
        assertEquals(list.get(0).getFile().getPath() , "testFile"+sep+"file1");
        assertEquals(list.get(1).getFile().getName() , "file2");
        assertEquals(list.get(1).getFile().getPath() , "testFile"+sep+"file2");

    }

    @AfterEach
    public void deleteFile(){
        File[] files = directory.listFiles();

        for(int i = 0; i < files.length; i++) {
            files[i].delete();
        }
        directory.delete();
    }

}
