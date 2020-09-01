<template>
  <orderdetailbase :url="queryOrderById"/>
</template>

<script>
  import orderdetailbase from '../../../order/OmsOrder/orderdetailbase'
  import {
    queryGroupOrderById
  } from '@/api/store/storegrouporderdetail';

  export default {
    components: {orderdetailbase},
    data() {
      return {
        queryOrderById: queryGroupOrderById
      }
    }
  }
</script>
