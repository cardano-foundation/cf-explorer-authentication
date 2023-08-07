package org.cardanofoundation.authentication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import org.cardanofoundation.explorer.common.exceptions.ErrorResponse;
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
@Tag(name = "Private Note Controller")
@Validated
public class NoteController {

  private final PrivateNoteService privateNoteService;

  @Operation(description = "Add a transaction note for the account")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input parameter error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "401", description = "Authentication error unsuccessful", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Error not specified", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping("/add")
  public ResponseEntity<MessageResponse> addNote(
      @Valid @RequestBody PrivateNoteRequest privateNoteRequest,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(
        privateNoteService.addPrivateNote(privateNoteRequest, httpServletRequest));
  }

  @Operation(description = "Get the list of transaction notes for the account by network type")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = BasePageResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input parameter error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "401", description = "Authentication error unsuccessful", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Error not specified", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/find-all")
  public ResponseEntity<BasePageResponse<PrivateNoteResponse>> findAllNote(
      @Parameter(
          name = "network",
          description = "Network type",
          example = "MAIN_NET",
          required = true)
      @RequestParam("network") @EnumValid(enumClass = ENetworkType.class) String network,
      @ParameterObject @PaginationValid @PaginationDefault(size = 10, page = 0) Pagination pagination,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(
        privateNoteService.findAllNote(httpServletRequest, network, pagination.toPageable()));
  }

  @Operation(description = "Remove transaction note from account")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input parameter error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "401", description = "Authentication error unsuccessful", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Error not specified", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @DeleteMapping("/delete/{noteId}")
  public ResponseEntity<MessageResponse> deleteById(
      @Parameter(
          name = "noteId",
          description = "Transaction note ID",
          example = "1",
          required = true)
      @PathVariable @Min(1) Long noteId) {
    return ResponseEntity.ok(privateNoteService.deleteById(noteId));
  }

  @Operation(description = "Edit transaction note of the account")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = PrivateNoteResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input parameter error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "401", description = "Authentication error unsuccessful", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Error not specified", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PutMapping("/edit")
  public ResponseEntity<PrivateNoteResponse> editNote(
      @Parameter(
          name = "note",
          description = "Transaction note content",
          example = "I love this transaction",
          required = true)
      @RequestParam("note") String note,
      @Parameter(
          name = "noteId",
          description = "Transaction note ID",
          example = "1",
          required = true)
      @RequestParam("noteId") @Min(1) Long noteId) {
    return ResponseEntity.ok(privateNoteService.editById(noteId, note));
  }
}
