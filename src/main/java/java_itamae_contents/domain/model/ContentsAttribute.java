package java_itamae_contents.domain.model;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 読み書きの対象とするファイルの情報を管理する。
 */
public class ContentsAttribute implements Serializable {
  private static final long serialVersionUID = 1L;

  @NotNull
  private String path;
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
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * 操作対象とするファイルの文字エンコーディングを返す。
   *
   * @return encoding 文字エンコーディング
   */
  public String getEncoding() {
    return encoding;
  }

  /**
   * 操作対象とするファイルの文字エンコーディングを設定する。
   *
   * @param encoding 文字エンコーディング
   */
  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }
}
