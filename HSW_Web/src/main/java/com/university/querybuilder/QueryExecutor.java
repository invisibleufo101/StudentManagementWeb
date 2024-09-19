package com.university.querybuilder;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.university.model.Model;
import com.university.util.JDBCUtil;

public class QueryExecutor {
	
	public QueryExecutor() {}
	
	<T extends Model> List<T> fetchResults(String query, List<Object> parameters, Class<? extends Model> modelClass){
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<T> modelObjects = new ArrayList<>();
        
        try {
            conn = JDBCUtil.getConnection();
            pstmt = conn.prepareStatement(query);
            setParameters(pstmt, parameters);
            rs = pstmt.executeQuery();
            modelObjects = RowMapHandler.mapRows(modelClass, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(rs, pstmt, conn);
        }

        return modelObjects;
	}
	
	<T extends Model> T fetchResult(String query, List<Object> parameters, Class<? extends Model> modelClass) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		T modelObject = null;
		
		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			setParameters(pstmt, parameters);
			rs = pstmt.executeQuery();
			modelObject = RowMapHandler.mapRow(modelClass, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
		
		return modelObject;
	}
	
	void execute(String query, List<Object> parameters, Class<? extends Model> modelClass) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			setParameters(pstmt, parameters);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(pstmt, conn);
		}
	}
	
	private void setParameters(PreparedStatement pstmt, List<Object> parameters) throws SQLException {
		for (int idx=1; idx<=parameters.size(); idx++) {
			Object parameter = parameters.get(idx-1); //  subtract 1 from idx to avoid ArrayOutOfBounds
			
			if (parameter instanceof String) {
				pstmt.setString(idx, (String) parameter);
			} else if (parameter instanceof Date) { // (!) this is java.sql.Date
				pstmt.setDate(idx, (Date) parameter);
			} else if (parameter instanceof Boolean) {
				pstmt.setBoolean(idx, (Boolean) parameter);
			} else if (parameter instanceof Byte) {
				pstmt.setByte(idx, (Byte) parameter);
			} else if (parameter instanceof Short) {
				pstmt.setShort(idx, (Short) parameter);
			} else if (parameter instanceof Double) {
				pstmt.setDouble(idx, (Double) parameter);
			} else if (parameter instanceof Float) {
				pstmt.setFloat(idx, (Float) parameter);
			} else if (parameter instanceof Long) {
				pstmt.setLong(idx, (Long) parameter);
			} else if (parameter instanceof Integer) {
				pstmt.setInt(idx, (Integer) parameter);
			} else if (parameter instanceof Array) {
				pstmt.setArray(idx, (Array) parameter);
			} else {
				throw new SQLException();
			}
		}
	}
}

