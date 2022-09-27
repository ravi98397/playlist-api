package com.api.playlist.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Playlist {
	
	@Id
	private String id;
	private String userId;
	private List<Song> songs;
	private AccessLevels accessLevel;
	
	public Playlist(String id, String userId, List<Song> songs, AccessLevels accessLevel) {
		super();
		this.id = id;
		this.userId = userId;
		this.songs = songs;
		this.accessLevel = accessLevel;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<Song> getSongs() {
		return songs;
	}
	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
	public AccessLevels getAccessLevel() {
		return accessLevel;
	}
	public void setAccessLevel(AccessLevels accessLevel) {
		this.accessLevel = accessLevel;
	}
	
	
}
