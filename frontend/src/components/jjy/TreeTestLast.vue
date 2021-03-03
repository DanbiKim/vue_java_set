<template>
  <!-- Begin Page Content -->
  <div class='container-fluid'>

    <!-- Page Heading -->
    <h1 class='h3 mb-1 text-gray-800'>Color Utilities</h1>
    <p class='mb-4'>Bootstrap's default utility classes can be found on the official <a href='https://getbootstrap.com/docs'>Bootstrap Documentation</a> page. The custom utilities below were created to extend this theme past the default utility classes built into Bootstrap's framework.</p>

    <!-- Content Row -->
    <div class='row'>

      <!-- First Column -->
      <div class='col-lg-4'>

        <!-- Custom Text Color Utilities -->
        <div class='card shadow mb-4'>
          <div class='card-header py-3'>
            <h6 class='m-0 font-weight-bold text-primary'>선택</h6>
          </div>
          <div class='card-body'>
            <v-jstree :data='treeData'
                      size='large'
                      whole-row
                      collapse
                      draggable
                      @item-click='itemClick'>
              <template slot-scope='_'>
                <div style='display: inherit; width: 200px'>
                  <i :class='_.vm.themeIconClasses' role='presentation' v-if='!_.model.loading'></i>
                  <span v-if='_.model.use_yn === "n"' style='text-decoration: line-through'>
                    {{_.model.text}}
                  </span>
                  <span v-else>
                    {{_.model.text}}
                  </span>
                  <!--
                  <button style='border: 0px; background-color: transparent; cursor: pointer;' @click='customItemClick(_.vm, _.model, $event)'><i class='fa fa-plus'></i></button>
                  -->
                </div>
              </template>
            </v-jstree>
          </div>
        </div>

        <!-- Custom Font Size Utilities -->
        <div class='card shadow mb-4'>
          <div class='card-header py-3'>
            <h6 class='m-0 font-weight-bold text-primary'>Custom Font Size Utilities</h6>
          </div>
          <div class='card-body'>
            <p class='text-xs'>.text-xs</p>
            <p class='text-lg mb-0'>.text-lg</p>
          </div>
        </div>

      </div>

      <!-- Second Column -->
      <div class='col-lg-8 pl-1' v-if="editingNode!=null">
        <!-- Background Gradient Utilities -->
        <div class='card shadow mb-4'>
          <div class='card-header py-3'>
            <h6 class='m-0 font-weight-bold text-primary'>기준정보</h6>
          </div>
          <div class='card-body'>
            <div class='col-md-8 px-2'>
              <b-form-group label='상위 메뉴명' label-cols-lg='3' label-size='sm'>
                {{editingNode.p_menu_nm}}
              </b-form-group>
            </div>
            <div class='col-md-8 px-2'>
              <b-form-group label='메뉴 ID' label-cols-lg='3' label-size='sm'>
                <b-form-input
                  type='text'
                  id='s_menu_id'
                  placeholder='menu id'
                  v-model='editingNode.page_id'
                ></b-form-input>
              </b-form-group>
            </div>
            <div class='col-md-8 px-2'>
              <b-form-group label='메뉴명' label-cols-lg='3' label-size='sm'>
                <b-form-input
                  type='text'
                  id='s_menu_nm'
                  placeholder='menu name'
                  v-model='editingNode.menu_nm'
                ></b-form-input>
              </b-form-group>
            </div>
            <div class='col-md-8 px-2'>
              <b-form-group label='메뉴 URL' label-cols-lg='3' label-size='sm'>
                <b-form-input
                  type='text'
                  id='s_url'
                  placeholder='Url'
                  v-model='editingNode.url'
                ></b-form-input>
              </b-form-group>
            </div>
            <div class='col-md-8 px-2'>
              <b-form-group label='타겟' label-cols-lg='3' label-size='sm'>
                <b-form-select v-model="editingNode.target">
                  <b-form-select-option value="none">none</b-form-select-option>
                  <b-form-select-option value="blank">새창</b-form-select-option>
                </b-form-select>
              </b-form-group>
            </div>
            <div class='col-md-8 px-2'>
              <b-form-group label='사용유무' label-cols-lg='3' label-size='sm'>
                <b-form-select v-model="editingNode.use_yn">
                  <b-form-select-option value="y">사용</b-form-select-option>
                  <b-form-select-option value="n">미사용</b-form-select-option>
                </b-form-select>
              </b-form-group>
            </div>
            <div class='col-md-8 px-2'>
              <b-form-group label='메뉴설명' label-cols-lg='3' label-size='sm'>
                <b-form-input
                  type='text'
                  id='s_memo'
                  placeholder='메뉴설명'
                  v-model='editingNode.memo'
                ></b-form-input>
              </b-form-group>
            </div>
          </div>
        </div>
        <div class='card shadow mb-4'>
          <div class='card-header py-3'>
            <h6 class='m-0 font-weight-bold text-primary'>권한설정</h6>
          </div>
          <div class='card-body row'>
            <div class='col-md-12 px-2'>
              <double-select
                v-if="showDoubleSelect"
                :items="selectItems"
                :selectedItems="poolItems"
                :searchable="true"
                :searchable-placeholder="searchPlaceholder"
                :sorteable="true"
                :orderBy="'name'"
                text-field="name"
                value-field="id"
                v-on:selectedListModified="testEvent"
              ></double-select>
            </div>
          </div>
        </div>
        <div class="text-center">
          <a href="#" class="btn btn-success btn-icon-split" @click.prevent.stop="saveMenu">
                    <span class="icon text-white-50">
                      <i class="fas fa-check"></i>
                    </span>
            <span class="text">저장</span>
          </a>
          <a href="#" class="btn btn-danger btn-icon-split ml-3" @click="deleteMenu($event)">
                    <span class="icon text-white-50">
                      <i class="fas fa-trash"></i>
                    </span>
            <span class="text">삭제</span>
          </a>
        </div>
      </div>
      <div v-else>
        메뉴를 선택해주세요
      </div>

      <!-- Third Column -->
    </div>
  </div>
  <!-- /.container-fluid -->
