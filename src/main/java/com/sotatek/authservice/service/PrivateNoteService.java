package com.sotatek.authservice.service;

import com.sotatek.authservice.model.request.note.PrivateNoteRequest;
import com.sotatek.authservice.model.response.PrivateNoteResponse;
import com.sotatek.authservice.model.response.base.BasePageResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;

public interface PrivateNoteService {

  /*
   * @author: phuc.nguyen5
   * @since: 6/12/2022
   * description: add note
   * @update:
   */
  Long addPrivateNote(PrivateNoteRequest privateNoteRequest, HttpServletRequest httpServletRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 6/12/2022
   * description: get all note
   * @update:
   */
  BasePageResponse<PrivateNoteResponse> findAllNote(HttpServletRequest httpServletRequest,
      Pageable pageable);

  /*
   * @author: phuc.nguyen5
   * @since: 6/12/2022
   * description: delete note by id
   * @update:
   */
  Boolean deleteById(Long noteId);

  /*
   * @author: phuc.nguyen5
   * @since: 6/12/2022
   * description: edit note by id
   * @update:
   */
  PrivateNoteResponse editById(Long noteId, String note);
}
