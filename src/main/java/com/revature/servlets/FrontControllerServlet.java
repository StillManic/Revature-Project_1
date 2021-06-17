package com.revature.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revature.models.Author;
import com.revature.models.Genre;
import com.revature.models.Story;
import com.revature.models.StoryType;
import com.revature.repositories.AuthorRepo;
import com.revature.repositories.GenreRepo;
import com.revature.repositories.StoryRepo;
import com.revature.repositories.StoryTypeRepo;

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
	
	private Gson gson = new Gson();
	public static HttpSession session;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Story.class, new Story.Deserializer());
		gsonBuilder.setDateFormat("yyyy-MM-dd");
		this.gson = gsonBuilder.create();
		
		String uri = request.getRequestURI();
		System.out.println(uri);
		String json;
		
//		request.getRequestDispatcher("index.jsp").forward(request, response);
		
		response.setHeader("Access-Control-Allow-Origin","*");		// Needed to avoid CORS violations
		response.setHeader("Content-Type", "application/json");		// Needed to enable json data to be passed between front and back end

		session = request.getSession();
		
		uri = uri.substring("/Project_1/controller/".length());
		switch (uri) {
			case "/add":
				int i = Integer.parseInt(request.getParameter("num1"));
				int j = Integer.parseInt(request.getParameter("num2"));
				
				int k = i + j;
				
				response.setContentType("text/plain");
				
				response.getWriter().println("The sum is: " + k);
				break;
			case "editor_login":
				break;
			case "author_login":
				System.out.println("Received author_login!");
				LoginInfo info = this.gson.fromJson(request.getReader(), LoginInfo.class);
				Author a = (new AuthorRepo()).getByUsernameAndPassword(info.username, info.password);
				if (a != null) {
					System.out.println("Author " + a.getFirstName() + " has logged in!");
					session.setAttribute("logged_in", a);
//					response.sendRedirect("story_proposal_form.html");
					response.getWriter().append("story_proposal_form.html");
				} else {
					System.out.println("Failed to login with credentials: username=" + info.username + " password=" + info.password);
				}
				break;
			case "get_story_types":
				List<StoryType> types = new ArrayList<StoryType>((new StoryTypeRepo()).getAll().values());
				List<Genre> genres = new ArrayList<Genre>((new GenreRepo()).getAll().values());
				a = (Author) session.getAttribute("logged_in");
				String[] jsons = new String[] { this.gson.toJson(types), this.gson.toJson(genres), this.gson.toJson(a) };
				json = gson.toJson(jsons);
				
				response.getWriter().append(json);
				break;
			case "submit_story_form":
//				StoryInfo stInfo = this.gson.fromJson(request.getReader(), StoryInfo.class);
//				System.out.println(stInfo);
				Story story = this.gson.fromJson(request.getReader(), Story.class);
				a = (Author) session.getAttribute("logged_in");
				story.setApprovalStatus("submitted");
				story.setAuthor(a);
				story = (new StoryRepo()).add(story);
				System.out.println(story);
				break;
			default: break;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);
	}
}
