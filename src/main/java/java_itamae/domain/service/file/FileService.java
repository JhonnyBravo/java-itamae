package java_itamae.domain.service.file;

import java_itamae.domain.model.file.FileResourceModel;
import java_itamae.domain.service.common.BaseService;

/** ファイルの操作を管理する。 */
public interface FileService extends BaseService {
  /**
   * ファイルを作成し、所有者・グループ所有者・パーミッションの設定を変更する。
   *
   * @param model 操作対象とするファイルの情報を納めた {@link FileResourceModel} を指定する。
   * @return status
   *     <ul>
   *       <li>0: 操作を実行せずに正常終了したことを表す。
   *       <li>1: 異常終了したことを表す。
   *       <li>2: 操作を実行して正常終了したことを表す。
   *     </ul>
   */
  int create(FileResourceModel model);

  /**
   * ファイルを削除する。
   *
   * @param model 操作対象とするファイルの情報を納めた {@link FileResourceModel} を指定する。
   * @return status
   *     <ul>
   *       <li>0: 操作を実行せずに正常終了したことを表す。
   *       <li>1: 異常終了したことを表す。
   *       <li>2: 操作を実行して正常終了したことを表す。
   *     </ul>
   */
  int delete(FileResourceModel model);
}
