package java_itamae.domain.component.file;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java_itamae.domain.model.status.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** ファイルが既に存在する場合のテスト */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExistFile {
  @Autowired private FileComponent component;
  private Path path;

  @Before
  public void setUp() throws Exception {
    path = component.convertToPath("test.txt");
    Files.createFile(path);
  }

  @After
  public void tearDown() throws Exception {
    if (path.toFile().isFile()) {
      Files.delete(path);
    }
  }

  /**
   * 以下の検証を実施する。
   *
   * <ul>
   *   <li>{@link FileComponent#create(String)} 実行後の返り値の確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#INIT} が返されること。
   * </ul>
   */
  @Test
  public void frs001() throws Exception {
    final Status status = component.create(path.toFile().getPath());
    assertThat(status, is(Status.INIT));
  }

  /**
   * 以下の検証を実施する。
   *
   * <ul>
   *   <li>{@link FileComponent#delete(String)} 実行後の返り値の確認。
   *   <li>操作対象とするファイルの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>返り値として {@link Status#DONE} が返されること。
   *   <li>引数 path に指定されたファイルが存在しないこと。
   * </ul>
   */
  @Test
  public void frs002() throws Exception {
    final File file = path.toFile();
    final Status status = component.delete(file.getPath());
    assertThat(status, is(Status.DONE));
    assertThat(file.isFile(), is(false));
  }
}
