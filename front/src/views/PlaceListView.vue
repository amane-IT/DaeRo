<template>
  <div class="body">
    <h2>여행지</h2>
    <br>
    <div>
    <b-table id="placetable" small :fields="fields" :items="placeList" responsive="sm">

      <template #cell(index)="data">
        {{ data.item.trip_place_seq }}
      </template>

      <template #cell(name)="data">
        <b><router-link class="link" id="router" :to="{ name: 'placeDetail', params: { placeSeq: data.item.trip_place_seq } }">{{ data.item.place_name }}</router-link></b>
      </template>

      <template #cell(address)="data">
        {{ data.item.address }}
      </template>

    </b-table>
    </div>

		<v-pagination
			v-model="page"
			@input="handlePage"
			:length="totalPage"
		></v-pagination>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'placeListView',
  data() {
    return {
      page: 1,
      fields: [
          { key: 'index', label: 'Index'},
          { key: 'name', label: '여행지명' },
          { key: 'address', label: '주소' }
        ],
      }
	},
	computed: {
    ...mapGetters(['placeList', 'totalPage', 'currentPage'])
	},
	methods: {
    ...mapActions(['getPlaceList']),
    handlePage() {
      this.getPlaceList(this.page)
    }
	},
	created() {
    this.getPlaceList(this.page)
	}

}
</script>

<style>

</style>