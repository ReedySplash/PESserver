package edu.pes.laresistencia.conf

import com.fasterxml.jackson.databind.ObjectMapper
import edu.pes.laresistencia.model.ChatMessage
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.CopyOnWriteArrayList

@Component
class SocketHandler : TextWebSocketHandler() {
    private val objectMapper = ObjectMapper()
    private val sessions = CopyOnWriteArrayList<WebSocketSession>()

    override fun handleTextMessage(session: WebSocketSession, rawMessage: TextMessage) {
        val message: ChatMessage = objectMapper.readValue(rawMessage.payload.toString(), ChatMessage::class.java)
        sessions.forEach {
            it.sendMessage(TextMessage(objectMapper.writeValueAsString(message)))
        }
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions.add(session)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions.remove(session)
    }
}