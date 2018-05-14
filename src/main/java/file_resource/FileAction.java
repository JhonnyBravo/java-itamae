package file_resource;

import java.io.File;
import java.io.IOException;

import common_resource.PathBean;
import common_resource.StatusBean;

/**
 * ファイルを作成または削除する。
 */
public class FileAction extends StatusBean {
    private PathBean pb = new PathBean();

    public FileAction(String path) {
        pb.setBaseName(path);
    }

    /**
    * @return cwd 作業ディレクトリのパスを返す。
    */
    public String getCwd() {
        return pb.getDirName();
    }

    /**
    * @param cwd 設定する作業ディレクトリのパスを指定する。
    */
    public void setCwd(String cwd) {
        pb.setDirName(cwd);
    }

    /**
    * ファイルを作成する。
    */
    public void create() {
        this.setCode(0);
        this.setMessage(null);
        this.setResourceName(null);

        File f = new File(pb.getPath());

        if (pb.getCode() == 1) {
            this.setCode(pb.getCode());
            this.setMessage(pb.getMessage());
            this.setResourceName("file_resource");

            System.err.println(this.getMessage());
        }

        if (f.isFile() == false) {
            try {
                f.createNewFile();

                this.setCode(2);
                this.setMessage(pb.getPath() + " を作成しました。");
                this.setResourceName("file_resource");

                System.out.println(this.getStatus());
            } catch (IOException e) {
                this.setCode(1);
                this.setMessage("エラーが発生しました。 " + e.toString());
                this.setResourceName("file_resource");

                System.err.println(this.getStatus());
            }
        }
    }

    /**
    * ファイルを削除する。
    */
    public void delete() {
        this.setCode(0);
        this.setMessage(null);
        this.setResourceName(null);

        File f = new File(pb.getPath());

        if (pb.getCode() == 1) {
            this.setCode(pb.getCode());
            this.setMessage(pb.getMessage());
            this.setResourceName("file_resource");

            System.err.println(this.getStatus());
        }

        if (f.isFile()) {
            f.delete();

            this.setCode(2);
            this.setMessage(pb.getPath() + " を削除しました。");
            this.setResourceName("file_resource");

            System.out.println(this.getStatus());
        }
    }

}
