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

    public Boolean checkForFeedbackFromUser(final Integer personId)
    {
        String selectFeedbackQuery = "SELECT * FROM feedback WHERE personId = ".concat(personId.toString());

        try
        {
            PreparedStatement statement = connection.prepareStatement(selectFeedbackQuery);
            ResultSet rs = statement.executeQuery();

            if(rs.next())
            {
                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt("id"));
                feedback.setMessage(rs.getString("message"));

                return true;
            }

            return null;

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;

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
