package owner_resource;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import status_resource.StatusController;

/**
 * UserPrincipal オブジェクトを生成する。
 */
@Service
public class CreateUserPrincipal extends StatusController {
    @Autowired
    private OwnerProperties op;

    /**
     * @param owner 生成対象とするユーザの名前を指定する。
     */
    public void init(String owner) {
        op.setOwner(owner);
    }

    /**
     * UserPrincipal オブジェクトを生成して返す。
     * 
     * @return UserPrincipal
     */
    public UserPrincipal runCommand() {
        this.initStatus();

        FileSystem fs = FileSystems.getDefault();
        UserPrincipalLookupService upls = fs.getUserPrincipalLookupService();

        UserPrincipal up = null;

        try {
            up = upls.lookupPrincipalByName(op.getOwner());
            this.setCode(2);
        } catch (IOException e) {
            this.setMessage("エラーが発生しました。 " + e.toString());
            this.errorTerminate();
        }

        return up;
    }
}
