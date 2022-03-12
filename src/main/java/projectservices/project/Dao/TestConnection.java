package projectservices.project.Dao;

import projectservices.project.Repository.SingleConnection;

import java.sql.Connection;

public class TestConnection
{
    public static void main(String[] args)
    {
        Connection conn = SingleConnection.getConnection();
        System.out.println(conn);
    }
}