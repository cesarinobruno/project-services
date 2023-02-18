package projectservices.project.Dao;

import org.springframework.security.crypto.password.PasswordEncoder;
import projectservices.project.Repository.SingleConnection;
import projectservices.project.model.Person;

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

    private static String PERSON_SQL = "SELECT * FROM person";
    private static String PERSON_FROM_SQL = "FROM person";
    private static String FEEDBACK_FROM_SQL = "FROM feedback";
    private static String PERSON_COLUMS = "(name, login, password)";

    public void save(Person person) throws SQLException {
        final StringBuilder sqlBuilder = new StringBuilder("INSERT INTO person " + PERSON_COLUMS).append(" VALUES (?, ?, ?)");

        try
        {
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getLogin());
            preparedStatement.setString(3, person.getPassword());

            final Boolean error = preparedStatement.execute();

            if(!error)
            {
                connection.commit();
            }
        }
        catch (Exception e)
        {
            connection.rollback();
            throw new SQLException();
        }
    }

    public List<Person> list()
    {
        final List<Person> personAdded = new ArrayList<>();

        final String sql = PERSON_SQL;

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
                personAdded.add(person);
            }

            return personAdded;

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public void delete(final Integer id, boolean feedbackAssociateFromPerson)
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

    public Person validatePersonByNameAndPasswordDAO(final Person person, final PasswordEncoder encoder) throws SQLException
    {
        final StringBuilder sqlQuery = new StringBuilder(PERSON_SQL)
                                                         .append(" where login = '")
                                                         .append(person.getLogin()).append("'");
        try
        {
            final PreparedStatement pdt = connection.prepareStatement(sqlQuery.toString());
            final ResultSet rs = pdt.executeQuery();
            final Person dbPerson;

            if(rs.next())
            {
              dbPerson = new Person(rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getString("login"), rs.getString("password"));

              if(!encoder.matches(person.getPassword(), dbPerson.getPassword()))
              {
                  return null;
              }
              return dbPerson;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Person getPersonById(Integer id)
    {
        final StringBuilder sqlBuilder = new StringBuilder(PERSON_SQL).append(" WHERE id = ").append(id);
        String query = sqlBuilder.toString();

        try
        {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            Person person = null;

            while (rs.next())
            {
                person = new Person(rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getString("login"), rs.getString("password"));
            }
            return person;
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public List<Person> listSortedByName(String sortType) throws Exception {
        final List<Person> list = new ArrayList<>();

        StringBuilder sqlBuilder = new StringBuilder(PERSON_SQL + " p ORDER BY ");

        switch (sortType)
        {
            case "createdOn":
                sqlBuilder.append("p.id");
                break;
            case "name":
                sqlBuilder.append("p.name");
                break;
            default:
                break;
        }

        final String personsOrdered = sqlBuilder.toString();

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
            throw new Exception();
        }
    }

    public Integer countPerson() {
        String sqlBuilder = new StringBuilder("SELECT COUNT(id) as qtd ").append(PERSON_FROM_SQL).toString();

        try {
            PreparedStatement statement = connection.prepareStatement(sqlBuilder);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return Integer.parseInt(rs.getString("qtd"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    public void update(Person person, Integer id) throws Exception
    {
        final StringBuilder sqlBuilder = new StringBuilder("UPDATE person SET name=?, login=? WHERE id = ?");

        try
        {
            final PreparedStatement statement = connection.prepareStatement(sqlBuilder.toString());
            statement.setString(1, person.getName());
            statement.setString(2, person.getLogin());
            statement.setInt(3, id);

            int update = statement.executeUpdate();

            if(update > 0)
            {
                connection.commit();
                return;
            }
            connection.rollback();
            throw new Exception("Operação não realizada");
        }
        catch (Exception e)
        {
            throw new Exception();
        }
    }
}
