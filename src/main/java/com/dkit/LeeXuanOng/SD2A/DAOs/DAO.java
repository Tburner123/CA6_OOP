package com.dkit.LeeXuanOng.SD2A.DAOs;

import java.sql.*;

import com.dkit.LeeXuanOng.SD2A.DAOExceptions.DAOException;


/** MySqlDao -
 * - implements functionality that is common to all MySQL DAOs
 * - i.e. getConection() and freeConnection()
 * All MySQL DAOs will extend (inherit from) this class in order to
 * gain the connection functionality, thus avoiding inclusion
 * of this code in every DAO class.
 *
 */
public class DAO {

    protected boolean test_database = false;


    public boolean getTest_database() {
        return test_database;
    }
    public void isTest_database(){
        this.test_database = !this.test_database;
    }

    public Connection getConnection() throws DAOException
    {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url;
        if(test_database){
             url = "jdbc:mysql://localhost:3306/instrument_data_testing";
        } else {
             url = "jdbc:mysql://localhost:3306/instrument_data";
        }
        String username = "root";
        String password = "";
        Connection connection = null;

        try
        {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Failed to find driver class " + e.getMessage());
            System.exit(1);
        }
        catch (SQLException e)
        {
            System.out.println("Connection failed " + e.getMessage());
            System.exit(2);
        }
        return connection;
    }

    public void freeConnection(Connection connection) throws DAOException
    {
        try
        {
            if (connection != null)
            {
                connection.close();
            }
        }
        catch (SQLException e)
        {
            System.out.println("Failed to free connection: " + e.getMessage());
            System.exit(1);
        }
    }

    public int getLastAffectID(String table) throws DAOException{
        int id = 0;
        try{
            Connection connection = this.getConnection();
            String query = "SELECT LAST_INSERT_ID();";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet result = ps.executeQuery();
            if(result.next()){
                id = result.getInt(1);
            }
        } catch (SQLException e ){
            throw new DAOException("getLastAffectID() " + e.getMessage());
        }
        return id;
    }
}