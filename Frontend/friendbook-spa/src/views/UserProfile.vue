<template>
  <div class="user-profile">
    <h1>User Profile</h1>
    <div v-if="userInfo">
      <div><strong>Username:</strong> {{ userInfo.username }}</div>
      <div><strong>First Name:</strong> {{ userInfo.firstName }}</div>
      <div><strong>Last Name:</strong> {{ userInfo.lastName }}</div>
      <div><strong>Address:</strong> {{ userInfo.address }}</div>
      <div><strong>Phone Number:</strong> {{ userInfo.phoneNumber }}</div>
      <div><strong>Gender:</strong> {{ userInfo.gender ? 'Male' : 'Female' }}</div>
      <div><strong>Bio:</strong> {{ userInfo.bio }}</div>
      <div><strong>Hobbies:</strong> {{ userInfo.hobbies }}</div>
      <div><strong>Relationship Status:</strong> {{ userInfo.relationStatus }}</div>
      <div><strong>Job Experience:</strong> {{ userInfo.jobExperiance }}</div>
      <div><strong>Date of Birth:</strong> {{ userInfo.dob }}</div>

      <div>
        <strong>Mutual Friends:</strong>
        <template v-if="mutualFriends.length > 0">
          <ul>
            <ul v-for="friend in mutualFriends" :key="friend.id">{{ friend.username }}</ul>
          </ul>
        </template>
        <template v-else>
          No mutual friends
        </template>
      </div>

      <button class="friend-request-btn" @click="sendFriendRequest">Send Friend Request</button>

      <button v-if="userRole === 'ADMIN'" class="delete-btn" @click="deleteUser()">
        Delete User
      </button>
    </div>
    <div v-else>
      User not Found
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      userInfo: null,
      userRole: localStorage.getItem('userRole'),
      mutualFriends: []
    };
  },
  mounted() {
    const userID = this.$route.params.userID;
    const urlProfile = `http://localhost:8080/api/UserProfile/${userID}/getProfile`;
    const localUserID = localStorage.getItem('userID');
    const urlMutualFriends = `http://localhost:8080/Friends/GetMutualFriends/${localUserID}/${userID}`;

    axios.get(urlProfile)
        .then((profileResponse) => {
          this.userInfo = profileResponse.data;
        })
        .catch((error) => {
          console.error('Error fetching user profile:', error);
        });

    axios.get(urlMutualFriends)
        .then((mutualFriendsResponse) => {
          this.mutualFriends = mutualFriendsResponse.data;
        })
        .catch((error) => {
          console.error('Error fetching mutual friends:', error);
        });
  },
  methods: {
    sendFriendRequest() {
      const localUserID = localStorage.getItem('userID');
      const userID = this.$route.params.userID;
      const url = `http://localhost:8080/api/notifications/${localUserID}/${userID}/`;

      axios
          .post(url)
          .then(() => {
            alert('Friend request sent!');
          })
          .catch((error) => {
            console.error('Error sending friend request:', error);
          });
    },
    deleteUser() {
      const userID = this.$route.params.userID;
      const url = `http://localhost:8080/admin/users/${userID}`;

      axios
          .delete(url)
          .then(() => {
            alert('User deleted successfully!');
            // Redirect to a different page or perform any other action after deletion
          })
          .catch((error) => {
            console.error('Error deleting user:', error);
          });
    },
  },
};
</script>

<style scoped>
.user-profile {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f2f2f2;
  border: 1px solid #ccc;
  border-radius: 4px;
}

h1 {
  margin-top: 0;
}

.friend-request-btn {
  margin-top: 10px;
  padding: 8px 16px;
  background-color: #4caf50;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.friend-request-btn:hover {
  background-color: #45a049;
}

.delete-btn {
  margin-top: 10px;
  padding: 8px 16px;
  background-color: #f44336;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.delete-btn:hover {
  background-color: #d32f2f;
}

.loading {
  text-align: center;
  color: #777;
}

.user-profile strong {
  color: black;
}

.user-profile div {
  color: black;
}
</style>
