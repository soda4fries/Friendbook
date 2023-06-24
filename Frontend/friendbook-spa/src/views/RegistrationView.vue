<template>
  <div class="dark-bg">
    <div class="container">
      <div class="card">
        <div class="card-body">
          <h2 class="card-title">Registration Form</h2>
          <form @submit.prevent="submitForm" class="needs-validation" novalidate>

            <div class="mb-3">
              <label for="username" class="form-label">Username:</label>
              <input type="text" id="username" v-model="username" class="form-control" required/>
              <div class="invalid-feedback">Please enter a username.</div>
            </div>

            <div class="mb-3">
              <label for="password" class="form-label">Password:</label>
              <input type="password" id="password" v-model="password" class="form-control" required/>
              <div class="invalid-feedback">Please enter a password.</div>
            </div>

            <div class="mb-3">
              <label for="email" class="form-label">Email:</label>
              <input type="email" id="email" v-model="email" class="form-control" required/>
              <div class="invalid-feedback">Please enter a valid email.</div>
            </div>

            <div class="mb-3">
              <label for="phone-number" class="form-label">Phone Number:</label>
              <input type="tel" id="phone-number" v-model="phoneNumber" class="form-control" required/>
              <div class="invalid-feedback">Please enter a valid phone number.</div>
            </div>

            <div class="mb-3">
              <label for="first-name" class="form-label">First Name:</label>
              <input type="text" id="first-name" v-model="firstName" class="form-control" required/>
              <div class="invalid-feedback">Please enter your first name.</div>
            </div>

            <div class="mb-3">
              <label for="last-name" class="form-label">Last Name:</label>
              <input type="text" id="last-name" v-model="lastName" class="form-control" required/>
              <div class="invalid-feedback">Please enter your last name.</div>
            </div>

            <div class="mb-3">
              <label for="date-of-birth" class="form-label">Date of Birth:</label>
              <input type="date" id="date-of-birth" v-model="dateOfBirth" class="form-control" required/>
              <div class="invalid-feedback">Please enter your date of birth.</div>
            </div>

            <div class="mb-3">
              <label for="address" class="form-label">Address:</label>
              <textarea id="address" v-model="address" class="form-control" required></textarea>
              <div class="invalid-feedback">Please enter your address.</div>
            </div>

            <div class="mb-3">
              <label for="gender" class="form-label">Gender:</label>
              <select id="gender" v-model="gender" class="form-select" required>
                <option value="male">Male</option>
                <option value="female">Female</option>
              </select>
              <div class="invalid-feedback">Please select your gender.</div>
            </div>

            <div class="mb-3">
              <label for="Relationship" class="form-label">Relationship Status:</label>
              <select id="relationshipStatus" v-model="relationshipStatus" class="form-select" required>
                <option value="Single">Male</option>
                <option value="In a Relationship">In a Relationship</option>
                <option value="Married">Married</option>
                <option value="Prefer not to say">Prefer not to say</option>
              </select>
              <div class="invalid-feedback">Please select your gender.</div>
            </div>

            <div class="mb-3">
              <label for="bio" class="form-label">Bio:</label>
              <textarea id="bio" v-model="bio" class="form-control" required></textarea>
              <div class="invalid-feedback">Please enter your bio.</div>
            </div>

            <div>
              <div class="mb-3">
                <label for="hobbies" class="form-label" @click="fetchHobbyList">Get Hobbies:</label>
                <div class="hobby-container">
                  <div v-for="hobby in hobbyList" :key="hobby" style="display: flex; align-items: center;">
                    <input type="checkbox" :id="hobby" :value="hobby" v-model="selectedHobbies" class="form-check-input"
                           style="margin-right: 0.5rem;">
                    <label :for="hobby" class="form-check-label">{{ hobby }}</label>
                  </div>
                </div>
                <div class="invalid-feedback">Please select your hobbies.</div>
                <div class="mt-2">
                  <span v-for="(hobby, index) in selectedHobbies" :key="index" class="badge bg-primary me-1">{{
                      hobby
                    }}</span>
                </div>
              </div>
            </div>

            <div>
              <div class="mb-3">
                <label for="job-experience" class="form-label">Job Experience:</label>
                <div>
                  <div v-for="(experience, index) in jobExperienceList" :key="index" class="job-experience-item">
                    <textarea v-model="jobExperienceList[index].value" class="form-control" required></textarea>
                    <button type="button" @click="removeExperience(index)" class="btn btn-danger btn-sm">Remove</button>
                  </div>
                </div>
                <button type="button" @click="addExperience" class="btn btn-primary mt-2">Add Experience</button>
                <div class="invalid-feedback" v-if="!jobExperienceList.length">Please enter your job experience.</div>
              </div>
            </div>

            <div class="mb-3">
              <label for="is-admin" class="form-label">Is Admin?</label>
              <input type="checkbox" id="is-admin" v-model="isAdmin" class="form-check-input"/>
            </div>


            <button type="submit" class="btn btn-primary btn-dark">Register</button>
          </form>
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
      username: '',
      password: '',
      email: '',
      phoneNumber: '',
      firstName: '',
      lastName: '',
      dateOfBirth: '',
      address: '',
      gender: 'male',
      bio: '',
      hobbies: '',
      jobExperience: '',
      isAdmin: false,
      relationshipStatus: '',
      hobbyList: [],
      selectedHobbies: [],
      jobExperienceList: [{value: ''}],
    };
  },
  methods: {
    addExperience() {
      this.jobExperienceList.push({value: ''}); // Add an empty experience object to the list
    },
    removeExperience(index) {
      this.jobExperienceList.splice(index, 1); // Remove the experience object at the specified index
    },
    async submitForm() {
      // Validate the form fields here

      const requestBody = {
        username: this.username,
        password: this.password,
        email: this.email,
        phoneNumber: this.phoneNumber,
        firstName: this.firstName,
        lastName: this.lastName,
        dateOfBirth: this.dateOfBirth,
        address: this.address,
        gender: this.gender === 'male' ? true : false,
        bio: this.bio,
        hobbies: this.selectedHobbies.join(','),
        jobExperiance: this.jobExperienceList.map(experience => experience.value).join(','), // Convert the list of experiences to a comma-separated string
        isAdminrole: this.isAdmin ? 1 : 0,
        relationshipStatus: this.relationshipStatus,
      };

      try {
        const response = await axios.post('http://localhost:8080/auth/register', requestBody);
        const {token, userID, userName, role} = response.data; // Extract the token, userID, and userName from the response
        localStorage.setItem('token', token); // Save the token to localStorage for future use
        localStorage.setItem('userID', userID); // Save the userID to localStorage for future use
        localStorage.setItem('userName', userName); // Save the userName to localStorage for future use
        localStorage.setItem("userRole", role);
        localStorage.setItem("isLoggedin", 'true');
        alert('Registration successful!' + '\n' + 'Welcome ' + userName + '!');
        this.$router.push('/homepost'); // Redirect the user to the home page
      } catch (error) {
        console.error('Failed to register:', error);
        alert(error.response.data.message);
        // Handle the error if needed
      }
    },
    async fetchHobbyList() {
      try {
        console.log("fetching hobby list");
        const response = await axios.get('http://localhost:8080/auth/api/UserProfile/hobbylist/');
        this.hobbyList = response.data; // Assuming the API returns an array of hobbies
        console.log(this.hobbyList);
      } catch (error) {
        console.error('Failed to fetch hobby list:', error);
      }
    },
    // Rest of the methods
  },
};
</script>

