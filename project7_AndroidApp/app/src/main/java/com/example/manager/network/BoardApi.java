
package com.example.manager.network;



import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class BoardApi {
    private static final String SERVER_BASE_URL = "http://192.168.1.2:9994/"; // TODO - IP 를 서버 PC 의 IP로 설정해주어야 함.

    private static BoardApi INSTANCE;

    public static BoardApi getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BoardApi();
        }
        return INSTANCE;
    }

    private final BoardService boardService;

    private BoardApi() {
        boardService = new Retrofit.Builder()
                .baseUrl(SERVER_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(BoardService.class);
    }

    public BoardService getBoardService() {
        return boardService;
    }
    
}

