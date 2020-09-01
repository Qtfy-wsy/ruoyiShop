<template>
  <div class="app-container">
    <div class="filter-container">
    <el-form :model="listQuery" ref="queryForm" :inline="true" label-width="68px">
              <el-form-item label="退单号" prop="backCode">
                <el-input
                  v-model="listQuery.backCode"
                  placeholder="请输入退单号"
                  clearable
                  size="small"
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
              <el-form-item label="订单号" prop="orderCode">
                              <el-input
                                v-model="listQuery.orderCode"
                                placeholder="请输入订单号"
                                clearable
                                size="small"
                                @keyup.enter.native="handleQuery"
                              />
                            </el-form-item>
       <el-form-item label="状态" prop="status">
              <el-select v-model="listQuery.status" placeholder="请选择退款／退货状态" clearable size="small">
                <el-option
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
             <el-form-item>
                        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>

                      </el-form-item>
                    </el-form>

    </div>

    <el-table
      :data="list"
      v-loading="listLoading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="退单号" min-width="170">
        <template slot-scope="scope">{{ scope.row.backCode }}</template>
      </el-table-column>
      <el-table-column label="订单号" min-width="170">
        <template slot-scope="scope">
          <router-link :to="{path:'/storemanager/storeorder/backorder/storeorderdetail',query:{id:scope.row.orderId}}">
            <el-link type="primary">{{ scope.row.orderCode }}</el-link>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="退单时间" min-width="160">
        <template slot-scope="scope">{{ scope.row.createTime }}</template>
      </el-table-column>
      <el-table-column label="退单总额">
        <template slot-scope="scope">{{ scope.row.backPrice.toFixed(2) }}</template>
      </el-table-column>
      <el-table-column label="实退金额">
        <template slot-scope="scope">
          {{scope.row.type=='1'?scope.row.backPrice.toFixed(2):(scope.row.realBackPrice?scope.row.realBackPrice.toFixed(2):'0.00')}}
        </template>
      </el-table-column>
      <el-table-column label="退单状态" min-width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == '1'" type="primary">退款申请</el-tag>
          <el-tag v-if="scope.row.status == '2'" type="success">退款成功</el-tag>
          <el-tag v-if="scope.row.status == '3'" type="danger">退款拒绝</el-tag>
          <el-tag v-if="scope.row.status == '4'" type="primary">退货申请</el-tag>
          <el-tag v-if="scope.row.status == '5'" type="danger">退货拒绝</el-tag>
          <el-tag v-if="scope.row.status == '6'" type="warning">待用户填写物流</el-tag>
          <el-tag v-if="scope.row.status == '7'" type="warning">待商家收货</el-tag>
          <el-tag v-if="scope.row.status == '8'" type="success">退货完成</el-tag>
          <el-tag v-if="scope.row.status == '9'" type="danger">退货失败</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="left" width="100">
        <template slot-scope="scope">
          <router-link v-if="showRefundDetail(scope.row)"
                       :to="{path:'/storemanager/storeorder/storerefundreview',query:{id:scope.row.id}}">
            <el-button type="success" size="mini">退款详情</el-button>
          </router-link>
          <router-link v-if="showReturnDetail(scope.row)"
                       :to="{path:'/storemanager/storeorder/storereturnreview',query:{id:scope.row.id}}">
            <el-button type="success" size="mini">退货详情</el-button>
          </router-link>
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

  import {queryStoreBackOrders} from '@/api/store/storebackorderlist';

  export default {
    data() {
      return {
        listLoading: true,
         // 退款／退货状态1:退款申请 字典
              statusOptions: [],
        listQuery: {
          pageNum: 0,
          pageSize: 10,
          backCode: '',
          orderCode: '',
          status: ''
        }, // 查询参数
        list: null, // 退单数据
        total: null, // 数据总数
      }
    },
    created() {
      this.getList()
       this.getDicts("afterSaleStatus").then(response => {
            this.statusOptions = response.data;
          });
    },
    methods: {
      getList() {
        this.listLoading = true
        queryStoreBackOrders(this.listQuery).then(res => {
          this.list = res.data;
          this.total = res.recordsTotal;
          this.listLoading = false;
        })
      },
      handleCurrentChange(val) {
        this.listQuery.pageNum = val - 1;
        this.getList();
      },
      handleSelectionChange(val) {
        if (val && val.length > 0) {
          this.delIds = val.map(row => row.id);
        } else {
          this.delIds = [];
        }
      },
      // 过滤查询
      handleQuery() {
        this.listQuery.pageNum = 0;
        this.getList();
      },
      handleSizeChange(val) {
        this.listQuery.pageNum = 0;
        this.listQuery.pageSize = val;
        this.getList();
      },
      // 显示退款详情
      showRefundDetail(order) {
        return order.type == '1';
      },
      // 显示退货详情
      showReturnDetail(order) {
        return order.type == '2';
      },
    }
  }
</script>
