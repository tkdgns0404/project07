package com.example.manager.network;



import com.example.manager.model.Board;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BoardService {

    @GET("board")
    Call<List<Board>> getAllBoards();

    @PUT("board/write")
    Call<Void> writeBoard(@Body Board board);

    @PUT("board/modify")
    Call<Void> modifyBoard(@Body Board board);

    @DELETE("board")
    Call<Void> deleteBoard(@Query("targets") String targets);

    @GET("board/search/{writer}")
    Call<List<Board>> getBoardsByWriter(@Path("writer") String writer);

    @GET("board/{idx}")
    Call<Board> findBoardByIdx(@Path("idx") int idx);
}
