<template>
  <div class="container mt-4">
    <h1 class="text-center">My Friends</h1>
    <div id="friend-cards">
      <div v-if="loading" class="text-center">Loading...</div>
      <div v-else>
        <div v-for="friend in friends" :key="friend.id" class="friend-card">
          <img :src="friend.profilePicture" class="profile-picture" alt="Profile Picture">
          <span class="friend-name">{{ friend.name }}</span>
          <button @click="sendMessage(friend.id)" class="message-button">Send Message</button>
        </div>
      </div>
    </div>
  </div>
</template>
  
  <script>
  import axios from 'axios';

  export default {
    data() {
      return {
        friends: [],
        loading: true,
      };
    },
    mounted() {
      this.fetchFriends();
    },
    methods: {
      async fetchFriends() {
        const userID = localStorage.getItem('userID');
        const apiUrl = `http://localhost:8080/Friends/GetFriendsList/${userID}`;

        axios
            .get(apiUrl)
            .then(response => {
              this.friends = response.data.map(friend => ({
                id: friend.id,
                name: friend.username,
                profilePicture: `https://cdn-icons-png.flaticon.com/512/3135/3135715.png`, // Assuming the profile picture URL follows this pattern
              }));
              this.loading = false;
            })
            .catch(error => {
              console.error('Error fetching friends:', error);
              this.loading = false;
            });
      },
      async sendMessage(friendId) {
        const userId = localStorage.getItem('userID');
        const apiUrl = `http://localhost:8080/api/message/Dm/${userId}/${friendId}`;

        try {
          const response = await axios.post(apiUrl);
          console.log('Message sent successfully:', response);
          alert('Conversation Created successfully!')
        } catch (error) {
          console.error('Error sending message:', error);
          alert('Conversation already exists!')
        }
      },
    },
  };
  </script>
  
  <style>
  body {
    background-color: #f8f9fa;
  }
  
  h1,
  .text-center {
    color: black;
  }

  .friend-card {
    display: flex;
    align-items: center;
    padding: 10px;
    margin-bottom: 10px;
    background-color: #fff;
    border-radius: 5px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    transition: box-shadow 0.3s ease;
  }

  .friend-card:hover {
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  }

  .friend-card .profile-picture {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    object-fit: cover;
    object-position: center;
    margin-right: 10px;
  }

  .friend-card .friend-name {
    margin-bottom: 5px;
    font-weight: bold;
    color: black;
  }

  .message-button {
    padding: 5px 10px;
    margin-left: auto;
    background-color: #4caf50;
    color: white;
    border: none;
    border-radius: 3px;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }

  .message-button:hover {
    background-color: #45a049;
  }
  </style>
  
  