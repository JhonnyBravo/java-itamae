package group_resource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * テストで使用するグループ所有者のグループ名を config/application.properties から取得する。
 */
@Service
@ConfigurationProperties(prefix = "test")
@Validated
public class GroupTestProperties {
    @NonNull
    private String groupName;

    /**
     * @return groupName テスト時に使用するグループ所有者のグループ名を返す。
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName テスト時に使用するグループ所有者のグループ名を指定する。
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
