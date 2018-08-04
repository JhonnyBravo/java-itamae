package group_resource;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;

import status_resource.StatusController;

/**
 * GroupPrincipal オブジェクトを生成する。
 */
public class CreateGroupPrincipal extends StatusController {
    private String group;

    /**
     * @param group 生成対象とするグループの名前を指定する。
     */
    public CreateGroupPrincipal(String group) {
        this.group = group;
    }

    /**
     * GroupPrincipal オブジェクトを生成して返す。
     * 
     * @return GroupPrincipal
     */
    public GroupPrincipal runCommand() {
        this.initStatus();

        FileSystem fs = FileSystems.getDefault();
        UserPrincipalLookupService upls = fs.getUserPrincipalLookupService();

        GroupPrincipal up = null;

        try {
            up = upls.lookupPrincipalByGroupName(this.group);
            this.setCode(2);
        } catch (IOException e) {
            this.setCode(1);
            this.setMessage("エラーが発生しました。 " + e.toString());
            this.errorTerminate();
        }

        return up;
    }
}
