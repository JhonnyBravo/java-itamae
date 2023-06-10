package java_itamae.domain.model.status;

/** 終了ステータスを表す。 */
public enum Status {
  /** 初期状態を表す。動作を実行せずに正常終了した場合に使用する。 */
  INIT(0),
  /** 動作の実行に成功したことを表す。動作を実行して正常終了した場合に使用する。 */
  DONE(2),
  /** 動作の実行中にエラーが発生したことを表す。動作の実行中に異常終了した場合に使用する。 */
  ERROR(1);

  private int status;

  Status(final int status) {
    this.status = status;
  }

  /**
   * 定数の値を返す。
   *
   * @return status
   *     <ul>
   *       <li>{@link Status#INIT}: 0
   *       <li>{@link Status#DONE}: 2
   *       <li>{@link Status#ERROR}: 1
   *     </ul>
   */
  public int getValue() {
    return this.status;
  }
}
