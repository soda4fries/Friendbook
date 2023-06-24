import { createRouter, createWebHistory } from 'vue-router'
import About from '../views/AboutView.vue'
import LoginView from '../views/LoginView.vue';
import RegistrationView from '../views/RegistrationView.vue';
import ProfileUpdate from '../views/ProfileUpdate.vue';
import Message from '../views/Message.vue'
import Posting from '../views/Posting.vue';
import Notifications from '../views/Notifications.vue'
import Homepost from '../views/Homepost.vue'
import Explore from '../views/Explore.vue'
import Friendlist from '../views/Friendlist.vue'
import Search from '../views/Search.vue'
import UserProfile from '../views/UserProfile.vue';
import TraceBack from "@/views/TraceBack.vue";

const routes = [
  {
    path: '/about',
    name: 'about',
    component: About
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/homepost',
    name: 'homepost',
    component: Homepost
  },
  {
    path: '/registration',
    name: 'registration',
    component: RegistrationView
  }, 
  {
    path: '/profile/update',
    name: 'profile-update',
    component: ProfileUpdate,
  } ,
  {
    path: '/message',
    name: 'message',
    component: Message,
  },
  {
    path: '/post',
    name: 'post',
    component: Posting,
  },
  {
    path: '/notifications',
    name: 'notifications',
    component: Notifications,
  },
  {
    path: '/explore',
    name: 'explore',
    component: Explore,
  },
  {
    path: '/friendlist',
    name: 'friendlist',
    component: Friendlist,
  },
  {
    path: '/search',
    name: 'Search',
    component: Search,
  },
  {
    path: '/user/:userID',
    name: 'UserProfile',
    component: UserProfile,
  },
  {
    path: '/traceback',
    name: 'TraceBack',
    component: TraceBack,
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
