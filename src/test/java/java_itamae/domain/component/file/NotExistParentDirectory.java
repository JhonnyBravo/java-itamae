package java_itamae.domain.component.file;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** 親ディレクトリが存在しない場合のテスト */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NotExistParentDirectory {
  @Autowired private FileComponent component;
  private Path path;

  @Before
  public void setUp() throws Exception {
    path = component.convertToPath("NotExist/test.txt");
  }

  /**
   * 以下の検証を実施する。
   *
   * <ul>
   *   <li>例外の発生確認。
   *   <li>操作対象とするファイルの存在確認。
   * </ul>
   *
   * <p>想定結果
   *
   * <ul>
   *   <li>{@link NoSuchFileException} が投げられること。
   *   <li>引数 path に指定されたファイルが存在しないこと。
   * </ul>
   */
  @Test(expected = NoSuchFileException.class)
  public void fre001() throws Exception {
    try {
      component.create(path.toFile().getPath());
    } catch (Exception e) {
      assertThat(path.toFile().isFile(), is(false));
      throw e;
    }
  }
}
