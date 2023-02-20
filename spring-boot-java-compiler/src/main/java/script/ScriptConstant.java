package script;

import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * @author loquy
 */
public class ScriptConstant {

    public static final String CLASS_NAME = "Main";
    public static final String CLASS_PATH = createScriptDir();

    public static String createScriptDir() {
        String path = ScriptConstant.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
            path = java.net.URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        File file = new File(path);
        File parent = new File(file.getParent());
        File resource = new File(parent.getParent());
        String customScriptPath = resource + File.separator + "custom-script" + File.separator;
        File customScript = new File(customScriptPath);
        if (!customScript.exists()) {
            customScript.mkdirs();
        }
        return customScriptPath;
    }
}
