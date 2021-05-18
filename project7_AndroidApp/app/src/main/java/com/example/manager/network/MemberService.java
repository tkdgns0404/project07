package com.example.manager.network;



import com.example.manager.model.LoginInfo;
import com.example.manager.model.Member;
import com.example.manager.model.findMember;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MemberService {

    @GET("member")
    Call<List<Member>> getAllMembers();

    @POST("member/login")
    Call<Member> loginMember(@Body LoginInfo loginInfo);

    @POST("member/findMember")
    Call<Member> findMember(@Body findMember findMember);

    @Multipart
    @POST("member/sign-up")
    Call<Void> signUpMember(@Part("member") Member member, @Part MultipartBody.Part profileFile);

    @Multipart
    @POST("member/modify-profile")
    Call<Void> modifyMemberWithProfile(@Part("member") Member member, @Part MultipartBody.Part profileFile);

    @PUT("member/modify")
    Call<Void> modifyMember(@Body Member member);

    @DELETE("member")
    Call<Void> deleteMembers(@Query("targets") String targets);

    @GET("member/{name}")
    Call<List<Member>> getMembersByName(@Path("name") String name);
}
