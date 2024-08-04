<template>
    <div>
      <div class="information">
        <p>이미지를 업로드하세요.</p>
        <input type="file" @change="selectFile" multiple accept="image/*" ref="fileRef" />
      </div>
      <div class="images" v-if="files.length > 0">
        {{ files }}
        <div v-for="file in files" :key="file.name" class="image">
          <img :src="file.preview" alt="이미지" > {{ file.preview }} / {{ file.name }}
        </div>
      </div>
    </div>
  </template>
  <script>
  import axios from 'axios'
  export default {
    data() {
      return {
        files: [],
      };
    },
    computed: {
      backendUrl() {
        //return process.env.VUE_APP_BACKEND_URL;
        return "http://192.168.0.107/myictstudy0529"
      },
    },
    methods: {
      selectFile(event) {
        // 파일 선택시 초기화 해줘야 선택한 파일만 files 에 들어 가기 때문이다.
        this.files = [];
        // 폼으로 데이터를 전송할 객체
        const formData = new FormData();
        const selectedFiles = event.target.files;
        for (let i = 0; i < selectedFiles.length; i++) {
          const file = selectedFiles[i];
          // 미리보기 URL 생성
          const preview = URL.createObjectURL(file);
          // 파일 정보와 미리보기 URL을 files 배열에 추가
          this.files.push({
            name: file.name,
            preview: preview,
          });
  
        // vo 객체의 필드들을 formData에 추가
        /*
            private String title;
            private String writer;
            private String content;
            private String imgn;
            private int hit;
            private String reip;
            private String bdte;
        */
        formData.append('title', '업로드 테스트');
        formData.append('writer', '하하');
        formData.append('content', '업로드 테스트');
        formData.append('reip', '192.168.0.107');
          // 업로드할 파일은 FormData에 추가
          //formData.append('files', file);
        formData.append('file', file);
        } //for end
        alert(`${this.files}`)
        // 업로드 로직 추가
        axios.post(`${this.backendUrl}/upboard/upboardAdd`, formData, {
        headers: { 'Content-Type': 'multipart/form-data' },
         }).then(() => {
          // 업로드가 성공하면 추가한 파일 목록을 서버에서 가져올 수 있도록 fetchFiles 메서드 호출
          this.fetchFiles();
        }).catch(error => {
          alert(error.message);
        });
      },
      fetchFiles() {
        
      }
    },
  };
  </script>
<style scoped>
.images img { width: 100px;}
</style>