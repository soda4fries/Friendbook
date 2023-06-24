<template>
  <div class="network-container">
    <div v-for="(hop, index) in network" :key="index" class="hop-container">
      <h2 class="hop-title">{{ 'Hop ' + index }}</h2>
      <div class="user-container" v-for="(user, innerIndex) in hop" :key="innerIndex">
        <div class="user-info">
          <p class="username">{{ user.username }}</p>
          <p class="user-id">{{ user.id }}</p>
        </div>
        <button class="friend-request-btn" @click="redirectToUserPage(user.id)">View Full Info</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      network: [],
    };
  },
  mounted() {
    // Retrieve userID from localStorage
    const userID = localStorage.getItem('userID');
    if (userID) {
      this.fetchNetwork(userID);
    }
  },
  methods: {
    fetchNetwork(userID) {
      axios
          .get(`http://localhost:8080/Friends/GetAllEnhancedNetwork/${userID}`)
          .then(response => {
            this.network = response.data;
          })
          .catch(error => {
            console.error(error);
          });
    },
    sendFriendRequest(userID) {
      const localUserID = localStorage.getItem('userID');
      const url = `http://localhost:8080/api/notifications/${localUserID}/${userID}/`;

      axios
          .post(url)
          .then(() => {
            alert('Friend request sent!');
          })
          .catch(error => {
            console.error('Error sending friend request:', error);
          });
    },
    redirectToUserPage(userID) {
      // Assuming you have defined a route named "user" for the user page
      this.$router.push({ name: "UserProfile", params: { userID } });
    },
  },
};
</script>

<style scoped>
.network-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.hop-container {
  margin-bottom: 20px;
  border: 1px solid #ccc;
  padding: 20px;
  border-radius: 8px;
  background-color: #f5f5f5;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.hop-title {
  margin: 0;
  font-size: 20px;
  color: #333;
}

.user-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: white;
}

.user-info {
  display: flex;
  flex-direction: column;
  margin-right: 100px;
  flex-grow: 1;
}

.username {
  margin: 0;
  font-weight: bold;
  color: black;
}

.user-id {
  margin: 0;
  color: #777;
}

.friend-request-btn {
  background-color: #4CAF50;
  color: white;
  padding: 6px 12px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.friend-request-btn:hover {
  background-color: #45a049;
}

@media screen and (max-width: 600px) {
  .hop-title {
    font-size: 18px;
  }
}
</style>
