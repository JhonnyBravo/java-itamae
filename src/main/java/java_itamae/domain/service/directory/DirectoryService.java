package java_itamae.domain.service.directory;

import java_itamae.domain.model.directory.DirectoryResourceModel;
import java_itamae.domain.service.common.BaseService;

/** ディレクトリの操作を管理する。 */
public interface DirectoryService extends BaseService {
  /**
   * ディレクトリを作成し、所有者・グループ所有者・パーミッションの設定を変更する。
   *
   * @param model 操作対象とするディレクトリの情報を納めた {@link DirectoryResourceModel} を指定する。
   * @return status
   *     <ul>
   *       <li>0: 操作を実行せずに正常終了したことを表す。
   *       <li>1: 異常終了したことを表す。
   *       <li>2: 操作を実行して正常終了したことを表す。
   *     </ul>
   */
  int create(DirectoryResourceModel model);

  /**
   * ディレクトリを削除する。
   *
   * @param model 操作対象とするディレクトリの情報を納めた {@link DirectoryResourceModel} を指定する。
   * @return status
   *     <ul>
   *       <li>0: 操作を実行せずに正常終了したことを表す。
   *       <li>1: 異常終了したことを表す。
   *       <li>2: 操作を実行して正常終了したことを表す。
   *     </ul>
   */
  int delete(DirectoryResourceModel model);
}
