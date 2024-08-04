<template>
    <div>
      <h1>Login</h1>
      <form @submit.prevent="submitLogin">
        <div>
          <label for="username">Username:</label>
          <input type="text" v-model="username" />
        </div>
        <div>
          <label for="password">Password:</label>
          <input type="password" v-model="password" />
        </div>
        <div>
          <button type="submit">Login</button>
        </div>
        <div v-if="errorMessage">{{ errorMessage }}</div>
      </form>
    </div>
  </template>
  
  <script>
  import { mapActions } from 'vuex'
  
  export default {
    data() {
      return {
        username: '',
        password: '',
        errorMessage: ''
      }
    },
    methods: {
      ...mapActions(['login']),
      submitLogin() {
        this.errorMessage = ''
        this.login({ username: this.username, password: this.password })
          .then(() => {
            this.$router.push('/')
          })
          .catch(err => {
            this.errorMessage = err.message
          })
      }
    }
  }
  </script>