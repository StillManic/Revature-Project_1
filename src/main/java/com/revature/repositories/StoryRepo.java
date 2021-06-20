package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revature.models.Author;
import com.revature.models.Editor;
import com.revature.models.Genre;
import com.revature.models.Story;
import com.revature.models.StoryType;
import com.revature.utils.JDBCConnection;

public class StoryRepo implements GenericRepo<Story> {
	private Connection conn = JDBCConnection.getConnection();
	
	@Override
	public Story add(Story s) {
		String sql = "insert into stories values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning *";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, s.getTitle());
			ps.setInt(2, s.getGenre().getId());
			ps.setInt(3, s.getType().getId());
			ps.setInt(4, s.getAuthor().getId());
			ps.setString(5, s.getDescription());
			ps.setString(6, s.getTagLine());
			ps.setDate(7, s.getCompletionDate());
			ps.setString(8, s.getApprovalStatus());
			ps.setString(9, s.getReason());
			ps.setDate(10, s.getSubmissionDate());
			if (s.getAssistant() == null) ps.setNull(11, java.sql.Types.INTEGER);
			else ps.setInt(11, s.getAssistant().getId());
			if (s.getEditor() == null) ps.setNull(12, java.sql.Types.INTEGER);
			else ps.setInt(12, s.getEditor().getId());
			if (s.getSenior() == null) ps.setNull(13, java.sql.Types.INTEGER);
			else ps.setInt(13, s.getSenior().getId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				s.setId(rs.getInt("id"));
				return s;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Story getById(Integer id) {
		String sql = "select * from stories where id = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) return this.make(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Story> getAllByGenre(Genre g) {
		String sql = "select * from stories where genre = ?;";
		try {
			List<Story> list = new ArrayList<Story>();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, g.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(this.make(rs));
			}
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Story> getAllByStatus(String status) {
		String sql = "select * from stories where approval_status = ?;";
		try {
			List<Story> list = new ArrayList<Story>();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, status);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(this.make(rs));
			}
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Story> getAllByGenreAndStatus(Genre g, String status) {
		String sql = "select * from stories where genre = ? and approval_status = ?;";
		try {
			List<Story> list = new ArrayList<Story>();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, g.getId());
			ps.setString(2, status);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(this.make(rs));
			}
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Map<Integer, Story> getAll() {
		String sql = "select * from stories;";
		try {
			Map<Integer, Story> map = new HashMap<Integer, Story>();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Story s = this.make(rs);
				map.put(s.getId(), s);
			}
			
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean update(Story s) {
		String sql = "update stories set title = ?, description = ?, tag_line = ?, completion_date = ?, approval_status = ?, reason = ?, assistant = ?, editor = ?, senior = ? where id = ? returning *;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, s.getTitle());
			ps.setString(2, s.getDescription());
			ps.setString(3, s.getTagLine());
			ps.setDate(4, s.getCompletionDate());
			ps.setString(5, s.getApprovalStatus());
			ps.setString(6, s.getReason());
			if (s.getAssistant() == null) ps.setNull(7, java.sql.Types.INTEGER);
			else ps.setInt(7, s.getAssistant().getId());
			if (s.getEditor() == null) ps.setNull(8, java.sql.Types.INTEGER);
			else ps.setInt(8, s.getEditor().getId());
			if (s.getSenior() == null) ps.setNull(9, java.sql.Types.INTEGER);
			else ps.setInt(9, s.getSenior().getId());
			ps.setInt(10, s.getId());
			return ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean delete(Story s) {
		String sql = "delete from stories where id = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, s.getId());
			return ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Story make(ResultSet rs) throws SQLException {
		Story s = new Story();
		s.setId(rs.getInt("id"));
		s.setTitle(rs.getString("title"));
		Genre g = new GenreRepo().getById(rs.getInt("genre"));
		s.setGenre(g);
		StoryType st = new StoryTypeRepo().getById(rs.getInt("story_type"));
		s.setType(st);
		Author a = new AuthorRepo().getById(rs.getInt("author"));
		s.setAuthor(a);
		s.setDescription(rs.getString("description"));
		s.setTagLine(rs.getString("tag_line"));
		s.setCompletionDate(rs.getDate("completion_date"));
		s.setApprovalStatus(rs.getString("approval_status"));
		s.setReason(rs.getString("reason"));
		s.setSubmissionDate(rs.getDate("submission_date"));
		Editor assistant = new EditorRepo().getById(rs.getInt("assistant"));
		s.setAssistant(assistant);
		Editor editor = new EditorRepo().getById(rs.getInt("editor"));
		s.setEditor(editor);
		Editor senior = new EditorRepo().getById(rs.getInt("senior"));
		s.setSenior(senior);
		return s;
	}

}
