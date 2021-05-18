package com.example.manager.repository;



import com.example.manager.model.Board;
import com.example.manager.network.BoardService;

import java.util.List;

import retrofit2.Callback;

public class BoardRepository {
    private BoardService boardService;

    public BoardRepository(BoardService boardService) {
        this.boardService = boardService;
    }

    public void writeBoard(Board board, Callback<Void> callback) {
        boardService.writeBoard(board).enqueue(callback);
    }

    public void getAllBoards(Callback<List<Board>> callback) {
        boardService.getAllBoards().enqueue(callback);
    }

    public void modifyBoard(Board board, Callback<Void> callback) {
        boardService.modifyBoard(board).enqueue(callback);
    }

    public void deleteBoards(String targets, Callback<Void> callback) {
        boardService.deleteBoard(targets).enqueue(callback);
    }

    public void getBoardsByWriter(String writer, Callback<List<Board>> callback) {
        boardService.getBoardsByWriter(writer).enqueue(callback);
    }

    public void getBoardByIdx(int idx, Callback<Board> callback) {
        boardService.findBoardByIdx(idx).enqueue(callback);
    }

}