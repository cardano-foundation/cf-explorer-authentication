package com.sotatek.authservice.controller;

import com.sotatek.authservice.model.request.note.PrivateNoteRequest;
import com.sotatek.authservice.model.response.PrivateNoteResponse;
import com.sotatek.authservice.model.response.base.BasePageResponse;
import com.sotatek.authservice.service.PrivateNoteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
@Tag(name = "Private Note Controller", description = "")
public class NoteController {

  private final PrivateNoteService privateNoteService;

  @PostMapping("/add")
  public ResponseEntity<Long> addNote(@Valid @RequestBody PrivateNoteRequest privateNoteRequest,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(
        privateNoteService.addPrivateNote(privateNoteRequest, httpServletRequest));
  }

  @GetMapping("/find-all")
  public ResponseEntity<BasePageResponse<PrivateNoteResponse>> findAllNote(
      @RequestParam("page") Integer page,
      @RequestParam("size") Integer size, HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(
        privateNoteService.findAllNote(httpServletRequest, PageRequest.of(page - 1, size)));
  }

  @DeleteMapping("/delete/{noteId}")
  public ResponseEntity<Boolean> deleteById(@PathVariable Long noteId) {
    return ResponseEntity.ok(privateNoteService.deleteById(noteId));
  }

  @PutMapping("/edit")
  public ResponseEntity<PrivateNoteResponse> editNote(@RequestParam("note") String note,
      @RequestParam("noteId") Long noteId) {
    return ResponseEntity.ok(privateNoteService.editById(noteId, note));
  }
}
