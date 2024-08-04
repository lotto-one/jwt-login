<template>
  <div class="home">
    <!--
    <img alt="Vue logo" src="../assets/logo.png">
    <HelloWorld msg="Welcome to Your Vue.js App"/>
    -->
    <p>
      <img alt="toyou1" :src="toyou1"/>
    </p>
    <p>
      <img alt="toyou2" :src="toyou2"/>
    </p>
    <HelloWorld :msg="message"/>
    <button @click="getData">getData</button>
  </div>
</template>

<script>
// npm install axios => Ajax 
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import axios from 'axios'
//axios.get(url).then((resp)=>{}).catch((err)=>{})
export default {
  name: 'HomeView',
  data(){
    return {
      message:"처음 화면",
      toyou1: process.env.BASE_URL + "img/toyou1.png", // 이미지 경로 설정
      toyou2: process.env.BASE_URL + "img/toyou2.jpg" // 이미지 경로 설정1
    }
  },
  methods:{
    getData(){
      axios.get(`${process.env.VUE_APP_BACK_END_URL}/api/msg`)
      .then((resp) => {
        console.log(`${resp.data} 잘 받았구나`)
        this.message=resp.data
      })
      .catch((err) => {
        console.log(error)
      })
    }
  },
  components: {
    HelloWorld
  }
}
</script>
