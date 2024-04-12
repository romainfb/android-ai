package com.supdevinci.aieaie

import com.supdevinci.aieaie.entity.ConversationEntity
import org.junit.Test

internal class DiscussionActivityTest {

    private var discussionGood = ConversationEntity(10, "Discussion !")
    private var discussionBadMessage = ConversationEntity(0, "")
    private var discussionBadId = ConversationEntity(0, "Discussion !")

    private var messageGood = ConversationEntity(10, "Message !")
    private var messageBadMessage = ConversationEntity(0, "")
    private var messageBadId = ConversationEntity(0, "Message !")

    // Tests for the discussion entity

    @Test
    fun isDiscussionGood() {
        assert(discussionGood.conversationId > 0)
    }

    @Test
    fun discussionBadMessage() {
        assert(discussionBadMessage.conversationName.isEmpty())
    }

    @Test
    fun discussionBadId() {
        assert(discussionBadId.conversationId == 0L)
    }

    // Tests for the message entity

    @Test
    fun isMessageGood() {
        assert(messageGood.conversationId > 0)
    }

    @Test
    fun messageBadMessage() {
        assert(messageBadMessage.conversationName.isEmpty())
    }

    @Test
    fun messageBadId() {
        assert(messageBadId.conversationId == 0L)
    }

}