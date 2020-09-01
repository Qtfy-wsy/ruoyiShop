<template>
  <div class="app-container">
    <div style="line-height: 50px">退款概况</div>
    <div class="detailsTable">
      <el-row>
        <el-col :span="12">退款原因：{{reasonMap[backOrder.reason]}}</el-col>
        <el-col :span="12">退款金额：{{backOrder.backPrice.toFixed(2)}}</el-col>
      </el-row>
      <el-row>
        <el-col :span="24">问题说明：{{backOrder.desc}}</el-col>
      </el-row>
    </div>
    <div style="line-height: 50px">订单操作日志</div>
    <el-table
      :data="backOrder.backOrderLogs"
      v-loading="listLoading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="操作内容">
        <template slot-scope="scope">{{logContent(scope.row)}}</template>
      </el-table-column>
      <el-table-column label="操作时间">
        <template slot-scope="scope">{{ scope.row.createTime }}</template>
      </el-table-column>
    </el-table>
    <div style="text-align: center; margin-top: 20px">
      <router-link to="/storemanager/storeorder/backorderlist">
        <el-button type="info" icon="el-icon-back">返回退单列表</el-button>
      </router-link>
    </div>
  </div>
</template>

<script>
  import {queryBackOrderById} from '@/api/store/storerefundreview';

  export default {
    data() {
      return {
        reasonMap: {
          '1': '不想买了',
          '2': '收货人信息有误',
          '3': '未按指定时间发货',
          '4': '其他',
          '5': '不想买了',
          '6': '商品质量问题',
          '7': '收到商品与描述不符',
          '8': '其他',
          '9': '系统自动申请'
        }, // 退款原因映射
        listLoading: true,
        backOrder: {
          backPrice: 0,
        },// 退单详情
      }
    },
    created() {
      this.query()
    },
    methods: {
      query() {
        queryBackOrderById(this.$route.query.id).then(res => {
          this.backOrder = res;
          this.listLoading = false;
        })
      },
    },
    computed: {
      logContent() {
        return function (log) {
          if (log.status == '1') {
            return '提交退款申请(操作：顾客)';
          } else if (log.status == '2') {
            return '退款' + this.backOrder.backPrice + '元成功 (操作：平台)留言：' + log.message + '';
          } else if (log.status == '3') {
            return '拒绝退款 (操作：平台)留言：' + log.message + '';
          }
          return '';
        }
      }
    }
  }
</script>
