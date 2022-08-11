<template>
<div>
    <b-button v-b-toggle.sidebar-no-header>Sidebar</b-button>
    <b-sidebar id="sidebar-no-header" aria-labelledby="sidebar-no-header-title" no-header shadow>
      <template #default="{ hide }">
        <div class="p-3">
          <h4 id="sidebar-no-header-title">DaeRo 관리자</h4>
          <nav class="mb-3">
            <b-nav vertical>
              <b-nav-item
              v-for="(item, idx) in items" :key="idx"
              @click="hide">
              <router-link class="link" id="router" :to="{ name: item.link }">{{ item.title }}</router-link>
              </b-nav-item>
            </b-nav>
          </nav>
          <b-button variant="outline-primary" block @click="hide">Close Sidebar</b-button>
        </div>
      </template>
    </b-sidebar>
  </div>
</template>

<script>
export default {
    name: 'NavBar',
    data () {
      return {
        items: [
          { title: '게시글 관리', icon: '', link: 'articleList' },
          { title: '유저 관리', icon: '', link: 'userList' },
          { title: '여행지 관리', icon: '', link: 'placeList' },
          { title: '신고 관리', icon: '', link: 'reportList' },
          { title: '1:1 문의 관리', icon: '', link: 'inquiryList' },
        ],
      }
    },
  }
</script>

<style>

</style>