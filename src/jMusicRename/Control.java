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
      String absolutePath = file.getPath();
      String relativePath = absolutePath.substring(rootPath.length() + 1);
      Mp3Info mp3info = new Mp3Info(relativePath);

      File fileTemp = new File(absolutePath + "Test");
      file.renameTo(fileTemp);
      Mp3File mp3file = new Mp3File(fileTemp.getPath());

      ID3v2 id3v2Tag = mp3file.hasId3v2Tag() ? id3v2Tag = mp3file.getId3v2Tag() : new ID3v24Tag();
      id3v2Tag.setGenre(ID3v1Genres.matchGenreDescription(mp3info.getGenre()));
      id3v2Tag.setComment(mp3info.getCountry());
      id3v2Tag.setArtist(mp3info.getArtist());
      id3v2Tag.setAlbum(mp3info.getAlbum());
      id3v2Tag.setTrack(mp3info.getTrack());
      mp3file.setId3v2Tag(id3v2Tag);

      mp3file.save(absolutePath);
      fileTemp.delete();
      filesOk++;

    } catch (Exception e) {
      filesError++;
      System.out.println("Error in " + file.getPath() + ": " + e);
    }
  }
}
