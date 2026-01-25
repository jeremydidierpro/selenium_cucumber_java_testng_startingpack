package functions.miscellaneous;

import java.io.File;

public class PathFinder {

    private PathFinder(){
    }


    /**
     * This method converts the provided relative path into an absolute path.
     * @param relativePath the path from the root of the project
     * @return the path from the root of the project
     */
    public static String getPath(String relativePath){
        File file = new File(relativePath);
        return file.getAbsolutePath();
    }

}
