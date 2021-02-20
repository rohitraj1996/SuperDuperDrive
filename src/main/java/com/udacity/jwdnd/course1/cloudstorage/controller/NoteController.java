package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }


    @PostMapping("/save")
    public String addNote(Authentication authentication, Note note, Model model){
        if(note.getNoteId() == null) {
            this.noteService.createNote(authentication.getName(), note);
        } else {
            this.noteService.updateNote(note);
        }
        model.addAttribute("result", "success");
        return "result";
    }


    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId, Model model){
        this.noteService.deleteNote(noteId);
        model.addAttribute("result", "success");
        return "result";
    }
}
