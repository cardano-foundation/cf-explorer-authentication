package com.sotatek.authservice.controller;

import com.sotatek.authservice.model.dto.BookMarkDto;
import com.sotatek.authservice.model.enums.EBookMarkType;
import com.sotatek.authservice.model.request.bookmark.BookMarkRequest;
import com.sotatek.authservice.service.BookMarkService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookmark")
public class BookMarkController {

  @Autowired
  private BookMarkService bookMarkService;

  @PostMapping("/create")
  public ResponseEntity<Long> addBookMark(@Valid @RequestBody BookMarkRequest bookMarkRequest,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(bookMarkService.addBookMark(bookMarkRequest, httpServletRequest));
  }

  @GetMapping("/find-by-account")
  public ResponseEntity<List<BookMarkDto>> findBookMarkByAccount(
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(bookMarkService.findBookMarkByAccount(httpServletRequest));
  }

  @GetMapping("/find-by-type/{bookMarkType}")
  public ResponseEntity<List<BookMarkDto>> findBookMarkByType(
      @PathVariable EBookMarkType bookMarkType,
      HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(bookMarkService.findBookMarkByType(httpServletRequest, bookMarkType));
  }

  @DeleteMapping("/delete-by-account")
  public ResponseEntity<Boolean> deleteByAccount(HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(bookMarkService.deleteByAccount(httpServletRequest));
  }

  @DeleteMapping("/delete-by-type/{bookMarkType}")
  public ResponseEntity<Boolean> deleteByType(HttpServletRequest httpServletRequest,
      @PathVariable EBookMarkType bookMarkType) {
    return ResponseEntity.ok(bookMarkService.deleteByType(httpServletRequest, bookMarkType));
  }

  @DeleteMapping("/delete-by-id/{bookMarkId}")
  public ResponseEntity<Boolean> deleteById(@PathVariable Long bookMarkId) {
    return ResponseEntity.ok(bookMarkService.deleteById(bookMarkId));
  }
}
