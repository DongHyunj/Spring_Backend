package org.example.backend.board;

import lombok.RequiredArgsConstructor;
import org.example.backend.board.model.Board;
import org.example.backend.board.model.BoardDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/reg")
    public ResponseEntity register(@RequestBody BoardDto.RegReq dto) {
        boardService.register(dto);
        return ResponseEntity.ok("성공");
    }

    @GetMapping("/list")
    public ResponseEntity list() {
        List<BoardDto.Res> boardList = boardService.list();
        return ResponseEntity.ok(boardList);
    }

    @GetMapping("/list/{idx}")
    public ResponseEntity read(@PathVariable Long idx) {
        BoardDto.Res board = boardService.read(idx);
        return ResponseEntity.ok(board);
    }

}
