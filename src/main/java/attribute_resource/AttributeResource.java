package attribute_resource;

import status_resource.Status;

/**
 * リソースの属性を管理する。
 */
public abstract class AttributeResource extends Status {
    protected abstract Object getAttribute();

    protected abstract Object createAttribute();

    public abstract void setAttribute();
}
