<template>
  <div id="wrapper">
    <the-sidebar></the-sidebar>
    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column" style="background-color: transparent!important">

      <!-- Main Content -->
      <div id="content">
        <the-topbar />
        <slot />
      </div>
      <!-- End of Main Content -->
      <!-- Footer -->
      <the-footer />
      <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->
  </div>
</template>

<script>
import TheHeader from '../components/common/TheHeader'
import TheFooter from '../components/common/TheFooter'
import TheSidebar from '../components/common/TheSidebar'
import TheTopbar from '../components/common/TheTopbar'

export default {
  name: 'DefaultLayout',
  components: {
    TheSidebar,
    TheHeader,
    TheFooter,
    TheTopbar
  },
  data () {
    return {
      routeChk: false
    }
  },
  watch: {
    // 라우팅으로 움직이는 페이지인지 체크, 나중에 혹시 쓸일이 있을지 몰라서 소스만 남겨놨다.
    // 새로 고침이나 url 쳐서 들어온 것과, 라우팅으로 페이지 이동해서 들어온 것을 분기 시킬일이..생길까..?
    $route: function () {
      console.log('route move~')
      this.routeChk = true
    }
  }
}
</script>
