<template>
  <printordersbase :url="queryOrders"/>
</template>

<script>
  import {
    queryOrders
  } from '@/api/order/printorders';

  import printordersbase from './printordersbase'

  export default {
    components: {printordersbase},
    data() {
      return {
        queryOrders: queryOrders
      }
    },
  }
</script>
