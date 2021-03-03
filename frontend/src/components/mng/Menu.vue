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
          <div class='card-header py-3 d-flex flex-row align-items-center justify-content-between'>
            <h6 class='m-0 font-weight-bold text-primary'>선택</h6>
            <div>
              <!--<a href="#" class="d-none d-sm-inline-block btn btn-sm btn-info shadow-sm"><i class="fas fa-save fa-sm text-white-50"></i> 순서저장</a>-->
              <a href="#" @click.prevent.stop="newMenu" class="d-none d-sm-inline-block btn btn-sm btn-success shadow-sm"><i class="fas fa-plus fa-sm text-white-50"></i> 등록</a>
            </div>
          </div>
          <div class='card-body'>
            <v-jstree :data='treeData'
                      size='large'
                      whole-row
                      collapse
                      @item-click='itemClick'
            >
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
      <div class='col-lg-8 pl-1' v-if="showNode">
        <!-- Background Gradient Utilities -->
        <div class='card shadow mb-4'>
          <div class='card-header py-3'>
            <h6 class='m-0 font-weight-bold text-primary'>기준정보</h6>
          </div>
          <div class='card-body'>
            <div class='col-md-8 px-2'>
              <b-form-group label='상위 메뉴명' label-cols-lg='3' label-size='sm'>
                <b-form-select v-model="editingNode.p_menu_no" id="s_p_menu_no">
                  <b-form-select-option value="" >선택</b-form-select-option>
                  <b-form-select-option value="0" >최상위 메뉴</b-form-select-option>
                  <b-form-select-option v-for="item in parentNode" :key="item.menu_no" :value="item.menu_no">
                    {{ item.menu_nm }}
                  </b-form-select-option>
                </b-form-select>
              </b-form-group>
            </div>
            <div class='col-md-8 px-2'>
              <b-form-group label='메뉴 ID' label-cols-lg='3' label-size='sm'>
                <b-input-group>
                  <b-form-input
                    type='text'
                    id='s_page_id'
                    placeholder='고유한 페이지 ID'
                    v-model='editingNode.page_id'
                  ></b-form-input>
                  <b-input-group-append>
                    <b-button variant="info" v-if="editingNode.page_id != originalPageId" @click.prevent.stop="duplicatePageIdChk">중복 확인</b-button>
                    <b-button variant="success" v-else disabled>사용 가능</b-button>
                  </b-input-group-append>
                </b-input-group>
              </b-form-group>
            </div>
            <div class='col-md-8 px-2'>
              <b-form-group label='메뉴명' label-cols-lg='3' label-size='sm'>
                <b-form-input
                  type='text'
                  id='s_menu_nm'
                  placeholder='메뉴명을 넣어주세요'
                  v-model='editingNode.menu_nm'
                ></b-form-input>
              </b-form-group>
            </div>
            <div class='col-md-8 px-2'>
              <b-form-group label='메뉴 URL' label-cols-lg='3' label-size='sm'>
                <b-input-group>
                  <b-form-input
                    type='text'
                    id='s_url'
                    placeholder='URL...'
                    v-model='editingNode.url'
                  ></b-form-input>
                  <b-input-group-append>
                    <b-button variant="info" v-if="editingNode.url != originalUrl" @click.prevent.stop="duplicateUrlChk">중복 확인</b-button>
                    <b-button variant="success" v-else disabled>사용 가능</b-button>
                  </b-input-group-append>
                </b-input-group>
              </b-form-group>
            </div>
            <div class='col-md-8 px-2'>
              <b-form-group label='타겟' label-cols-lg='3' label-size='sm'>
                <b-form-select v-model="editingNode.target" id='s_target'>
                  <b-form-select-option value="none">NONE</b-form-select-option>
                  <b-form-select-option value="blank">새창</b-form-select-option>
                </b-form-select>
              </b-form-group>
            </div>
            <div class='col-md-8 px-2'>
              <b-form-group label='사용유무' label-cols-lg='3' label-size='sm'>
                <b-form-select v-model="editingNode.use_yn" id='s_use_yn'>
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
                v-on:selectedListModified="doubleSelectEvent"
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
          <a href="#" class="btn btn-danger btn-icon-split ml-3" @click="deleteMenu($event)" v-if="editingNode.menu_no != 0">
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
  name: 'Menu',
  components: {
    VJstree,
    doubleSelect
  },
  data () {
    return {
      editingNode: {},
      showNode: false,
      parentNode: [],
      treeData: [],
      searchPlaceholder: '검색',
      showDoubleSelect: false,
      selectItems: [],
      poolItems: [],
      originalUrl: '',
      originalPageId: ''
    }
  },
  mounted () {
    // console.log(this.treeData)
  },
  created () {
    this.getMenuList()
  },
  methods: {
    doubleSelectEvent (list) {
      // console.log(this.poolItems)
      // this.poolItems.forEach(function (element) {
      //   console.log(element.name)
      // })
      // console.log(list)
    },
    duplicateUrlChk () {
      this.$axios.get('/api/menu/menuUrlDuplChk', {
        params: {
          url: this.editingNode.url,
          menu_no: this.editingNode.menu_no
        }
      })
        .then((res) => {
          var resResult = JSON.parse(res.request.response)
          if (resResult.success) {
            alert(resResult.message)
            this.originalUrl = this.editingNode.url
          } else {
            alert(resResult.message)
          }
        })
        .catch((error) => {
          console.log(error)
        })
    },
    duplicatePageIdChk () {
      this.$axios.get('/api/menu/menuPageIdDuplChk', {
        params: {
          page_id: this.editingNode.page_id,
          menu_no: this.editingNode.menu_no
        }
      })
        .then((res) => {
          var resResult = JSON.parse(res.request.response)
          if (resResult.success) {
            alert(resResult.message)
            this.originalPageId = this.editingNode.page_id
          } else {
            alert(resResult.message)
          }
        })
        .catch((error) => {
          console.log(error)
        })
    },
    newMenu () {
      this.editingNode = {}
      this.editingNode.menu_no = 0
      this.editingNode.p_menu_no = ''
      this.originalPageId = ''
      this.originalUrl = ''
      this.editingNode.target = 'none'
      this.editingNode.use_yn = 'y'
      this.getMenuParentInfo(0)
    },
    saveMenu () {
      // 클릭 단계에서 event를 멈추는 방식
      // console.log(this.editingNode)
      if (!this.validation()) return false
      if (confirm('저장 하시겠습니까?')) {
        // var params = new URLSearchParams();
        // params.append('menuModel', this.editingNode);
        // params.append('menuGroup', this.poolItems);
        this.editingNode.auth_group = this.poolItems
        console.log(this.editingNode.auth_group)
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
      // console.log(this.editingNode.menu_no)
      if (confirm('해당 메뉴를 삭제하시겠습니까?')) {
        this.$axios.post('/api/menu/menuDelete',
          this.editingNode)
          .then((res) => {
            var resResult = JSON.parse(res.request.response)
            if (resResult.success) {
              alert('삭제하였습니다.')
              this.$router.go(this.$router.currentRoute)
            }
          })
          .catch((error) => {
            alert('알수 없는 오류가 발생하였습니다.(msg:' + error + ')')
            console.log(error)
          })
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
          alert('알수 없는 오류가 발생하였습니다.(msg:' + error + ')')
          console.log(error)
        })
    },
    getMenuParentInfo (menuNo) {
      this.$axios.get('/api/menu/menuParentInfo', {
        params: {
          menu_no: menuNo
        }
      })
        .then((res) => {
          var resResult = JSON.parse(res.request.response)
          if (resResult.success) {
            this.parentNode = resResult.data
            if (menuNo !== 0) {
              this.getMenuInfo(menuNo)
            }
            this.getMenuGroup(menuNo)
          }
        })
        .catch((error) => {
          alert('알수 없는 오류가 발생하였습니다.(msg:' + error + ')')
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
            this.originalUrl = resResult.data.url
            this.originalPageId = resResult.data.page_id
            // 스크롤 맨 위로 올리기
            window.scrollTo({top: 0, left: 0, behavior: 'auto'})
          }
        })
        .catch((error) => {
          alert('알수 없는 오류가 발생하였습니다.(msg:' + error + ')')
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
            this.showNode = true
            // console.log(this.selectItems)
          }
        })
        .catch((error) => {
          alert('알수 없는 오류가 발생하였습니다.(msg:' + error + ')')
          console.log(error)
        })
    },

    validation () {
      if (this.editingNode.p_menu_no === '') {
        alert('상위 메뉴명을 선택해주세요')
        $('#s_p_menu_no').focus()
        return false
      } else if (typeof this.editingNode.page_id === 'undefined' || this.editingNode.page_id === '') {
        alert('메뉴 ID를 입력해주세요')
        $('#s_page_id').focus()
        return false
      } else if (this.editingNode.page_id !== this.originalPageId) {
        alert('메뉴 ID의 중복검사를 해주세요')
        $('#s_page_id').focus()
        return false
      } else if (typeof this.editingNode.menu_nm === 'undefined' || this.editingNode.menu_nm === '') {
        alert('메뉴명을 입력해주세요')
        $('#s_menu_nm').focus()
        return false
      } else if (typeof this.editingNode.url === 'undefined' || this.editingNode.url === '') {
        alert('메뉴 URL을 입력해주세요')
        $('#s_url').focus()
        return false
      } else if (this.editingNode.url !== this.originalUrl) {
        alert('URL 중복 검사를 해주세요')
        $('#s_url').focus()
        return false
      }
      return true
    },

    itemClick: function (node) {
      // console.log('id: ' + node.model.id)
      this.getMenuParentInfo(node.model.id)
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
