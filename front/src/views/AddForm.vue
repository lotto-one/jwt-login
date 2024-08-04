<template>
    <div class="container mt-3">
      <h1 class="display-1 text-center">{{ msg }}</h1>
      <form @submit.prevent="submit">
        <div class="mb-3 mt-3">
          <label for="id" class="form-label">아이디:</label>
          <div class="input-group">
            <input
              type="text"
              class="form-control"
              id="id"
              name="id"
              placeholder="아이디 입력"
              v-model="id"
            />
            <button class="btn btn-primary" type="button" @click="idCheck">아이디 확인</button>
          </div>
        </div>
        <p style="display:none" id="idCheck-msg" ></p>
        <div class="mb-3 mt-3">
          <label for="name" class="form-label">이름:</label>
          <input type="text" class="form-control" id="name" name="name" v-model="name" />
        </div>
        <div class="mb-3 mt-3">
          <label for="pwd" class="form-label">비밀번호:</label>
          <input type="password" class="form-control" id="pwd" name="pwd" v-model="pwd" />
        </div>
        <div class="mb-3 mt-3">
          <label for="email" class="form-label">email:</label>
          <input type="email" class="form-control" id="email" name="email" v-model="email" /> 
        </div>
        <div class="mb-3 mt-3">
          <button class="btn btn-success w-100" type="submit">가입하기</button>
        </div>
      </form>
    </div>
  </template>
<script>
import axios from 'axios';
export default {
    data(){
        return{
            msg: "SignUp",
            id: "",
            pwd: "",
            name: "",
            email: "",
        }
    },
    methods:{
        idCheck() {
            const userId = this.id;
            const idCheckMsg = document.getElementById("idCheck-msg");
            if(userId === ""){
                idCheckMsg.innerText = "아이디는 필수 입력 사항입니다.";
                idCheckMsg.style.display = "block";
                document.getElementById("id").focus();
                return false;
            }else{
                idCheckMsg.style.display = "none";
            }
            // axios를 사용해서 아이디 체크를 전송한다.
            // postman에서 테스트 해보기 
            // http://192.168.0.107/myictstudy0529/members/idchk/xman
            axios.get(`http://192.168.0.107/myictstudy0529/members/idchk/${userId}`)
            .then((res) => {
                console.log(res); // 존재하면 1, 사용가능 0 
                if (res.data === 0) {
                    idCheckMsg.innerText = "사용 가능한 아이디입니다.";
                    idCheckMsg.style.color = "green";
                }else{
                    idCheckMsg.innerText = "이미 사용 중인 아이디입니다.";
                    idCheckMsg.style.color = "red";
                }
                idCheckMsg.style.display = "block";
            })
            .catch((error) => {})
        },
        submit() {
            const memberData = {
                id: this.id,pwd: this.pwd,name: this.name,email: this.email,
            };
            axios.post('http://192.168.0.107/myictstudy0529/members/signup', memberData)
            .then((res) => {
                console.log(res);
                alert('회원가입이 완료되었습니다.');
                this.$router.push("/");
            
            })
            .catch((error) => {
                console.error(error);
                alert('회원가입 중 오류가 발생했습니다.');
            });
            }
    }
}
</script>