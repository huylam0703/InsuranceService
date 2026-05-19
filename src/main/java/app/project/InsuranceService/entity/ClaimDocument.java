package app.project.InsuranceService.entity;

import app.project.InsuranceService.enums.ClaimDocumentType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_claims_document")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClaimDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "claim_id", nullable = false)
    Claim claim;

    String fileName;

    String fileUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    ClaimDocumentType fileType;

    @Column(nullable = false)
    String mimeType;

    @Column(nullable = false)
    Long fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by", nullable = false)
    User uploadedBy;

    LocalDateTime uploadedAt;
}
