package java_itamae.domain.component.contents;

import java.util.List;

import java_itamae.domain.component.common.StreamComponent;
import java_itamae.domain.model.contents.ContentsModel;

/** テキストファイルの読み書きを管理する。 */
public interface ContentsComponent extends StreamComponent {
  /**
   * テキストファイルの内容を読込んで {@link List} へ変換して返す。
   *
   * @param model 操作対象とするテキストファイルの情報を収めた {@link ContentsModel} を指定する。
   * @return contents 変換された {@link List}
   * @throws Exception {@link Exception}
   */
  List<String> getContents(ContentsModel model) throws Exception;

  /**
   * テキストファイルへの書込みを実行する。
   *
   * @param model 操作対象とするテキストファイルの情報を収めた {@link ContentsModel} を指定する。
   * @param contents 書込み対象とする文字列を収めた {@link List} を指定する。
   * @return status 終了ステータスを返す。
   *     <ul>
   *       <li>0: 何も実行せずに正常終了したことを表す
   *       <li>1: 異常終了したことを表す。
   *       <li>2: 書込みを実行して正常終了したことを表す。
   *     </ul>
   */
  int updateContents(ContentsModel model, List<String> contents);

  /**
   * テキストファイルの内容を空にする。
   *
   * @param model 操作対象とするテキストファイルの情報を収めた {@link ContentsModel} を指定する。
   * @return status 終了ステータスを返す。
   *     <ul>
   *       <li>0: 何も実行せずに正常終了したことを表す
   *       <li>1: 異常終了したことを表す。
   *       <li>2: 削除を実行して正常終了したことを表す。
   *     </ul>
   */
  int deleteContents(ContentsModel model);
}
