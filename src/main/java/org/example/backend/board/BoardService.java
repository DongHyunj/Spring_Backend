package org.example.backend.board;

import lombok.RequiredArgsConstructor;
import org.example.backend.board.model.Board;
import org.example.backend.board.model.BoardDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void register(BoardDto.RegReq dto) {
        boardRepository.save(dto.toEntity());
    }

    public List<BoardDto.Res> list() {
        List<Board> boardList = boardRepository.findAll();
        return boardList.stream().map(BoardDto.Res::from).toList();
    }

    public BoardDto.Res read(Long idx) {
        Board board = boardRepository.findById(idx).orElseThrow();
        return BoardDto.Res.from(board);
    }
}
