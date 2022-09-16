package java_itamae.domain.model.directory;

import java.io.Serializable;
import java_itamae.domain.model.common.NotWindows;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/** directory resource のモデルクラス */
public class DirectoryResourceModel implements Serializable {
  private static final long serialVersionUID = 1L;
  /** リソース名 */
  private final String resourceName;

  /** 実行する動作の名前 */
  @NotNull
  @Pattern(regexp = "create|delete")
  private String action;

  /** 操作対象とするディレクトリのパス */
  @NotNull private String path;

  /** ディレクトリ所有者の名前 */
  private String owner;

  /** ディレクトリのグループ所有者の名前 */
  @NotWindows private String group;

  /** ディレクトリパーミッションの値 */
  @Pattern(regexp = "[0-7]{3}")
  @NotWindows
  private String mode;

  /** ディレクトリを再帰的に操作するかどうかを表すフラグ */
  @Pattern(regexp = "true|false")
  private String recursive;

  /** 初期化処理を実行する。 */
  public DirectoryResourceModel() {
    this.resourceName = "directory";
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
   * @param action 動作名を指定する。。
   *     <ul>
   *       <li>create: ディレクトリを作成する。
   *       <li>delete: ディレクトリを削除する。
   *     </ul>
   */
  public void setAction(final String action) {
    this.action = action;
  }

  /**
   * 操作対象とするディレクトリのパスを返す。
   *
   * @return path 操作対象とするディレクトリのパス
   */
  public String getPath() {
    return path;
  }

  /**
   * 操作対象とするディレクトリのパスを設定する。
   *
   * @param path 操作対象とするディレクトリのパス
   */
  public void setPath(final String path) {
    this.path = path;
  }

  /**
   * ディレクトリの所有者名を返す。
   *
   * @return owner ディレクトリの所有者名
   */
  public String getOwner() {
    return owner;
  }

  /**
   * ディレクトリの所有者名を設定する。
   *
   * @param owner ディレクトリに設定する所有者名を指定する。
   */
  public void setOwner(final String owner) {
    this.owner = owner;
  }

  /**
   * ディレクトリのグループ所有者名を返す。
   *
   * @return group ディレクトリのグループ所有者名
   */
  public String getGroup() {
    return group;
  }

  /**
   * ディレクトリのグループ所有者名を設定する。
   *
   * @param group ディレクトリに設定するグループ所有者名
   */
  public void setGroup(final String group) {
    this.group = group;
  }

  /**
   * ディレクトリのパーミッション値を返す。
   *
   * @return mode ディレクトリのパーミッション値
   */
  public String getMode() {
    return mode;
  }

  /**
   * ディレクトリのパーミッション値を設定する。
   *
   * @param mode ディレクトリに設定するパーミッション値を 3 桁の整数で指定する。
   *     <ul>
   *       <li>0: ---
   *       <li>1: --x
   *       <li>2: -w-
   *       <li>3: -wx
   *       <li>4: r--
   *       <li>5: r-x
   *       <li>6: rw-
   *       <li>7: rwx
   *     </ul>
   */
  public void setMode(final String mode) {
    this.mode = mode;
  }

  /**
   * ディレクトリを再帰的に操作するかどうを真偽値で返す。
   *
   * @return recursive ディレクトリを再帰的に操作するかどうかを表す真偽値。
   */
  public boolean isRecursive() {
    return Boolean.parseBoolean(this.recursive);
  }

  /**
   * ディレクトリを再帰的に操作するかどうかを真偽値で設定する。
   *
   * @param recursive ディレクトリを再帰的に操作するかどうかを表す真偽値を指定する。
   *     <ul>
   *       <li>true: ディレクトリを再帰的に操作する。
   *       <li>false: ディレクトリを再帰的に操作しない。
   *     </ul>
   */
  public void setRecursive(final String recursive) {
    this.recursive = recursive;
  }
}
