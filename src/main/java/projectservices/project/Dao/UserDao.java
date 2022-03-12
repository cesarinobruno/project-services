package projectservices.project.Dao;

import projectservices.project.Repository.SingleConnection;
import projectservices.project.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao
{
    private Connection connection;


    public UserDao()
    {
        connection = SingleConnection.getConnection();
    }

    public void save(User user)
    {
        String sql = "INSERT INTO user(name, login, password) VALUES (?, ?, ?)";

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());

            preparedStatement.execute();
            connection.commit();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public List<User> list()
    {
        List<User> listUser = new ArrayList<>();

        String sql = "SELECT * FROM user";

        try
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next())
            {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));

                listUser.add(user);
            }

            return listUser;

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public void delete(Integer id)
    {
        String sql = "DELETE FROM user WHERE id = ".concat(Integer.toString(id));

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
            connection.commit();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
}
