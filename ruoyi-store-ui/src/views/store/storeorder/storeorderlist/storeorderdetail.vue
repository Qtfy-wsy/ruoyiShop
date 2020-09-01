<template>
  <orderdetailbase :url="queryOrderById"/>
</template>

<script>
  import orderdetailbase from '../../../order/OmsOrder/orderdetailbase'
  import {
    queryOrderById
  } from '@/api/store/storeorderdetail';

  export default {
    components: {orderdetailbase},
    data() {
      return {
        queryOrderById: queryOrderById
      }
    }
  }
</script>
