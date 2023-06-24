<template>
  <div class="upload-container">
    <div class="upload-form bg-dark-subtle p-4 rounded">
      <input type="text" v-model="imageLink" placeholder="Image Link" class="form-control mb-4">
      <div class="caption-field">
        <label for="caption" class="font-weight-bold">Caption:</label>
        <input type="text" id="caption" v-model="caption" class="form-control">
      </div>
      <button @click="showImagePreview" class="btn btn-primary">Show Preview</button>
      <img v-if="previewImage" :src="previewImage" alt="Preview" class="preview-image mb-4">
      <button @click="uploadPost" class="btn btn-primary">Upload Post</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      imageLink: '',
      caption: '',
      previewImage: null,
    };
  },
  methods: {
    showImagePreview() {
      this.previewImage = this.imageLink;
    },
    async uploadPost() {
      const userId = localStorage.getItem('userID'); // Retrieve user ID from local storage

      if (!userId) {
        console.error('User ID not found in local storage');
        return;
      }

      if (!this.imageLink) {
        console.error('No image link provided');
        return;
      }

      // Create the request body
      const request = {
        caption: this.caption,
        base64image: this.imageLink,
      };
      fetch(`http://localhost:8080/api/HomePage/UploadPost/${userId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(request),
      })
          .then((response) => response.json())
          .then((data) => {
            console.log('Post uploaded:', data);
            alert('Post uploaded successfully');
            // Perform any additional actions after successful upload
          })
          .catch((error) => {
            console.error('Error uploading post:', error);
            alert(error.message);
          });
    },
  },
};
</script>

<style>
.upload-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.upload-form {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.preview-image {
  width: 200px;
  height: auto;
  margin-bottom: 20px;
}

.caption-field {
  margin-bottom: 20px;
}

input[type="text"],
label {
  color: black;
}
</style>
