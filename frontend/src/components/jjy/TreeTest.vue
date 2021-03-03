<template>
  <!-- Begin Page Content -->
  <div class="container-fluid">

    <!-- Page Heading -->
    <h1 class="h3 mb-2 text-gray-800">Tables</h1>
    <p class="mb-4">DataTables is a third party plugin that is used to generate the demo table below. For more information about DataTables, please visit the <a target="_blank" href="https://datatables.net">official DataTables documentation</a>.</p>
    <b-card-group deck>
      <b-card
        border-variant="primary"
        header="Tree Example"
        header-bg-variant="primary"
        header-text-variant="white"
        align="center"
      >
        <b-card-body class="text-center">
          <b-card-title>선택</b-card-title>

          <b-card-text>
            <!--
              data : 트리 데이타 array
              nodeKeyProp : key로 쓸 명칭 -> default : id
              nodeChildrenProp : 자식key로 쓸 명칭  -> default : children
              nodeLabelProp : label로 쓸 명칭  -> default : name
              showIcons : icon을 사용 할지  -> default : false
              iconClassProp : icon으로 쓸 명칭 -> default : icon
              defaultIconClass : 기본 icon class  -> default : null
              prependIconClass : icon class에 앞에 미리 들어갈 class 정의  -> default : null
              nodesDraggable :  drag&drop 가능 여부-> default : false
              contextMenu : 마우스 오른쪽 클릭 메뉴 사용 여부 -> default : true
              renameNodeOnDblClick : 마우스 더블 클릭했을 시 이름 변경 사용 여부 -> default : true
              contextMenuItems : contextMenu와 이어지는, 오른쪽 클릭했을 떄 나오는 값들 array
              -> default : [ { code: 'DELETE_NODE', label: 'Delete node' }, { code: 'RENAME_NODE', label: 'Rename node' } ]
              EVENT
              nodeSelect : 트리 선택 했을 시 이벤트
              contextMenuItemSelect : 오른쪽 클릭했을 시 나오는 메뉴를 클릭 했을 떄 이벤트
            -->
            <b-tree-view :data="treeData"
                         :showIcons=true
                         :renameNodeOnDblClick=false
                         :contextMenuItems="contextMenuItems"
                         :nodesDraggable="true"
                         :contextMenu="true"
                         iconClassProp="icon"
                         prependIconClass="fa"
                         @contextMenuItemSelect="menuItemSelected"
                         @nodeSelect="nodeSelect"
            >
            </b-tree-view>
          </b-card-text>
        </b-card-body>
      </b-card>

      <b-card
        border-variant="warning"
        header="표시"
        header-bg-variant="warning"
        header-border-variant="warning"
        align="center"
      >
        <b-card-body class="text-center" v-if="!selectedNodeChk">
          <b-card-text>선택해주세요</b-card-text>
        </b-card-body>
        <b-card-body class="text-center" v-if="selectedNodeChk">
          <b-card-text>
            Node {{ selectedNode.name }} is selected
          </b-card-text>
        </b-card-body>
      </b-card>
    </b-card-group>
  </div>
  <!-- /.container-fluid -->
</template>

<script>
// https://github.com/kamil-lip/bootstrap-vue-treeview
import { bTreeView } from 'bootstrap-vue-treeview'

export default {
  name: 'MyPage2',
  components: {
    bTreeView
  },
  data () {
    return {
      treeData: [
        {
          'id': 2, 'name': 'Venus1', 'icon': 'fa-folder', 'children': [{'id': 22, 'name': 'Neptune1'}, {'id': 222, 'name': 'Stratus1'}]
        },
        {
          'id': 3, 'name': 'Venus2', 'children': [{'id': 33, 'name': 'Neptune2'}, {'id': 333, 'name': 'Stratus2'}]
        },
        {
          'id': 4, 'name': 'Venus3', 'children': [{'id': 44, 'name': 'Neptune3'}, {'id': 444, 'name': 'Stratus3'}]
        }
      ],
      contextMenuItems: [
        { code: 'DELETE_NODE', label: 'Delete node' },
        { code: 'RENAME_NODE', label: 'Rename node' },
        { code: 'test1', label: 'test1' },
        { code: 'test2', label: 'test2' }
      ],
      selectedNode: null,
      selectedNodeChk: false
    }
  },
  mounted () {
  },
  created () {
  },
  methods: {
    nodeSelect: function (node, isSelected) {
      console.log('Node ' + node.data.name + ' has been ' + (isSelected ? 'selected' : 'deselected'))
      if (isSelected) {
        this.selectedNode = node.data
        this.selectedNodeChk = true
      } else if (node.data === this.selectedNode) {
        this.selectedNode = null
        this.selectedNodeChk = false
      }
    },
    menuItemSelected: function (item, node) {
      console.log('~~~~~~')
      if (item.code === 'test1') {
        console.log('~~1')
      } else if (item.code === 'test2') {
        console.log('~~2')
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

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.page {
  height: 100vh;
  font-weight: bold;
}
</style>
