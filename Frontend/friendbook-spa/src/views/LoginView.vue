<template>
  <div class="dark-bg">
    <div class="container">
      <div class="card square-card">
        <div class="card-body">
          <h2 class="card-title">Login</h2>
          <form @submit.prevent="submitForm" class="needs-validation" novalidate>
            <div class="mb-3">
              <label for="username-phone" class="form-label">Username or Phone Number:</label>
              <input type="text" id="username-phone" v-model="usernameOrPhone" class="form-control" required/>
              <div class="invalid-feedback">Please enter your username or phone number.</div>
            </div>

            <div class="mb-3">
              <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" id="toggle" v-model="isUsernameSelected">
                <label class="form-check-label" for="toggle">Is Username</label>
              </div>
            </div>

            <div class="mb-3">
              <label for="password" class="form-label">Password:</label>
              <input type="password" id="password" v-model="password" class="form-control" required/>
              <div class="invalid-feedback">Please enter your password.</div>
            </div>

            <button type="submit" class="btn btn-primary btn-dark" @click="submitForm">Login</button>
          </form>
          <p class="mt-3 text-center">
            Don't have an account?
            <router-link to="/registration">Sign up</router-link>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import router from "@/router";

export default {
  data() {
    return {
      usernameOrPhone: "",
      password: "",
      isUsernameSelected: true, // Default selection is username
    };
  },
  methods: {
    submitForm() {
      // Prepare the login request data
      const loginData = {
        cred: this.usernameOrPhone,
        credType: this.isUsernameSelected ? "CRED_USERNAME" : "CRED_PHONE",
        password: this.password,
      };

      // Send the login request to the server
      fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(loginData),
      })
          .then(response => response.json())
          .then(data => {
            // Save the received token and user information to local storage
            localStorage.setItem("token", data.token);
            localStorage.setItem("userID", data.userID);
            localStorage.setItem("userName", data.userName);
            localStorage.setItem("userRole", data.role);
            localStorage.setItem("isLoggedin", 'true');
            localStorage.setItem("viewedPost", '');
            alert("Login successful!" + "\n" + "Welcome " + data.userName);
            router.push("/homepost")

            // Optional: Perform any additional actions upon successful login
            // e.g., navigate to a new page, display a success message, etc.
          })
          .catch(error => {
            // Handle any errors that occurred during the login request
            console.error("Login failed:", error);
            // Optional: Display an error message to the user
          });
    },
  },
};
</script>

<style scoped>
@import 'bootstrap/dist/css/bootstrap.min.css';

.container {
  margin-top: 50px;
}

.square-card {
  max-width: 400px;
  margin: 0 auto;
  border-radius: 0 !important;
}

.btn-primary {
  margin-top: 20px;
}

.btn-dark {
  background-color: #343a40;
  border-color: #343a40;
}
</style>
