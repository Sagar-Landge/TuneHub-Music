package com.example.demo.services;

import java.util.List;

import com.example.demo.entities.Playlist;
import com.example.demo.entities.Song;

public interface PlayListService {

	public void addPlaylist(Playlist playlist);

	public List<Playlist> fetchAllPlaylist();

	public List<Playlist> getAllPlaylists();

	public void deletePlaylist(List<Integer> selectedIds);

	



	


}