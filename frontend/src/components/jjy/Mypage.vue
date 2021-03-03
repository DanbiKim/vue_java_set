<template>
  <div class="index page">
    <hr/>
    modal test<br/>
    <b-button variant="warning" @click="$bvModal.show('modal-test')">modal test</b-button>
    <!-- <b-button variant="warning" v-b-modal.modal-test>modal test</b-button> -->
    <hr/>
    List Test : 재용페이지3 {{msg}}
    <b-button variant="success" @click="getList">조회</b-button>
    <b-form-input placeholder="Enter your name"></b-form-input>
    <b-list-group>
      <b-list-group-item v-for="(item, index) in boardList" :class="{'active':index%2==0}" :key="item.idx">{{index + ':'+ item.title+'['+item.contents+']'}}</b-list-group-item>
    </b-list-group>
    <br/>
    icon test
    <hr/>
    <font-awesome-icon icon="user-secret" size="xs"/>
    <font-awesome-icon icon="user-secret" size="lg"/>
    <font-awesome-icon icon="user-secret" size="2x"/>
    <font-awesome-icon icon="user-secret" size="3x"/>
    <font-awesome-icon icon="user-secret" size="4x"/>
    <font-awesome-icon :icon="['fas', 'user-secret']" />
    <font-awesome-icon :icon="['fab', 'font-awesome']" />
    <font-awesome-layers full-width class="fa-4x">
      <font-awesome-icon icon="user-secret"/>
      <font-awesome-layers-text counter value="2" position="top-right" />
    </font-awesome-layers>
    {{test}}
    <example-modal></example-modal>
  </div>
</template>

<script>
import ExampleModal from '@/components/modal/ExampleModal.vue'
// 예제 설명: https://bootstrap-vue.org/docs/components/modal
export default {
  name: 'MyPage',
  components: {ExampleModal},
  data () {
    return {
      boardList: [],
      msg: 'Welcome to Your Vue.js App',
      test: ''
    }
  },
  mounted () {
    // this.doc_test()
  },
  created () {
    this.getList()
  },
  methods: {
    getList: function () {
      //  ddd
      this.$axios.get('/api/jjy/board/1')
        .then((res) => {
          // console.log(res.request.response)
          var resResult = JSON.parse(res.request.response)
          if (resResult.success) {
            // console.log(resResult.data.length)
            this.boardList = resResult.data
            // res_result.data.forEach(element => {
            //   console.log(element.title)
            // })
          }
        })
        .catch((error) => {
          console.log(error)
        })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.page {
  height: 100vh;
  font-weight: bold;
}
</style>
