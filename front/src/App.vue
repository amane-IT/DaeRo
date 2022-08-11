<template>
  <v-app>
    <nav-bar></nav-bar>
    <v-main class="body">
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
import NavBar from '@/components/NavBar.vue'

export default {
  name: 'App',
  components: { NavBar },

  data: () => ({
    //
  }),
};
</script>

<style>
  .body {
    margin: 5rem 17rem;
  }
</style>