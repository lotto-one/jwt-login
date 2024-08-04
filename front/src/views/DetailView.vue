<template>
    <div lass="container mt-3">
        <h1 class="display-1 text-center">{{ msg }}</h1>
    <form>
      <input type="hidden" name="num" :value="result.num"  readonly>
      <div class="mb-3 mt-3">
        <label for="name" class="form-label">이름:</label>
        <input type="text" class="form-control" id="name" name="name" readonly 
        :value="result.name"/>
      </div>
      <div class="mb-3 mt-3">
        <label for="name" class="form-label">email:</label>
        <input type="email" class="form-control" id="email"  name="email" readonly 
        v-bind:value="result.email"/>
      </div>
    </form>  
    </div>
</template>
<script>
import axios from 'axios';

export default {
    data() {
        return {
            msg:"테스형님의 사용자 정보",
            result:{}
        }
    },
    created(){
      console.log("----------------Router-------------------")
      console.log(this.$route);
      console.log("----------------Router Query-------------------")
      //console.log(this.$route.query);
      //this.result=this.$route.query;
      console.log("----------------Router Params-------------------")  
      // this.result = {
      //   num:this.$route.params.num,
      //   name:this.$route.params.name,
      //   email:this.$route.params.email
      // }
      console.log("----------------axios -------------------")  
     //Axios를 사용한 Detail구현하기 
     this.fetchData(this.$route.params.num)
    },
    methods:{

      fetchData(num){
        axios.get(`http://192.168.0.107/myictstudy0529/users/detail/${num}`)
        .then((resp)=>{
         console.log(resp)
         this.result=resp.data
         })
        .catch((error)=>{
          console.log(error)
        })
      }
    }
}
</script>