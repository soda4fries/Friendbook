<template>
  <div class="container">
    <h1>Viewed Posts</h1>
    <div class="card futuristic-card" v-for="post in viewedPosts" :key="post.id">
      <div class="card-body">
        <div class="d-flex align-items-center mb-3">
          <img src="https://cdn-icons-png.flaticon.com/512/3135/3135715.png" class="rounded-circle profile-picture" alt="Profile Picture">
          <h5 class="card-title ms-3">{{ post.posterUserName }}</h5>
        </div>
        <p class="card-text">{{ post.caption }}</p>
        <div class="aspect-ratio-wrapper">
          <img :src="post.imageBase64" class="card-img-top futuristic-card-image" alt="User Image">
        </div>
        <div class="card-actions d-flex justify-content-between align-items-center mt-3">
          <button ref="likesButton" @click="toggleLike(post)" class="btn btn-sm btn-outline-dark" data-bs-toggle="tooltip" data-bs-placement="top" :title="post.likeUser.join(', ')">
            <i class="far fa-thumbs-up"></i> {{ post.likeUser.length }} Likes
          </button>
          <button v-if="userRole === 'ADMIN'" @click="deletePost(post.id)" class="btn btn-sm btn-outline-danger"><i class="fas fa-trash"></i> Delete</button>
          <button v-else class="btn btn-sm btn-outline-dark"><i class="fas fa-share"></i> Share</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      viewedPosts: [],
      userId: '',
      userRole: '',
    };
  },
  mounted() {
    this.userId = localStorage.getItem('userID');
    this.userName = localStorage.getItem('userName');
    this.userRole = localStorage.getItem('userRole');
    this.fetchViewedPosts();
    this.$nextTick(() => {
      const likesButton = this.$refs.likesButton;
      if (likesButton) {
        likesButton.addEventListener('mouseenter', () => {
          this.$bvTooltip.show('likesButtonTooltip');
        });
        likesButton.addEventListener('mouseleave', () => {
          this.$bvTooltip.hide('likesButtonTooltip');
        });
      }
    });
  },
  methods: {
    fetchViewedPosts() {
      let viewedPostsData = localStorage.getItem('viewedPost');
      const inputString = viewedPostsData;
      viewedPostsData = [...new Set(inputString.split(','))].join(',');

      if (viewedPostsData) {
        const viewedPostIds = viewedPostsData.split(',');
        const promises = viewedPostIds.map((postId) => {
          return fetch(`http://localhost:8080/api/HomePage/GetOnePost/${postId}`)
              .then((response) => response.json())
              .then((postData) => postData);
        });
        Promise.all(promises)
            .then((posts) => {
              this.viewedPosts = posts;
            })
            .catch((error) => {
              console.error('Error fetching viewed posts:', error);
              alert(error.message);
            });
      }
    },
    toggleLike(post) {
      if (post.likeUser.includes(this.userName)) {
        this.unlikePost(post.id);
      } else {
        this.likePost(post.id);
      }
    },
    likePost(postId) {
      fetch(`http://localhost:8080/api/HomePage/LikePost/${this.userId}/${postId}`, {
        method: 'POST',
      })
          .then((response) => response.json())
          .then((data) => {
            const postIndex = this.viewedPosts.findIndex((post) => post.id === postId);
            if (postIndex !== -1) {
              this.viewedPosts[postIndex].likeUser.push(this.userName);
            }
          })
          .catch((error) => {
            console.error('Error liking post:', error);
            alert(error.message);
          });
    },
    unlikePost(postId) {
      fetch(`http://localhost:8080/api/HomePage/UnlikePost/${this.userId}/${postId}`, {
        method: 'POST',
      })
          .then((response) => response.json())
          .then((data) => {
            const postIndex = this.viewedPosts.findIndex((post) => post.id === postId);
            if (postIndex !== -1) {
              this.viewedPosts[postIndex].likeUser = this.viewedPosts[postIndex].likeUser.filter(
                  (userId) => userId !== this.userName
              );
            }
          })
          .catch((error) => {
            console.error('Error unliking post:', error);
            alert(error.message);
          });
    },
    deletePost(postId) {
      fetch(`http://localhost:8080/admin/DeletePosts/${postId}`, {
        method: 'DELETE',
      })
          .then((response) => response.json())
          .then((data) => {
            alert(JSON.stringify(data));
            // Remove the deleted post from the viewedPosts array
            this.viewedPosts = this.viewedPosts.filter((post) => post.id !== postId);
          })
          .catch((error) => {
            console.error('Error deleting post:', error);
            alert(error.message);
          });
    },
  },
};
</script>

<style>
.container {
  margin-top: 20px;
}

.futuristic-card {
  background-color: #ffffff;
  color: #000000;
  border: none;
  border-radius: 10px;
  box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px; /* Add margin bottom for spacing between cards */
}

.profile-picture {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
  object-position: center;
}

.aspect-ratio-wrapper {
  position: relative;
  padding-top: 75%; /* 4:3 aspect ratio (h/w * 100) */
}

.futuristic-card-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  border-radius: 10px;
  object-fit: cover;
}

.card-actions {
  margin-top: 10px;
}

.card-actions button {
  font-size: 12px;
  padding: 5px 10px;
  border-radius: 20px;
}

.card-actions button i {
  margin-right: 5px;
}

@media (max-width: 576px) {
  .col-md-8 {
    max-width: 100%;
  }
}
</style>
