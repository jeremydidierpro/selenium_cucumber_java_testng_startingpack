package functions.miscellaneous;

import java.io.File;

public class PathFinder {

    private PathFinder(){
    }


    /**
     * This method turns the relative path provided into the absolute path
     * @param relativePath path from the root of the project
     * @return the path from the root of the local machine
     */
    public static  String getPath(String relativePath){
        File file = new File(relativePath);
        return file.getAbsolutePath();
    }

}
