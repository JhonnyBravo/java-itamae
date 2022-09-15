package java_itamae.domain.model.template;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Optional;

/** template resource のモデルクラス。 */
@IsValidPath
@IsValidSource
public class TemplateResourceModel implements Serializable {
  private static final long serialVersionUID = 1L;

  /** リソース名 */
  private final String resourceName;

  /** 実行する動作の名前 */
  @NotNull
  @Pattern(regexp = "create|delete")
  private String action;

  /** 書込みの対象とするファイルのパス */
  @NotNull private String path;

  /** 読込みの対象とするファイルのパス */
  private String source;

  /** ファイルの読書きに使用する文字エンコーディング */
  private String encoding;

  /** 初期化処理を実行する。 */
  public TemplateResourceModel() {
    this.resourceName = "template";
  }

  /**
   * リソース名を返す。
   *
   * @return resourceName リソース名
   */
  public String getResourceName() {
    return resourceName;
  }

  /**
   * 実行する動作名を返す。
   *
   * @return action 動作名
   */
  public String getAction() {
    return action;
  }

  /**
   * 実行する動作名を設定する。
   *
   * @param action 動作名を指定する。
   *     <ul>
   *       <li>create: テキストファイルの内容を上書きする。
   *       <li>delete: テキストファイルの内容を全削除する。
   *     </ul>
   */
  public void setAction(final String action) {
    this.action = action;
  }

  /**
   * 書込み対象とするテキストファイルのパスを返す。
   *
   * @return path 書込み対象とするファイルのパス。
   */
  public String getPath() {
    return path;
  }

  /**
   * 書込み対象とするテキストファイルのパスを設定する。
   *
   * @param path 書込み対象とするファイルのパスを指定する。
   */
  public void setPath(final String path) {
    this.path = path;
  }

  /**
   * 読込み対象とするテキストファイルのパスを返す。
   *
   * @return source 読込み対象とするファイルのパス。
   */
  public String getSource() {
    return source;
  }

  /**
   * 読込み対象とするテキストファイルのパスを設定する。
   *
   * @param source 読込み対象とするファイルのパスを指定する。
   */
  public void setSource(final String source) {
    this.source = source;
  }

  /**
   * ファイル読書きの実行時に使用する文字エンコーディングの名称を返す。
   *
   * @return encoding 文字エンコーディング。
   */
  public String getEncoding() {
    final Optional<String> value = Optional.ofNullable(encoding);
    return value.orElse(Charset.defaultCharset().displayName());
  }

  /**
   * ファイル読書きの実行時に使用する文字エンコーディングを設定する。
   *
   * @param encoding 文字エンコーディングを指定する。
   */
  public void setEncoding(final String encoding) {
    this.encoding = encoding;
  }
}
