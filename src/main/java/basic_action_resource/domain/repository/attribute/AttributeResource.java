package basic_action_resource.domain.repository.attribute;

/**
 * リソースの属性を管理する。
 */
public interface AttributeResource<T> {
    public abstract T createAttribute() throws Exception;

    public abstract T getAttribute() throws Exception;

    public abstract boolean setAttribute() throws Exception;
}