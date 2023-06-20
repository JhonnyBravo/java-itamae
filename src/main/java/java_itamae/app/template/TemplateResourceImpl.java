package java_itamae.app.template;

import jakarta.inject.Inject;
import java.util.List;
import java.util.Map;
import java_itamae.app.common.BaseResource;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.status.Status;
import java_itamae.domain.model.template.TemplateResourceModel;
import java_itamae.domain.service.contents.ContentsService;

/** ファイルの読書きを管理する。 */
public class TemplateResourceImpl implements BaseResource<TemplateResourceModel> {
  /** {@link ContentsService} のインスタンス */
  @Inject private transient ContentsService service;

  /**
   * プロパティ群を収めた {@link Map} を {@link TemplateResourceModel} に変換して返す。
   *
   * @param properties プロパティ群を収めた {@link Map} を指定する。
   * @return model {@link Map} から変換された {@link TemplateResourceModel} を返す。
   */
  @Override
  public TemplateResourceModel convertToModel(final Map<String, String> properties) {
    final TemplateResourceModel model = new TemplateResourceModel();

    model.setAction(properties.get("action"));
    model.setPath(properties.get("path"));
    model.setSource(properties.get("source"));
    model.setEncoding(properties.get("encoding"));

    return model;
  }

  /**
   * ファイルを読込み、 {@link List} に変換して返す。
   *
   * @param model 読込み対象とするテキストファイルの情報を収めた {@link TemplateResourceModel} を指定する。
   * @return contents 変換された {@link List}
   * @throws Exception {@link Exception}
   */
  private List<String> getSourceContents(final TemplateResourceModel model) throws Exception {
    final ContentsModel sourceModel = new ContentsModel();

    if ("delete".equals(model.getAction())) {
      sourceModel.setPath(model.getPath());
    } else {
      sourceModel.setPath(model.getSource());
    }

    sourceModel.setEncoding(model.getEncoding());

    service.init(sourceModel);
    return service.getContents();
  }

  /**
   * ファイルを上書きする。
   *
   * @param model 書込み対象とするテキストファイルの情報を収めた {@link TemplateResourceModel} を指定する。
   * @param contents 書込み対象とする文字列を収めた {@link List} を指定する。
   * @return status 終了ステータスを返す。
   *     <ul>
   *       <li>0: 何も実行せずに正常終了したことを表す
   *       <li>1: 異常終了したことを表す。
   *       <li>2: 書込みを実行して正常終了したことを表す。
   *     </ul>
   *
   * @throws Exception
   */
  private Status updateContents(final TemplateResourceModel model, final List<String> contents)
      throws Exception {
    Status status = Status.INIT;

    final ContentsModel targetModel = new ContentsModel();
    targetModel.setPath(model.getPath());
    targetModel.setEncoding(model.getEncoding());

    service.init(targetModel);

    if ("create".equals(model.getAction())) {
      status = service.updateContents(contents);
    } else if ("delete".equals(model.getAction())) {
      status = service.deleteContents();
    }

    return status;
  }

  @Override
  @SuppressWarnings("unused")
  public Status apply(final Map<String, String> properties) {
    Status status = Status.INIT;
    final TemplateResourceModel model = this.convertToModel(properties);

    if (this.validate(model)) {
      try {
        final List<String> sourceContents = this.getSourceContents(model);
        status = this.updateContents(model, sourceContents);
      } catch (final Exception e) {
        final String message = e.toString();
        this.getLogger().warn("{}", message);
        status = Status.ERROR;
      }
    } else {
      status = Status.ERROR;
    }

    return status;
  }
}
