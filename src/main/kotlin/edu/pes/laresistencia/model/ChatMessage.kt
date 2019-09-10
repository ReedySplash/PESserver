package edu.pes.laresistencia.model

class ChatMessage {

    var type: MessageType? = null
    var content: String? = null
    var sender: String? = null

    enum class MessageType {
        CHAT, JOIN, LEAVE
    }
}
