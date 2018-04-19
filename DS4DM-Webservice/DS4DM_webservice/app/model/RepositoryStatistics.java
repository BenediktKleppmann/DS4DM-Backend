package model;

public class RepositoryStatistics {
	
	private String created_timestamp;
	private String creator_ip;
	
	public void setCreatedTimestamp(String created_timestamp){
		this.created_timestamp = created_timestamp;
	}
	
	public String getCreatedTimestamp(){
		return this.created_timestamp;
	}
	
	public void setCreatorIP(String creator_ip){
		this.creator_ip = creator_ip;
	}
	
	public String getCreatorIP(){
		return this.creator_ip;
	}

}
