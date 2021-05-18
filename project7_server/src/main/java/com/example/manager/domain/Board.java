package com.example.manager.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@SequenceGenerator(
        name="BOARD_SEQ_GEN",
        sequenceName="BOARD_SEQ",
        initialValue=1,
        allocationSize=1
        )
@Entity
@Table(name="BOARD")
@Getter @Setter
public class Board {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,
            generator="BOARD_SEQ_GEN"        
            )
	private int idx;
	@Column(name = "TITLE")
	private String title;
	@Column(name = "WRITER")
	private String writer;
	@Column(name = "CONTENT")
	private String content;
	@Column(name = "CNT")
	private int cnt;

	
}

