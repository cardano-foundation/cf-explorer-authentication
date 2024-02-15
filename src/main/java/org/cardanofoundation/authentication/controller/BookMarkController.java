package org.cardanofoundation.authentication.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;

import org.cardanofoundation.authentication.model.enums.EBookMarkType;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import org.cardanofoundation.authentication.model.request.bookmark.BookMarkRequest;
import org.cardanofoundation.authentication.model.response.BookMarkResponse;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.model.response.base.BasePageResponse;
import org.cardanofoundation.authentication.service.BookMarkService;
import org.cardanofoundation.explorer.common.exception.ErrorResponse;
import org.cardanofoundation.explorer.common.validation.EnumValid;
import org.cardanofoundation.explorer.common.validation.pagination.Pagination;
import org.cardanofoundation.explorer.common.validation.pagination.PaginationDefault;
import org.cardanofoundation.explorer.common.validation.pagination.PaginationValid;

@RestController
@RequestMapping("/api/v1/bookmark")
@RequiredArgsConstructor
@Tag(name = "BookMark Controller")
@Validated
public class BookMarkController {

  private final BookMarkService bookMarkService;

  @Operation(description = "Add a bookmark for the account")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = @Content(schema = @Schema(implementation = BookMarkResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input parameter error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Authentication error unsuccessful",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Error not specified",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      })
  @PostMapping("/add")
  public ResponseEntity<BookMarkResponse> addBookMark(
      @Valid @RequestBody BookMarkRequest bookMarkRequest, HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(bookMarkService.addBookMark(bookMarkRequest, httpServletRequest));
  }

  @Operation(
      description = "Get the list of bookmarks for the account by bookmark type and network type")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = @Content(schema = @Schema(implementation = BasePageResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input parameter error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Authentication error unsuccessful",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Error not specified",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      })
  @GetMapping("/find-all")
  public ResponseEntity<BasePageResponse<BookMarkResponse>> findBookMarkByType(
      @Parameter(name = "type", description = "Bookmark type", example = "POOL", required = true)
          @RequestParam("type")
          @EnumValid(enumClass = EBookMarkType.class)
          String type,
      @Parameter(
              name = "network",
              description = "Network type",
              example = "MAIN_NET",
              required = true)
          @RequestParam("network")
          @EnumValid(enumClass = ENetworkType.class)
          String network,
      @ParameterObject @PaginationValid @PaginationDefault(size = 10, page = 0)
          Pagination pagination,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(
        bookMarkService.findBookMarkByType(
            httpServletRequest, type, network, pagination.toPageable()));
  }

  @Operation(description = "Remove bookmark from the account")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = @Content(schema = @Schema(implementation = MessageResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input parameter error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Authentication error unsuccessful",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Error not specified",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      })
  @DeleteMapping("/delete")
  public ResponseEntity<MessageResponse> deleteBookMark(
      @Parameter(name = "type", description = "Bookmark type", example = "POOL", required = true)
          @RequestParam("type")
          @EnumValid(enumClass = EBookMarkType.class)
          String type,
      @Parameter(
              name = "network",
              description = "Network type",
              example = "MAIN_NET",
              required = true)
          @RequestParam("network")
          @EnumValid(enumClass = ENetworkType.class)
          String network,
      @RequestParam("keyword") String keyword,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(
        bookMarkService.deleteBookMark(type, network, keyword, httpServletRequest));
  }

  @Operation(description = "Get the list of bookmarks for the account by network type")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Success",
            content =
                @Content(
                    array =
                        @ArraySchema(schema = @Schema(implementation = BookMarkResponse.class)))),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input parameter error",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Authentication error unsuccessful",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Error not specified",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      })
  @GetMapping("/find-all-key")
  public ResponseEntity<List<BookMarkResponse>> findKeyBookMark(
      @Parameter(
              name = "network",
              description = "Network type",
              example = "MAIN_NET",
              required = true)
          @RequestParam("network")
          @EnumValid(enumClass = ENetworkType.class)
          String network,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(bookMarkService.findKeyBookMark(httpServletRequest, network));
  }
}
