package java_itamae.domain.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import java_itamae.app.validator.NotWindows;

/**
 * リソースの管理に使用する属性を管理する。
 */
public class Attribute implements Serializable {
  private static final long serialVersionUID = 1L;

  @NotNull
  private String path;
  private String owner;
  @NotWindows
  private String group;
  @Pattern(regexp = "[0-7]{3}")
  @NotWindows
  private String mode;

  /**
   * 操作対象とするリソースのパスを返す。
   *
   * @return path 操作対象とするリソースのパス
   */
  public String getPath() {
    return path;
  }

  /**
   * 操作対象とするリソースのパスを設定する。
   *
   * @param path 操作対象とするリソースのパス
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * リソースの所有者を返す。
   *
   * @return owner リソースの所有者
   */
  public String getOwner() {
    return owner;
  }

  /**
   * リソースの所有者を設定する。
   *
   * @param owner リソースに設定する所有者
   */
  public void setOwner(String owner) {
    this.owner = owner;
  }

  /**
   * リソースのグループ所有者を返す。
   *
   * @return group リソースのグループ所有者
   */
  public String getGroup() {
    return group;
  }

  /**
   * リソースのグループ所有者を設定する。
   *
   * @param group リソースに設定するグループ所有者
   */
  public void setGroup(String group) {
    this.group = group;
  }

  /**
   * リソースのパーミッション値を返す。
   *
   * @return mode リソースのパーミッション値
   */
  public String getMode() {
    return mode;
  }

  /**
   * リソースのパーミッション設定値を設定する。
   *
   * @param mode リソースに設定するパーミッション値を 3 桁の整数で指定する。
   *        <ul>
   *        <li>0: ---</li>
   *        <li>1: --x</li>
   *        <li>2: -w-</li>
   *        <li>3: -wx</li>
   *        <li>4: r--</li>
   *        <li>5: r-x</li>
   *        <li>6: rw-</li>
   *        <li>7: rwx</li>
   *        </ul>
   */
  public void setMode(String mode) {
    this.mode = mode;
  }
}
