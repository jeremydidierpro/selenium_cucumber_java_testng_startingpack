package functions.sql;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Secrets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseManager {
    private static Logger logger = LogManager.getLogger(DataBaseManager.class);

    private DataBaseManager(){
    }


    /***
     * Initiate the connexion
     */
    public static Connection connectDB(String dataBaseName){
        String server = Secrets.sqlServer();

        Properties props = new Properties();
        String password = Secrets.sqlPassword();
        String url = "jdbc:postgresql://" + server +"/"+dataBaseName+"?currentSchema="+dataBaseName+"_own";
        props.setProperty("user", dataBaseName);
        props.setProperty("password", password);
        try {
           return DriverManager.getConnection(url,props);
        } catch (SQLException e) {
            logger.error(() -> e);
            e.printStackTrace();
        }
        return null;
    }

    /***
     * Close the connection.
     * We have to call this method at the end of each test case.
     */
    public static Connection closeDB(Connection conn) {
        try {
            if(conn != null && !conn.isClosed()){
                conn.close();
            }
        } catch (SQLException throwable) {
            logger.error(() -> throwable);
            throwable.printStackTrace();
        }
        conn = null;
        return conn;
    }




}
