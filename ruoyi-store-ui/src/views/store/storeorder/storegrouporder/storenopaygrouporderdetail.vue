<template>
  <orderdetailbase :url="queryOrderById"/>
</template>

<script>
  import orderdetailbase from '../../../order/OmsOrder/orderdetailbase'
  import {
    queryGroupOrderById
  } from '@/api/store/storenopaygrouporderdetail';

  export default {
    components: {orderdetailbase},
    data() {
      return {
        queryOrderById: queryGroupOrderById
      }
    }
  }
</script>
