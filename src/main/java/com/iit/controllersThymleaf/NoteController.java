package com.iit.controllersThymleaf;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import com.iit.entities.Note;
import com.iit.repositories.NoteRepository;

@Controller
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteRepository noteRepos;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("notes", noteRepos.findAll());
        return "notes"; // templates/notes.html
    }

    @GetMapping("/form")
    public String formNote(Model model) {
        model.addAttribute("note", new Note());
        return "formNote"; // templates/formNote.html
    }

    @PostMapping("/save")
    public String save(@Valid Note n, BindingResult br) {
        if (br.hasErrors()) return "formNote";
        noteRepos.save(n);
        return "confirmation";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id) {
        Note n = noteRepos.findById(id).orElse(null);
        model.addAttribute("note", n);
        return "editNote"; // templates/editNote.html
    }

    @PostMapping("/update")
    public String update(@Valid Note n, BindingResult br) {
        if (br.hasErrors()) return "editNote";
        noteRepos.save(n);
        return "confirmation";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        noteRepos.deleteById(id);
        return "redirect:index";
    }
}

