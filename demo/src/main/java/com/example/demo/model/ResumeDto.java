package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDto {

	
	public enum MessageType {
		ENTER, TALK
	}
	
	private MessageType messageType; // 메시지 타입
    private Long chatRoomId; // 방 번호
    private Long senderId; // 채팅을 보낸 사람
    private String message; // 메시지
}
