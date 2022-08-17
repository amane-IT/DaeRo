<template>
  <div class="sideBar">
    <h5>DaeRo 관리자</h5>
    <br>
    <ul>
      <li><router-link class="link" id="router" :to="{ name: 'articleList' }">게시글 관리</router-link></li>
      <li><router-link class="link" id="router" :to="{ name: 'userList' }">유저 관리</router-link></li>
      <li><router-link class="link" id="router" :to="{ name: 'placeList' }">여행지 관리</router-link></li>
      <li><router-link class="link" id="router" :to="{ name: 'reportList' }">신고 관리</router-link></li>

      <li><router-link class="link" id="router" :to="{ name: 'inquiryList' }">1:1 문의</router-link></li>
      <li><router-link class="link" id="router" :to="{ name: 'noticeList' }">공지사항</router-link></li>
      <li><router-link class="link" id="router" :to="{ name: 'faqList' }">FAQ</router-link></li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'sideBar'
}
</script>

<style scoped>
.sideBar {
  margin: 100px 0 0 0;
}
ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  width: 15%;
  /* background-color: #eff4f8; */
  position: fixed;
  height: 100%;
  overflow: auto;
}

li a {
  display: block;
  color: #000;
  padding: 8px 16px;
  text-decoration: none;
}

li a.active {
  background-color: #a7d4ff;
  color: white;
}

li a:hover:not(.active) {
  background-color: #a7d4ff;
  color: white;
}
</style>