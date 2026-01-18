package functions.sql;

import exceptions.IncorrectSQLRequestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBAccounts {
    private static Connection conn;
    private static final Logger logger = LogManager.getLogger(DBAccounts.class);

    /***
     * Make the constructor private to not be able to instantiate it
     */
    private DBAccounts(){
    }

    /***
     * Generate A new connection if the current one is null on the first call.
     */
    private static void getConnection(){
        if(conn==null){
            logger.info("****** Trying to connect to Account DB");
            conn = DataBaseManager.connectDB("accounts");
            logger.info("****** Account DB connected");
        }
    }

    /***
     * Call closeDB method to close the bdd the to conn parameter
     */
    public static void closeDBAccounts(){
        if(conn!=null){
            logger.info("****** Trying to close Account DB");
            conn = DataBaseManager.closeDB(conn);
            logger.info("****** Account DB closed");
        }
    }

    /***
     * By using this method, the account will be updated.
     * @param userEmail
     */
    public static void updateAccount(String userEmail){
        getConnection();
        PreparedStatement prepare = null;
        try {
            prepare = conn.prepareStatement("UPDATE account SET last_name = 'Josh' WHERE  email= ?");
            prepare.setString(1,userEmail);
            PreparedStatement finalPrepare = prepare;
            logger.info(() -> "****** Trying to execute SQL request:: " + finalPrepare.toString());
            prepare.executeUpdate();
        } catch (SQLException throwables) {
            logger.error(() -> throwables);
            throw new IncorrectSQLRequestException(throwables.getMessage());
        }finally {
            try {
                assert prepare != null;
                prepare.close();
            } catch (SQLException throwables) {
                logger.error(() -> throwables);
                throwables.printStackTrace();
            }
        }
    }


}
