package com.example.webprogrammingproject.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "club_applications")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@AllArgsConstructor
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ClubApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "application_id", updatable = false)
    private Long id;

    @Column(name = "applicant_id")
    private String applicantEmail;

    @Column(name = "applicant_name")
    private String applicantName;

    @Column(name = "applicant_major")
    private String applicantMajor;

    @Column(name = "applicant_student_number")
    private String applicantStudentNumber;

    @Builder.Default
    @Column(name = "club_type")
    @ColumnDefault("'중앙'")
    private String clubType = "중앙";

    @Column(name = "club_name")
    private String clubName;

    @Column(name = "advisor_name")
    private String advisorName;

    @Column(name = "advisor_major")
    private String advisorMajor;

    @Column(name = "advisor_contact")
    private String advisorContact;

    @Builder.Default
    @Column(name = "result")
    @ColumnDefault("'검토'")
    private String result = "검토";

    @Column(name = "rejection reason")
    private String rejectionReason;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
