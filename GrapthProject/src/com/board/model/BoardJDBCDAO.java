package com.board.model;
import java.sql.*;
import java.util.*;


public class BoardJDBCDAO implements BoardDAO_interface {
	String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	String url = "jdbc:sqlserver://localhost:1433;DatabaseName=project";
	String userid = "sa";
	String passwd = "sa123456";
	
	
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
		
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, boardVO.getB_id());
			pstmt.setInt(2, boardVO.getM_id());
			pstmt.setString(3, boardVO.getB_name());
			pstmt.setInt(4, boardVO.getC_id());
			pstmt.setString(5, boardVO.getB_description());
			pstmt.setString(6, boardVO.getB_authority());
			
			pstmt.executeUpdate();
		
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
	public void update(BoardVO boardVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STMT);
			
//			pstmt.setInt(2, boardVO.getM_id());
			pstmt.setString(1, boardVO.getB_name());
//			pstmt.setInt(4, boardVO.getC_id());
			pstmt.setString(2, boardVO.getB_description());
			pstmt.setString(3, boardVO.getB_authority());
			pstmt.setInt(4, boardVO.getB_id());
			
			pstmt.executeUpdate();
			
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
			
		Class.forName(driver);
		con = DriverManager.getConnection(url, userid, passwd);
		pstmt = con.prepareStatement(DELETE_STMT);
		
		pstmt.setInt(1, b_id);
		
		pstmt.executeUpdate();
		
		
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		}catch(ClassNotFoundException e){
			throw new RuntimeException("Could't load database driver." + e.getMessage());
			
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
	public static void main(String[] args) {
		
		BoardJDBCDAO bd = new BoardJDBCDAO();
		
//		//insert
//		BoardVO bdVO1 = new BoardVO();
//		bdVO1.setB_id(1);
//		bdVO1.setC_id(1);
//		bdVO1.setB_name("Spoart");
//		bdVO1.setM_id(1001);
//		bdVO1.setB_description("basaball");
//		bdVO1.setB_authority("public");
//		bd.insert(bdVO1);
		
		//Update
		BoardVO bdVO2 = new BoardVO();
		bdVO2.setB_name("act");
		bdVO2.setB_description("basaball");
		bdVO2.setB_authority("public");
		bdVO2.setB_id(1);
		bd.update(bdVO2);
		
		
		//Delete
//		bd.delete(1);
		
		//select-one
		BoardVO boardVO3 = bd.findByPrimaryKey(1);
		System.out.println(boardVO3.getB_id() + ",");
		System.out.println(boardVO3.getC_id() + ",");
		System.out.println(boardVO3.getB_name() + ",");
		System.out.println(boardVO3.getM_id() + ",");
		System.out.println(boardVO3.getB_description() + ",");
		System.out.println(boardVO3.getB_authority());
		System.out.println("-------------------------");
		
		
		//select-all
//		List<BoardVO> list = bd.getAll();
//		for (BoardVO aBd : list) {
//			System.out.print(aBd.getB_id() + ",");
//			System.out.print(aBd.getC_id() + ",");
//			System.out.print(aBd.getB_name() + ",");
//			System.out.print(aBd.getM_id() + ",");
//			System.out.print(aBd.getB_description() + ",");
//			System.out.print(aBd.getB_authority() + ",");
//			System.out.println();
	
//		}
	}
}
