package org.example.backend.board;

import lombok.RequiredArgsConstructor;
import org.example.backend.board.model.BoardDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/reg")
    public ResponseEntity register(@RequestBody BoardDto.RegReq dto) {
        boardService.register(dto);
        return ResponseEntity.ok("성공");
    }

}
