<template>
  <div class="container mt-4">
    <div class="row justify-content-center">
      <div class="col-md-8">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title mb-4">Update Profile</h5>
            <form @submit.prevent="submitForm">
              <div class="mb-3">
                <label for="address" class="form-label">Address</label>
                <input type="text" id="address" class="form-control" v-model="user.address">
              </div>
              <div class="mb-3">
                <label for="phoneNumber" class="form-label">Phone Number</label>
                <input type="tel" id="phoneNumber" class="form-control" v-model="user.phoneNumber">
              </div>
              <div class="mb-3">
                <label for="bio" class="form-label">Bio</label>
                <textarea id="bio" class="form-control" rows="4" v-model="user.bio"></textarea>
              </div>
              <div>
                <div class="mb-3">
                  <label for="hobbies" class="form-label" @click="fetchHobbyList">Get Hobbies:</label>
                  <div class="hobby-container">
                    <div v-for="hobby in hobbyList" :key="hobby" style="display: flex; align-items: center;">
                      <input type="checkbox" :id="hobby" :value="hobby" v-model="user.selectedHobbies" class="form-check-input" style="margin-right: 0.5rem;">
                      <label :for="hobby" class="form-check-label">{{ hobby }}</label>
                    </div>
                  </div>
                  <div class="invalid-feedback">Please select your hobbies.</div>
                  <div class="mt-2">
                    <span v-for="(hobby, index) in user.selectedHobbies" :key="index" class="badge bg-primary me-1">{{ hobby }}</span>
                  </div>
                </div>
              </div>
              <div class="row mb-3">
                <div class="col-8">
                  <label for="newJob" class="form-label">Add New Job Experience</label>
                  <input type="text" id="newJob" class="form-control" v-model="newJobExperience">
                </div>
                <div class="col-4 d-flex align-items-end">
                  <button class="btn btn-primary" @click="addNewJobExperience">Add Job Experience</button>
                </div>
              </div>
              <div class="text-center">
                <button type="submit" class="btn btn-primary">Update Profile</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>




<script>
import axios from "axios";

export default {
  name: 'ProfileUpdate',
  data() {
    return {
      hobbyList: [],
      user: {
        address: '',
        phoneNumber: '',
        gender: '',
        bio: '',
        hobby: [],
        selectedHobbies: [],
      },
      jobExperience: '',
      newJobExperience: '',
    };
  },
  methods: {
    submitForm() {
      const userId = localStorage.getItem('userID');
      if (userId) {
        const updatedProfile = {
          address: this.user.address,
          phoneNumber: this.user.phoneNumber,
          bio: this.user.bio,
          hobbies: this.user.selectedHobbies.join(','),
        };

        // Send the updated user profile to the server
        axios.post(`http://localhost:8080/api/UserProfile/${userId}/editProfile`, updatedProfile)
            .then(response => {
              console.log('Profile updated successfully:', response.data);
              alert('Profile updated successfully!');
              // Perform any further actions or show success message
            })
            .catch(error => {
              console.error('Failed to update profile:', error);
              // Handle error or show error message
            });
      } else {
        console.error('User ID not found.');
        // Handle error or show error message
      }
    },
    getUserProfile(userId) {
      // Make an API request to retrieve the user profile data
      // Replace 'api/UserProfile/{userId}/getProfile' with the actual API endpoint
      fetch(`http://localhost:8080/api/UserProfile/${userId}/getProfile`)
          .then(response => response.json())
          .then(data => {
            this.user.firstName = data.firstName;
            this.user.lastName = data.lastName;
            this.user.address = data.address;
            this.user.phoneNumber = data.phoneNumber;
            this.user.gender = data.gender;
            this.user.bio = data.bio;
            const hobbiesString = data.hobbies.slice(1, -1);
            this.user.selectedHobbies = hobbiesString.split(',');
            this.jobExperience = data.jobExperiance.slice(1, -1);
          })
          .catch(error => {
            console.error('Error retrieving user profile:', error);
          });
    },
    async fetchHobbyList() {
      try {
        console.log("fetching hobby list");
        const response = await axios.get('http://localhost:8080/auth/api/UserProfile/hobbylist/');
        this.hobbyList = response.data; // Assuming the API returns an array of hobbies
        console.log(this.user.hobbyList);
      } catch (error) {
        console.error('Failed to fetch hobby list:', error);
      }
    },
    addNewJobExperience() {
      const userId = localStorage.getItem('userID');
      const newJob = this.newJobExperience.trim();
      if (userId && newJob !== '') {
        axios.post(`http://localhost:8080/api/UserProfile/addjob/${userId}/${newJob}`)
            .then(response => {
              alert('New job experience added successfully!');
              console.log('New job experience added successfully:', response.data);
              this.newJobExperience = '';
              // Update the jobExperience data or perform any further actions
            })
            .catch(error => {
              console.error('Failed to add new job experience:', error);
              // Handle error or show error message
            });
      } else {
        console.error('User ID not found or new job experience is empty.');
        // Handle error or show error message
      }
    },
  },
  mounted() {
    // Retrieve the userId from localStorage
    const userId = localStorage.getItem('userID');
    if (userId) {
      this.getUserProfile(userId);
    }
  },
};
</script>

<style>
body {
  background-color: #000000;
  color: #ffffff;
}
</style>
