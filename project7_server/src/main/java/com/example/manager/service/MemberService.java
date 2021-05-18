package com.example.manager.service;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.manager.common.LocalConfig;
import com.example.manager.common.MemberNotFoundException;
import com.example.manager.common.UploadFailureException;
import com.example.manager.domain.Member;
import com.example.manager.dto.MemberFindDto;
import com.example.manager.dto.MemberLoginDto;
import com.example.manager.repository.MemberRepository;

@Service
public class MemberService {
	@Autowired
    private LocalConfig localConfig;
	@Autowired
    private MemberRepository memberRepository;

    /**
     * 모든 회원 조회
     */
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    /**
     * 아이디, 비밀번호를 통해 회원 조회
     */
    public Member loginMember(MemberLoginDto dto) {
        Optional<Member> member = memberRepository.findByIdAndPassword(dto.getId(), dto.getPassword());
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new MemberNotFoundException("There is no member by " + dto.getId());
        }
    }
    
    public Member findMember(MemberFindDto dto) {
    	Optional<Member> member = memberRepository.findById(dto.getId());
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new MemberNotFoundException("There is no member by " + dto.getId());
        }
    	
    }

    /**
     * 회원 가입
     */
    public void signUpMember(Member member, MultipartFile file) {
    	
    		memberRepository.save(member);
    		try {
    			// 파일 업로드
    			uploadProfilePicture(member.getId(), file);
    		} catch (Exception e) {
    			// file upload 실패시 생성하려던 계정 삭제
    			memberRepository.delete(member);
    			throw e;
    		}
    	
    } 
    	
    

    /**
     * 회원 정보 수정
     */
    public void modifyMember(Member member) {
        // 회원 조회
        Optional<Member> target = memberRepository.findById(member.getId());
        if (target.isPresent()) {
            // DB 에서 회원 정보 수정
            memberRepository.save(member);
        } else {
            throw new MemberNotFoundException("There is no Member by " + member.getId());
        }
    }

    /**
     * 회원 정보 수정
     */
    public void modifyMember(Member member, MultipartFile file) {
        // 회원 조회
        Optional<Member> target = memberRepository.findById(member.getId());
        if (target.isPresent()) {
            // DB 에서 회원 정보 수정
            memberRepository.save(member);
            // 프로필 수정
            uploadProfilePicture(member.getId(), file);
        } else {
            throw new MemberNotFoundException("There is no Member by " + member.getId());
        }
    }
    
    /**
     * 회원 정보 삭제 및 프로필 파일 삭제
     */
    public void deleteMembers(String targets) {
        String[] memberIds = targets.split(",");
        for (String memberId : memberIds) {
            // 회원 조회
            Optional<Member> target = memberRepository.findById(memberId);
            if (target.isPresent()) {
                // DB 에서 회원 삭제
                memberRepository.deleteById(memberId);
                try {
                    // 프로필 파일 삭제
                    Member member = target.get();
                    String fileName = member.getProfilePicUrl().substring(member.getProfilePicUrl().lastIndexOf("/") + 1);
                    File file = new File(localConfig.getUploadFilePath() + "\\" + fileName);
                    if (file.exists()) {
                        file.delete();
                    }
                } catch (Exception ignore) {
                    // 파일 삭제 실패하는 경우에 대해서는 무시
                }
            } else {
                throw new MemberNotFoundException("There is no Member by " + memberId);
            }
        }
    }

    /**
     * 이름으로 회원 검색
     */
    public List<Member> findMembersByName(String name) {
        return memberRepository.findByName(name);
    }
    

    /**
     * 프로필 사진 등록
     */
    private void uploadProfilePicture(String memberId, MultipartFile file) {
        Optional<Member> target = memberRepository.findById(memberId);
        if(target.isPresent()) {
            try {
                String fileName = file.getOriginalFilename();   // 원본 파일 이름

                // 저장할 디렉토리 생성
                createDirectory(localConfig.getUploadFilePath());
                String saveUri = localConfig.getUploadFilePath() + "\\" + fileName;  // 최종 저장 경로
                File profileFile = new File(saveUri);
                if (profileFile.exists()) {
                	profileFile.delete();
                }
                profileFile = new File(saveUri);

                // 파일 저장
                file.transferTo(profileFile);

                // member 의 profile url 설정
                Member member = target.get();
                member.setProfilePicUrl(localConfig.getBaseUrl() + fileName);
                memberRepository.save(member);
            } catch (Exception e) {
                throw new UploadFailureException("Failed to upload file, because " + e.getMessage());
            }
        } else {
            throw new MemberNotFoundException("There is no Member by " + memberId);
        }
    }

    /**
     * 디렉토리가 없을 경우 생성
     */
    private void createDirectory(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
