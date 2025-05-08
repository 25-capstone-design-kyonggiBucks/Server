package com.capstone.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "file_activity_log")
public class FileActivityLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ActionType actionType;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private FileStatus status;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private FileType fileType;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private LocalDateTime timeStamp;

    @Column
    private String errorMessage;

    @PrePersist
    protected void OnCreate() {
        timeStamp = LocalDateTime.now();
    }

    public static FileActivityLog of(User user, ActionType actionType, FileType fileType, FileStatus status,
                                     String filePath, String fileName) {
        FileActivityLog fileActivityLog = new FileActivityLog();
        fileActivityLog.user = user;
        fileActivityLog.actionType = actionType;
        fileActivityLog.fileType = fileType;
        fileActivityLog.status = status;
        fileActivityLog.filePath = filePath;
        fileActivityLog.fileName = fileName;
        return fileActivityLog;
    }

    public void markAsFailed(String message) {
        this.status = FileStatus.FAILURE;
        this.errorMessage = message;
    }

}
