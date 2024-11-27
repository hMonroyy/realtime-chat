package classes.model;

import java.sql.Timestamp;
import java.util.Date;

public class User {

	private String nick;
	private String userhost;
	private Timestamp date;
	private int lastRead;
	
	public User(String nick, String userhost, Timestamp date, int lastRead) {
		super();
		this.nick = nick;
		this.userhost = userhost;
		this.date = date;
		this.lastRead = lastRead;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getUserhost() {
		return userhost;
	}

	public void setUserhost(String userhost) {
		this.userhost = userhost;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public int getLastRead() {
		return lastRead;
	}

	public void setLastRead(int lastRead) {
		this.lastRead = lastRead;
	}
	
}
