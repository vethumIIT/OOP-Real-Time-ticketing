package com.project.realtime_ticketing_system.repositories;


import com.project.realtime_ticketing_system.models.data_transfer_objects.RepoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.SQLiteErrorCode;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManager {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);
    private String url = "jdbc:sqlite:mydatabase.db";

    public DatabaseManager(){}

    public DatabaseManager(String url){
        this.url=url;
    }

    public String writeDatabase(String SQLCommand, List<Object> parameters){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection(this.url);

            PreparedStatement stmt = c.prepareStatement(SQLCommand);

            if (parameters!=null) {
                for (int p = 1; p <= parameters.size(); p++) {

                    Object param = parameters.get(p - 1);

                    if (param instanceof Long) {
                        stmt.setLong(p, (Long) param);
                    } else if (param instanceof String) {
                        stmt.setString(p, (String) param.toString());
                    } else if (param instanceof Integer) {
                        stmt.setInt(p, (Integer) param);
                    } else if (param instanceof Double) {
                        stmt.setDouble(p, (Double) param);
                    }
                }
            }
            stmt.executeUpdate();
            stmt.close();
            c.close();

            return "Success";
        }catch (SQLException e){
            if (e.getErrorCode() == SQLiteErrorCode.SQLITE_CONSTRAINT.code){
                logger.error(e.getMessage());
                return "Exists";
            }else {
                logger.error("Error: {}",e.getMessage());
                throw new RuntimeException(e.getMessage());
                //return "Error";
            }
        }
        catch (Exception e) {
            logger.error("createEvent Error: {}",e.getMessage());
            throw new RuntimeException(e.getMessage());
            //return "Error";
        }
    }

    public RepoResponse writeDatabaseReturn(String SQLCommand, List<Object> parameters){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection(this.url);

            PreparedStatement stmt = c.prepareStatement(SQLCommand);

            if (parameters!=null) {
                for (int p = 1; p <= parameters.size(); p++) {

                    Object param = parameters.get(p - 1);

                    if (param instanceof Long) {
                        stmt.setLong(p, (Long) param);
                    } else if (param instanceof String) {
                        stmt.setString(p, (String) param);
                    } else if (param instanceof Integer) {
                        stmt.setInt(p, (Integer) param);
                    } else if (param instanceof Double) {
                        stmt.setDouble(p, (Double) param);
                    }

                }
            }

            int affectedRows = stmt.executeUpdate();

            Long id = null;

            if (affectedRows>0){

                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()){
                    id = generatedKeys.getLong(0);
                    System.out.println("ID for created booking: "+ id);
                }
            }
            stmt.close();
            c.close();

            return new RepoResponse("Success", id);
        } catch (Exception e) {
            //System.out.println("Failed to create record");
            System.out.println(e.toString());
            return new RepoResponse( "Failed", null);
        }
    }

    public List<Map<String,Object>> readDatabase(String SQLCommand, List<Object> parameters){
        List<Map<String, Object>> resultList = new ArrayList<>();
        try{
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection(this.url);

            PreparedStatement stmt = c.prepareStatement(SQLCommand);

            if (parameters!=null) {
                for (int p = 1; p <= parameters.size(); p++) {

                    Object param = parameters.get(p - 1);

                    if (param instanceof Long) {
                        stmt.setLong(p, (Long) param);
                    } else if (param instanceof String) {
                        stmt.setString(p, (String) param);
                    } else if (param instanceof Integer) {
                        stmt.setInt(p, (Integer) param);
                    } else if (param instanceof Double) {
                        stmt.setDouble(p, (Double) param);
                    }
                }
            }

            ResultSet rs = stmt.executeQuery();

            ResultSetMetaData rsMetaData = rs.getMetaData();
            int column_count = rsMetaData.getColumnCount();

            while (rs.next()){

                Map<String, Object> rowMap = new HashMap<>();

                for (int i=1; i<=column_count; i++){

                    String columnName = rsMetaData.getColumnName(i);
                    Object columnValue = rs.getObject(i);

                    rowMap.put(columnName, columnValue);
                }
                resultList.add(rowMap);

            }
            if(resultList.isEmpty()){
                return null;
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return resultList;
    }


}
