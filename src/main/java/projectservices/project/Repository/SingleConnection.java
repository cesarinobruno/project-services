package projectservices.project.Repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {

    private static String url = "jdbc:mysql://172.17.0.2:3306/db_project";
    private static String user = "root";
    private static String password = "root";
    private static Connection connection = null;

    static
    {
        connect();
    }

    private static void init()
    {
        connect();
    }

    private static void connect()
    {
        try
        {
            if(connection == null)
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, password);
                connection.setAutoCommit(false);
                System.out.println("Conectou com sucesso");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Connection getConnection()
    {
        return connection;
    }
}
