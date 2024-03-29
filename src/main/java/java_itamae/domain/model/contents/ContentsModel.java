package java_itamae.domain.model.contents;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Optional;

/** 読み書きの対象とするファイルの情報を管理する。 */
public class ContentsModel implements Serializable {
  private static final long serialVersionUID = 1L;
  /** 操作対象とするファイルのパス */
  @NotNull private String path;
  /** ファイルの読書きに使用する文字エンコーディング */
  private String encoding;

  /**
   * 操作対象とするファイルのパスを返す。
   *
   * @return path 操作対象とするファイルのパス
   */
  public String getPath() {
    return path;
  }

  /**
   * 操作対象とするファイルのパスを設定する。
   *
   * @param path 操作対象とするファイルのパス
   */
  public void setPath(final String path) {
    this.path = path;
  }

  /**
   * 操作対象とするファイルの文字エンコーディングを返す。
   *
   * @return encoding 文字エンコーディング
   */
  public String getEncoding() {
    final Optional<String> value = Optional.ofNullable(encoding);
    return value.orElse(Charset.defaultCharset().displayName());
  }

  /**
   * 操作対象とするファイルの文字エンコーディングを設定する。
   *
   * @param encoding 文字エンコーディング
   */
  public void setEncoding(final String encoding) {
    this.encoding = encoding;
  }
}
