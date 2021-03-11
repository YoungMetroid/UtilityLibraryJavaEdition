package com.Utility_Library;

import java.sql.*;

public class DataBaseHandler {

    public String _DB_URL = "";
    public String _User  = "";
    public String _Password = "";
    public Connection connection = null;
    public Statement statement = null;
    public static ErrorLogger logger;
    public DataBaseHandler()
    {
        logger = ErrorLogger.getInstance();
    }
    public boolean OpenConnection()
    {

        try{

            connection = DriverManager.getConnection(_DB_URL,_User,_Password);
            if(connection != null){
                DatabaseMetaData dm = (DatabaseMetaData) connection.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
                return true;
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            logger.logException(ex);
        }

        return false;
    }
    public boolean CloseConnection()
    {
        try {
            connection.close();
            return true;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            logger.logException(ex);
        }
        return false;
    }

    public boolean Execute(String query)
    {
        try
        {
            statement = connection.createStatement();
            statement.execute(query);
            return true;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            logger.logException(ex);
        }
        return false;
    }

}
