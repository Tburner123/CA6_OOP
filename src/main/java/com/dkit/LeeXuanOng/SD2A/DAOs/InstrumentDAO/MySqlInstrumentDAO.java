package com.dkit.LeeXuanOng.SD2A.DAOs.InstrumentDAO;

import com.dkit.LeeXuanOng.SD2A.DAOException.DAOException;
import com.dkit.LeeXuanOng.SD2A.DAOs.DAO;
import com.dkit.LeeXuanOng.SD2A.DTOs.Instrument;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlInstrumentDAO extends DAO implements InstrumentDAOInterface {


    @Override
    public List<Instrument> findAllInstruments() throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Instrument> instrumentsList = new ArrayList<>();

        try {
            connection = this.getConnection();
            String query = "SELECT * FROM INSTRUMENT";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                int instrumentId = rs.getInt("insId");
                String insName = rs.getString("insName");
                int insStock = rs.getInt("insStock");
                String instrumentDescription = rs.getString("insDesc");
                double insPrice = rs.getDouble("insPrice");
                String insCategory = rs.getString("insCategory");
                Instrument i = new Instrument(instrumentId, insName, insCategory, insStock, insPrice, insCategory);
                instrumentsList.add(i);
            }
        } catch (SQLException e) {
            throw new DAOException("findAllInstruments() " + e.getMessage());
        } finally {
            try{
                if(rs != null){
                    rs.close();
                }
                if(ps != null){
                    ps.close();
                }
                if(connection != null){
                    this.freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DAOException("findAllInstruments() " + e.getMessage());
            }

        }
        return instrumentsList;
    }

    @Override
    public Instrument findInstrumentByInstrumentId(int instrumentId) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Instrument i = null;

        try {
            connection = this.getConnection();
            String query = "SELECT * FROM INSTRUMENT WHERE insId = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, instrumentId);
            rs = ps.executeQuery();
            if (rs.next()) {
                String insName = rs.getString("insName");
                int insStock = rs.getInt("insStock");
                String instrumentDescription = rs.getString("insDesc");
                double insPrice = rs.getDouble("insPrice");
                String insCategory = rs.getString("insCategory");
                i = new Instrument(instrumentId, insName, insCategory, insStock, insPrice, insCategory);
            }
        } catch (SQLException e) {
            throw new DAOException("findInstrumentByInstrumentId() " + e.getMessage());
        } finally {
            try{
                if(rs != null){
                    rs.close();
                }
                if(ps != null){
                    ps.close();
                }
                if(connection != null){
                    this.freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DAOException("findInstrumentByInstrumentId() " + e.getMessage());
            }

        }
        return i;
    }

    @Override
    public boolean deleteInstrument(int instrumentId) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        int result = 0;

        try {
            connection = this.getConnection();
            String query = "DELETE FROM INSTRUMENT WHERE insId = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, instrumentId);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("deleteInstrument() " + e.getMessage());
        } finally {
            try{
                if(ps != null){
                    ps.close();
                }
                if(connection != null){
                    this.freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DAOException("deleteInstrument() " + e.getMessage());
            }

        }
        return (result == 1);
    }

    @Override
    public boolean addInstrument(Instrument instrument) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        int result = 0;
        Instrument instruemnt = null;

        try{
            connection = this.getConnection();
            String query = "INSERT INTO INSTRUMENT (insName, insStock, insDesc, insPrice, insCategory) VALUES (?,?,?,?,?)";
            ps = connection.prepareStatement(query);
            ps.setString(1, instrument.getInsName());
            ps.setInt(2, instrument.getInsStrock());
            ps.setString(3, instrument.getInsDesc());
            ps.setDouble(4, instrument.getInsPrice());
            ps.setString(5, instrument.getInsCategory());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("addInstrument() " + e.getMessage());
        } finally {
            try{
                if(ps != null){
                    ps.close();
                }
                if(connection != null){
                    this.freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DAOException("addInstrument() " + e.getMessage());
            }

        }
        return (result == 1);
    }
}