<style>

.job-experience-item {
  display: flex;
  align-items: center;
  margin-bottom: 1rem;
}

.job-experience-item textarea {
  flex-grow: 1;
  margin-right: 0.5rem;
}

.job-experience-item button {
  margin-left: 0.5rem;
}

.hobby-container {
  height: 200px; /* Adjust the height as needed */
  overflow-y: auto;
}

.container {
  max-width: 600px;
  margin: 0 auto;
}

.card {
  background-color: #444;
  border-radius: 10px;
  padding: 20px;
}

.card-title {
  color: #fff;
  font-size: 24px;
  margin-bottom: 20px;
}

.form-label {
  font-weight: bold;
}

.invalid-feedback {
  color: #ff6b6b;
  display: none;
}

.form-control {
  border: none;
  background-color: #555;
  color: #fff;
  border-radius: 5px;
  padding: 10px;
  margin-bottom: 10px;
}

.form-control:focus {
  outline: none;
  box-shadow: 0 0 5px #ff6b6b;
}

textarea.form-control {
  resize: vertical;
}

.form-select {
  border: none;
  background-color: #555;
  color: #fff;
  border-radius: 5px;
  padding: 10px;
  margin-bottom: 10px;
}

.btn-primary {
  background-color: #ff6b6b;
  color: #fff;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  cursor: pointer;
}

.btn-primary:hover {
  background-color: #ff4d4d;
}

.checkbox-label {
  font-weight: normal;
  margin-left: 5px;
}

</style>

