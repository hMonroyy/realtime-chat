package classes.model;

import java.util.Date;

public class Message {

	private int id_message;
	private String nick;
	private String message;
	private Date date;
	
	public Message(int id_message, String nick, String message, Date date) {
		super();
		this.id_message = id_message;
		this.nick = nick;
		this.message = message;
		this.date = date;
	}
	
	public Message(String nick, String message, Date date) {
        super();
        this.nick = nick;
        this.message = message;
        this.date = date;
    }

	public int getId_message() {
		return id_message;
	}

	public void setId_message(int id_message) {
		this.id_message = id_message;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
