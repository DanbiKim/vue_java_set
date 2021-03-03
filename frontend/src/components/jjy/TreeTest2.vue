<template>
  <!-- Begin Page Content -->
  <div class='container-fluid'>

    <!-- Page Heading -->
    <h1 class='h3 mb-2 text-gray-800'>Tables</h1>
    <p class='mb-4'>DataTables is a third party plugin that is used to generate the demo table below. For more information about DataTables, please visit the <a target='_blank' href='https://datatables.net'>official DataTables documentation</a>.</p>
    <div>
      <b-row>
        <b-col col md="4" >
          부서
          <b-button @click="addChildNode">추가</b-button>
          <b-button @click="refreshNode">새로고침</b-button>
          <b-button @click="saveNode">순서저장</b-button>
          <hr>
          <v-jstree :data='treeData'
                    size="large"
                    allow-batch
                    whole-row
                    collapse
                    draggable
                    @item-click='itemClick'></v-jstree>
        </b-col>
        <b-col col md="2">
        </b-col>
        <b-col col md="6" style="background-color:red">
        </b-col>
      </b-row>
    </div>

    <b-card-group deck>
      <b-card
        border-variant='primary'
        header='Tree Example'
        header-bg-variant='primary'
        bg-variant="none"
        header-text-variant='white'
        align='center'
      >
        <b-card-body class='text-center'>
          <b-card-title id='test'>선택</b-card-title>
          <b-button @click="addChildNode">추가</b-button>
          <b-button @click="removeNode">삭제</b-button>
          <b-card-text>
            <v-jstree :data='treeData'
                      show-checkbox
                      multiple
                      allow-batch
                      whole-row
                      collapse
                      draggable
                      @item-click='itemClick'></v-jstree>
          </b-card-text>
        </b-card-body>
      </b-card>

      <b-card
        border-variant='warning'
        header='표시'
        header-bg-variant='warning'
        header-border-variant='warning'
        align='center'
      >
     <!--   <b-card-body class='text-center' v-if='!selected'>
          <b-card-text>선택해주세요</b-card-text>
        </b-card-body>
        <b-card-body class='text-center' v-if='selected'>
          <b-card-text>
            Node {{ selected }} is selected
          </b-card-text>
        </b-card-body>-->
      </b-card>
    </b-card-group>
  </div>
  <!-- /.container-fluid -->
</template>

<script>
// https://www.npmjs.com/package/vue-jstree-2
// https://github.com/zdy1988/vue-jstree
// 데모 : https://zdy1988.github.io/vue-jstree/
import VJstree from 'vue-jstree'
export default {
  name: 'MyPage2',
  components: {
    VJstree
  },
  data () {
    return {
      searchword: '',
      editingItem: {},
      editingNode: null,
      treeData: [
        {
          'id': 1,
          'text': 'Same but with checkboxes',
          'value': 'value1',
          'children': [
            {
              'text': 'initially selected',
              'selected': true
            },
            {
              'text': 'custom icon',
              'icon': 'fa fa-warning icon-state-danger'
            },
            {
              'text': 'initially open',
              'icon': 'fa fa-folder icon-state-default',
              'opened': true,
              'children': [
                {
                  'text': 'Another node'
                }
              ]
            },
            {
              'text': 'custom icon',
              'icon': 'fa fa-warning icon-state-warning'
            },
            {
              'text': 'disabled node',
              'icon': 'fa fa-check icon-state-success',
              'disabled': true
            }
          ]
        },
        {
          'text': 'Same but with checkboxes',
          'opened': true,
          'children': [
            {
              'text': 'initially selected',
              'selected': true
            },
            {
              'text': 'custom icon',
              'icon': 'fa fa-warning icon-state-danger'
            },
            {
              'text': 'initially open',
              'icon': 'fa fa-folder icon-state-default',
              'opened': true,
              'children': [
                {
                  'text': 'Another node'
                }
              ]
            },
            {
              'text': 'custom icon',
              'icon': 'fa fa-warning icon-state-warning'
            },
            {
              'text': 'disabled node',
              'icon': 'fa fa-check icon-state-success',
              'disabled': true
            }
          ]
        },
        {
          'text': 'And wholerow selection'
        }
      ]
    }
  },
  mounted () {
  },
  created () {
  },
  methods: {
    itemClick: function (node) {
      alert(node.model.id)
      this.editingNode = node
      this.editingItem = node.model
      console.log('node:' + node.model.text)
    },
    addChildNode: function () {
      if (this.editingItem.id !== undefined) {
        this.editingItem.addChild({
          text: '새이름',
          value: '새이름'
        })
      }
    },
    removeNode: function () {
      if (this.editingItem.id !== undefined) {
        var index = this.editingNode.parentItem.indexOf(this.editingItem)
        this.editingNode.parentItem.splice(index, 1)
      }
    },
    getList: function () {
      this.$axios.get('/api/jjy/board/1')
        .then((res) => {
          console.log(res.request.response)
          var resResult = JSON.parse(res.request.response)
          if (resResult.success) {
            console.log(resResult.data.length)
            this.boardList = resResult.data
            // this.items = [] // 초기화
            resResult.data.forEach(element => {
              console.log(element.title)
              this.items.push({
                first_name: element.title,
                last_name: element.idx,
                age: element.contents
              })
            })
            this.totalRows = this.items.length
          }
        })
        .catch((error) => {
          console.log(error)
        })
    }
  }
}
</script>

<!-- Add 'scoped' attribute to limit CSS to this component only -->
<style scoped>
.tree-default-large .tree-wholerow-clicked {
  background: #e1e1e1!important;
}
.page {
  height: 100vh;
  font-weight: bold;
}
</style>
