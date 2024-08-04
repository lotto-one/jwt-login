import { createStore } from 'vuex'

export default createStore({
  state: {
    // 초기화 localStorage 선언
    username:localStorage.getItem("username") || "",
    token:localStorage.getItem("token") || "",
  },
  getters: {
    // 인증 했는지 판단해서 토큰이 있으면 true, 없으면 false
    isAuthenticated :state => !!state.token,
    // 로그인한 사용자 값 반환 
    username: state => state.username
  },
  mutations: { // state의 속성값을 변경하는 속성 
    // 사용자 이름과 토큰을 state에 설정하고, localStorage에도 저장
    setAuth(state, { username, token }) {
      state.username = username
      state.token = token
      // 이름과 토큰을 로컬 스토리지에 저장
      localStorage.setItem('username', username)
      localStorage.setItem('token', token)
    },
    // 사용자 이름과 토큰을 state에서 제거하고, localStorage에서도 삭제
    clearAuth(state) {
      state.username = ''
      state.token = ''
      localStorage.removeItem('username')
      localStorage.removeItem('token')
    }
  },
  actions: {
    // 사용자 이름과 비밀번호를 사용하여 로그인을 시도
    login({ commit }, { username, password }) {
      return new Promise((resolve, reject) => {
        //axios로 스프링부트의 login 전송 
        //Token을 받아서 
        // 가상의 토큰을 생성
        if (username === 'test' && password === '11') {
          commit('setAuth', { username, token: 'fake-jwt-token' })
          resolve() // 로그인 성공
        } else {
          reject(new Error('Invalid credentials')) // 로그인 실패
        }
      })
    },
      // 로그아웃을 수행하여 state를 초기화
      logout({ commit }) {
        // 서버로 axios로 logout
        commit('clearAuth')
      }
    }

})
