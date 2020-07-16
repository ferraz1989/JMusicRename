package jMusicRename;

import java.io.File;

import com.mpatric.mp3agic.ID3v1Genres;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.Mp3File;

public class Control {

  private static String rootPath;
  private static int filesOk = 0;
  private static int filesError = 0;

  public static void renameMusic(String path) {
    rootPath = path;
    long t1 = System.currentTimeMillis();
    File folder = new File(path);
    renameMusicFolder(folder);
    long t2 = System.currentTimeMillis();
    System.out.println("Files without error: " + filesOk);
    System.out.println("Files with error: " + filesError);
    System.out.println("Time: " + (t2 - t1) / 1000 / 60 + " minutes.");
  }

  private static void renameMusicFolder(File folder) {
    System.out.println("Folder: " + folder.getPath());
    for (File file : folder.listFiles()) {
      if (file.isDirectory()) {
        renameMusicFolder(file);
      } else if (file.isFile()) {
        renameMusicFile(file);
      }
    }
  }

  private static void renameMusicFile(File file) {
    try {
      final String absolutePath = file.getPath();
      final String relativePath = absolutePath.substring(rootPath.length());
      final String fileName = relativePath.substring(relativePath.lastIndexOf('/') + 1);

      StringBuilder folderStructure = new StringBuilder(relativePath.substring(0, relativePath.lastIndexOf('/')));
      final String genre = getNextFolder(folderStructure);
      final String artist = getNextFolder(folderStructure);
      final String album = getNextFolder(folderStructure);

      final File fileTemp = new File(absolutePath + "Test");
      file.renameTo(fileTemp);
      final Mp3File mp3file = new Mp3File(fileTemp.getPath());

      final ID3v2 id3v2Tag = mp3file.hasId3v2Tag() ? mp3file.getId3v2Tag() : new ID3v24Tag();
      id3v2Tag.setGenre(ID3v1Genres.matchGenreDescription(genre));
      id3v2Tag.setArtist(artist);
      id3v2Tag.setAlbum(album);
      id3v2Tag.setTrack(fileName);
      mp3file.setId3v2Tag(id3v2Tag);

      mp3file.save(absolutePath);
      fileTemp.delete();
      filesOk++;
    } catch (Exception e) {
      filesError++;
      System.out.println("Error in " + file.getPath() + ": " + e);
    }
  }

  private static String getNextFolder(StringBuilder folderStructure) {
    if (folderStructure.toString().indexOf('/') == -1) {
      return "";
    }
    String folder = folderStructure.substring(1);
    if (folder.indexOf('/') != -1) {
      folder = folder.substring(0, folder.indexOf('/'));
    }
    folderStructure.delete(0, folder.length() + 1);
    return folder;
  }
}
