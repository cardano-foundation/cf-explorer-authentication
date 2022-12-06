package com.sotatek.authservice.controller;

import com.sotatek.authservice.service.PrivateNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
public class NoteController {

  private final PrivateNoteService privateNoteService;

  @PostMapping("/add")
  public ResponseEntity<Long> addNote() {
    return null;
  }

}
