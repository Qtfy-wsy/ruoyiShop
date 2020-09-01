<template>
  <div class="app-container">
    <div id="print_box">
      <div v-for="order in orders">
        <div style="line-height: 50px">订单编号：{{order.orderCode}}</div>
        <div class="detailsTable">
          <el-row>
            <el-col :span="8">买家：{{order.customerName}}</el-col>
            <el-col :span="8">支付方式：{{payType[order.payType]}}</el-col>
            <el-col :span="8">下单时间：{{order.createTime}}</el-col>
          </el-row>
          <el-row>
            <el-col :span="8">运费：{{order.freightPrice.toFixed(2)}}</el-col>
            <el-col :span="8">订单原始金额：{{order.originalPrice.toFixed(2)}}</el-col>
            <el-col :span="8">订单优惠总金额：{{order.concessionalRate.toFixed(2)}}</el-col>
          </el-row>
          <el-row>
            <el-col :span="8">订单交易金额：{{order.price.toFixed(2)}}</el-col>
            <el-col :span="8">收货人：{{order.orderAttr.receiptName}}</el-col>
            <el-col :span="8">手机：{{order.orderAttr.receiptMobile}}</el-col>
          </el-row>
          <el-row>
            <el-col :span="8">收货地址：{{order.orderAttr.receiptAddress}}</el-col>
            <el-col :span="8">详细地址：{{order.orderAttr.receiptDetailAddress}}</el-col>
            <el-col :span="8">订单状态：{{statusMap[order.status]}}</el-col>
          </el-row>
          <el-row>
            <el-col :span="24">店铺名称：{{order.storeName}}</el-col>
          </el-row>
        </div>
        <table cellspacing="0" cellpadding="0" border="0" class="el-table"
               style="margin-top: 5px; border: 1px solid #EBEEF5;" width="100%">
          <thead>
          <tr>
            <th class="cell is-leaf">商品名称</th>
            <th width="100" class="cell is-leaf">销售价</th>
            <th width="60" class="cell is-leaf">数量</th>
            <th class="cell is-leaf">商品规格</th>
            <th width="100" class="cell is-leaf">商品总价</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="sku in order.orderSkus">
            <td class="cell">{{sku.skuName}}</td>
            <td class="cell">{{sku.skuPrice.toFixed(2)}}</td>
            <td class="cell">{{sku.num}}</td>
            <td class="cell">{{sku.skuSpecs}}</td>
            <td class="cell">{{sku.price.toFixed(2)}}</td>
          </tr>
          </tbody>
        </table>
      </div>
      <div style="text-align: right; line-height: 40px">打印时间：{{time}}&nbsp;&nbsp;&nbsp;&nbsp;操作者：{{operator}}</div>
    </div>
    <div style="text-align: center; margin-top: 20px">
      <el-button @click="goBack" type="info" icon="el-icon-back">返回订单列表</el-button>
      <el-button type="primary" icon="el-icon-printer" v-print="'#print_box'">打印</el-button>
    </div>
  </div>
</template>
<script>
  import {parseTime} from '@/utils/index';

  export default {
    props: ["url"],
    data() {
      return {
        statusMap: {
          "1": "待付款",
          "2": "待发货",
          "3": "待收货",
          "4": "已完成",
          "5": "已关闭",
          "6": "已关闭",
          "7": "已关闭"
        }, // 订单状态
        payType: {
          "0": "在线支付",
          "1": "货到付款"
        },// 支付方式
        orders: [],
        operator: '',
        time: parseTime(new Date(), 'yyyy-MM-dd hh:mm:ss'),
      }
    },
    created() {
      this.queryOrders()
    },
    methods: {
      queryOrders() {
        this.url(this.$route.query.ids).then(res => {
          this.orders = res.orders;
          this.operator = res.operator;
        })
      },
      // 返回
      goBack() {
        this.$router.back(-1);
      }
    }
  }
</script>
