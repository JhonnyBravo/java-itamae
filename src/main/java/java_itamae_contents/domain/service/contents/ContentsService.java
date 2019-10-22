package java_itamae_contents.domain.service.contents;

import java.util.List;

public interface ContentsService {
    /**
     * @return contents ファイルを読込み、 List に変換して返す。
     * @throws Exception {@link java.lang.Exception}
     */
    public List<String> getContents() throws Exception;

    /**
     * ファイルを上書きする。
     *
     * @param content 書込み対象とする文字列を指定する。
     * @return status
     *         <ul>
     *         <li>true: 上書きに成功したことを表す。</li>
     *         <li>false: 上書きを実行しなかったことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    public boolean setContent(String content) throws Exception;

    /**
     * ファイル末尾へ文字列を追記する。
     *
     * @param content 書込み対象とする文字列を指定する。
     * @return status
     *         <ul>
     *         <li>true: 追記に成功したことを表す。</li>
     *         <li>false: 追記を実行しなかったことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    public boolean appendContent(String content) throws Exception;

    /**
     * ファイルを空にする。
     *
     * @return status
     *         <ul>
     *         <li>true: ファイルの内容削除に成功したことを表す。</li>
     *         <li>false: ファイルの内容削除を実行しなかったことを表す。</li>
     *         </ul>
     * @throws Exception {@link java.lang.Exception}
     */
    public boolean deleteContents() throws Exception;
}
