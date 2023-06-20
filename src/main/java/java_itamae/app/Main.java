package java_itamae.app;

import java_itamae.domain.model.status.Status;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/** {@link Application} を起動する。 */
public class Main {
  /** {@link Application} を起動する。 */
  @SuppressWarnings("unused")
  public static void main(final String[] args) {
    final Weld weld = new Weld();
    Status status = Status.INIT;

    try (WeldContainer container = weld.initialize()) {
      final Application app = container.select(Application.class).get();
      status = app.main(args);
    }

    System.exit(status.getValue());
  }
}
