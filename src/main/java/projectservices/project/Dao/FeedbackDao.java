package projectservices.project.Dao;

import projectservices.project.Repository.SingleConnection;
import projectservices.project.model.Feedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FeedbackDao
{
    private Connection connection;

    public FeedbackDao()
    {
        connection = SingleConnection.getConnection();
    }

    public void checkForFeedbackFromUser(final Integer personId, final Feedback feedback)
    {
        String selectFeedbackQuery = "SELECT * FROM feedback WHERE personId = ".concat(personId.toString());

        try
        {
            PreparedStatement statement = connection.prepareStatement(selectFeedbackQuery);
            ResultSet rs = statement.executeQuery();

            if(rs.next())
            {
                feedback.setPersonId(rs.getInt("personId"));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void save(final Feedback feedback, final Integer personId)
    {
        String query = "INSERT INTO feedback(message, personId) VALUES (?, ?)";

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, feedback.getMessage());
            preparedStatement.setObject(2, personId);

            preparedStatement.execute();
            connection.commit();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
