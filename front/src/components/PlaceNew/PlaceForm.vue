<template>
  <div>
    <v-btn depressed
        color="blue"
        outlined
        id="v-btn"
      >
      <v-icon
        dark
        left
      >
        mdi-arrow-left
      </v-icon>
      <router-link class="blue--text" v-if="action=='CREATE'" id="router" :to="{ name: 'placeList' }">목록</router-link>
      <router-link class="blue--text" v-else id="router" :to="{ name: 'placeDetail', params: { placeSeq: placeSeq } }">BACK</router-link>
    </v-btn>
    
    <div class="placeform">
      <form @submit.prevent="onSubmit">
        <div>
          <label for="title">여행지명</label>
          <input class="inputbox" v-model="newPlace.name" type="text" id="title" required>
        </div>
        <div>
          <label for="title">주소</label>
          <input class="inputbox" v-model="newPlace.address" type="text" id="title" required>
        </div>
        <div>
          <label for="title">위도</label>
          <input class="inputbox" v-model="newPlace.latitude" type="text" id="title" required>
        </div>
        <div>
          <label for="title">경도</label>
          <input class="inputbox" v-model="newPlace.longitude" type="text" id="title" required>
        </div>
        <div>
          <label for="title">이미지경로</label>
          <input class="inputbox" v-model="newPlace.imageUrl" type="text" id="title" required>
        </div>
        <div>
          <label for="content">설명</label>
          <textarea class="inputbox" v-model="newPlace.description" type="text" id="content" required></textarea>
        </div>
        <div>
          <v-btn><button>{{ action }}</button></v-btn>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'placeForm',
  props: {
    place: Object,
    action: String,
  },
  data() {
    return {
      newPlace: {
        name: this.place.name,
        address: this.place.address,
        latitude: this.place.latitude,
        longitude: this.place.longitude,
        imageUrl: this.place.imageUrl,
        description: this.place.description,
      },
      placeSeq: this.place.placeSeq
    }
  },

  methods: {
    ...mapActions(['createPlace', 'updatePlace']),
    onSubmit() {
      if (this.action === 'CREATE') {
        this.createPlace(this.newPlace)
      } else if (this.action === 'UPDATE') {
        const payload = {
          pk: this.place.pk,
          ...this.newPlace,
        }
        this.updatePlace(payload)
      }
    },
  },
}
</script>

<style scoped>
.placeform {
  color: #545775;
  margin: 5vh;
}
.inputbox {
  color: #545775;
}
#title {
  width: 50vw;
  margin: 2vh;
}

#content {
  width: 80vw;
  height: 35vh;
  margin: 2vh;
}
.link {
  color: #545775
}
.back {
  text-align: left;
  margin-left: 5vh;
}
#router {
  text-decoration: none;
  color: black;
}
#v-btn {
  display: flex;
  margin-left: 40px;
  justify-content: flex-start;
}
.inputbox {
  width: 100px;
}
</style>
