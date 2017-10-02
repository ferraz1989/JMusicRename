package jMusicRename;

public class Mp3Info {

  private String path;
  private String genre;
  private String country;
  private String artist;
  private String album;
  private String track;

  public Mp3Info(String filePath) {
    int depth = getPathDepth(filePath);

    // Genre - Country - Artist - Album - Track
    if (depth == 5) {
      path = filePath;
      genre = path.substring(0, path.indexOf("/"));
      path = path.substring(genre.length() + 1);
      country = path.substring(0, path.indexOf("/"));
      path = path.substring(country.length() + 1);
      artist = path.substring(0, path.indexOf("/"));
      path = path.substring(artist.length() + 1);
      album = path.substring(0, path.indexOf("/"));
      path = path.substring(album.length() + 1);
      track = path;
    }

    // Genre - Artist - Album - Track
    else if (depth == 4) {
      path = filePath;
      genre = path.substring(0, path.indexOf("/"));
      path = path.substring(genre.length() + 1);
      artist = path.substring(0, path.indexOf("/"));
      path = path.substring(artist.length() + 1);
      album = path.substring(0, path.indexOf("/"));
      path = path.substring(album.length() + 1);
      track = path;
    }

    // Genre - Artist - Track
    else if (depth == 3) {
      path = filePath;
      genre = path.substring(0, path.indexOf("/"));
      path = path.substring(genre.length() + 1);
      artist = path.substring(0, path.indexOf("/"));
      path = path.substring(artist.length() + 1);
      track = path;
    }

    // Genre - Track
    else if (depth == 2) {
      path = filePath;
      genre = path.substring(0, path.indexOf("/"));
      path = path.substring(genre.length() + 1);
      track = path;
    }
  }

  private static int getPathDepth(String path) {
    return (int) path.chars().filter(ch -> ch == '/').count() + 1;
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
