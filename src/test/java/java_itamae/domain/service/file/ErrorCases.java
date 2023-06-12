package java_itamae.domain.service.file;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java_itamae.domain.model.contents.ContentsModel;
import java_itamae.domain.model.file.FileResourceModel;
import java_itamae.domain.service.properties.PropertiesService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** エラーケースのテスト */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ErrorCases {
  @Autowired private FileService fs;
  @Autowired private PropertiesService ps;
  private Path path;

  @Before
  public void setUp() throws Exception {
    path = FileSystems.getDefault().getPath("test.txt");

    final ContentsModel model = new ContentsModel();
    model.setPath("src/test/resources/test.properties");
    ps.init(model);
  }

  @After
  public void tearDown() throws Exception {
    if (path.toFile().isFile()) {
      Files.delete(path);
    }
  }

  /**
   * path が指定されないまま {@link FileService#create(FileResourceModel)} を実行した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link NullPointerException} が発生すること。
   * </ul>
   */
  @Test(expected = NullPointerException.class)
  public void fse001() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    model.setOwner(ps.getProperty("owner"));
    model.setGroup(ps.getProperty("group"));
    model.setMode("640");

    fs.create(model);
  }

  /**
   * path が指定されないまま {@link FileService#delete(FileResourceModel)} を実行した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link NullPointerException} が発生すること。
   * </ul>
   */
  @Test(expected = NullPointerException.class)
  public void fse002() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    fs.delete(model);
  }

  /**
   * 親ディレクトリが存在しないパスを対象に {@link FileService#create(FileResourceModel)} を実行した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link NoSuchFileException} が発生すること。
   * </ul>
   */
  @Test(expected = NoSuchFileException.class)
  public void fse003() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    model.setPath("NotExist/test.txt");
    model.setOwner(ps.getProperty("owner"));
    model.setGroup(ps.getProperty("group"));
    model.setMode("640");

    fs.create(model);
  }

  /**
   * 新しいファイル所有者として、存在しないユーザの名前を指定して {@link FileService#create(FileResourceModel)} を実行した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link UserPrincipalNotFoundException} が発生すること。
   * </ul>
   */
  @Test(expected = UserPrincipalNotFoundException.class)
  public void fse004() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    model.setPath(path.toFile().getPath());
    model.setOwner("NotExist");
    model.setGroup(ps.getProperty("group"));
    model.setMode("640");

    fs.create(model);
  }

  /**
   * 新しいグループ所有者として、存在しないグループの名前を指定して {@link FileService#create(FileResourceModel)}
   * を実行した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link UserPrincipalNotFoundException} が発生すること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test(expected = UserPrincipalNotFoundException.class)
  public void fse005() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    model.setPath(path.toFile().getPath());
    model.setOwner(ps.getProperty("owner"));
    model.setGroup("NotExist");
    model.setMode("640");

    fs.create(model);
  }

  /**
   * 新しいパーミッション設定として不正なパーミッション設定値を指定して {@link FileService#create(FileResourceModel)}
   * を実行した場合の動作検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link IllegalArgumentException} が発生すること。
   * </ul>
   */
  // @Ignore("Windows の場合は非対応である為、実行しない。")
  @Test(expected = IllegalArgumentException.class)
  public void fse006() throws Exception {
    final FileResourceModel model = new FileResourceModel();
    model.setPath(path.toFile().getPath());
    model.setOwner(ps.getProperty("owner"));
    model.setGroup(ps.getProperty("group"));
    model.setMode("a40");

    fs.create(model);
  }
}
