package daos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mentioned")
public class Mentioned {
	@Id @GeneratedValue
	@Column(name = "Id")
	private Long id;

	@Column(name = "proj_real_name")
	private String realname;

	@Column(name = "mentioned_in")
	private String mentionedIn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public void setMentionedIn(String mentionedIn) {
		this.mentionedIn = mentionedIn;
	}

	public String getMentionedIn() {
		return mentionedIn;
	}

	

	
}
