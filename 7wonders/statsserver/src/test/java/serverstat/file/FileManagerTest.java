package serverstat.file;

import log.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {
    FileManager fileManager;
    String path = "testFile/";
    File directory = new File("testFile");

    @BeforeEach
    void init(){
        Logger.logger.verbose = false;
        fileManager = new FileManager(path+"testFile");
        directory.mkdir();
    }


    /**
     * Ce test permet de verifier si lorsque le file est existant, il est bien detecter
     * Et s'il ne l'est pas il ne doit pas etres la
     */
    @Test
    void existsTest(){
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


    /**
     * Ce test permet de verifier si lorsqu'il y a ecriture dans le fichier le contenu est bien present
     * et que c'est le bon contenu
     */
    @Test
    void writeAndGetFileContentTest(){
        String content = "hello world";
        fileManager.create();
        fileManager.write(content);
        assertEquals(content,fileManager.getRaw());
    }

    /**
     * Ici on verifie que lorsque le fichier n'existe pas et qu'on essais d'ecrire dedans
     * il se cree et l'ecriture est bien presente
     */
    @Test
    void creatWhenWriteTest(){
        //on suprime le file
        fileManager.deleteFile();
        String content = "hello world";
        // le file est bien suprimer
        assertFalse(fileManager.getFile().exists());

        //on essais d'ecrire dans le file supprimer
        fileManager.write(content);

        //le file se cree
        assertTrue(fileManager.getFile().exists());
        assertNotNull(fileManager.getFile());
        assertEquals(content,fileManager.getRaw());
        assertEquals(path,fileManager.getParentPath()+"/");

    }

    /**
     * Ce test permet de verifier si lorsqu'on vide un fichier il est bien vider
     */
    @Test
    void clearFileTest(){
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

    /**
     * Ce tets permet de verifier si on demande le texte dans le fichier
     * c'est bien le bon texte qui est retourner
     */
    @Test
    void getRawTest(){
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

    /**
     * Ce test permet de verifier que lorsqu'on essais de cree une liste de fichier
     * cette taches s'effectue avec succée
     */
    @Test
    void listToFileTest(){
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
    void deleteFile(){
        File[] files = directory.listFiles();

        for(int i = 0; i < files.length; i++) {
            files[i].delete();
        }
        directory.delete();
    }

}
