<template>
  <v-app>
    <v-container>
      <v-row>
        <side-bar v-if="isChecked"></side-bar>
        <v-main class="body">
          <router-view/>
        </v-main>
      </v-row>

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
    margin: 5px 300px 5px 300px;
  }
  .link {
    text-decoration: none;
  }
</style>