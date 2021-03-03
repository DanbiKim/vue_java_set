<template>
  <!-- Begin Page Content -->
  <div class="container-fluid">

    <!-- Page Heading -->
    <h1 class="h3 mb-2 text-gray-800">Tables</h1>
    <p class="mb-4">DataTables is a third party plugin that is used to generate the demo table below. For more information about DataTables, please visit the <a target="_blank" href="https://datatables.net">official DataTables documentation</a>.</p>

    <!-- DataTales Example -->
    <div class="card shadow mb-4">
      <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">DataTables Example</h6>
      </div>
      <div class="card-body">
        <b-form-group label="Table Options" label-cols-lg="2">
          <b-form-checkbox v-model="striped" inline>Striped</b-form-checkbox>
          <b-form-checkbox v-model="bordered" inline>Bordered</b-form-checkbox>
          <b-form-checkbox v-model="borderless" inline>Borderless</b-form-checkbox>
          <b-form-checkbox v-model="outlined" inline>Outlined</b-form-checkbox>
          <b-form-checkbox v-model="small" inline>Small</b-form-checkbox>
          <b-form-checkbox v-model="hover" inline>Hover</b-form-checkbox>
          <b-form-checkbox v-model="dark" inline>Dark</b-form-checkbox>
          <b-form-checkbox v-model="fixed" inline>Fixed</b-form-checkbox>
          <b-form-checkbox v-model="footClone" inline>Foot Clone</b-form-checkbox>
          <b-form-checkbox v-model="noCollapse" inline>No border collapse</b-form-checkbox>
          <b-form-checkbox v-model="stacked" inline>Stacked</b-form-checkbox>
        </b-form-group>
        <b-form-group label="Head Variant" label-cols-lg="2">
          <b-form-radio-group v-model="headVariant" class="mt-lg-2">
            <b-form-radio :value="null" inline>None</b-form-radio>
            <b-form-radio value="light" inline>Light</b-form-radio>
            <b-form-radio value="dark" inline>Dark</b-form-radio>
          </b-form-radio-group>
        </b-form-group>
        <b-form-group label="Table Variant" label-for="table-style-variant" label-cols-lg="2">
          <b-form-select
            v-model="tableVariant"
            :options="tableVariants"
            id="table-style-variant"
          >
            <template v-slot:first>
              <option value="">-- None --</option>
            </template>
          </b-form-select>
        </b-form-group>
        <b-form-group
          label="Filter"
          label-cols-lg="2"
          label-align-sm="right"
          label-size="sm"
          class="mb-0"
        >
          <b-input-group size="sm">
            <b-form-input
              v-model="filter"
              type="search"
              id="filterInput"
              placeholder="Type to Search"
            ></b-form-input>
            <b-input-group-append>
              <b-button :disabled="!filter" @click="filter = ''">Clear</b-button>
            </b-input-group-append>
          </b-input-group>
        </b-form-group>

        <b-form-group
          label="Filter On"
          label-cols-sm="3"
          label-align-sm="right"
          label-size="sm"
          description="Leave all unchecked to filter on all data"
          class="mb-0">
          <b-form-checkbox-group v-model="filterOn" class="mt-1">
            <b-form-checkbox value="first_name">First Name</b-form-checkbox>
            <b-form-checkbox value="last_name">Last Name</b-form-checkbox>
            <b-form-checkbox value="age">Age</b-form-checkbox>
          </b-form-checkbox-group>
        </b-form-group>
        <b-form-group
          label="Per page"
          label-cols-sm="6"
          label-cols-md="4"
          label-cols-lg="3"
          label-align-sm="right"
          label-size="sm"
          label-for="perPageSelect"
          class="mb-0"
        >
          <b-form-select
            v-model="perPage"
            id="perPageSelect"
            size="sm"
            :options="pageOptions"
          ></b-form-select>
        </b-form-group>

        <div class="table-responsive">
          <b-button @click="toggleBusy">Toggle Busy State</b-button>
          <b-table
            :striped="striped"
            :bordered="bordered"
            :borderless="borderless"
            :outlined="outlined"
            :small="small"
            :hover="hover"
            :dark="dark"
            :fixed="fixed"
            :foot-clone="footClone"
            :no-border-collapse="noCollapse"
            :head-variant="headVariant"
            :table-variant="tableVariant"
            :stacked="stacked"
            :current-page="currentPage"
            :per-page="perPage"
            :filter="filter"
            :filter-included-fields="filterOn"
            :sort-by.sync="sortBy"
            :sort-desc.sync="sortDesc"
            :sort-direction="sortDirection"
            @filtered="onFiltered"
            :items="items" :busy="isBusy" :fields="fields" class="mt-3" responsive caption-top>
            <template v-slot:table-busy>
              <div class="text-center text-danger my-2">
                <b-spinner class="align-middle"></b-spinner>
                <strong>Loading...</strong>
              </div>
            </template>
            <template v-slot:table-caption>This is a table caption.</template>
          </b-table>
          <b-pagination
            v-model="currentPage"
            :total-rows="totalRows"
            :per-page="perPage"
            align="fill"
            size="sm"
            class="my-0"
          ></b-pagination>
        </div>
      </div>
    </div>

  </div>
  <!-- /.container-fluid -->
</template>

<script>
export default {
  name: 'MyPage2',
  data () {
    return {
      boardList: [],
      msg: 'Welcome to Your Vue.js App',
      test: '',
      isBusy: false,
      fields: [
        {
          key: 'first_name',
          sortable: true,
          sortDirection: 'desc',
          formatter: value => {
            return value.toUpperCase()
          }
        },
        {
          key: 'last_name',
          sortable: false
        },
        {
          key: 'age',
          sortable: false,
          variant: 'primary',
          class: 'text-center'
        }
      ],
      items: [
        { first_name: 'dickerson', last_name: 'MacDonald', age: 40, isActive: true },
        { first_name: 'larsen', last_name: 'Shaw', age: 21, isActive: false },
        { first_name: 'Geneva', last_name: 'Wilson', age: 89, isActive: true },
        { first_name: 'Jami', last_name: 'Carney', age: 38, isActive: false }
      ],
      tableVariants: [
        'primary',
        'secondary',
        'info',
        'danger',
        'warning',
        'success',
        'light',
        'dark'
      ],
      striped: false,
      bordered: false,
      borderless: false,
      outlined: false,
      small: false,
      hover: false,
      dark: false,
      fixed: false,
      footClone: false,
      headVariant: null,
      tableVariant: '',
      noCollapse: false,
      stacked: false,
      totalRows: 1,
      currentPage: 1,
      perPage: 5,
      pageOptions: [3, 5, 10, 15],
      sortBy: '',
      sortDesc: false,
      sortDirection: 'asc',
      filter: null,
      filterOn: []
    }
  },
  mounted () {
    // axios 반응이 느려서, getList후에 다시한번 아래 줄을 또 넣었다. 사실상 여기는 지워도 되지만 놔뒀다.
    this.totalRows = this.items.length
  },
  created () {
    this.getList()
  },
  methods: {
    onFiltered (filteredItems) {
      // Trigger pagination to update the number of buttons/pages due to filtering
      this.totalRows = filteredItems.length
      this.currentPage = 1
    },
    toggleBusy () {
      this.isBusy = !this.isBusy
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
