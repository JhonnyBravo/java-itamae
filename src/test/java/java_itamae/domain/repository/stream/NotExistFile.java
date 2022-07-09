package java_itamae.domain.repository.stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java_itamae.domain.model.contents.ContentsModel;
import org.junit.Before;
import org.junit.Test;

/** ファイルが存在しない場合のテスト */
public class NotExistFile {
  private ContentsModel model;
  private StreamRepository repository;

  @Before
  public void setUp() throws Exception {
    repository = new StreamRepositoryImpl();

    model = new ContentsModel();
    model.setPath("NotExist.txt");
  }

  /**
   * {@link StreamRepository#getReader(ContentsModel)} 実行時に {@link FileNotFoundException} が送出されること。
   */
  @Test(expected = FileNotFoundException.class)
  public void sre001() throws Exception {
    try {
      repository.getReader(model);
    } catch (final Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  /**
   *
   *
   * <ul>
   *   <li>{@link StreamRepository#getWriter(ContentsModel)} 実行時に {@link FileNotFoundException}
   *       が送出されること。
   *   <li>ファイルが作成されないこと。
   * </ul>
   */
  @Test(expected = FileNotFoundException.class)
  public void sre002() throws Exception {
    try {
      repository.getWriter(model);
    } catch (final Exception e) {
      System.err.println(e);
      assertThat(new File(model.getPath()).isFile(), is(false));
      throw e;
    }
  }
}
