package java_itamae.domain.service.contents;

import java.util.List;
import java_itamae.domain.component.contents.ContentsComponent;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.status.Status;
import java_itamae.domain.service.common.BaseService;

/** ファイルの読み書きを操作する。 */
public interface ContentsService extends BaseService {
  /**
   * 変数 model へ操作対象とするテキストファイルの情報を収めた {@link ContentsModel} を設定する。
   *
   * @param model 操作対象とするファイルの情報を収めた {@link ContentsModel} を指定する。
   */
  void init(ContentsModel model);

  /**
   * テキストファイルを読込み、 {@link String} の {@link List} に変換して返す。
   *
   * <ol>
   *   <li>{@link ContentsComponent#getContents(ContentsModel)} から取得した {@link List} を返り値として返す。
   *   <li>
   * </ol>
   *
   * @return contents テキストファイルの内容を格納した {@link List} を返す。
   * @throws Exception ファイルの読込中に発生した例外を投げる。
   */
  List<String> getContents() throws Exception;

  /**
   * テキストファイルを上書きする。
   *
   * <ol>
   *   <li>{@link ContentsComponent#updateContents(ContentsModel, List)} から取得した {@link Status}
   *       を返り値として返す。
   * </ol>
   *
   * @param contents 書込み対象とする文字列を収めた {@link List} を指定する。
   * @return status {@link Status} を返す。
   * @throws Exception ファイルの書込み中に発生した例外を投げる。
   */
  Status updateContents(List<String> contents) throws Exception;

  /**
   * テキストファイルの内容を空にする。
   *
   * <ol>
   *   <li>{@link ContentsComponent#deleteContents(ContentsModel)} から取得した {@link Status} を返り値として返す。
   * </ol>
   *
   * @return status {@link Status} を返す。
   * @throws Exception ファイルの内容削除中に発生した例外を投げる。
   */
  Status deleteContents() throws Exception;
}
