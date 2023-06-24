<template>
  <div class="bg-white">


    <div class="user-box">
      <div class="list-group rounded-0">
        <div
            class="list-group-item"
            v-for="(chat, index) in chats"
            :key="index"
            :class="['list-group-item', { active: chat.isActive }]"
            @click="selectConversation(chat.id)"
        >
          <p>{{ chat.name }}</p>
          <p class="text-small mb-0 text-muted">{{ chat.latestMessage }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    chats: {
      type: Array,
      required: true
    }
  },
  methods: {
    selectConversation(conversationId) {
      this.$emit('select-conversation', conversationId);
      this.chats.forEach(chat => {
        chat.isActive = chat.id === conversationId;
      });
    }
  }
};
</script>

<style scoped>

.list-group-item.active {
  background-color: #bdbdbd;
  color: #fff;
}
</style>

