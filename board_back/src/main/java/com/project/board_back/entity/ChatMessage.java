package com.project.board_back.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chat_messages")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roomId;
    private String sender;
    private String message;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @OneToMany(mappedBy = "chatMessage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UploadFile> files = new ArrayList<>();

    public enum MessageType {
        JOIN, TALK, LEAVE
    }

    @Builder
    public ChatMessage(Long roomId, String sender, String message, MessageType messageType) {
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
        this.messageType = messageType;
    }
}
