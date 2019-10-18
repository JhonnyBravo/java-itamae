package content_resource.domain.repository;

/**
 * ファイルの読み書きを管理する。
 */
public interface ContentResource<T> {
    public T getContent() throws Exception;

    public boolean setContent(T model) throws Exception;

    public boolean deleteContent() throws Exception;
}
