<template>
    <!-- 이제 이 페이지는 안씀. 초창기 테스트 페이지 -->
    <b-navbar type="dark" variant="dark" class="nav">
      <b-navbar-nav>
        <b-nav-item to="/" >Home</b-nav-item>
      </b-navbar-nav>
      <b-dropdown id="dropdown-1" text="재용" class="m-md-2">
        <b-dropdown-item :to="{ name: 'JjyMypage'}" v-on:click="menuHighlight('jjyMypage')" id="jjyMypage">List Test</b-dropdown-item>
        <b-dropdown-item v-on:click="menuHighlight('a2')" id="a2">Second Action</b-dropdown-item>
        <b-dropdown-item v-on:click="menuHighlight('a3')" id="a3">Third Action</b-dropdown-item>
        <b-dropdown-divider></b-dropdown-divider>
        <b-dropdown-item>Active action</b-dropdown-item>
      </b-dropdown>
      <b-navbar-nav>
        <!--<b-nav-item to="/router/jjy/mypage" >재용</b-nav-item>-->
        <b-nav-item :to="{ name: 'JjyMypage'}" >재용</b-nav-item>

      </b-navbar-nav>
      <b-navbar-nav>
        <b-nav-item href="/router/jjy/mypage" >재용2</b-nav-item>
        <b-nav-item to="/" >이예나</b-nav-item>
      </b-navbar-nav>
      <b-navbar-nav>
        <b-nav-item to="/" >김예나</b-nav-item>
      </b-navbar-nav>
      <b-navbar-nav>
        <b-nav-item to="/" >이상슨</b-nav-item>
      </b-navbar-nav>
      <b-navbar-nav class="ml-auto">
        <b-nav-form>
          <b-form-input size="sm" class="mr-sm-2" placeholder="Search"></b-form-input>
          <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>
        </b-nav-form>
        <b-nav-item-dropdown text="Lang" right>
          <b-dropdown-item href="#">한국어</b-dropdown-item>
          <b-dropdown-item href="#">English</b-dropdown-item>
        </b-nav-item-dropdown>

        <b-nav-item-dropdown text="User" right>
          <b-dropdown-item href="#">Account</b-dropdown-item>
          <b-dropdown-item href="#">Settings</b-dropdown-item>
          <b-dropdown-item href="/user/logout">Log Out</b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-navbar>
</template>

<script>

export default {
  name: 'TheHeader',
  data () {
    return {
      active: 'active'
    }
  },
  methods: {
    menuHighlight (id) {
      console.log(id)
      // 전체 active 없애기
      var activeClassList = document.querySelectorAll('.dropdown-menu a.active')
      activeClassList.forEach(function (obj) {
        obj.classList.remove('active')
      })
      // 클릭한 해당만 active
      document.getElementById(id).classList.add('active')
    }
  }
}
</script>
