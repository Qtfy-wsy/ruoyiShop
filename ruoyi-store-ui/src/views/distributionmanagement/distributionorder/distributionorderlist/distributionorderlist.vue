<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input placeholder="订单号" size="medium" maxlength="30" v-model="listQuery.orderCode" style="width: 200px;"
                class="filter-item"/>
      <el-input placeholder="店铺名称" size="medium" maxlength="30" v-model="listQuery.storeName" style="width: 200px;"
                class="filter-item"/>
      <el-button class="filter-item" type="primary" size="medium" @click="handleQuery" icon="el-icon-search">搜索
      </el-button>
    </div>

    <el-table
      class="orderExpandTable"
      :data="list"
      v-loading="listLoading"
      border
      fit
      highlight-current-row
    >
      <el-table-column type="expand">
        <template slot-scope="scope">
          <el-table
            :data="scope.row.orderSkus"
            border
            fit
            highlight-current-row
          >
            <el-table-column label="商品名称" min-width="162">
              <template slot-scope="scope">{{scope.row.skuName }}</template>
            </el-table-column>
            <el-table-column label="规格" min-width="110">
              <template slot-scope="scope">{{ scope.row.skuSpecs }}</template>
            </el-table-column>
            <el-table-column label="数量" width="60">
              <template slot-scope="scope">{{ scope.row.num }}</template>
            </el-table-column>
            <el-table-column label="商品总额" width="80">
              <template slot-scope="scope">{{ scope.row.price.toFixed(2) }}</template>
            </el-table-column>
            <el-table-column label="一级佣金比例" width="105">
              <template slot-scope="scope">{{ scope.row.commissionRate }}</template>
            </el-table-column>
            <el-table-column label="一级佣金总额" width="105">
              <template slot-scope="scope">{{ (scope.row.price * scope.row.commissionRate).toFixed(2) }}
              </template>
            </el-table-column>
            <el-table-column label="二级佣金比例" width="105">
              <template slot-scope="scope">{{ scope.row.scommissionRate }}</template>
            </el-table-column>
            <el-table-column label="二级佣金总额" width="105">
              <template slot-scope="scope">{{ (scope.row.price * scope.row.scommissionRate).toFixed(2) }}
              </template>
            </el-table-column>
          </el-table>
        </template>
      </el-table-column>
      <el-table-column label="订单编号">
        <template slot-scope="scope">{{ scope.row.orderCode }}</template>
      </el-table-column>
      <el-table-column label="店铺名称">
        <template slot-scope="scope">{{ scope.row.storeName }}</template>
      </el-table-column>
      <el-table-column label="买家">
        <template slot-scope="scope">{{ scope.row.customerName }}</template>
      </el-table-column>
      <el-table-column label="来源">
        <template slot-scope="scope">
          <span v-if="scope.row.source == '1'">PC</span>
          <span v-if="scope.row.source == '2'">H5</span>
          <span v-if="scope.row.source == '3'">APP</span>
          <span v-if="scope.row.source == '4'">小程序</span>
        </template>
      </el-table-column>
      <el-table-column label="订单状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == '1'" type="warning">待付款</el-tag>
          <el-tag v-if="scope.row.status == '2'" type="warning">待发货</el-tag>
          <el-tag v-if="scope.row.status == '3'" type="warning">待收货</el-tag>
          <el-tag v-if="scope.row.status == '4'" type="success">已完成</el-tag>
          <el-tag v-if="scope.row.status == '5'" type="info">取消订单</el-tag>
          <el-tag v-if="scope.row.status == '6'" type="info">退款通过</el-tag>
          <el-tag v-if="scope.row.status == '7'" type="info">退款通过</el-tag>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="listQuery.pageNum+1" :page-sizes="[10,20,30,50]" :page-size="listQuery.pageSize"
                     layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

  </div>
</template>

<script>
  import {
    spreadOrdersList
  } from '@/api/distributionorderlist';  //引入JS

  export default {
    data() {
      return {
        listLoading: true, //正在加载
        listQuery: { //查询参数
          pageNum: 0,
          pageSize: 10,
          orderCode: '',
          storeName: ''
        },
        list: null, // 订单数据,
        total: null, // 数据总数,
      }
    },
    created() {
      this.getList(); //初始化列表
    },
    methods: {

      //刷新列表
      handleQuery() {
        this.listQuery.pageNum = 0;
        this.getList();
      },

      //获取列表数据
      getList() {
        this.listLoading = true
        spreadOrdersList(this.listQuery).then(response => {
          this.list = response.data;
          this.total = response.recordsTotal;
          this.listLoading = false
        });
      },

      //翻页
      handleCurrentChange(val) {
        this.listQuery.pageNum = val - 1;
        this.getList();
      },

      //改变页面容积
      handleSizeChange(val) {
        this.listQuery.pageNum = 0;
        this.listQuery.pageSize = val;
        this.getList();
      }
    }
  }
</script>
