package content_resource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * テキストファイルの読み書きを管理する。
 */
public class TxtResource extends IOStreamResource implements ContentResource<List<String>> {
    /**
     * @param path 操作対象とするファイルのパスを指定する。
     */
    public TxtResource(String path) {
        super(path);
    }

    /**
     * ファイルへ文字列を書き込む。
     *
     * @param model 書込み対象とする文字列を格納した List を指定する。
     * @return status
     *         <ul>
     *         <li>true: 書込みが成功したことを表す。</li>
     *         <li>false: 書込みが失敗したことを表す。</li>
     *         </ul>
     */
    @Override
    public boolean setContent(List<String> model) throws Exception {
        boolean status = false;

        try (BufferedWriter writer = new BufferedWriter(getWriter())) {
            for (final String line : model) {
                writer.write(line);
                writer.newLine();
            }

            status = true;
        }

        return status;
    }

    /**
     * @return contents ファイル全行を読込み、 List に変換して返す。
     */
    @Override
    public List<String> getContent() throws Exception {
        final List<String> contents = new ArrayList<String>();

        try (BufferedReader buffer = new BufferedReader(getReader())) {
            String line = null;

            while ((line = buffer.readLine()) != null) {
                contents.add(line);
            }
        }

        return contents;
    }

    /**
     * ファイルの内容を削除する。
     *
     * @return status
     *         <ul>
     *         <li>true: 削除に成功したことを表す。</li>
     *         <li>false: 削除に失敗したことを表す。</li>
     *         </ul>
     */
    @Override
    public boolean deleteContent() throws Exception {
        boolean status = false;

        try (BufferedWriter buffer = new BufferedWriter(getWriter())) {
            buffer.write("");
            status = true;
        }

        return status;
    }
}
