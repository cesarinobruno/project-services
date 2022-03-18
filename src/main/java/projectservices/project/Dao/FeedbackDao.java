package projectservices.project.Dao;

import projectservices.project.Repository.SingleConnection;
import projectservices.project.model.Feedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FeedbackDao
{
    private Connection connection;

    public FeedbackDao()
    {
        connection = SingleConnection.getConnection();
    }

    public void save(Feedback feedback, Integer personId)
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
