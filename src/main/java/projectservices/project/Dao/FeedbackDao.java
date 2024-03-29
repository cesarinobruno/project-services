package projectservices.project.Dao;

import projectservices.project.Repository.SingleConnection;
import projectservices.project.model.Feedback;
import projectservices.project.model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDao
{
    private Connection connection;

    private static String FEEDBACK_SQL = "SELECT * FROM feedback fd";
    private static String FEEDBACK_INSERT = "INSERT INTO feedback (message, personId)";

    public FeedbackDao()
    {
        connection = SingleConnection.getConnection();
    }

    public List<Feedback> feedbackList() throws SQLException
    {
        final List<Feedback> feedbackListResult = new ArrayList<>();
        final StringBuilder sqlBuilder = new StringBuilder(FEEDBACK_SQL);
        PreparedStatement statement = connection.prepareStatement(sqlBuilder.toString());
        ResultSet rs = statement.executeQuery();

        while (rs.next())
        {
            Feedback feedback = new Feedback();
            feedback.setId(rs.getInt("id"));
            feedback.setMessage(rs.getString("message"));
            feedback.setPersonId(rs.getInt("personId"));

            feedbackListResult.add(feedback);
        }
         return feedbackListResult;
    }
    public void checkForFeedbackFromUser(final Integer personId, final Feedback feedback)
    {
        StringBuilder sqlBuilder = new StringBuilder(FEEDBACK_SQL + " WHERE fd.personId = ").append(personId);

        try
        {
            PreparedStatement statement = connection.prepareStatement(sqlBuilder.toString());
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
        StringBuilder sqlBuilder = new StringBuilder(FEEDBACK_INSERT + " VALUES (?, ?)");
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
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
