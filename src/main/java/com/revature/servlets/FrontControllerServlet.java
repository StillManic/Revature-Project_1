package com.revature.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.revature.models.Author;
import com.revature.models.Editor;
import com.revature.models.Genre;
import com.revature.models.Story;
import com.revature.models.StoryType;
import com.revature.repositories.AuthorRepo;
import com.revature.repositories.EditorRepo;
import com.revature.repositories.GenreRepo;
import com.revature.repositories.StoryRepo;
import com.revature.repositories.StoryTypeRepo;
import com.revature.utils.Utils;

public class FrontControllerServlet extends HttpServlet {
	class LoginInfo {
		public String username;
		public String password;
	}
	
//	class StoryInfo {
//		public String title;
//		public String genre;
//		public String type;
//		public String description;
//		public String tagline;
//		public Date date;
//	}
	
	public FrontControllerServlet() {
		Utils.loadEntries();
	}
	
	private Gson gson = new Gson();
	public static HttpSession session;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Story.class, new Story.Deserializer());
//		gsonBuilder.registerTypeAdapter(Author.class, new Author.Deserializer());
		gsonBuilder.setDateFormat("yyyy-MM-dd");
		this.gson = gsonBuilder.create();
		
		String uri = request.getRequestURI();
		System.out.println(uri);
		String json;
		
		response.setHeader("Access-Control-Allow-Origin", "*");		// Needed to avoid CORS violations
		response.setHeader("Content-Type", "application/json");		// Needed to enable json data to be passed between front and back end

		session = request.getSession();
		
		uri = uri.substring("/Project_1/controller/".length());
		switch (uri) {
			case "sign_up_author": {
				System.out.println("Received author sign up!");
				Author a = this.gson.fromJson(request.getReader(), Author.class);
				if (a != null) {
					a = new AuthorRepo().add(a);
					System.out.println("Created new Author " + a + " and logged in!");
					session.setAttribute("logged_in", a);
					// TODO: change this to "author_main.html" when it exits!!!
					response.getWriter().append("editor_main.html");
				} else {
					System.out.println("Failed to create new Author account!");
				}
				break;
			}
			// TODO: can editor login and author login be combined into the same thing? would require that login info across the two tables be unique
			case "editor_login": {
				System.out.println("Recieved editor_login!");
				LoginInfo info = this.gson.fromJson(request.getReader(), LoginInfo.class);
				Editor e = new EditorRepo().getByUsernameAndPassword(info.username, info.password);
				if (e != null) {
					System.out.println("Editor " + e.getFirstName() + " has logged in!");
					session.setAttribute("logged_in", e);
					response.getWriter().append("editor_main.html");
				} else {
					System.out.println("Failed to login with credentials: username=" + info.username + " password=" + info.password);
				}
				break;
			}
			case "author_login": {
				System.out.println("Received author_login!");
				LoginInfo info = this.gson.fromJson(request.getReader(), LoginInfo.class);
				Author a = new AuthorRepo().getByUsernameAndPassword(info.username, info.password);
				if (a != null) {
					System.out.println("Author " + a.getFirstName() + " has logged in!");
					session.setAttribute("logged_in", a);
					response.getWriter().append("story_proposal_form.html");
				} else {
					System.out.println("Failed to login with credentials: username=" + info.username + " password=" + info.password);
				}
				break;
			}
			case "logout": {
				System.out.println("Logging out!");
				session.invalidate();
				response.getWriter().append("index.html");
				break;
			}
			case "get_story_types": {
				List<StoryType> types = new ArrayList<StoryType>(new StoryTypeRepo().getAll().values());
				List<Genre> genres = new ArrayList<Genre>(new GenreRepo().getAll().values());
				Author a = (Author) session.getAttribute("logged_in");
				String[] jsons = new String[] { this.gson.toJson(types), this.gson.toJson(genres), this.gson.toJson(a) };
				json = gson.toJson(jsons);
				response.getWriter().append(json);
				break;
			}
			case "submit_story_form": {
				Story story = this.gson.fromJson(request.getReader(), Story.class);
				Author a = (Author) session.getAttribute("logged_in");
				story.setApprovalStatus("submitted");
				story.setAuthor(a);
				story = new StoryRepo().add(story);
				System.out.println(story);
				break;
			}
			case "get_proposals": {
				Editor e = (Editor) session.getAttribute("logged_in");
				if (e == null) System.out.println("editor null!!!!");
				Set<Genre> genres = Utils.getGenres(e);
				List<Story> stories = new ArrayList<Story>();
				
				for (Genre g : genres) stories.addAll(new StoryRepo().getAllByGenre(g));
				
				json = this.gson.toJson(stories);
				
//				String[] jsons = new String[] { this.gson.toJson(stories), "" };
				response.getWriter().append(json);
				break;
			}
			case "save_story_to_session": {
				JsonElement root = JsonParser.parseReader(request.getReader());
				session.setAttribute("story", root.getAsJsonObject());
				response.getWriter().append("saved");
				break;
			}
			case "get_story_from_session": {
				JsonObject sj = (JsonObject) session.getAttribute("story");
				System.out.println(sj);
				response.getWriter().append(sj.toString());
				break;
			}
			case "approve_story": {
				Story s = this.gson.fromJson(request.getReader(), Story.class);
				String status = s.getApprovalStatus();
				switch (status) {
					case "submitted":
						s.setApprovalStatus("approved_assistant");
						break;
					case "approved_assistant":
						s.setApprovalStatus("approved_editor");
						break;
					case "approved_editor":
						s.setApprovalStatus("approved_senior");
						break;
					case "approved_senior":
						s.setApprovalStatus("waiting_for_draft");
						break;
					case "waiting_for_draft":
						s.setApprovalStatus("draft_approved");
						break;
					default: break;
				}
				
				
				break;
			}
			case "deny_story": {
				break;
			}
			case "request_info": {
				break;
			}
			default: break;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);
	}
}
