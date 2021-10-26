package logiledus;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesValidator {
    public static String validate(String fileAbsolutePath){
        try{
            Path locationAsPath = Paths.get(fileAbsolutePath);
            if (Files.notExists(locationAsPath) || Files.isDirectory(locationAsPath))
                return "";
            return fileAbsolutePath;
        }
        catch (Exception ignored){
            return "";
        }
    }
}
