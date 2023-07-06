package org.cardanofoundation.authentication.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import org.cardanofoundation.authentication.model.request.note.PrivateNoteRequest;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.model.response.PrivateNoteResponse;
import org.cardanofoundation.authentication.model.response.base.BasePageResponse;
import org.cardanofoundation.authentication.service.PrivateNoteService;
import org.cardanofoundation.explorer.common.annotation.EnumValid;
import org.cardanofoundation.explorer.common.validation.pagination.Pagination;
import org.cardanofoundation.explorer.common.validation.pagination.PaginationDefault;
import org.cardanofoundation.explorer.common.validation.pagination.PaginationValid;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@Validated
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
      @RequestParam("network") @EnumValid(enumClass = ENetworkType.class) String network,
      @ParameterObject @PaginationValid @PaginationDefault(size = 10, page = 0) Pagination pagination,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(
        privateNoteService.findAllNote(httpServletRequest, network, pagination.toPageable()));
  }

  @DeleteMapping("/delete/{noteId}")
  public ResponseEntity<MessageResponse> deleteById(@PathVariable @Min(1) Long noteId) {
    return ResponseEntity.ok(privateNoteService.deleteById(noteId));
  }

  @PutMapping("/edit")
  public ResponseEntity<PrivateNoteResponse> editNote(@RequestParam("note") String note,
      @RequestParam("noteId") @Min(1) Long noteId) {
    return ResponseEntity.ok(privateNoteService.editById(noteId, note));
  }
}
