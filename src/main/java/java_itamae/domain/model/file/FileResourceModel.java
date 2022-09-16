package java_itamae.domain.model.file;

import java.io.Serializable;
import java_itamae.domain.model.common.NotWindows;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/** file resource のモデルクラス */
public class FileResourceModel implements Serializable {
  private static final long serialVersionUID = 1L;

  /** リソース名 */
  private final String resourceName;

  /** 実行する動作の名前 */
  @NotNull
  @Pattern(regexp = "create|delete")
  private String action;

  /** 操作対象とするファイルのパス */
  @NotNull private String path;

  /** ファイル所有者の名前 */
  private String owner;

  /** ファイルのグループ所有者の名前 */
  @NotWindows private String group;

  /** ファイルパーミッションの設定値 */
  @Pattern(regexp = "[0-7]{3}")
  @NotWindows
  private String mode;

  /** 初期化処理を実行する。 */
  public FileResourceModel() {
    this.resourceName = "file";
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
   *       <li>create: ファイルを作成する。
   *       <li>delete: ファイルを削除する。
   *     </ul>
   */
  public void setAction(final String action) {
    this.action = action;
  }

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
   * ファイルの所有者名を返す。
   *
   * @return owner ファイルの所有者名
   */
  public String getOwner() {
    return owner;
  }

  /**
   * ファイルの所有者名を設定する。
   *
   * @param owner ファイルに設定する所有者名を指定する。
   */
  public void setOwner(final String owner) {
    this.owner = owner;
  }

  /**
   * ファイルのグループ所有者名を返す。
   *
   * @return group ファイルのグループ所有者名
   */
  public String getGroup() {
    return group;
  }

  /**
   * ファイルのグループ所有者名を設定する。
   *
   * @param group ファイルに設定するグループ所有者名
   */
  public void setGroup(final String group) {
    this.group = group;
  }

  /**
   * ファイルのパーミッション値を返す。
   *
   * @return mode ファイルのパーミッション値
   */
  public String getMode() {
    return mode;
  }

  /**
   * ファイルのパーミッション値を設定する。
   *
   * @param mode ファイルに設定するパーミッション値を 3 桁の整数で指定する。
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
}
