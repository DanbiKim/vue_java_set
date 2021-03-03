<template>
  <div>
    <b-navbar-nav class="bg-gradient-primary sidebar sidebar-dark accordion" tag="ul">
      <!-- Sidebar - Brand -->
      <a class="sidebar-brand d-flex align-items-center justify-content-center" href="index.html">
        <div class="sidebar-brand-icon rotate-n-15">
          <i class="fas fa-laugh-wink"></i>
        </div>
        <div class="sidebar-brand-text mx-3">SB Admin <sup>2</sup></div>
      </a>
      <!-- Divider -->
      <hr class="sidebar-divider my-0">

      <!-- Nav Item - Dashboard -->
      <b-nav-item to="/">
        <i class="fas fa-fw fa-tachometer-alt"></i>
        <span>Dashboard</span>
      </b-nav-item>
      <!-- Divider -->
      <hr class="sidebar-divider">

      <!-- Heading -->
      <li class="nav-item">
        <!-- Nav Item - Pages Collapse Menu -->
        <b-navbar-toggle target="navbar-toggle-collapse" class="nav-link" style="border:0!important;outline:0" data-toggle="collapse">
          <template v-slot:default="{ expanded }">
            <i class="fas fa-fw fa-cog"></i>
            <span>장재용</span>
          </template>
        </b-navbar-toggle>
        <b-collapse id="navbar-toggle-collapse" is-nav>
          <b-navbar-nav class="bg-white py-2 collapse-inner rounded" tag="div">
            <h6 class="collapse-header">Custom Components:</h6>
            <router-link class="collapse-item" :to="{name:'JjyMypage'}" active-class="active">MyPage</router-link>
            <router-link class="collapse-item" :to="{name:'JjyMypage2'}" active-class="active" exact>MyPage</router-link>
            <router-link class="collapse-item" :to="{name:'JjyTree'}" active-class="active" exact>Tree(x)</router-link>
            <router-link class="collapse-item" :to="{name:'JjyTree2'}" active-class="active" exact>Tree2(x)</router-link>
            <router-link class="collapse-item" :to="{name:'TreeTestLast'}" active-class="active" exact>TreeTestLast</router-link>
            <router-link class="collapse-item" :to="{name:'Menu'}" active-class="active" exact>Menu</router-link>
          </b-navbar-nav>
        </b-collapse>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider">

      <!-- Heading -->
      <div class="sidebar-heading">
        Interface
      </div>
      <li class="nav-item">
        <!-- Nav Item - Pages Collapse Menu -->
        <b-navbar-toggle target="navbar-toggle-collapse2" class="nav-link" style="border:0!important;outline:0" data-toggle="collapse">
          <template v-slot:default="{ expanded }">
            <i class="fas fa-fw fa-cog"></i>
            <span>Components</span>
          </template>
        </b-navbar-toggle>
        <b-collapse id="navbar-toggle-collapse2" is-nav>
          <b-navbar-nav class="bg-white py-2 collapse-inner rounded" tag="div">
            <h6 class="collapse-header">Custom Components:</h6>
            <a class="collapse-item" href="buttons.html" onclick="javascript:alert('1');return false;">Buttons</a>
            <a class="collapse-item" href="cards.html">Cards</a>
          </b-navbar-nav>
        </b-collapse>
      </li>
      <hr class="sidebar-divider">

      <li class="nav-item" v-for="item in menuList" :key="item.id">
        <a class="nav-link" :href="item.url" v-if="!item.children">
          <span>{{item.text}}</span>
        </a>
        <div v-else>
        <!-- Nav Item - Pages Collapse Menu -->
          <b-navbar-toggle v-bind:target="'navbar-toggle-collapse'+item.id" class="nav-link" style="border:0!important;outline:0" data-toggle="collapse">
            <template v-slot:default="{ expanded }">
              <!-- <i class="fas fa-fw fa-cog"></i> -->
              <span>{{item.text}}</span>
            </template>
          </b-navbar-toggle>
          <b-collapse v-bind:id="'navbar-toggle-collapse'+item.id" is-nav>
            <b-navbar-nav class="bg-white py-2 collapse-inner rounded" tag="div">
              <h6 class="collapse-header">{{item.text}}:</h6>
              <router-link :title="childrenItem.text" class="collapse-item cut-text" v-for="childrenItem in item.children" :key="childrenItem.id" :to="{name:childrenItem.page_id}" active-class="active">
                {{childrenItem.text}}
              </router-link>
            </b-navbar-nav>
          </b-collapse>
        </div>
      </li>
    </b-navbar-nav>
    <!-- End of Sidebar -->
  </div>
</template>

<script>

export default {
  name: 'TheSidebar',
  data () {
    return {
      active: 'active',
      menuList: []
    }
  },
  created () {
    this.getMenuList()
  },
  methods: {
    getMenuList () {
      this.$axios.get('/api/menu/listTree', {
        params: {
          use_yn: 'y'
        }
      })
        .then((res) => {
          var resResult = JSON.parse(res.request.response)
          if (resResult.success) {
            this.menuList = resResult.data
          }
        })
        .catch((error) => {
          console.log(error)
        })
    },
    // 현재 안씀
    sidebarToggle () {
      var body = document.querySelector('body')
      body.classList.toggle('sidebar-toggled')
      var sidebar = document.querySelector('.sidebar')
      sidebar.classList.toggle('toggled')
      return false
    },
    // 현재 안씀
    navLink (event) {
      // 부트스트랩 기능을 구현해봤다..하..고통
      // b-nav 로 언젠간 고치면 된다..이때는 이게 있는줄 모르고..
      var target1 = event.target.getAttribute('data-target')
      // 클릭이 a 태그로 하지 않고 i 태그나 span 태그일 경우가 있어서
      if (target1 === null) {
        target1 = event.target.parentElement.getAttribute('data-target')
      }

      if (target1) {
        target1 = target1.replace('#', '')
      }
      if (event.target.getAttribute('aria-expanded') === 'true') {
        event.target.setAttribute('aria-expanded', 'false')
      } else {
        event.target.setAttribute('aria-expanded', 'true')
      }
      event.target.classList.toggle('collapsed')
      document.getElementById(target1).classList.toggle('show')
      // obj.classList.toggle('collapsed')
      // console.log(targetId)
      return false
    }
  }
}
</script>

<style>

</style>
