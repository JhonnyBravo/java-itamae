package java_itamae.domain.service.file;

import java_itamae.domain.component.file.FileComponent;
import java_itamae.domain.component.group.GroupComponent;
import java_itamae.domain.component.mode.ModeComponent;
import java_itamae.domain.component.owner.OwnerComponent;
import java_itamae.domain.model.file.FileResourceModel;
import java_itamae.domain.model.status.Status;
import java_itamae.domain.service.common.BaseService;

/** ファイルの操作を管理する。 */
public interface FileService extends BaseService {
  /**
   * ファイルを作成し、所有者・グループ所有者・パーミッションの設定を変更する。
   *
   * <ol>
   *   <li>変数 status へ {@link Status#INIT} を設定する。
   *   <li>{@link FileComponent#create(String)} を実行し、返り値を変数 status へ設定する。
   *   <li>{@link FileResourceModel#getOwner()} を実行し、値が null であるか確認する。
   *       <ul>
   *         <li>null である場合、後続処理へ遷移する。
   *         <li>null ではない場合、 {@link OwnerComponent#updateOwner(String, String)} を実行し、返り値を変数 status
   *             へ設定する。
   *       </ul>
   *   <li>{@link FileResourceModel#getGroup()} を実行し、値が null であるか確認する。
   *       <ul>
   *         <li>null である場合、後続処理へ遷移する。
   *         <li>null ではない場合、 {@link GroupComponent#updateGroup(String, String)} を実行し、返り値を変数 status
   *             へ設定する。
   *       </ul>
   *   <li>{@link FileResourceModel#getMode()} を実行し、値が null であるか確認する。
   *       <ul>
   *         <li>null である場合、後続処理へ遷移する。
   *         <li>null ではない場合、 {@link ModeComponent#updateMode(String, String)} を実行し、返り値を変数 status
   *             へ設定する。
   *       </ul>
   *   <li>返り値として変数 status を返す。
   * </ol>
   *
   * @param model 操作対象とするファイルの情報を納めた {@link FileResourceModel} を指定する。
   * @return status {@link Status} を返す。
   * @throws Exception 操作実行中に発生した例外を投げる。
   */
  Status create(FileResourceModel model) throws Exception;

  /**
   * ファイルを削除する。
   *
   * <ol>
   *   <li>変数 status へ {@link Status#INIT} を設定する。
   *   <li>{@link FileComponent#delete(String)} を実行し、返り値を変数 status へ設定する。
   *   <li>返り値として変数 status を返す。
   * </ol>
   *
   * @param model 操作対象とするファイルの情報を納めた {@link FileResourceModel} を指定する。
   * @return status {@link Status} を返す。
   * @throws Exception ファイル削除中に発生した例外を投げる。
   */
  Status delete(FileResourceModel model) throws Exception;
}
