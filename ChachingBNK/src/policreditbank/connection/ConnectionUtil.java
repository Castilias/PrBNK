
package policreditbank.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class ConnectionUtil {
    private static final String username = "hbstudent";
    private static final String password = "hbstudent";
    public static Connection getConnectio() throws Exception{
        Connection connect = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/bank?"
                            + "user="+username+"&password="+password+"");
        } catch (Exception e){
            System.out.println(e);
            throw e;
        }
        return connect;
    }
    
    public static void closeConnection(Connection connect,  PreparedStatement preparedStatement,  ResultSet resultSet){
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            
            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
