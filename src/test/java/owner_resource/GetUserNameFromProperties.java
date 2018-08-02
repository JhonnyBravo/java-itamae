/**
 * 
 */
package owner_resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author sanfr
 *
 */
public class GetUserNameFromProperties {
    private String path;

    public GetUserNameFromProperties(String path) {
        this.path = path;
    }

    public String getUserName() {
        String userName = null;
        Properties p = new Properties();

        try {
            InputStream input = new FileInputStream(this.path);
            p.load(input);
            userName = p.getProperty("userName");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userName;
    }

}
