<template>
  <b-modal
    id="modal-test"
    ref="modal-test2"
    title="BootstrapVue2"
    size="xl"
    centered
    scrollable
    modal-class=""
    dialog-class=""
    content-class=""
    body-class=""
    @shown="getList2"
  >
    <p class="my-2">This modal has large footer buttons</p>
    <b-list-group>
      <b-list-group-item v-for="(item, index) in boardList" :class="{'active':index%2==0}" :key="item.idx">{{index + ':'+ item.title+'['+item.contents+']'}}</b-list-group-item>
    </b-list-group>
  </b-modal>
</template>
<script>
export default {
  name: 'ExampleModal',
  data () {
    return {
      boardList: []
    }
  },

  beforeMount () {
  },

  mounted () {
    // 모달이 열렸는지 체크
    /* this.$root.$on('bv::modal::shown', (bvEvent, modalId) => {
      console.log('1111')
      this.getList2()
      console.log('Modal is about to be shown', bvEvent, modalId)
    })
    */
  },
  created () {
    // this.getList()
  },
  methods: {
    getList2: function () {
      console.log('getList2')
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
