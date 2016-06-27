package com.huihui.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;


/**
 * ����һ���������ݿ�Ĺ�����
 * 
 * @author Administrator
 * 
 */
public class SqlHelper {

	private static String DBDRIVER;
	private static String DBURL;
	private static String DBUSER;
	private static String DBPASS;

	// ������Ҫ�ı���
	private static Connection conn = null;
	private static PreparedStatement pstmt = null;
	private static ResultSet rs = null;

	public static Connection getConn() {
		return conn;
	}

	public static PreparedStatement getPstmt() {
		return pstmt;
	}

	public static ResultSet getRs() {
		return rs;
	}

	// ����������ֻ��Ҫ����һ��
	static {
		FileInputStream fis = null;

		try {
			// ��dbinfo.properties�ļ��ж�ȡ������Ϣ
			Properties pp = new Properties();
			fis = new FileInputStream(
					"E:\\myeclipse10code\\UserLoginUp\\dbinfo.properties");
			pp.load(fis);
			DBDRIVER = pp.getProperty("driver");
			DBURL = pp.getProperty("url");
			DBUSER = pp.getProperty("user");
			DBPASS = pp.getProperty("pass");

			Class.forName(DBDRIVER);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			fis = null;
		}
	}

	// �õ�����
	public static Connection getConnection() {
		try {
			conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	// �ر���Դ
	public static void close(ResultSet rs, PreparedStatement pstmt,
			Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				rs = null;
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pstmt = null;
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				conn = null;
			}
		}
	}

	// ��дһ��update/delete/insert
	// sql��ʽ�� update ���� set �ֶ���=�� where �ֶ�=��
	public static void executeUpdate1(String sql, String[] parameters) {
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			// ������ֵ
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					pstmt.setString(i + 1, parameters[i]);
				}
			}
			// ִ��
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			close(rs, pstmt, conn);
		}
	}

	// ����ж��update/delete/insert��䡾��Ҫ��������
	public static void executeUpdate2(String[] sqls, String[][] parameters) {
		try {
			conn = getConnection();
			// ��Ϊ��ʱ�û�����Ŀ����Ƕ��sql���
			conn.setAutoCommit(false);
			if (sqls != null) {
				for (int i = 0; i < parameters.length; i++) {
					pstmt = conn.prepareStatement(sqls[i]);
					if (parameters[i] != null) {
						for (int j = 0; j < parameters[i].length; j++) {
							pstmt.setString(j + 1, parameters[i][j]);
						}
					}
					pstmt.executeUpdate();
				}
			}
			conn.commit();
		} catch (Exception e) {
			// �ع�
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			close(rs, pstmt, conn);
		}
	}

	// ͳһ��select
	public static ResultSet executeQuery(String sql, String[] parameters) {

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					pstmt.setString(i + 1, parameters[i]);
				}
			}
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			// �������ر�����Դ����û��ʹ��ResultSet�ˣ�Ҳ��û��return��
			// close(rs, pstmt, conn);
		}

		return rs;
	}
	
	//�޸ĺ��ͳһ��select (ResultSet-->ArrayList)
	public static ArrayList executeQuery1(String sql,String[] parameters){
		ArrayList list = new ArrayList();
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			
			
			if(parameters!=null){
				for (int i = 0; i < parameters.length; i++) {
					pstmt.setString(i+1, parameters[i]);
				}
			}
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int column = rsmd.getColumnCount();//������Եõ����ѯ��䷵�ص��ܵ�����
			
			while (rs.next()) {
				Object[] ob = new Object[column];//�������飬��ʾһ������
				for (int i = 0; i < column; i++) {
					ob[i] = rs.getObject(i+1);
				}
				list.add(ob);
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally{
			//�ر���Դ
			close(rs, pstmt, conn);
		}
		
	}

}
