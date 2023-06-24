<template>
  <div class="d-flex justify-content-center">
    <div class="card mt-4" style="max-width: 1000px;">
      <div class="card-body">
        <h2 class="card-title">Notifications</h2>
        <div class="list-group" v-if="notifications.length > 0">
          <a
              v-for="(notification, index) in notifications"
              :key="notification.id"
              :href="notification.link"
              :class="['list-group-item', 'list-group-item-action', { 'unread': !notification.read }]"
          >
            <div class="d-flex w-100 justify-content-between align-items-center">
              <div>
                <h5 class="mb-1">{{ getFriendRequestMessage(notification.message) }}</h5>
                <small class="text-muted">{{ notification.timestamp }}</small>
              </div>
              <div>
                <small>{{ notification.author }}</small>
              </div>
            </div>
            <p class="mb-1 visually-hidden">{{ notification.message }}</p>
            <button v-if="notification.type === 'FRIEND_REQ'" @click="ConfirmFriendRequest(notification)" class="btn btn-primary">
              Accept Friend Request
            </button>
          </a>
        </div>
        <p v-else>No notifications available.</p>
        <p v-if="loading">Loading notifications...</p>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';

export default {
  data() {
    return {
      notifications: [],
      loading: false,
    };
  },
  mounted() {
    this.fetchNotifications();
  },
  methods: {
    fetchNotifications() {
      this.loading = true;
      const userID = localStorage.getItem('userID');
      const url = `http://localhost:8080/api/notifications/${userID}/`;
      axios
          .get(url)
          .then((response) => {
            this.notifications = response.data;
            this.loading = false;
          })
          .catch((error) => {
            console.error(error);
            this.loading = false;
          });
    },
    getFriendRequestMessage(message) {
      const parts = message.split(',');
      const userName = parts[1].split(':')[1];
      return `${userName} has sent a friend request`;
    },
    ConfirmFriendRequest(notification) {
      const localUserID = localStorage.getItem('userID');
      const userID = notification.message.split(',')[0].split(':')[1];
      const url = `http://localhost:8080/Friends/AddFriend/${localUserID}/${userID}`;
      axios
          .post(url)
          .then(() => {
            alert('Friend request Accepted!');
          })
          .catch((error) => {
            console.error('Error sending friend request:', error);
            alert("Already Friend");
          });
    },
  },
};
</script>

<style scoped>
.card {
  width: auto;
  background-color: #f8f9fa;
  border-radius: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.unread {
  font-weight: bold;
  background-color: #f1f1f1;
}

.btn-primary {
  background-color: #007bff;
  border-color: #007bff;
}

.btn-primary:hover {
  background-color: #0069d9;
  border-color: #0062cc;
}

.btn-primary:focus,
.btn-primary.focus {
  box-shadow: 0 0 0 0.2rem rgba(38, 143, 255, 0.5);
}
</style>
