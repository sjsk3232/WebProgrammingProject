package com.example.webprogrammingproject.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "club_members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@AllArgsConstructor
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ClubMember {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "club_member_id", updatable = false)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="club_id")
    private Club club;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @Builder.Default
    @Column(name = "is_master")
    @ColumnDefault("false")
    private Boolean isMaster = false;

    @Builder.Default
    @Column(name = "position")
    @ColumnDefault("'부원'") // 대표, 부대표, 총무 등...
    private String position = "부원";

    @Builder.Default
    @Column(name = "state")
    @ColumnDefault("'활동'") // 활동, 탈퇴 등...
    private String state = "활동";

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
