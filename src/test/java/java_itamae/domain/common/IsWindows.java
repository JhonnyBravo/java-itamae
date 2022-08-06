package java_itamae.domain.common;

import java.util.function.Supplier;

import org.springframework.stereotype.Service;

/** OS が Windows であるかどうかを判定する。 */
@Service
public class IsWindows implements Supplier<Boolean> {
  /**
   * OS が Windows であるかどうかを判定する。
   *
   * @return result
   *     <ul>
   *       <li>OS が Windows である場合: true を返す。
   *       <li>OS が Windows ではない場合: false を返す。
   *     </ul>
   */
  @Override
  public Boolean get() {
    boolean result = false;

    if (System.getProperty("os.name").substring(0, 3).equals("Win")) {
      result = true;
    }

    return result;
  }
}
