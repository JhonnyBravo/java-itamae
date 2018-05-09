package file_resource;

import java.io.File;
import java.io.IOException;

/**
 * 操作対象とするファイルのパスを生成する。
 */
public class PathBean {
    /**
    * ファイル名。
    */
    private String baseName = null;

    /**
    * ディレクトリ名。
    */
    private String dirName = null;

    /**
    * dirName と baseName を連結して正規化したパス文字列。
    */
    private String path = null;

    /**
    * @return baseName ファイル名を返す。
    */
    public String getBaseName() {
        return baseName;
    }

    /**
    * @param baseName 設定するファイル名を指定する。
    */
    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    /**
    * @return dirName ディレクトリ名を返す。
    */
    public String getDirName() {
        return dirName;
    }

    /**
    * @param dirName 設定するディレクトリ名を指定する。
    */
    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    /**
    * @return path 正規化されたパスを返す。
    */
    public String getPath() {
        File f = null;

        if (this.dirName != null && this.baseName != null) {
            //dirName, baseName 両方が指定されている場合
            f = new File(this.dirName, this.baseName);
        } else if (this.dirName != null && this.baseName == null) {
            //dirName のみが指定されている場合
            f = new File(this.dirName);
        } else if (this.dirName == null && this.baseName != null) {
            //baseName のみが指定されている場合
            f = new File(this.baseName);
        }

        try {
            this.path = f.getCanonicalPath();
        } catch (IOException e) {
            System.err.println(e);
        }

        return path;
    }

}
