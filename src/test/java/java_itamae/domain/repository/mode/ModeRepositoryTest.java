package java_itamae.domain.repository.mode;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ModeRepositoryTest {
    /**
     * エラーテスト
     */
    public static class Test1 {
        private ModeRepository mr;

        @Before
        public void setUp() throws Exception {
            mr = new ModeRepositoryImpl();
        }

        @Test(expected = FileNotFoundException.class)
        public void 存在しないディレクトリのパーミッション設定を変更しようとした場合にFileNotFoundExceptionが送出されること() throws Exception {
            try {
                mr.setMode("NotExist", "640");
            } catch (final Exception e) {
                System.err.println(e);
                throw e;
            }
        }

        @Test(expected = FileNotFoundException.class)
        public void 存在しないファイルのパーミッション設定を変更しようとした場合にFileNotFoundExceptionが送出されること() throws Exception {
            try {
                mr.setMode("NotExist.txt", "640");
            } catch (final Exception e) {
                System.err.println(e);
                throw e;
            }
        }
    }

    /**
     * ディレクトリのパーミッション設定を変更する場合のテスト
     */
    @RunWith(Theories.class)
    public static class Test2 {
        // owner パーミッション
        @DataPoint
        public static String OWNER1 = "100";
        @DataPoint
        public static String OWNER2 = "200";
        @DataPoint
        public static String OWNER3 = "300";
        @DataPoint
        public static String OWNER4 = "400";
        @DataPoint
        public static String OWNER5 = "500";
        @DataPoint
        public static String OWNER6 = "600";
        @DataPoint
        public static String OWNER7 = "700";

        // group パーミッション
        @DataPoint
        public static String GROUP1 = "010";
        @DataPoint
        public static String GROUP2 = "020";
        @DataPoint
        public static String GROUP3 = "030";
        @DataPoint
        public static String GROUP4 = "040";
        @DataPoint
        public static String GROUP5 = "050";
        @DataPoint
        public static String GROUP6 = "060";
        @DataPoint
        public static String GROUP7 = "070";

        // others パーミッション
        @DataPoint
        public static String OTHERS1 = "001";
        @DataPoint
        public static String OTHERS2 = "002";
        @DataPoint
        public static String OTHERS3 = "003";
        @DataPoint
        public static String OTHERS4 = "004";
        @DataPoint
        public static String OTHERS5 = "005";
        @DataPoint
        public static String OTHERS6 = "006";
        @DataPoint
        public static String OTHERS7 = "007";

        // owner, group, others パーミッション同時変更
        @DataPoint
        public static String ALL = "740";

        private ModeRepository mr;
        private File directory;

        @Before
        public void setUp() throws Exception {
            mr = new ModeRepositoryImpl();
            directory = new File("test_dir");
            directory.mkdir();
        }

        @After
        public void tearDown() throws Exception {
            directory.delete();
        }

        @Theory
        public void ディレクトリのパーミッション設定を変更できて終了ステータスがtrueであること(String mode) throws Exception {
            final boolean status = mr.setMode("test_dir", mode);
            assertThat(status, is(true));
        }

        @Theory
        public void 新しく設定するパーミッション値が現在設定されているパーミッション値と同一である場合に終了ステータスがfalseであること(String mode) throws Exception {
            mr.setMode("test_dir", mode);
            final boolean status = mr.setMode("test_dir", mode);
            assertThat(status, is(false));
        }
    }

    /**
     * ファイルのパーミッション設定を変更する場合のテスト
     */
    @RunWith(Theories.class)
    public static class Test3 {
        // owner パーミッション
        @DataPoint
        public static String OWNER1 = "100";
        @DataPoint
        public static String OWNER2 = "200";
        @DataPoint
        public static String OWNER3 = "300";
        @DataPoint
        public static String OWNER4 = "400";
        @DataPoint
        public static String OWNER5 = "500";
        @DataPoint
        public static String OWNER6 = "600";
        @DataPoint
        public static String OWNER7 = "700";

        // group パーミッション
        @DataPoint
        public static String GROUP1 = "010";
        @DataPoint
        public static String GROUP2 = "020";
        @DataPoint
        public static String GROUP3 = "030";
        @DataPoint
        public static String GROUP4 = "040";
        @DataPoint
        public static String GROUP5 = "050";
        @DataPoint
        public static String GROUP6 = "060";
        @DataPoint
        public static String GROUP7 = "070";

        // others パーミッション
        @DataPoint
        public static String OTHERS1 = "001";
        @DataPoint
        public static String OTHERS2 = "002";
        @DataPoint
        public static String OTHERS3 = "003";
        @DataPoint
        public static String OTHERS4 = "004";
        @DataPoint
        public static String OTHERS5 = "005";
        @DataPoint
        public static String OTHERS6 = "006";
        @DataPoint
        public static String OTHERS7 = "007";

        // owner, group, others パーミッション同時変更
        @DataPoint
        public static String ALL = "740";

        private ModeRepository mr;
        private File file;

        @Before
        public void setUp() throws Exception {
            mr = new ModeRepositoryImpl();
            file = new File("test.txt");
            file.createNewFile();
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Theory
        public void ファイルのパーミッション設定を変更できて終了ステータスがtrueであること(String mode) throws Exception {
            final boolean status = mr.setMode("test.txt", mode);
            assertThat(status, is(true));
        }

        @Theory
        public void 新しく設定するパーミッション値が現在設定されているパーミッション値と同一である場合に終了ステータスがfalseであること(String mode) throws Exception {
            mr.setMode("test.txt", mode);
            final boolean status = mr.setMode("test.txt", mode);
            assertThat(status, is(false));
        }
    }

    /**
     * 不正なパーミッション値が設定されている場合のテスト
     */
    @RunWith(Theories.class)
    public static class Test4 {
        // パーミッション値が 3 桁ではない場合
        @DataPoint
        public static String ERROR1 = "6410";
        @DataPoint
        public static String ERROR2 = "64";

        // パーミッション値に数字以外の文字列が含まれている場合
        @DataPoint
        public static String ERROR3 = "a42";
        @DataPoint
        public static String ERROR4 = "-42";
        @DataPoint
        public static String ERROR5 = "+42";

        // 7 より大きい数値が含まれている場合
        @DataPoint
        public static String ERROR6 = "871";
        @DataPoint
        public static String ERROR7 = "781";
        @DataPoint
        public static String ERROR8 = "768";

        private ModeRepository mr;
        private File file;

        @Rule
        public ExpectedException exception = ExpectedException.none();

        @Before
        public void setUp() throws Exception {
            mr = new ModeRepositoryImpl();
            file = new File("test.txt");
            file.createNewFile();
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Theory
        public void setMode実行時にExceptionが送出されること(String mode) throws Exception {
            exception.expect(Exception.class);

            try {
                mr.setMode("test.txt", mode);
            } catch (final Exception e) {
                System.err.println(e);
                throw e;
            }
        }
    }
}
