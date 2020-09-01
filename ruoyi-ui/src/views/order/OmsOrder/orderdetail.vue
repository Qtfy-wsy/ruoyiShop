<template>
    <orderdetailbase :url="queryOrderById"/>
</template>

<script>
  import orderdetailbase from './orderdetailbase'
  import {
    queryOrderById
  } from '@/api/order/orderdetail';

  export default {
    components: {orderdetailbase},
    data() {
      return {
        queryOrderById: queryOrderById
      }
    }
  }
</script>
