package content_resource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * テキストファイルの読み書きを管理する。
 */
public class TxtResource extends ContentResource<List<String>> {
    private final Logger logger;

    /**
     * @param path 操作対象とするファイルのパスを指定する。
     */
    public TxtResource(String path) {
        super(path);
        logger = Logger.getLogger(this.getClass().getName());
        logger.addHandler(new ConsoleHandler());
        logger.setUseParentHandlers(false);
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
        } catch (final Exception e) {
            logger.throwing(this.getClass().getName(), "setContent", e);
            throw e;
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
        } catch (final Exception e) {
            logger.throwing(this.getClass().getName(), "getContent", e);
            throw e;
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
        } catch (final Exception e) {
            logger.throwing(this.getClass().getName(), "deleteContent", e);
            throw e;
        }

        return status;
    }
}
