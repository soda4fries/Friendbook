<template>
  <div class="search-container">
    <input
        type="text"
        v-model="searchTerm"
        @input="searchItems"
        placeholder="Search..."
        class="search-input"
    />

    <select v-model="searchOption" class="search-option">
      <option value="username">username phone number e-mail</option>
      <option value="fuzzy">Fuzzy</option>
    </select>

    <ul v-if="searchResults.length > 0" class="search-results">
      <li v-for="result in searchResults" :key="result.userName" class="search-item">
        <div>Username: {{ result.userName }}</div>
        <div>Bio: {{ result.bio }}</div>
        <div>Full Name: {{ result.fullName }}</div>
        <div class="visually-hidden">UserID: {{ result.userID }}</div>
        <button @click="redirectToUserPage(result.userID)">View Full Info</button>
      </li>
    </ul>

    <div v-else class="no-results">
      No results found.
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchTerm: "",
      searchOption: "username", // Default option is "username"
      items: [],
    };
  },
  computed: {
    searchResults() {
      if (this.searchTerm === "") {
        return [];
      }

      return this.items;
    },
  },
  methods: {
    async searchItems() {
      if (this.searchTerm === "") {
        return;
      }

      let results = [];

      if (this.searchOption === "username") {
        const url = "http://localhost:8080/api/Search/UserNamePhoneEmail/";
        const requestBody = {
          searchterm: this.searchTerm,
        };

        try {
          const response = await fetch(url, {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(requestBody),
          });
          results = await response.json();
        } catch (error) {
          console.error("Error fetching search results:", error);
        }
      } else if (this.searchOption === "fuzzy") {
        const url = "http://localhost:8080/api/Search/BioFuzzySearch/";
        const requestBody = {
          searchterm: this.searchTerm,
        };

        try {
          const response = await fetch(url, {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(requestBody),
          });
          results = await response.json();
        } catch (error) {
          console.error("Error fetching search results:", error);
        }
      }

      this.items = results;
    },
    redirectToUserPage(userID) {
      // Assuming you have defined a route named "user" for the user page
      this.$router.push({ name: "UserProfile", params: { userID } });
    },
  },
};
</script>

<style scoped>
.search-container {
  position: relative;
  max-width: 400px;
  margin: 0 auto;
}

.search-input {
  width: 100%;
  padding: 8px 12px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
  outline: none;
}

.search-option {
  margin-top: 4px;
  width: 100%;
  padding: 8px 12px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
  outline: none;
}

.search-results {
  position: absolute;
  top: calc(100% + 4px);
  left: 0;
  z-index: 1;
  width: 100%;
  max-height: 200px;
  overflow-y: auto;
  padding: 8px;
  background-color: #fff;
  border: 1px solid #ccc;
  border-top: none;
  border-radius: 0 0 4px 4px;
  list-style-type: none;
}

.search-item {
  padding: 4px;
  cursor: pointer;
  color: #000;
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.search-item button {
  padding: 8px 12px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.search-item button:hover {
  background-color: #0056b3;
}

.no-results {
  padding: 8px;
  text-align: center;
  color: #777;
}
</style>