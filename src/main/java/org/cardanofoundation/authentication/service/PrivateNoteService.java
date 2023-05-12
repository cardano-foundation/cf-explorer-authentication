package org.cardanofoundation.authentication.service;

import jakarta.servlet.http.HttpServletRequest;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import org.cardanofoundation.authentication.model.request.note.PrivateNoteRequest;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.model.response.PrivateNoteResponse;
import org.cardanofoundation.authentication.model.response.base.BasePageResponse;
import org.springframework.data.domain.Pageable;

public interface PrivateNoteService {

  /*
   * @author: phuc.nguyen5
   * @since: 6/12/2022
   * description: add note
   * @update:
   */
  MessageResponse addPrivateNote(PrivateNoteRequest privateNoteRequest,
      HttpServletRequest httpServletRequest);

  /*
   * @author: phuc.nguyen5
   * @since: 6/12/2022
   * description: get all note
   * @update:
   */
  BasePageResponse<PrivateNoteResponse> findAllNote(HttpServletRequest httpServletRequest,
      ENetworkType network,
      Pageable pageable);

  /*
   * @author: phuc.nguyen5
   * @since: 6/12/2022
   * description: delete note by id
   * @update:
   */
  MessageResponse deleteById(Long noteId);

  /*
   * @author: phuc.nguyen5
   * @since: 6/12/2022
   * description: edit note by id
   * @update:
   */
  PrivateNoteResponse editById(Long noteId, String note);
}
