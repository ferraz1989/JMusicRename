package jMusicRename;

import java.io.File;

public class Control {

  public static void renameMusic(String path) {
    File folder = new File(path);
    renameMusic(folder);
  }

  private static void renameMusic(File folder) {
    if (folder.isDirectory()) {
      System.out.println("Directory " + folder.getName());
      for (File file : folder.listFiles()) {
        if (file.isFile()) {
          System.out.println("File " + file.getName());
        } else if (file.isDirectory()) {
          renameMusic(file);
        }
      }
    }
  }

}