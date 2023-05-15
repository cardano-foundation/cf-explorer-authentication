package org.cardanofoundation.authentication.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.cardanofoundation.authentication.model.enums.EBookMarkType;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import org.cardanofoundation.authentication.model.request.bookmark.BookMarkRequest;
import org.cardanofoundation.authentication.model.request.bookmark.BookMarksRequest;
import org.cardanofoundation.authentication.model.response.AddBookMarkResponse;
import org.cardanofoundation.authentication.model.response.BookMarkResponse;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.model.response.base.BasePageResponse;
import org.cardanofoundation.authentication.service.BookMarkService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookmark")
@RequiredArgsConstructor
@Tag(name = "BookMark Controller", description = "")
public class BookMarkController {

  private final BookMarkService bookMarkService;

  @PostMapping("/add")
  public ResponseEntity<BookMarkResponse> addBookMark(
      @Valid @RequestBody BookMarkRequest bookMarkRequest, HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(bookMarkService.addBookMark(bookMarkRequest, httpServletRequest));
  }

  @GetMapping("/find-all")
  public ResponseEntity<BasePageResponse<BookMarkResponse>> findBookMarkByType(
      @RequestParam("type") EBookMarkType type, @RequestParam("network") ENetworkType network,
      @ParameterObject @PageableDefault(size = 10, page = 0) Pageable pageable,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(
        bookMarkService.findBookMarkByType(httpServletRequest, type, network, pageable));
  }

  @DeleteMapping("/delete/{bookMarkId}")
  public ResponseEntity<MessageResponse> deleteById(@PathVariable Long bookMarkId) {
    return ResponseEntity.ok(bookMarkService.deleteById(bookMarkId));
  }

  @GetMapping("/find-all-key")
  public ResponseEntity<List<BookMarkResponse>> findKeyBookMark(
      @RequestParam("network") ENetworkType network,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(bookMarkService.findKeyBookMark(httpServletRequest, network));
  }

  @PostMapping("/add-list")
  public ResponseEntity<AddBookMarkResponse> addBookMarks(
      @Valid @RequestBody BookMarksRequest bookMarksRequest,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(bookMarkService.addBookMarks(bookMarksRequest, httpServletRequest));
  }
}
