package jMusicRename;

public class Mp3Info {

  private String path;
  private String genre;
  private String country;
  private String artist;
  private String album;
  private String track;

  public Mp3Info(String filePath) {
    path = filePath;
    genre = path.substring(0, path.indexOf("/"));
    path = path.substring(path.indexOf(genre));
    country = path.substring(0, path.indexOf("/"));
    path = path.substring(path.indexOf(country));
    artist = path.substring(0, path.indexOf("/"));
    path = path.substring(path.indexOf(artist));
    album = path.substring(0, path.indexOf("/"));
    path = path.substring(path.indexOf(album));
    track = path.substring(0, path.indexOf("/"));
  }

  public String getGenre() {
    return genre;
  }

  public String getCountry() {
    return country;
  }

  public String getArtist() {
    return artist;
  }

  public String getAlbum() {
    return album;
  }

  public String getTrack() {
    return track;
  }
}
