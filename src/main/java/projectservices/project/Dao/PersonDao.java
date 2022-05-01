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

    private static String PERSON_SQL = "SELECT * FROM person";
    private static String PERSON_FROM_SQL = "FROM person";
    private static String FEEDBACK_FROM_SQL = "FROM feedback";
    private static String PERSON_COLUMS = "(name, login, password)";

    public void save(Person person)
    {
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO person " + PERSON_COLUMS).append(" VALUES (?, ?, ?)");

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());

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

        String sql = PERSON_SQL;

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

    public void delete(final Integer id, final Boolean feedbackAssociateFromPerson)
    {
        StringBuilder sqlBuilder = null;

        if(feedbackAssociateFromPerson)
        {
            sqlBuilder = new StringBuilder("DELETE " + FEEDBACK_FROM_SQL + " WHERE personId = " + id);
            String deleteFeedbackQuery = sqlBuilder.toString();
            sqlBuilder = null;

            try
            {
                PreparedStatement preparedStatement = connection.prepareStatement(deleteFeedbackQuery);
                preparedStatement.execute();
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(sqlBuilder == null)
        {
            sqlBuilder = new StringBuilder("DELETE " + PERSON_FROM_SQL + " WHERE id = " + id);

            String deletePersonQuery = sqlBuilder.toString();

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
    }

    public Integer getPerson(Integer id)
    {
        StringBuilder sqlBuilder = new StringBuilder(PERSON_SQL).append(" WHERE id = ").append(id);
        String query = sqlBuilder.toString();

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

    public List<Person> listSortedByName()
    {
        //melhorar sql e usar o stringBuilder
        //adicionar um switch, pois futuramente teremos ordenação diferente.

        List<Person> list = new ArrayList<>();

        String personsOrdered = "SELECT * FROM person p ORDER BY p.name";

        try
        {
            PreparedStatement statement = connection.prepareStatement(personsOrdered);
            ResultSet rs = statement.executeQuery();

            while (rs.next())
            {
                Person person = new Person();
                person.setId(rs.getInt("id"));
                person.setName(rs.getString("name"));
                person.setLogin(rs.getString("login"));
                person.setPassword(rs.getString("password"));

                list.add(person);
            }

            return list;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public Integer countPerson()
    {
        String sqlBuilder = new StringBuilder("SELECT COUNT(id) as qtd ").append(PERSON_FROM_SQL).toString();
        int count = 0;
        try
        {
            PreparedStatement statement = connection.prepareStatement(sqlBuilder);
            ResultSet rs = statement.executeQuery();

            if(rs.next())
            {
                count = Integer.parseInt(rs.getString("qtd"));
            }

            return count;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
}
