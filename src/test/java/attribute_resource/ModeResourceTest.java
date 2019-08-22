package attribute_resource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ModeResourceTest {
    public static class エラーテスト {
        /**
         * OS が Windows であるかどうかを確認する。
         *
         * @return boolean
         *         <ul>
         *         <li>true: OS が Windows であることを表す。</li>
         *         <li>false: OS が Windows ではないことを表す。</li>
         *         </ul>
         */
        private boolean isWindows() {
            final String osName = System.getProperty("os.name");
            boolean result;

            if (osName.substring(0, 3).equals("Win")) {
                result = true;
            } else {
                result = false;
            }

            return result;
        }

        @Test
        public void Windowsでディレクトリのパーミッション設定を変更しようとした場合にエラーとなり終了ステータスがfalseであること()
                throws FileNotFoundException, IOException {
            if (isWindows()) {
                final File file = new File("testDir");
                file.mkdir();

                final AttributeResource<?> ar = new ModeResource("testDir", "700");
                final boolean status = ar.setAttribute();
                assertThat(status, is(false));

                file.delete();
            }
        }

        @Test
        public void Windowsでファイルのパーミッション設定を変更しようとした場合にエラーとなり終了ステータスがfalseであること()
                throws FileNotFoundException, IOException {
            if (isWindows()) {
                final File file = new File("test.txt");

                try {
                    file.createNewFile();
                } catch (final IOException e) {
                    e.printStackTrace();
                    fail("エラーが発生しました。 " + e);
                }

                final AttributeResource<?> ar = new ModeResource("test.txt", "700");
                final boolean status = ar.setAttribute();
                assertThat(status, is(false));

                file.delete();
            }
        }

        @Test(expected = FileNotFoundException.class)
        public void 存在しないディレクトリのパーミッション設定を変更しようとした場合にエラーとなりFileNotFoundExceptionが送出されること()
                throws FileNotFoundException, IOException {
            if (!isWindows()) {
                final AttributeResource<?> ar = new ModeResource("testDir", "640");
                ar.setAttribute();
            }
        }

        @Test(expected = FileNotFoundException.class)
        public void 存在しないファイルのパーミッション設定を変更しようとした場合にエラーとなりFileNotFoundExceptionが送出されること()
                throws FileNotFoundException, IOException {
            if (!isWindows()) {
                final AttributeResource<?> ar = new ModeResource("test.txt", "640");
                ar.setAttribute();
            }
        }
    }

    @RunWith(Theories.class)
    public static class ディレクトリのパーミッション設定を変更する場合 {
        // owner パーミッションの変更テスト
        @DataPoint
        public static String OWNER_PERM1 = "100";

        @DataPoint
        public static String OWNER_PERM2 = "200";

        @DataPoint
        public static String OWNER_PERM3 = "300";

        @DataPoint
        public static String OWNER_PERM4 = "400";

        @DataPoint
        public static String OWNER_PERM5 = "500";
        @DataPoint
        public static String OWNER_PERM6 = "600";
        @DataPoint
        public static String OWNER_PERM7 = "700";
        // group パーミッションの変更テスト
        @DataPoint
        public static String GROUP_PERM1 = "010";
        @DataPoint
        public static String GROUP_PERM2 = "020";
        @DataPoint
        public static String GROUP_PERM3 = "030";
        @DataPoint
        public static String GROUP_PERM4 = "040";

        @DataPoint
        public static String GROUP_PERM5 = "050";
        @DataPoint
        public static String GROUP_PERM6 = "060";
        @DataPoint
        public static String GROUP_PERM7 = "070";
        // others パーミッションの変更テスト
        @DataPoint
        public static String OTHERS_PERM1 = "001";
        @DataPoint
        public static String OTHERS_PERM2 = "002";
        @DataPoint
        public static String OTHERS_PERM3 = "003";
        @DataPoint
        public static String OTHERS_PERM4 = "004";

        @DataPoint
        public static String OTHERS_PERM5 = "005";
        @DataPoint
        public static String OTHERS_PERM6 = "006";
        @DataPoint
        public static String OTHERS_PERM7 = "007";
        // owner, group, others パーミッションの同時変更。
        @DataPoint
        public static String ALL1 = "740";
        private final File file = new File("testDir");

        /**
         * OS が Windows であるかどうかを確認する。
         *
         * @return boolean
         *         <ul>
         *         <li>true: OS が Windows であることを表す。</li>
         *         <li>false: OS が Windows ではないことを表す。</li>
         *         </ul>
         */
        private boolean isWindows() {
            final String osName = System.getProperty("os.name");
            boolean result;

            if (osName.substring(0, 3).equals("Win")) {
                result = true;
            } else {
                result = false;
            }

            return result;
        }

        @Before
        public void setUp() throws Exception {
            file.createNewFile();
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Theory
        public void ディレクトリのパーミッション設定を変更できて終了ステータスがtrueであること(String mode) throws FileNotFoundException, IOException {
            if (!isWindows()) {
                System.out.println("パーミッション: " + mode);

                final AttributeResource<?> ar = new ModeResource("testDir", mode);
                final boolean status = ar.setAttribute();
                assertThat(status, is(true));
            }
        }

        @Theory
        public void 新しいパーミッションとして設定しようとしている値が現在のパーミッション設定と同一である場合に終了ステータスがfalseであること(String mode)
                throws FileNotFoundException, IOException {
            if (!isWindows()) {
                System.out.println("パーミッション: " + mode);

                final AttributeResource<?> ar = new ModeResource("testDir", mode);
                ar.setAttribute();
                final boolean status = ar.setAttribute();
                assertThat(status, is(false));
            }
        }
    }

    @RunWith(Theories.class)
    public static class ファイルのパーミッション設定を変更する場合 {
        // owner パーミッションの変更テスト
        @DataPoint
        public static String OWNER_PERM1 = "100";

        @DataPoint
        public static String OWNER_PERM2 = "200";

        @DataPoint
        public static String OWNER_PERM3 = "300";

        @DataPoint
        public static String OWNER_PERM4 = "400";

        @DataPoint
        public static String OWNER_PERM5 = "500";
        @DataPoint
        public static String OWNER_PERM6 = "600";
        @DataPoint
        public static String OWNER_PERM7 = "700";
        // group パーミッションの変更テスト
        @DataPoint
        public static String GROUP_PERM1 = "010";
        @DataPoint
        public static String GROUP_PERM2 = "020";
        @DataPoint
        public static String GROUP_PERM3 = "030";
        @DataPoint
        public static String GROUP_PERM4 = "040";

        @DataPoint
        public static String GROUP_PERM5 = "050";
        @DataPoint
        public static String GROUP_PERM6 = "060";
        @DataPoint
        public static String GROUP_PERM7 = "070";
        // others パーミッションの変更テスト
        @DataPoint
        public static String OTHERS_PERM1 = "001";
        @DataPoint
        public static String OTHERS_PERM2 = "002";
        @DataPoint
        public static String OTHERS_PERM3 = "003";
        @DataPoint
        public static String OTHERS_PERM4 = "004";

        @DataPoint
        public static String OTHERS_PERM5 = "005";
        @DataPoint
        public static String OTHERS_PERM6 = "006";
        @DataPoint
        public static String OTHERS_PERM7 = "007";
        // owner, group, others パーミッションの同時変更。
        @DataPoint
        public static String ALL1 = "740";
        private final File file = new File("test.txt");

        /**
         * OS が Windows であるかどうかを確認する。
         *
         * @return boolean
         *         <ul>
         *         <li>true: OS が Windows であることを表す。</li>
         *         <li>false: OS が Windows ではないことを表す。</li>
         *         </ul>
         */
        private boolean isWindows() {
            final String osName = System.getProperty("os.name");
            boolean result;

            if (osName.substring(0, 3).equals("Win")) {
                result = true;
            } else {
                result = false;
            }

            return result;
        }

        @Before
        public void setUp() throws Exception {
            file.createNewFile();
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Theory
        public void ファイルのパーミッション設定を変更できて終了ステータスがtrueであること(String mode) throws FileNotFoundException, IOException {
            if (!isWindows()) {
                System.out.println("パーミッション: " + mode);

                final AttributeResource<?> ar = new ModeResource("test.txt", mode);
                final boolean status = ar.setAttribute();
                assertThat(status, is(true));
            }
        }

        @Theory
        public void 新しいパーミッションとして設定しようとしている値が現在のパーミッション設定と同一である場合に終了ステータスがfalseであること(String mode)
                throws FileNotFoundException, IOException {
            if (!isWindows()) {
                System.out.println("パーミッション: " + mode);

                final AttributeResource<?> ar = new ModeResource("test.txt", mode);
                ar.setAttribute();
                final boolean status = ar.setAttribute();
                assertThat(status, is(false));
            }
        }
    }

    @RunWith(Theories.class)
    public static class 不正なパーミッション値が指定されている場合 {
        // パーミッション値が 3 桁の数列ではない場合
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
        private final File file = new File("test.txt");

        /**
         * OS が Windows であるかどうかを確認する。
         *
         * @return boolean
         *         <ul>
         *         <li>true: OS が Windows であることを表す。</li>
         *         <li>false: OS が Windows ではないことを表す。</li>
         *         </ul>
         */
        private boolean isWindows() {
            final String osName = System.getProperty("os.name");
            boolean result;

            if (osName.substring(0, 3).equals("Win")) {
                result = true;
            } else {
                result = false;
            }

            return result;
        }

        @Before
        public void setUp() throws Exception {
            file.createNewFile();
        }

        @After
        public void tearDown() throws Exception {
            file.delete();
        }

        @Theory
        public void 不正なパーミッションを指定した場合にエラーとなり終了ステータスがfalseであること(String mode) throws FileNotFoundException, IOException {
            if (!isWindows()) {
                System.out.println("パーミッション: " + mode);

                final AttributeResource<?> ar = new ModeResource("test.txt", mode);
                final boolean status = ar.setAttribute();
                assertThat(status, is(false));
            }
        }
    }
}
