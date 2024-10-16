package com.sio.repositories;

import com.sio.models.Target;
import com.sio.tools.DataSourceProvider;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TargetRepository {
    private DataSource dataSource;
    private Connection connection;


    public TargetRepository() {
        this.dataSource = DataSourceProvider.getDataSourceInstance();
    }

    /**
     * Find a target by hash
     * @param hash String
     * @return Target
     */
    public Target findByHash(String hash) {
        try{
            this.connection = this.dataSource.getConnection();
            PreparedStatement pSt = this.connection.prepareStatement("SELECT hash,codeName,name FROM targets WHERE hash = ?");
            pSt.setString(1, hash);
            ResultSet rs = pSt.executeQuery();
            if(rs.next()){
                return new Target(
                        rs.getString("hash"),
                        rs.getString("codeName"),
                        rs.getString("name")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                if (this.connection != null) {
                    this.connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        return null;
    }

    /**
     * Find all targets
     * @return ArrayList<Target>
     */
    public ArrayList<Target> findAll() {
        ArrayList<Target> targets = new ArrayList<>();
        try {
            this.connection = this.dataSource.getConnection();
            PreparedStatement pSt = this.connection.prepareStatement("SELECT hash, codeName, name FROM targets");
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                targets.add(new Target(
                        rs.getString("hash"),
                        rs.getString("codeName"),
                        rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                if (this.connection != null) {
                    this.connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        return targets;
    }

    /**
     * Create a target
     * @param t Target
     */
    public void create(Target t) {
        try {
            this.connection = this.dataSource.getConnection();
            PreparedStatement pSt = this.connection.prepareStatement("INSERT INTO targets (hash, codeName, name) VALUES (?, ?, ?)");
            pSt.setString(1, t.getHash());
            pSt.setString(2, t.getCodeName());
            pSt.setString(3, t.getName());
            pSt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                if (this.connection != null) {
                    this.connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Delete a target
     * @param t Target
     */
    public void delete(Target t) {
        try {
            this.connection = this.dataSource.getConnection();
            PreparedStatement pSt = this.connection.prepareStatement("DELETE FROM targets WHERE hash = ?");
            pSt.setString(1, t.getHash());
            pSt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                if (this.connection != null) {
                    this.connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}
