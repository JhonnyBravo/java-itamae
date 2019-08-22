package attribute_resource;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * リソースの属性を管理する。
 */
public abstract class AttributeResource<T> {
    protected abstract T createAttribute() throws IOException;

    protected abstract T getAttribute() throws FileNotFoundException, IOException;

    public abstract boolean setAttribute() throws FileNotFoundException, IOException;
}
