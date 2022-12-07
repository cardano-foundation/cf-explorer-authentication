package com.sotatek.authservice.controller;

import com.sotatek.authservice.model.enums.EBookMarkType;
import com.sotatek.authservice.model.request.bookmark.BookMarkRequest;
import com.sotatek.authservice.model.response.BookMarkResponse;
import com.sotatek.authservice.model.response.base.BasePageResponse;
import com.sotatek.authservice.service.BookMarkService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookmark")
@RequiredArgsConstructor
@Tag(name = "BookMark Controller", description = "")
public class BookMarkController {

  private final BookMarkService bookMarkService;

  @PostMapping("/add")
  public ResponseEntity<Long> addBookMark(@Valid @RequestBody BookMarkRequest bookMarkRequest,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(bookMarkService.addBookMark(bookMarkRequest, httpServletRequest));
  }

  @GetMapping("/find-all")
  public ResponseEntity<BasePageResponse<BookMarkResponse>> findBookMarkByType(
      @RequestParam EBookMarkType type, @RequestParam("page") Integer page,
      @RequestParam("size") Integer size, HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(bookMarkService.findBookMarkByType(httpServletRequest, type,
        PageRequest.of(page - 1, size)));
  }

  @DeleteMapping("/delete/{bookMarkId}")
  public ResponseEntity<Boolean> deleteById(@PathVariable Long bookMarkId) {
    return ResponseEntity.ok(bookMarkService.deleteById(bookMarkId));
  }
}
