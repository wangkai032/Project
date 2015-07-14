package com.board.model;
import java.sql.*;
import java.util.*;

import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class BoardJNDIDAO implements BoardDAO_interface {
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
		private static DataSource ds = null;
		static {
			try {
				Context ctx = new InitialContext();
				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
	
	
	private static final String INSERT_STMT = 
			"INSERT INTO Board(b_id, m_id, b_name, c_id, b_description, b_authority) VALUES(?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_STMT = 
			"UPDATE Board SET   b_name=?,  b_description=?, b_authority=? WHERE b_id=?";
	private static final String DELETE_STMT = 
			"DELETE Board WHERE b_id = ?";
	private static final String GET_ONE_STMT = 
			"SELECT b_id, m_id, b_name, c_id, b_description, b_authority FROM Board WHERE b_id=?";
	private static final String GET_ALL_STMT = 
			"SELECT b_id, m_id, b_name, c_id, b_description, b_authority FROM Board ORDER BY b_id";
	

	@Override
	public void insert(BoardVO boardVO){
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
		
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, boardVO.getB_id());
			pstmt.setInt(2, boardVO.getM_id());
			pstmt.setString(3, boardVO.getB_name());
			pstmt.setInt(4, boardVO.getC_id());
			pstmt.setString(5, boardVO.getB_description());
			pstmt.setString(6, boardVO.getB_authority());
			
			pstmt.executeUpdate();
		
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}


	@Override
	public void update(BoardVO boardVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			
//			pstmt.setInt(2, boardVO.getM_id());
			pstmt.setString(1, boardVO.getB_name());
//			pstmt.setInt(4, boardVO.getC_id());
			pstmt.setString(2, boardVO.getB_description());
			pstmt.setString(3, boardVO.getB_authority());
			pstmt.setInt(4, boardVO.getB_id());
			
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(Integer b_id) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			
		
		con = ds.getConnection();
		pstmt = con.prepareStatement(DELETE_STMT);
		
		pstmt.setInt(1, b_id);
		
		pstmt.executeUpdate();
		
		
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}


	@Override
	public BoardVO findByPrimaryKey(Integer b_id) {
		BoardVO boardVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1, b_id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				boardVO = new BoardVO();
				boardVO.setB_id(rs.getInt("b_id"));
				boardVO.setC_id(rs.getInt("c_id"));
				boardVO.setB_name(rs.getString("b_name"));
				boardVO.setM_id(rs.getInt("m_id"));
				boardVO.setB_description(rs.getString("b_description"));
				boardVO.setB_authority(rs.getString("b_authority"));
				
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return boardVO;
	}
	

	@Override
	public List<BoardVO> getAll() {
		List<BoardVO> list = new ArrayList<BoardVO>();
		BoardVO boardVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				boardVO = new BoardVO();
				boardVO.setB_id(rs.getInt("b_id"));
				boardVO.setC_id(rs.getInt("c_id"));
				boardVO.setB_name(rs.getString("b_name"));
				boardVO.setM_id(rs.getInt("m_id"));
				boardVO.setB_description(rs.getString("b_description"));
				boardVO.setB_authority(rs.getString("b_authority"));
				list.add(boardVO);

			}
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally{
			if(rs != null){
				try{
					rs.close();
				}catch(SQLException se){
					se.printStackTrace(System.err);
				}
			}
			if(pstmt != null) {
				try{
					pstmt.close();
				}catch(SQLException se){
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

}
