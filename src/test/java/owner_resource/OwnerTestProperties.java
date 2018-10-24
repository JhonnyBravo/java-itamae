package owner_resource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * テストで使用する所有者のユーザ名を config/application.properties から取得する。
 */
@Service
@ConfigurationProperties(prefix = "test")
@Validated
public class OwnerTestProperties {
    @NonNull
    private String ownerName;

    /**
     * @return ownerName テスト時に使用する所有者のユーザ名を返す。
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * @param ownerName テスト時に使用する所有者のユーザ名を指定する。
     */
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
