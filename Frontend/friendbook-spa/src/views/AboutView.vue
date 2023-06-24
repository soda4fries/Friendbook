<template>
  <div class="container mt-4">
    <div class="row justify-content-center">
      <div class="col-md-10">
        <div class="card">
          <div class="card-body">
            <div class="text-center">
              <img :src="user.profilePicture" class="rounded-circle profile-picture" alt="Profile Picture">
              <h5 class="card-title mt-3">{{ user.fullName }}</h5>
            </div>
            <hr>
            <div class="row">
              <div class="col-sm-6">
                <h6>Email</h6>
                <p>{{ user.email }}</p>
              </div>
              <div class="col-sm-6">
                <h6>Username</h6>
                <p>{{ user.username }}</p>
              </div>
            </div>
            <hr>
            <div class="row">
              <div class="col-sm-6">
                <h6>Full Name</h6>
                <p>{{ user.fullName }}</p>
              </div>
              <div class="col-sm-6">
                <h6>Birthday</h6>
                <p>{{ user.birthday }}</p>
              </div>
            </div>
            <hr>
            <div class="row">
              <div class="col-sm-6">
                <h6>Age</h6>
                <p>{{ user.age }}</p>
              </div>
              <div class="col-sm-6">
                <h6>Address</h6>
                <p>{{ user.address }}</p>
              </div>
            </div>
            <hr>
            <div class="row">
              <div class="col-sm-6">
                <h6>Phone Number</h6>
                <p>{{ user.phoneNumber }}</p>
              </div>
              <div class="col-sm-6">
                <h6>Gender</h6>
                <p>{{ user.gender }}</p>
              </div>
            </div>
            <hr>
            <div class="row">
              <div class="col-sm-6">
                <h6>Bio</h6>
                <p>{{ user.bio }}</p>
              </div>
              <div class="col-sm-6">
                <h6>Hobbies</h6>
                <p>{{ user.hobbies }}</p>
              </div>
            </div>
            <hr>
            <div class="row">
              <div class="col-sm-6">
                <h6>Job Experience</h6>
                <p>{{ user.jobExperience }}</p>
              </div>
              <div class="col-sm-6">
                <h6>User Role</h6>
                <p>{{ user.isAdmin }}</p>
              </div>
            </div>
            <hr>
            <div class="text-center">
              <a href="/profile/update" class="btn btn-primary bg-black">Update Profile</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'AboutView',
  data() {
    return {
      user: {},
    };
  },
  mounted() {
    const userID = localStorage.getItem('userID');
    const url = `http://localhost:8080/api/UserProfile/${userID}/getProfile`;

    axios
        .get(url)
        .then((response) => {
          const data = response.data;
          this.user = {
            username: data.username,
            email: data.email,
            fullName: `${data.firstName} ${data.lastName}`,
            totalFriends: data.totalFriends,
            age: this.calculateAge(data.dob),
            birthday: data.dob,
            address: data.address,
            phoneNumber: data.phoneNumber,
            gender: data.gender ? 'Male' : 'Female',
            bio: data.bio,
            hobbies: data.hobbies.slice(1, -1),
            jobExperience: data.jobExperiance.slice(1, -1),
            isAdmin: data.role,
            profilePicture: 'https://cdn-icons-png.flaticon.com/512/3135/3135715.png',
          };
        })
        .catch((error) => {
          console.error('Error fetching user profile:', error);
        });
  },
  methods: {
    calculateAge(dob) {
      // Calculate age based on date of birth (dob)
      const birthDate = new Date(dob);
      const today = new Date();
      let age = today.getFullYear() - birthDate.getFullYear();
      const monthDiff = today.getMonth() - birthDate.getMonth();
      if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
        age--;
      }
      return age;
    },
    deleteUser() {
      const userID = localStorage.getItem('userID');
      const url = `api/admin/users/${userID}`;

      axios
          .delete(url)
          .then(() => {
            // Handle successful deletion
            alert('User deleted successfully.');
          })
          .catch((error) => {
            console.error('Error deleting user:', error);
          });
    },
  },
};
</script>

<style>
body {
  background-color: #000000;
  color: #ffffff;
}

.profile-picture {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  object-fit: cover;
  object-position: center;
}
</style>
