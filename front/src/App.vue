<template>
  <v-app>
    <v-container class="d-flex">
      <side-bar v-if="isChecked"></side-bar>
      <v-main>
        <router-view/>
      </v-main>
    </v-container>
  </v-app>
</template>

<script>
import SideBar from '@/components/SideBar.vue'

export default {
  name: 'App',
  components: { SideBar },
  data: () => ({
    isChecked: true
  }),
  mounted() {
    switch(document.location.pathname){
      case '/':
        this.isChecked = false;
        break;
    }   
  }
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