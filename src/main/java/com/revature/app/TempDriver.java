package com.revature.app;

import java.sql.Date;

import com.revature.models.Author;
import com.revature.models.Editor;
import com.revature.models.GEJoin;
import com.revature.models.Genre;
import com.revature.models.Story;
import com.revature.models.StoryType;
import com.revature.repositories.AuthorRepo;
import com.revature.repositories.EditorRepo;
import com.revature.repositories.GEJoinRepo;
import com.revature.repositories.GenreRepo;
import com.revature.repositories.StoryRepo;
import com.revature.repositories.StoryTypeRepo;
import com.revature.utils.Utils;

public class TempDriver {
	private static AuthorRepo ar = new AuthorRepo();
	private static EditorRepo er = new EditorRepo();
	private static GenreRepo gr = new GenreRepo();
	private static StoryRepo sr = new StoryRepo();
	private static StoryTypeRepo str = new StoryTypeRepo();
	
	public static void main(String[] args) {
		Author a = new Author("Test", "Author", "He is a test author");
		ar.add(a);
		
		StoryType novel = str.getByName("novel");
		StoryType novella = str.getByName("novella");
		StoryType short_story = str.getByName("short story");
		StoryType article = str.getByName("article");
		
		Editor hisham = er.add(new Editor("Hisham", "Haqq", "hisham", "haqq"));
		Editor emmett = er.add(new Editor("Emmett", "Riddle", "emmett", "riddle"));
		Editor erika = er.add(new Editor("Erika", "Fomich", "erika", "fomich"));
		Editor bennett = er.add(new Editor("Bennett", "Kerbow", "bennett", "kerbow"));
		Editor sydney = er.add(new Editor("Sydney", "Porter", "sydney", "porter"));
		Editor jason = er.add(new Editor("Jason", "Weible", "jason", "weible"));
		
		Genre scifi = gr.add(new Genre("Sci-fi"));
		Genre fantasy = gr.add(new Genre("Fantasy"));
		Genre horror = gr.add(new Genre("Horror"));
		
		Utils.addEntry(scifi, hisham, true, false);
		Utils.addEntry(scifi, emmett, false, false);
		Utils.addEntry(scifi, erika, false, false);
		Utils.addEntry(scifi, bennett, false, true);
		
		Utils.addEntry(fantasy, emmett, true, false);
		Utils.addEntry(fantasy, erika, false, false);
		Utils.addEntry(fantasy, bennett, false, false);
		Utils.addEntry(fantasy, sydney, false, true);
		
		Utils.addEntry(horror, erika, true, false);
		Utils.addEntry(horror, bennett, false, false);
		Utils.addEntry(horror, sydney, false, false);
		Utils.addEntry(horror, jason, false, true);
		
		Story story = new Story();
		story.setTitle("Story Title");
		story.setGenre(fantasy);
		story.setType(short_story);
		story.setAuthor(a);
		story.setDescription("A short description!");
		story.setTagLine("Amazing Tag Line!");
		story.setCompletionDate(new Date(2021, 06, 14));
		story.setApprovalStatus("pending");
		story.setReason("");
		sr.add(story);
		
		Utils.loadEntries();
		GEJoin join = (new GEJoinRepo()).getById(5);
		System.out.println(join);
	}
}
