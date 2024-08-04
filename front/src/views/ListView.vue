<template>
    <div class="container mt-3">
        <h1 class="display-1 text-center">사용자 목록</h1>
        <div class="btn-group">
            <router-link to="/user/save" class="btn btn-primary">사용자 추가</router-link>
        </div>
        <table class="table table-hover mt-3">
            <thead class="table-dark">
            <tr>
              <th>번호</th>
              <th>이름</th>
              <th>이메일</th>
              <th>가입날짜</th>
            </tr>
          </thead>
          <tbody>
                <tr class="cursor-pointer" v-for="row in list" :key="row.num" 
                    @click="$event => href(row)">
                    <td>{{row.num}}</td>
                    <td>{{row.name}}</td>
                    <td>{{row.email}}</td>
                    <td>{{row.udate}}</td>
                </tr>
          </tbody>
          <tfoot>
            <tr><td colspan="4">{{list}}</td></tr>
          </tfoot>
        </table>
    </div>
</template>
<script>
import axios from 'axios';

export default {
    name:"사용자 리스트",
    // created()
     // Vue 인스턴스가 생성되고 나서 호출됨, 아직 HTML의 화면 요소는 적용하기 전이므로
    // <template>에는 여전히 접근할 수 없다. 
    // data 속성에 접근할 수 있고 methods 속성으로 로직을 실행할 수는 있다.
    // 외부 데이터 가져올 때 주로 사용 (비동기식 axios)
    // https://cafe.naver.com/ictstudy2024/525
    created(){
        this.fetchData()
    },
    data() {
        return {
            /*
            list:[{num:1,name:'테스형',email:'javabook@naver.com',udate:Date.now()},
                {num:2,name:'소머즈',email:'smz@naver.com',udate:Date.now()},
                {num:3,name:'소머즈2',email:'smz2@naver.com',udate:Date.now()},
                 ] 
            */
           //axios를 사용해서 비동기식으로 데이터를 연결할 배열 
            list:[]
        }
    },
    methods:{
        fetchData(){
            //비동기식으로 데이터를 바인딩 
            //axios.get(url).then((resp) => {}).catch((err)=>{})
            axios.get('http://192.168.0.107/myictstudy0529/users/userList')
            .then((resp)=>{
                console.log(resp.data)
                this.list = resp.data
            })
            .catch((err)=>{})
        },
        href(row){
            /*@click="$event => href(row)" */
            console.log("======================================")
            console.log(row)
            //this.$router.push({name:'DetailView'}) //일반적인 라우터
            // query형식의 라우터 - Get방식의 쿼리로 전달
            //this.$router.push({name:'DetailView',query:row}); 

            // params형식의 라우터 
            // :num/:name/:email
            this.$router.push({name:'DetailView',params:row})
        }
    }
}
</script>
