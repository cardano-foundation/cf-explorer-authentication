package com.sotatek.authservice.controller;

import com.sotatek.authservice.model.enums.EBookMarkType;
import com.sotatek.authservice.model.request.bookmark.BookMarkRequest;
import com.sotatek.authservice.model.response.BookMarkResponse;
import com.sotatek.authservice.model.response.MessageResponse;
import com.sotatek.authservice.model.response.base.BasePageResponse;
import com.sotatek.authservice.service.BookMarkService;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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
  public ResponseEntity<MessageResponse> addBookMark(
      @Valid @RequestBody BookMarkRequest bookMarkRequest,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(bookMarkService.addBookMark(bookMarkRequest, httpServletRequest));
  }

  @GetMapping("/find-all")
  public ResponseEntity<BasePageResponse<BookMarkResponse>> findBookMarkByType(
      @RequestParam("type") EBookMarkType type,
      @ParameterObject @PageableDefault(size = 10, page = 0) Pageable pageable,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(
        bookMarkService.findBookMarkByType(httpServletRequest, type, pageable));
  }

  @DeleteMapping("/delete/{bookMarkId}")
  public ResponseEntity<MessageResponse> deleteById(@PathVariable Long bookMarkId) {
    return ResponseEntity.ok(bookMarkService.deleteById(bookMarkId));
  }
}
