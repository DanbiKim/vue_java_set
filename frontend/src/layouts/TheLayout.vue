<template>
    <component :is="layout">
        <slot />
    </component>
</template>

<script>
import DefaultLayout from './DefaultLayout'
import NoLayout from './NoLayout'

export default {
  name: 'TheLayout',
  components: {
    DefaultLayout,
    NoLayout
  },
  data () {
    return {
      layout: 'DefaultLayout'
    }
  },

  watch: {
    // 레이아웃을 변경하기 위한..
    $route: function () {
      // console.log(this.$route.name)
      // console.log(this.$route.meta.requiresAuth)
      // console.log(this.$route.meta.layout)
      if (this.$route.meta.layout) {
        this.layout = this.$route.meta.layout
        // console.log('layout:' + this.layout)
      }
    }
  }
}
</script>
