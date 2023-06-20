package java_itamae.domain.component.file;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import jakarta.inject.Inject;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/** 親ディレクトリが存在しない場合のテスト */
public class NotExistParentDirectory {
  @Inject private FileComponent component;
  private Path path;

  @Rule
  public WeldInitiator weld = WeldInitiator.from(FileComponentImpl.class).inject(this).build();

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
