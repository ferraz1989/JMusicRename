package jMusicRename;

import java.io.File;

import com.mpatric.mp3agic.Mp3File;

public class Control {

  static int filesTotal = 0;
  static int filesWith1With2 = 0;
  static int filesWith1Without2 = 0;
  static int filesWithout1With2 = 0;
  static int filesWithout1Without2 = 0;
  static int filesError = 0;

  public static void renameMusic(String path) {
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
          + " hasId3v2Tag: " + mp3file.hasId3v2Tag());
    } catch (Exception e) {
      filesError++;
      System.out.println("Error in file: " + file.getName());
    }
  }
}
