package jMusicRename;

import java.io.File;

import com.mpatric.mp3agic.Mp3File;

public class Control {

  private static String rootPath;

  private static int filesTotal = 0;
  private static int filesWith1With2 = 0;
  private static int filesWith1Without2 = 0;
  private static int filesWithout1With2 = 0;
  private static int filesWithout1Without2 = 0;
  private static int filesError = 0;

  public static void renameMusic(String path) {
    rootPath = path;
    long t1 = System.currentTimeMillis();
    File folder = new File(path);
    renameMusicFolder(folder);
    System.out.println("filesTotal: " + filesTotal);
    System.out.println("filesWith1With2: " + filesWith1With2);
    System.out.println("filesWith1Without2: " + filesWith1Without2);
    System.out.println("filesWithout1With2: " + filesWithout1With2);
    System.out.println("filesWithout1Without2: " + filesWithout1Without2);
    System.out.println("filesError: " + filesError);
    long t2 = System.currentTimeMillis();
    System.out.println("Time: " + (t2 - t1) / 1000 / 60 + " minutes.");
  }

  private static void renameMusicFolder(File folder) {
    if (folder.isDirectory()) {
      System.out.println("Directory " + folder.getName());
      for (File file : folder.listFiles()) {
        if (file.isFile()) {
          renameMusicFile(file);
        } else if (file.isDirectory()) {
          renameMusicFolder(file);
        }
      }
    }
  }

  private static void renameMusicFile(File file) {
    filesTotal++;
    try {
      Mp3Info mp3info = new Mp3Info(file.getPath().substring(file.getPath().indexOf(rootPath)));
      Mp3File mp3file = new Mp3File(file.getPath());
      if (mp3file.hasId3v1Tag() && mp3file.hasId3v2Tag()) {
        filesWith1With2++;
      } else if (mp3file.hasId3v1Tag() && !mp3file.hasId3v2Tag()) {
        filesWith1Without2++;
      } else if (!mp3file.hasId3v1Tag() && mp3file.hasId3v2Tag()) {
        filesWithout1With2++;
      } else {
        filesWithout1Without2++;
      }
      System.out.println("File " + file.getName() + " hasId3v1Tag: " + mp3file.hasId3v1Tag()
          + " hasId3v2Tag: " + mp3file.hasId3v2Tag() + ".");
      System.out.println("Genre " + mp3info.getGenre());
      System.out.println("Country " + mp3info.getCountry());
      System.out.println("Artist " + mp3info.getArtist());
      System.out.println("Album " + mp3info.getAlbum());
      System.out.println("Track " + mp3info.getTrack());
    } catch (Exception e) {
      filesError++;
      System.out.println("Error in file: " + file.getName());
    }
  }
}
