package name.tlphat.ministore.server.entities;

public class BasicString implements String {

  private final java.lang.String value;

  public BasicString(java.lang.String value) {
    this.value = value;
  }

  @Override
  public boolean exceedsSizeLimit(int maxNumberOfCharacters) {
    return value.length() > maxNumberOfCharacters;
  }
}
