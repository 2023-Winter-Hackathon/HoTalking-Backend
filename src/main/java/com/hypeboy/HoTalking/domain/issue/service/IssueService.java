package com.hypeboy.HoTalking.domain.issue.service;

import com.hypeboy.HoTalking.domain.comment.exception.MemberNotMatchExceptioin;
import com.hypeboy.HoTalking.domain.issue.domain.entity.Issue;
import com.hypeboy.HoTalking.domain.issue.presentation.dto.request.AddIssueRequest;
import com.hypeboy.HoTalking.domain.issue.exception.IssueNotFoundException;
import com.hypeboy.HoTalking.domain.issue.exception.NoPermissionException;
import com.hypeboy.HoTalking.domain.issue.domain.repository.IssueRepository;
import com.hypeboy.HoTalking.domain.member.domain.entity.Member;
import com.hypeboy.HoTalking.domain.member.domain.enums.Role;
import com.hypeboy.HoTalking.domain.member.domain.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;

    private final MemberRepository memberRepository;

    public ResponseEntity<?> save(Issue issue) {
        issueRepository.save(issue);
        return ResponseEntity.ok().body("issue 저장 성공");
    }

    public ResponseEntity<?> addIssue(final Member member, AddIssueRequest request) {

        Issue issue = Issue.builder()
                .issueName(request.getIssueName())
                .author(member)
                .build();
        issueRepository.save(issue);

        member.addIssue(issue);
        memberRepository.save(member);

        return ResponseEntity.ok().body("성공적으로 저장되었습니다");
    }

    public ResponseEntity<?> deleteIssue(final Member member, Long id) {
        Issue issue = getIssue();
        Member author = issue.getAuthor();
        Role role = member.getRole();

        if(role==Role.JUNIOR || role==Role.SENIOR) {
            throw new NoPermissionException();
        }

        if(author.getId().equals(member.getId())) {
            throw new MemberNotMatchExceptioin();
        }

        member.removeIssue(issue);
        memberRepository.save(member);

        issueRepository.deleteById(id);

        return ResponseEntity.ok("성공적으로 삭제 되었습니다");
    }

    public Issue getIssue() {
        return issueRepository.findById(1L)
                .orElseThrow(() -> IssueNotFoundException.EXCEPTION);
    }

}