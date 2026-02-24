package org.example.backend.board.model;

import lombok.*;

public class BoardDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegReq {
        private String title;
        private String contents;

        public Board toEntity() {
            return Board.builder()
                    .title(this.title)
                    .contents(this.contents)
                    .build();
        }
    }
}