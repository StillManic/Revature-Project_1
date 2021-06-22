package com.revature.models;

import java.lang.reflect.Type;
import java.sql.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.revature.repositories.AuthorRepo;
import com.revature.repositories.GenreRepo;
import com.revature.repositories.StoryTypeRepo;

public class Story {
//	public enum StatusLevel {
//		SUBMITTED,
//		ASSISTANT_APPROVED,
//		ASSISTANT_URGENT,
//		EDITOR_APPROVED,
//		EDITOR_URGENT,
//		SENIOR_APPROVED,
//		SENIOR_URGENT,
//		DRAFT_APPROVED
//	}
	private Integer id;
	private String title;
	private Genre genre;
	private StoryType type;
	private Author author;
	private String description;
	private String tagLine;
	private Date completionDate;
	private Date submissionDate;
	private String approvalStatus;
	private String reason;
	private String request;
	private String response;
	private String receiverName;
	private String requestorName;
	// These are null until the proposal is approved by that level
	private Editor assistant;
	private Editor editor;
	private Editor senior;
	
	public Story() {}
	
//	public static String getStatusString(StatusLevel level) {
//		switch (level) {
//			case SUBMITTED: return "submitted";
//			case ASSISTANT_APPROVED: return "assistant_approved";
//			case ASSISTANT_URGENT: return "assistant_urgent";
//			case EDITOR_APPROVED: return "editor_approved";
//			case EDITOR_URGENT: return "editor_urgent";
//			case SENIOR_APPROVED: return "senior_approved";
//			case SENIOR_URGENT: return "senior_urgent";
//			case DRAFT_APPROVED: return "draft_approved";
//			default: return null;
//		}
//	}
	
//	public static StatusLevel getStatusLevel(String level) {
//		switch (level.toLowerCase()) {
//			case "submitted": return StatusLevel.SUBMITTED;
//			case "assistant_approved": return StatusLevel.ASSISTANT_APPROVED;
//			case "assistant_urgent": return StatusLevel.ASSISTANT_URGENT;
//			case "editor_approved": return StatusLevel.EDITOR_APPROVED;
//			case "editor_urgent": return StatusLevel.EDITOR_URGENT;
//			case "senior_approved": return StatusLevel.SENIOR_APPROVED;
//			case "senior_urgent": return StatusLevel.SENIOR_URGENT;
//			case "draft_approved": return StatusLevel.DRAFT_APPROVED;
//			default: return null;
//		}
//	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public StoryType getType() {
		return type;
	}

	public void setType(StoryType type) {
		this.type = type;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTagLine() {
		return tagLine;
	}

	public void setTagLine(String tagLine) {
		this.tagLine = tagLine;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getRequestorName() {
		return requestorName;
	}

	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}

	public Editor getAssistant() {
		return assistant;
	}

	public void setAssistant(Editor assistant) {
		this.assistant = assistant;
	}

	public Editor getEditor() {
		return editor;
	}

	public void setEditor(Editor editor) {
		this.editor = editor;
	}

	public Editor getSenior() {
		return senior;
	}

	public void setSenior(Editor senior) {
		this.senior = senior;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((approvalStatus == null) ? 0 : approvalStatus.hashCode());
		result = prime * result + ((assistant == null) ? 0 : assistant.hashCode());
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((completionDate == null) ? 0 : completionDate.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((editor == null) ? 0 : editor.hashCode());
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + ((receiverName == null) ? 0 : receiverName.hashCode());
		result = prime * result + ((request == null) ? 0 : request.hashCode());
		result = prime * result + ((requestorName == null) ? 0 : requestorName.hashCode());
		result = prime * result + ((response == null) ? 0 : response.hashCode());
		result = prime * result + ((senior == null) ? 0 : senior.hashCode());
		result = prime * result + ((submissionDate == null) ? 0 : submissionDate.hashCode());
		result = prime * result + ((tagLine == null) ? 0 : tagLine.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Story other = (Story) obj;
		if (approvalStatus == null) {
			if (other.approvalStatus != null)
				return false;
		} else if (!approvalStatus.equals(other.approvalStatus))
			return false;
		if (assistant == null) {
			if (other.assistant != null)
				return false;
		} else if (!assistant.equals(other.assistant))
			return false;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (completionDate == null) {
			if (other.completionDate != null)
				return false;
		} else if (!completionDate.equals(other.completionDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (editor == null) {
			if (other.editor != null)
				return false;
		} else if (!editor.equals(other.editor))
			return false;
		if (genre == null) {
			if (other.genre != null)
				return false;
		} else if (!genre.equals(other.genre))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (receiverName == null) {
			if (other.receiverName != null)
				return false;
		} else if (!receiverName.equals(other.receiverName))
			return false;
		if (request == null) {
			if (other.request != null)
				return false;
		} else if (!request.equals(other.request))
			return false;
		if (requestorName == null) {
			if (other.requestorName != null)
				return false;
		} else if (!requestorName.equals(other.requestorName))
			return false;
		if (response == null) {
			if (other.response != null)
				return false;
		} else if (!response.equals(other.response))
			return false;
		if (senior == null) {
			if (other.senior != null)
				return false;
		} else if (!senior.equals(other.senior))
			return false;
		if (submissionDate == null) {
			if (other.submissionDate != null)
				return false;
		} else if (!submissionDate.equals(other.submissionDate))
			return false;
		if (tagLine == null) {
			if (other.tagLine != null)
				return false;
		} else if (!tagLine.equals(other.tagLine))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Story [id=").append(id).append(", title=").append(title).append(", genre=").append(genre)
				.append(", type=").append(type).append(", author=").append(author).append(", description=")
				.append(description).append(", tagLine=").append(tagLine).append(", completionDate=")
				.append(completionDate).append(", submissionDate=").append(submissionDate).append(", approvalStatus=")
				.append(approvalStatus).append(", reason=").append(reason).append(", request=").append(request)
				.append(", response=").append(response).append(", receiverName=").append(receiverName)
				.append(", requestorName=").append(requestorName).append(", assistant=").append(assistant)
				.append(", editor=").append(editor).append(", senior=").append(senior).append("]");
		return builder.toString();
	}
	
	public static class Deserializer implements JsonDeserializer<Story> {
		@Override
		public Story deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			Story story = new Story();
			JsonObject jo = json.getAsJsonObject();
			if (jo.has("author")) {
				// TODO: move this to AuthorServices
				story.setAuthor(context.deserialize(jo.get("author"), Author.class));
			}
			if (jo.has("approvalStatus")) {
				story.setApprovalStatus(context.deserialize(jo.get("approvalStatus"), String.class));
			}
			if (jo.has("reason")) {
				story.setReason(context.deserialize(jo.get("reason"), String.class));
			}
			if (jo.has("id")) {
				story.setId(context.deserialize(jo.get("id"), Integer.class));
			}
			story.setTitle(context.deserialize(jo.get("title"), String.class));
			// TODO: move this to GenreServices!!!
			story.setGenre(context.deserialize(jo.get("genre"), Genre.class));
			// TODO: move this to StoryTypeServices!!!
			story.setType(context.deserialize(jo.get("type"), StoryType.class));
			story.setDescription(context.deserialize(jo.get("description"), String.class));
			story.setTagLine(context.deserialize(jo.get("tagLine"), String.class));
			story.setCompletionDate(context.deserialize(jo.get("completionDate"), Date.class));
			story.setSubmissionDate(context.deserialize(jo.get("submissionDate"), Date.class));
			story.setRequest(context.deserialize(jo.get("request"), String.class));
			story.setResponse(context.deserialize(jo.get("response"), String.class));
			story.setReceiverName(context.deserialize(jo.get("receiverName"), String.class));
			story.setRequestorName(context.deserialize(jo.get("requestorName"),	String.class));
			story.setAssistant(context.deserialize(jo.get("assistant"), Editor.class));
			story.setEditor(context.deserialize(jo.get("editor"), Editor.class));
			story.setSenior(context.deserialize(jo.get("senior"), Editor.class));
			return story;
		}
	}
}
