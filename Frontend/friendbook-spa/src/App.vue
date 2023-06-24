<template>
  <div id="app">
    <nav class="sidebar navbar navbar-expand-lg navbar-dark bg-dark fixed-bottom">
      <div v-if="true" class="container">
        <ul class="navbar-nav flex-column">
          <li class="nav-item">
            <router-link to="/homepost" class="nav-link" data-bs-toggle="tooltip" data-bs-placement="right" title="Home">
              <i class="fas fa-home"></i>
            </router-link>
          </li>
          <li class="nav-item">
            <router-link to="/Traceback" class="nav-link" data-bs-toggle="tooltip" data-bs-placement="right" title="TraceBack">
              <i class="fa-duotone fa-backward"></i>
            </router-link>
          </li>
          <li class="nav-item">
            <router-link to="/search" class="nav-link" data-bs-toggle="tooltip" data-bs-placement="right" title="Search">
              <i class="fas fa-search"></i>
            </router-link>
          </li>
          <li class="nav-item">
            <router-link to="/message" class="nav-link" data-bs-toggle="tooltip" data-bs-placement="right" title="Message">
              <i class="fas fa-envelope"></i>
            </router-link>
          </li>
          <li class="nav-item">
            <router-link to="/about" class="nav-link" data-bs-toggle="tooltip" data-bs-placement="right" title="Profile">
              <i class="fas fa-user"></i>
            </router-link>
          </li>
          <li class="nav-item">
            <router-link to="/post" class="nav-link" data-bs-toggle="tooltip" data-bs-placement="right" title="Post">
              <i class="fas fa-pen"></i>
            </router-link>
          </li>
          <li class="nav-item">
            <router-link to="/explore" class="nav-link" data-bs-toggle="tooltip" data-bs-placement="right" title="Explore">
              <i class="fas fa-compass"></i>
            </router-link>
          </li>
          <li class="nav-item">
            <router-link to="/friendlist" class="nav-link" data-bs-toggle="tooltip" data-bs-placement="right" title="Friends">
              <i class="fas fa-user-friends"></i>
            </router-link>
          </li>
          <li class="nav-item">
            <router-link to="/notifications" class="nav-link" data-bs-toggle="tooltip" data-bs-placement="right" title="Notifications">
              <i class="fas fa-bell"></i>
            </router-link>
          </li>
          <li class="nav-item">
            <router-link to='/login' class="nav-link" data-bs-toggle="tooltip" data-bs-placement="right" title="Logout">
              <i class="fas fa-sign-out-alt"></i>
            </router-link>
          </li>
        </ul>
      </div>
      <div>

      </div>
    </nav>
    <router-view/>
  </div>
</template>

<script>
export default {
  methods: {
    logout() {
      this.$router.push('/login');
    },
    navigateHome() {
      this.$router.push('/');
    },
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #ffffff;
}

nav {
  padding: 30px;
  height: 100vh; /* Set the height to 100% of the viewport height */
  width: 60px; /* Set the desired fixed width for the sidebar */
  position: fixed; /* Fix the position of the sidebar */
  left: 0; /* Align the sidebar to the left */
  top: 0; /* Align the sidebar to the top */
  background-color: #343a40; /* Set the background color of the sidebar */
}

nav a {
  display: flex; /* Use flexbox for centering */
  align-items: center; /* Center vertically */
  justify-content: center;
  text-align: center;
  color: #ffffff;
  padding: 10px;
  margin-bottom: 10px;
  border-radius: 50%;
  width: 35px;
  height: 35px;
  transition: background-color 0.3s ease;
  font-size: 18px;
}

nav a:hover {
  background-color: #42b983;
}

nav i {
   font-size: 18px;
  width: 18px;
  height: 18px;

}

nav a.router-link-exact-active {
  background-color: #42b983;
}

</style>

