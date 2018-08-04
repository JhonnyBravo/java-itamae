package group_resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetGroupNameFromProperties {
    private String path;

    public GetGroupNameFromProperties(String path) {
        this.path = path;
    }

    public String getGroupName() {
        String groupName = null;
        Properties p = new Properties();

        try {
            InputStream input = new FileInputStream(this.path);
            p.load(input);
            groupName = p.getProperty("groupName");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return groupName;
    }

}
