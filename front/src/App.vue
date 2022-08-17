<template>
  <v-app>
    <v-container>
      <v-row>
        <side-bar v-if="isChecked"></side-bar>
        <v-main :class="{ 'body' : isChecked === true }">
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
    max-width: 70%;
    margin: 5px 300px 50px 300px;
    padding: 0 50px 0 0;
  }
  .link {
    text-decoration: none;
  }
  .v-main__wrap {
    max-width: 80%;
}
</style>