</template>

<script>
// 멀티 셀렉트 참고하자
// https://github.com/viniciussvl/vue-double-select#readme

// 트리 구조 참고
// https://www.npmjs.com/package/vue-jstree-2
// https://github.com/zdy1988/vue-jstree
// 데모 : https://zdy1988.github.io/vue-jstree/
import VJstree from 'vue-jstree'
import doubleSelect from 'vue-double-select'

export default {
  name: 'test',
  components: {
    VJstree,
    doubleSelect
  },
  data () {
    return {
      editingItem: {},
      editingNode: null,
      treeData: [],
      searchPlaceholder: '검색',
      showDoubleSelect: false,
      selectItems: [],
      poolItems: []
    }
  },
  mounted () {
    // console.log(this.treeData)
  },
  created () {
    this.getMenuList()
  },
  methods: {
    testEvent (list) {
      console.log(this.poolItems)
      this.poolItems.forEach(function (element) {
        console.log(element.name)
      })
      console.log(list)
    },

    saveMenu () {
      // 클릭 단계에서 event를 멈추는 방식
      console.log(this.editingNode.menu_no)
      if (confirm('저장 하시겠습니까?')) {
        this.$axios.post('/api/menu/menuSave',
          this.editingNode)
          .then((res) => {
            var resResult = JSON.parse(res.request.response)
            if (resResult.success) {
              alert('저장하였습니다.')
              this.$router.go(this.$router.currentRoute)
            }
          })
          .catch((error) => {
            console.log(error)
          })
      }
    },

    deleteMenu (e) {
      // 스크립트에서 이벤트를 멈추는 방식
      console.log(this.editingNode.menu_no)
      if (confirm('해당 메뉴를 삭제하시겠습니까?')) {

      }

      e.preventDefault()
    },

    getMenuList () {
      this.$axios.get('/api/menu/listTree', {
        params: {
          use_yn: ''
        }
      })
        .then((res) => {
          var resResult = JSON.parse(res.request.response)
          if (resResult.success) {
          //  console.log(resResult.data)
            this.treeData = resResult.data
          }
        })
        .catch((error) => {
          console.log(error)
        })
    },

    getMenuInfo (menuNo) {
      this.$axios.get('/api/menu/menuInfo', {
        params: {
          menu_no: menuNo
        }
      })
        .then((res) => {
          var resResult = JSON.parse(res.request.response)
          if (resResult.success) {
          //  console.log(resResult.data)
            this.editingNode = resResult.data
            this.getMenuGroup(resResult.data.menu_no)
          }
        })
        .catch((error) => {
          console.log(error)
        })
    },

    getMenuGroup (menuNo) {
      // alert(menuNo)
      this.showDoubleSelect = false // 이걸 안보이게 했다고 밑에서 완성하고 보여줘야 제대로 그려진다
      this.$axios.get('/api/menu/menuGroupInfo', {
        params: {
          menu_no: menuNo
        }
      })
        .then((res) => {
          var resResult = JSON.parse(res.request.response)
          if (resResult.success) {
            // console.log(resResult.data)
            this.selectItems = resResult.data.allGroupInfo
            this.poolItems = resResult.data.menuGroupInfo
            this.showDoubleSelect = true
            // console.log(this.selectItems)
          }
        })
        .catch((error) => {
          console.log(error)
        })
    },

    itemClick: function (node) {
      this.editingItem = node.model
      // console.log('id: ' + node.model.id)
      this.getMenuInfo(node.model.id)
    },
    customItemClickWithCtrl: function () {
      console.log('1')
    }
  }
}
</script>
<!-- Add 'scoped' attribute to limit CSS to this component only -->
<style>
.invisible{visibility:hidden}.msl-search-list-input{margin-bottom:10px}.msl-searchable-list{display:flex;flex-direction:column}.msl-searchable-list__items{border:1px solid #5b5d66;flex-basis:100%;overflow-y:auto;overflow-x:hidden}.msl-searchable-list__no-item{font-size:.8em;padding:5px 10px;color:#c0c2c6}.msl-searchable-list__item{font-size:.9em;padding:5px 10px;cursor:pointer;border-bottom:1px solid #252b45}.msl-searchable-list__item:hover{background:#3858e7;color:#fff}.msl-multi-select{display:inline-flex;height:300px}.msl-multi-select__actions{display:flex;justify-content:center;flex-direction:column;align-items:center;padding:0 10px}.msl-multi-select__action{cursor:pointer}.msl-multi-select__action:hover{color:#c0c2c6}.msl-multi-select__action-select-all{margin-bottom:20px}.msl-multi-select__action-unselect-all{margin-top:20px}.msl-multi-select__action-icon{margin-top:17px;color:#919191}.msl-multi-select__list{width:100%}

.tree-default-large .tree-wholerow-clicked {
  background: #e1e1e1!important;
}

.tree-default-large .tree-wholerow-hovered {
  background: #eee!important;
}
.tree-wholerow-ul .tree-wholerow {
  z-index: 0!important;
}
</style>
