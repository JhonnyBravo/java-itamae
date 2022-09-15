package java_itamae.domain.service.contents;

import java.util.List;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.service.common.BaseService;

/** ファイルの読み書きを操作する。 */
public interface ContentsService extends BaseService {
  /**
   * 初期化処理を実行する。
   *
   * @param model 操作対象とするファイルの情報を収めた {@link ContentsModel} を指定する。
   */
  void init(ContentsModel model);

  /**
   * ファイルを読込み、 {@link List} に変換して返す。
   *
   * @return contents 変換された {@link List}
   * @throws Exception {@link Exception}
   */
  List<String> getContents() throws Exception;

  /**
   * ファイルを上書きする。
   *
   * @param contents 書込み対象とする文字列を収めた {@link List} を指定する。
   * @return status 終了ステータスを返す。
   *     <ul>
   *       <li>0: 何も実行せずに正常終了したことを表す
   *       <li>1: 異常終了したことを表す。
   *       <li>2: 書込みを実行して正常終了したことを表す。
   *     </ul>
   */
  int updateContents(List<String> contents);

  /**
   * ファイルを空にする。
   *
   * @return status 終了ステータスを返す。
   *     <ul>
   *       <li>0: 何も実行せずに正常終了したことを表す
   *       <li>1: 異常終了したことを表す。
   *       <li>2: 削除を実行して正常終了したことを表す。
   *     </ul>
   */
  int deleteContents();
}
