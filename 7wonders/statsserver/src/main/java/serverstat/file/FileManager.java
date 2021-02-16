package serverstat.file;

import log.LoggerComponent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FileManager est une class qui permet une gestion simplifiee
 * des fichiers en Java.
 */
public class FileManager
{
    /* FIELDS */
    /** file represente un fichier que le fileManager s'occupe */
    private File file;

    /**
     * Constructeur a l'aide d'une String
     * @param path une String representant le chemin du fichier voulue
     */
    public FileManager (String path)
    {this.file = new File(path);}

    /**
     * Constructeur a l'aide d'un file
     * @param file le file qui vas etre utiliser
     */
    public FileManager (File file)
    {this.file = file;}


    /**
     * Permet de creer plusieurs FileManager a
     * l'aide d'un chemin de de nom de fichier
     * @param path le repertoire a partir duquel sont stockes/creees les fichiers
     * @param filesName la liste des nom de fichier
     * @return une list de FileManager
     */
    public static List<FileManager> ListToFileManager(String path , List<String> filesName) {
        ArrayList<FileManager> filesManager = new ArrayList<FileManager>();
        for (String name : filesName) {
            filesManager.add(new FileManager(new File(path,name)));
        }
        return filesManager;
    }

    /** Permet de creer le fichier
     * @return true : créer; false non créer
     */
    public boolean create()
    {
        try {
            return this.file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace(LoggerComponent.err);
            LoggerComponent.getInstance().error(this.file.getName()+ " ne peut pas être créé sur le chemin " + this.file.getAbsolutePath());
        }
        return false;
    }

    /**
     * Permet de savoir si le fichier existe ou non
     * @return Un boolean
     */
    public boolean exists ()
    {return this.file.exists();}

    /**
     * Permet de remplacer le contenu d'un fichier par content
     * @param content Le futur contenu du fichier
     */
    public void write (String content)
    {
        //si le fichier n'est pas encore créer on le crée
        if (!file.exists()) {
            boolean isCreate = create();
            if(!isCreate) return;
        }

        // Si on a les droits pour ecrire
        if(file.canWrite()){
            PrintWriter pw;
            // On essaye d'ecrire
            try {
                pw = new PrintWriter(this.file);
                // On essaye d'ecrire ici
                pw.write(content);
                pw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace(LoggerComponent.err);
                LoggerComponent.getInstance().error("La fichier " + this.file.getName() + " n'existe pas !");
            }

        } else {
            LoggerComponent.getInstance().error(this.file.getName() + " ne peut pas etre modifié !");
        }

    }

    /**
     * Permet de renvoyer tout le contenu d'un fichier
     * sous forme brut
     * @return un String
     */
    public String getRaw()
    {

        //si le fichier n'est pas créer
        if (!file.exists()) return null;

        // Si on a les droits pour lire le fichier
        if (this.file.canRead())
        {
            // On essaye de lire la premiere ligne
            /* INITIALISATION */
            FileReader r = null;
            BufferedReader br = null;
            StringBuilder resultString = new StringBuilder();
            try
            {
                /* INITIALISATION */
                r = new FileReader(this.file);
                br = new BufferedReader(r);

                /* GET THE CONTENT */
                String tmp;
                //On remet le \n sauf la premiere fois que l'on lit une ligne
                if((tmp = br.readLine()) != null){
                    resultString.append(tmp);
                }
                while ((tmp = br.readLine()) != null)
                {
                    resultString.append("\n"+tmp);
                }



            } catch (Exception e) {
                e.printStackTrace(LoggerComponent.err);
                LoggerComponent.getInstance().error(this.file.getName() + " n'existe pas !");
            } finally {
                /* CLOSE FILESTREAMS */
                try {
                    if(br!=null) {
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace(LoggerComponent.err);
                }
                try {
                    if(r!=null) {
                        r.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace(LoggerComponent.err);
                }
            }
            // On renvoie la premier ligne
            return resultString.toString();
        } else {
            LoggerComponent.getInstance().error(this.file.getName() + " ne peut pas etre lu !");
        }
        return "";
    }

    /**
     * Permet de suprimer un fichier
     * @return true ou false selon la reussite de la suppression
     */
    public boolean deleteFile(){
        if(exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * Permet de recueperer le chemin du repertoire parent
     * @return le chemin du repertoire parent
     */
    public String getParentPath(){
        return file.getParent();
    }

    /** Permet de recuperer le File du file manager
     * @return le file
     */
    public File getFile() {
        return file;
    }

    /** Permet de vider totalement un fichier */
    public void clearFile ()
    {write("");}
}