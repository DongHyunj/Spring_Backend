package org.example.backend.board;

import lombok.RequiredArgsConstructor;
import org.example.backend.board.model.BoardDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void register(BoardDto.RegReq dto) {
        boardRepository.save(dto.toEntity());
    }
}
