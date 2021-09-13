import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static String zipFolderPath = "C:\\Users\\nikolskaya\\Desktop\\Games\\savegames\\saves.zip";
    public static String unZipFolderPath = "C:\\Users\\nikolskaya\\Desktop\\Games\\savegames";

    public static void main(String[] args) {
        openZip(zipFolderPath, unZipFolderPath);
        System.out.println(openProgress(unZipFolderPath + "\\save2.dat"));
    }

    public static void openZip(String zipFolderPath, String unZipFolderPath) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFolderPath))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fos = new FileOutputStream(unZipFolderPath + "\\" + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fos.write(c);
                }
                fos.flush();
                zin.closeEntry();
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameProgress openProgress(String PathForDeserialize) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(PathForDeserialize);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return gameProgress;
    }
}
