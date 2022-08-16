<template>
  <v-app>
    <v-container class="d-flex">
      <side-bar></side-bar>
      <v-main>
        <router-view/>
      </v-main>
    </v-container>
    <!-- <nav-bar></nav-bar> -->
    <!-- <v-main>
      <router-view/>
    </v-main> -->
  </v-app>
</template>

<script>
// import NavBar from '@/components/NavBar.vue'
import SideBar from '@/components/SideBar.vue'

export default {
  name: 'App',
  components: { SideBar },

  data: () => ({
    //
  }),
};
</script>

<style>
  .body {
    margin: 5rem 10rem;
  }
  .link {
    text-decoration: none;
  }
</style>