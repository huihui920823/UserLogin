package com.huihui.service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.huihui.domain.User;
import com.huihui.util.SqlHelper;

public class UserService {
	
	//��֤�û��Ƿ�Ϸ��ĺ���
	public boolean checkUser(User user){
		boolean flag = false;
		try {
			String sql = "select * from users where username = ? and passwd = ?";
			String[] parameters = {user.getUsername(),user.getPasswd()};
			
			ArrayList list = SqlHelper.executeQuery1(sql, parameters);
			if(list.size()==1){
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	// ��֤�û��Ƿ�Ϸ��ĺ���
//	public boolean checkUser(User user) {
//		boolean flag = false;
//		try {
//			String sql = "select * from users where username=? and passwd=?";
//			String[] parameters = { user.getUsername(), user.getPasswd() };
//			ResultSet rs = SqlHelper.executeQuery(sql, parameters);
//			if (rs.next()) {
//				flag = true;
//			}
//			SqlHelper
//					.close(rs, SqlHelper.getPstmt(), SqlHelper.getConnection());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return flag;
//	}

	// ��ѯusers���й��ж�������¼
	public int getRowCount() {
		int i = 0;
		String sql = "select count(*) from users";
		ResultSet rs = null;
		try {
			rs = SqlHelper.executeQuery(sql, null);
			rs.next();
			i = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper
					.close(rs, SqlHelper.getPstmt(), SqlHelper.getConnection());
		}
		return i;
	}

	// ��ѯ��ҳ�е�ǰҳ������ʾusers�е�����
	public ArrayList<User> getUserInfo(int pageNow, int pageSize) {
		ArrayList<User> list = new ArrayList<User>();
		String sql = "select * from users order by id limit " + (pageNow - 1)
				* pageSize + "," + pageSize + "";
		ResultSet rs = null;
		try {
			rs = SqlHelper.executeQuery(sql, null);
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setGrade(rs.getInt("grade"));
				user.setPasswd(rs.getString("passwd"));
				list.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper
					.close(rs, SqlHelper.getPstmt(), SqlHelper.getConnection());
		}
		return list;
	}

	// ����û�
	public boolean addUser(User user) {
		boolean flag = true;
		String sql = "insert into users (username,email,grade,passwd) values (?,?,?,?)";
		String parameters[] = { user.getUsername(), user.getEmail(),
				user.getGrade() + "", user.getPasswd() };
		try {
			SqlHelper.executeUpdate1(sql, parameters);
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	// �޸��û�
	public boolean updateUser(User user) {
		boolean flag = true;
		String sql = "update users set username=?,email=?,grade=?,passwd=? where id=?";
		String[] parameters = { user.getUsername(), user.getEmail(),
				user.getGrade() + "", user.getPasswd(), user.getId() + "" };
		try {
			SqlHelper.executeUpdate1(sql, parameters);
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	// ɾ���û�
	public boolean delUser(String id) {
		boolean flag = true;
		String sql = "delete from users where id = ?";
		String parameters[] = { id };
		try {
			SqlHelper.executeUpdate1(sql, parameters);
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	// �����û�ID�����û�
	public User selectUser(String id) {
		User user = new User();
		String sql = "select * from users where id=?";
		String[] parameters = { id };
		ResultSet rs = null;
		try {
			rs = SqlHelper.executeQuery(sql, parameters);
			if (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setGrade(rs.getInt("grade"));
				user.setPasswd(rs.getString("passwd"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper
					.close(rs, SqlHelper.getPstmt(), SqlHelper.getConnection());
		}
		return user;
	}

	// �����û��������û���Ϣ(��ȷ��ѯ)
	public ArrayList<User> selectUserByName1(String username) {
		ArrayList<User> list = new ArrayList<User>();
		String sql = "select * from users where username=?";
		String[] parameters = { username };
		ResultSet rs = null;
		try {
			rs = SqlHelper.executeQuery(sql, parameters);
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setGrade(rs.getInt("grade"));
				user.setPasswd(rs.getString("passwd"));
				list.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper
					.close(rs, SqlHelper.getPstmt(), SqlHelper.getConnection());
		}
		return list;
	}

	// �����û��������û���Ϣ(ģ����ѯ)
	public ArrayList<User> selectUserByName2(String username) {
		ArrayList<User> list = new ArrayList<User>();
		String sql = "select * from users where username like ?";
		String[] parameters = { "%" + username + "%" };
		ResultSet rs = null;
		try {
			rs = SqlHelper.executeQuery(sql, parameters);
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setGrade(rs.getInt("grade"));
				user.setPasswd(rs.getString("passwd"));
				list.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper
					.close(rs, SqlHelper.getPstmt(), SqlHelper.getConnection());
		}

		return list;
	}

	// �����û���ģ����ѯ�����û���Ϣ����
	public int selectUserByNameCount(String username) {
		int i = 0;
		String sql = "select Count(*) from users where username like ?";
		String[] parameters = { "%" + username + "%" };
		ResultSet rs = null;
		try {
			rs = SqlHelper.executeQuery(sql, parameters);
			rs.next();
			i = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper
			.close(rs, SqlHelper.getPstmt(), SqlHelper.getConnection());
		}
		return i;
	}

}
