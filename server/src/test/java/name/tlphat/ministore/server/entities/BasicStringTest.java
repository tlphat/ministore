package name.tlphat.ministore.server.entities;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BasicStringTest {

  @Test
  void stringExceedsSizeLimit() {
    final BasicString input = new BasicString("123456");
    assertTrue(input.exceedsSizeLimit(5));
  }

  @Test
  void stringWithinSizeLimit() {
    final BasicString input = new BasicString("12345");
    assertFalse(input.exceedsSizeLimit(5));
  }
}
