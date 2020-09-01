<template>
  <printordersbase :url="queryPrintStoreOrderDetails"/>
</template>

<script>
  import {
    queryPrintStoreOrderDetails
  } from '@/api/store/printstoreorders';

  import printordersbase from '../../../order/OmsOrder/printordersbase'

  export default {
    components: {printordersbase},
    data() {
      return {
        queryPrintStoreOrderDetails: queryPrintStoreOrderDetails
      }
    },
  }
</script>
