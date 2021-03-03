<template>
  <div id="app">
    <the-layout>
      <router-view />
    </the-layout>
    </div>
</template>

<script>
import TheLayout from './layouts/TheLayout'

export default {
  name: 'App',
  components: { TheLayout }
}
</script>

<style>
#app {
  @import url('https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i');
}
.sidebar {
  min-height: 100%
}
</style>
