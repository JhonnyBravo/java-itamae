package java_itamae.domain.component.common;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java_itamae.domain.model.contents.ContentsModel;

/** ファイルストリームを管理する。 */
public interface StreamComponent extends BaseComponent {

  /**
   * {@link Reader} を取得する。
   *
   * @param model 読込み対象とするテキストファイルの情報を収めた {@link ContentsModel} を指定する。
   * @return reader {@link Reader} を返す。
   * @throws Exception {@link Exception}
   */
  default Reader getReader(final ContentsModel model) throws Exception {
    final Path path = this.convertToPath(model.getPath());

    if (!path.toFile().isFile()) {
      throw new FileNotFoundException(model.getPath() + " が見つかりません。");
    }

    return Files.newBufferedReader(path, Charset.forName(model.getEncoding()));
  }

  /**
   * {@link Writer} を取得する。
   *
   * @param model 書込み対象とするテキストファイルの情報を収めた {@link ContentsModel} を指定する。
   * @return writer {@link Writer} を返す。
   * @throws Exception {@link Exception}
   */
  default Writer getWriter(final ContentsModel model) throws Exception {
    final Path path = this.convertToPath(model.getPath());

    if (!path.toFile().isFile()) {
      throw new FileNotFoundException(model.getPath() + " が見つかりません。");
    }

    return Files.newBufferedWriter(path, Charset.forName(model.getEncoding()));
  }
}
