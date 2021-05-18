package com.example.manager.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/*CREATE TABLE MEMBER(
		ID VARCHAR2(100 BYTE), 
		PASSWORD VARCHAR2(200 BYTE), 
		NAME VARCHAR2(100 BYTE), 
		EMAIL VARCHAR2(200 BYTE),
		BIRTH VARCHAR2(20),
		ROLE VARCHAR2(20),
		PHONE_NUM VARCHAR2(100 BYTE), 
		ADDRESS VARCHAR2(1000 BYTE), 
		ADDRESS2 VARCHAR2(20 BYTE),
		PROFILE_PIC_URL VARCHAR(1000 BYTE),
		REG_DATE VARCHAR(100 BYTE)
	   );*/

@Entity
@Table(name = "MEMBER")
@Getter @Setter
public class Member {
    @Id
    @Column(name = "ID", unique = true)
    private String id;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "NAME")
    private String name;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Column(name = "PROFILE_PIC_URL")
    private String profilePicUrl;
}
