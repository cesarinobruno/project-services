package projectservices.project.Dao;

import projectservices.project.Repository.SingleConnection;
import projectservices.project.model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDao
{
    private Connection connection;


    public PersonDao()
    {
        connection = SingleConnection.getConnection();
    }

    public void save(Person person)
    {
        String sql = "INSERT INTO person(name, login, password) VALUES (?, ?, ?)";

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getLogin());
            preparedStatement.setString(3, person.getPassword());

            preparedStatement.execute();
            connection.commit();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public List<Person> list()
    {
        List<Person> listPerson = new ArrayList<>();

        String sql = "SELECT * FROM person";

        try
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next())
            {
                Person person = new Person();
                person.setId(rs.getInt("id"));
                person.setName(rs.getString("name"));
                person.setLogin(rs.getString("login"));
                person.setPassword(rs.getString("password"));

                listPerson.add(person);
            }

            return listPerson;

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public void delete(final Integer id, final Boolean feedbackAssociateFromUser)
    {
        if(feedbackAssociateFromUser != null)
        {
            String deleteFeedbackQuery = "DELETE FROM feedback WHERE personId = ".concat(Integer.toString(id));

            try
            {
                PreparedStatement preparedStatement = connection.prepareStatement(deleteFeedbackQuery);
                preparedStatement.execute();
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        String deletePersonQuery = "DELETE FROM person WHERE id = ".concat(Integer.toString(id));

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(deletePersonQuery);
            preparedStatement.execute();
            connection.commit();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Integer getPerson(Integer id)
    {
        String query = "SELECT * FROM person WHERE id = ".concat(Integer.toString(id));

        try
        {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            Person person = new Person();

            while (rs.next())
            {
                person.setId(rs.getInt("id"));
            }

            return person.getId();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
