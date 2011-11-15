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

	@Column(name = "this_real_name")
	private String realname;

	@Column(name = "is_mentioned_in")
	private Long mentionedIn;
	
	@Column(name = "context")
	private String context;

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

	public void setMentionedIn(Long mentionedIn) {
		this.mentionedIn = mentionedIn;
	}

	public Long getMentionedIn() {
		return mentionedIn;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getContext() {
		return context;
	}
}
