package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.services.PlayListService;
import com.example.demo.services.PlayListServiceImplementation;
import com.example.demo.services.SongService;
@Controller
public class DeleteController {
	@Autowired
	SongService songService;
	@Autowired
	PlayListService playlistService;
	
	
	
//	  @PostMapping("/delete")
//	    public String deletePlaylistsByNames(@RequestParam List<String> playlistNames) {
//	        playlistService.deletePlaylistsByNames(playlistNames);
//	        return "admin";
//	    }

	    @PostMapping("/delete")
	    public String deleteSelectedPlaylists(@RequestParam("selectedIds") List<Integer> selectedIds) {
	      
	    	 playlistService.deletePlaylist(selectedIds);
	        
	        return "admin";
	    }


}
