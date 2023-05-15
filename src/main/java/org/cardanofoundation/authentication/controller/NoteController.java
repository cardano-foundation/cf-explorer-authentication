package org.cardanofoundation.authentication.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import org.cardanofoundation.authentication.model.request.note.PrivateNoteRequest;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.model.response.PrivateNoteResponse;
import org.cardanofoundation.authentication.model.response.base.BasePageResponse;
import org.cardanofoundation.authentication.service.PrivateNoteService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/api/v1/note")
@RequiredArgsConstructor
@Tag(name = "Private Note Controller", description = "")
public class NoteController {

  private final PrivateNoteService privateNoteService;

  @PostMapping("/add")
  public ResponseEntity<MessageResponse> addNote(
      @Valid @RequestBody PrivateNoteRequest privateNoteRequest,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(
        privateNoteService.addPrivateNote(privateNoteRequest, httpServletRequest));
  }

  @GetMapping("/find-all")
  public ResponseEntity<BasePageResponse<PrivateNoteResponse>> findAllNote(
      @RequestParam("network") ENetworkType network,
      @ParameterObject @PageableDefault(size = 10, page = 0) Pageable pageable,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(privateNoteService.findAllNote(httpServletRequest, network, pageable));
  }

  @DeleteMapping("/delete/{noteId}")
  public ResponseEntity<MessageResponse> deleteById(@PathVariable Long noteId) {
    return ResponseEntity.ok(privateNoteService.deleteById(noteId));
  }

  @PutMapping("/edit")
  public ResponseEntity<PrivateNoteResponse> editNote(@RequestParam("note") String note,
      @RequestParam("noteId") Long noteId) {
    return ResponseEntity.ok(privateNoteService.editById(noteId, note));
  }
}
