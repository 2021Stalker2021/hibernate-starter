package com.dmdev.listener;

import com.dmdev.entity.Chat;
import com.dmdev.entity.User;
import com.dmdev.entity.UserChat;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;

public class UserChatListener {

    @PostPersist
    public void postPersist(UserChat userChat) {
        var chat = userChat.getChat();
        chat.setCount(chat.getCount() + 1);
    }
    @PostRemove
    public void postRemove(UserChat userChat) {
        var chat = userChat.getChat();
        chat.setCount(chat.getCount() - 1);
    }
}
