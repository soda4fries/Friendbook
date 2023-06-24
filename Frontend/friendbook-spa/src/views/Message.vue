<template>
  <div class="container py-5 px-4">
    <div class="row rounded-lg overflow-hidden shadow">
      <div class="col-4 px-0">
        <div class="bg-gray px-4 py-2 bg-dark">
          <p class="h5 mb-0 py-1" @click="fetchChats">Previous Chats</p>
        </div>
        <div class="bg-white conversation-container">
          <ConversationList :chats="chats" :activeConversationId="activeConversationId" @select-conversation="selectConversation" />
        </div>
      </div>

      <div class="col-8 px-0">
        <div class="bg-white">
          <div class="bg-dark px-4 py-2">
            <div class="sender-name">{{ activeChatName }}</div>
          </div>

          <div class="chat-box bg-light">
            <MessageList :conversationId="activeConversationId" />
          </div>

          <form action="#" class="bg-light typing-area" @submit.prevent="sendMessage">
            <input
                type="text"
                placeholder="Type a message"
                aria-describedby="button-addon2"
                class="form-control rounded-0 border-0 py-4 bg-light"
                v-model="newMessage"
            />
            <button id="button-addon2" type="submit" class="btn btn-link"> <i class="fa fa-paper-plane"></i></button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ConversationList from '@/components/ConversationList';
import MessageList from '@/components/MessageList';
import axios from 'axios';

export default {
  components: {
    ConversationList,
    MessageList
  },
  data() {
    return {
      chats: [
      ],
      activeConversationId: '',
      newMessage: ''
    };
  },
  computed: {
    activeChatName() {
      const activeChat = this.chats.find(chat => chat.id === this.activeConversationId);
      return activeChat ? activeChat.name : '';
    }
  },
  methods: {

    selectConversation(conversationId) {
      this.activeConversationId = conversationId;
    },
    sendMessage() {
      if (this.newMessage.trim() !== '') {
        const newMessage = {
          message: this.newMessage,
          sender: localStorage.getItem('userID')
        };
        axios
            .post(`http://localhost:8080/api/messaging/sendMessage/${this.activeConversationId}`, newMessage)
            .then(response => {
              // Message sent successfully
              this.newMessage = '';
            })
            .catch(error => {
              console.error('Error sending message:', error);
            });
      }
    },
    fetchChats() {
      const userID = localStorage.getItem('userID');
      const apiUrl = `http://localhost:8080/api/messaging/${userID}/conversations`;
      const userName = localStorage.getItem('userName');

      axios
          .get(apiUrl)
          .then(response => {
            this.chats = response.data.map(conversation => {
              const inputString = conversation.converastionName;
              const usersArray = inputString.split(",").map(item => item.split(":")[1]);

              const [conversationId, conversationName] = conversation.converastionName.split(':');
              const oppositeUserName = usersArray.find(name => name !== userName);

              return {
                id: conversation.conversationID,
                name: oppositeUserName,
                latestMessage: '',
                isActive: false
              };
            });
          })
          .catch(error => {
            console.error('Error fetching chats:', error);
          });
    },
  },
  created() {
    this.fetchChats();
  }
};
</script>

<style scoped>
.container {
  background-color: #f8f9fa;
  min-height: 100vh;
}

.conversation-container {
  height: calc(100vh - 100px);
  overflow-y: scroll;
  border-right: 1px solid #dee2e6;
}

.sender-name {
  font-weight: bold;
  margin-bottom: 5px;
  color: #fff;
}

.chat-box {
  height: calc(100vh - 180px);
  overflow-y: scroll;
}

.typing-area {
  display: flex;
  align-items: center;
  padding: 10px;
  background-color: #fff;
  border-top: 1px solid #dee2e6;
}

.typing-area input[type="text"] {
  flex: 1;
  border: none;
  border-radius: 20px;
  padding: 10px 15px;
  margin-right: 10px;
  background-color: #f8f9fa;
}

.typing-area button {
  border: none;
  background: #fff;
  font-size: 18px;
  color: #007bff;
}

/* Conversation List styles */
.list-group-item {
  transition: background 0.3s ease;
  cursor: pointer;
  padding: 10px 15px;
  border: none;
  border-bottom: 1px solid #dee2e6;
}

.list-group-item:hover {
  background-color: #f8f9fa;
}

.list-group-item.active {
  background-color: #007bff;
  color: #fff;
}

.list-group-item .name {
  font-weight: bold;
  margin-bottom: 5px;
  color: #007bff;
}

.list-group-item .latest-message {
  font-size: 14px;
  color: #6c757d;
}



/* Message List styles */
.media-body {
  display: flex;
  flex-direction: column;
}

.message-bubble {
  display: inline-block;
  padding: 10px 15px;
  border-radius: 20px;
  font-size: 14px;
  max-width: 70%;
}

.message-bubble-primary {
  background-color: #007bff;
  color: #fff;
}

.message-bubble-light {
  background-color: #f8f9fa;
  color: #6c757d;
}

.message-timestamp {
  font-size: 12px;
  color: #6c757d;
}

</style>
