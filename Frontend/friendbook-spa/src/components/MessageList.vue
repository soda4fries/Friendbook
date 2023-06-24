<template>
  <div class="message-list">
    <div
        class="media"
        v-for="(message, index) in messages"
        :key="index"
        :class="{
        'ml-auto': message.sender === 'You',
        'mr-auto': message.sender !== 'You'
      }"
    >
      <div class="media-body">
        <div
            class="rounded py-2 px-3 mb-2 message-bubble"
            :class="{
            'bg-primary text-white': message.sender === 'You',
            'bg-light text-muted': message.sender !== 'You',
            'border border-muted': message.sender !== 'You'
          }"
        >
          <p class="text-small mb-0">{{ message.content }}</p>
        </div>
      </div>
      <div class="timestamp-container">
        <p
            class="small text-muted"
            :class="{
            'text-right': message.sender === 'You',
            'text-left': message.sender !== 'You'
          }"
        >
          {{ message.timestamp }}
        </p>
      </div>
    </div>
  </div>
</template>



<script>
import axios from 'axios';

export default {
  props: {
    conversationId: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      messages: []
    };
  },
  mounted() {
    this.fetchMessages();
    setInterval(this.fetchMessages, 1000);
  },
  watch: {
    conversationId() {
      this.fetchMessages();
    }
  },
  methods: {

    fetchMessages() {

      if (this.conversationId==='') {
        return;
      }

      function getRelativeTime(timestamp) {
        const now = new Date();
        const messageTime = new Date(timestamp);
        const diffInMinutes = Math.floor((now - messageTime) / (1000 * 60));

        if (diffInMinutes < 1) {
          return "just now";
        } else if (diffInMinutes < 60) {
          return `${diffInMinutes} minute${diffInMinutes > 1 ? "s" : ""} ago`;
        } else if (diffInMinutes < 1440) {
          return `${Math.floor(diffInMinutes / 60)} hour${Math.floor(diffInMinutes / 60) > 1 ? "s" : ""} ago`;
        } else if (diffInMinutes < 2880) {
          return "yesterday";
        } else {
          const options = { month: "short", day: "numeric" };
          return messageTime.toLocaleDateString(undefined, options);
        }
      }

      axios
          .get(`http://localhost:8080/api/messaging/${this.conversationId}/messages/1`)
          .then(response => {
            const messageData = response.data;
            const username = localStorage.getItem('userName');
            this.messages = messageData.map(message => ({
              messageId: message.messageId,
              content: message.message,
              timestamp: getRelativeTime(message.timestamp),
              sender: (message.senderUsername === username) ? 'You' : message.senderUsername
            }));
          })
          .catch(error => {
            console.error(error);
          });
    }
  }
};
</script>

<style scoped>
.message-list {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.media {
  width: fit-content;
}

.media.ml-auto {
  margin-left: auto; /* Align messages from 'You' to the right */
}

.message-bubble {
  display: inline-block;
}
.timestamp-container {
  margin-top: 5px;
}

.timestamp-container .text-left {
  text-align: left;
}
</style>
