<template>
  <div class="app-container">
    <el-radio-group v-model="orderStatus" @change="changeStatus" style="margin-bottom: 20px">
      <el-radio-button :label="0">全部</el-radio-button>
      <el-radio-button :label="1">拼团中</el-radio-button>
      <el-radio-button :label="2">已成团</el-radio-button>
      <el-radio-button :label="3">已失效</el-radio-button>
      <el-radio-button :label="4">未付款</el-radio-button>
    </el-radio-group>
    <div class="filter-container">
      <el-input maxlength="34" v-model="listQuery.groupId" placeholder="拼团编号" size="medium" style="width: 150px;"
                class="filter-item"/>
      <el-input maxlength="15" v-model="listQuery.customerName" placeholder="用户名" size="medium" style="width: 150px;"
                class="filter-item"/>
      <el-date-picker
        style="width: 355px"
        class="search-datepicker"
        size="medium"
        :clearable="false"
        v-model="searchTimeValue"
        type="datetimerange"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        format="yyyy-MM-dd HH:mm:ss"
        value-format="yyyy-MM-dd HH:mm:ss"
        align="right">
      </el-date-picker>
      <el-button class="filter-item" type="primary" size="medium" icon="el-icon-search" @click="handleQuery">搜索
      </el-button>

    </div>

    <el-table
      :data="list"
      v-loading="listLoading"
      border
      fit
      highlight-current-row
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" align="center" width="40"/>
      <el-table-column label="拼团编号" min-width="162">
        <template slot-scope="scope">{{ scope.row.groupId }}</template>
      </el-table-column>
      <el-table-column label="订单编号" min-width="162">
        <template slot-scope="scope">{{ scope.row.orderCode }}</template>
      </el-table-column>
      <el-table-column label="拼团用户" min-width="110">
        <template slot-scope="scope">{{ scope.row.customerName }}</template>
      </el-table-column>
      <el-table-column label="拼团角色">
        <template slot-scope="scope">
          <span v-if="scope.row.groupHead == '0'" style="color: #FCB322">团长</span>
          <span v-if="scope.row.groupHead == '1'">团员</span>
        </template>
      </el-table-column>
      <el-table-column label="下单时间" width="160">
        <template slot-scope="scope">{{ scope.row.createTime }}</template>
      </el-table-column>
      <el-table-column label="订单总额">
        <template slot-scope="scope">{{ scope.row.price.toFixed(2) }}</template>
      </el-table-column>
      <el-table-column label="订单状态" width="82">
        <el-tag type="warning">待付款</el-tag>
      </el-table-column>
      <el-table-column label="操作" width="170">
        <template slot-scope="scope">
          <router-link :to="{path:'/marketingManager/marketingGoods/nopaygrouporderdetail',query:{id:scope.row.id}}">
            <el-button type="success" style="margin: 0 5px 0 0" size="mini">详情</el-button>
          </router-link>
          <el-button type="primary" style="margin: 0" size="mini" @click="toConfirmOrder(scope.row.id)">确认付款</el-button>
          <el-button size="mini" style="margin: 5px 0 0 0" type="danger" @click="cancelDialog(scope.row.id)">取消
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="listQuery.pageNum+1" :page-sizes="[10,20,30,50]" :page-size="listQuery.pageSize"
                     layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <el-dialog title="确认付款" :visible.sync="confirmShow">
      <el-alert title="注意！ 确认付款后订单状态将修改为待发货，你确定要修改吗？" style="margin-bottom: 20px" type="warning" :closable="false"
                show-icon></el-alert>
      <el-form :model="confirmReason" :rules="confirmRules" ref="confirmForm" label-position="right"
               label-width="100px">
        <el-form-item prop="reason">
          <span slot="label">请填写原因</span>
          <el-input maxlength="200" v-model="confirmReason.reason"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="confirmShow = false">取消</el-button>
        <el-button type="primary" @click="confrimOrder()">确定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import {
    queryNotPayGroupOrders,
    cancelGroupOrder,
    confirmGroupOrder,
    exportAllOrder,
    exportCheckedGroupOrder
  } from '@/api/order/nopaylist';

  export default {
    data() {
      return {
        confirmRules: {
          reason: [
            {
              required: true,
              message: '请填写原因!',
              trigger: 'blur'
            }
          ]
        },  // 验证规则
        confirmReason: {reason: ''},// 确认付款的原因
        confirmOrderId: 0,// 确认付款时候的订单id
        confirmShow: false, // 确认付款二次确认标记
        isProcess: false, // 接口是否处理中
        searchTimeValue: null,// 搜索时间
        orderStatus: '4',//拼团订单状态
        listLoading: true, // 加载中
        list: null, // 拼团订单数据
        total: null, // 数据总数
        listQuery: {
          pageNum: 0,
          pageSize: 10,
          groupId: '',
          customerName: '',
          startTime: '',
          endTime: ''
        }, // 查询参数
        selectedIds: []// 选中的订单id
      }
    },
    created() {
      this.getList()
    },
    methods: {
      getList() {
        this.listLoading = true
        queryNotPayGroupOrders(this.listQuery).then((response) => {
          this.list = response.data;
          this.total = response.recordsTotal;
          this.listLoading = false
        })
      },
      handleCurrentChange(val) {
        this.listQuery.pageNum = val - 1;
        this.getList();
      },
      handleSelectionChange(val) {
        if (val && val.length > 0) {
          this.selectedIds = val.map(row => row.id);
        } else {
          this.selectedIds = [];
        }
      },
      // 过滤查询
      handleQuery() {
        this.listQuery.pageNum = 0;

        // 设置时间
        if (this.searchTimeValue) {
          this.listQuery.startTime = this.searchTimeValue[0];
          this.listQuery.endTime = this.searchTimeValue[1];
        } else {
          this.listQuery.startTime = '';
          this.listQuery.endTime = '';
        }

        this.getList();
      },
      handleSizeChange(val) {
        this.listQuery.pageNum = 0;
        this.listQuery.pageSize = val;
        this.getList();
      },
      changeStatus() {
        if (this.orderStatus == '4') {
          return false
        } else {
          this.$router.push({path: '/order/orderManager/grouplist', query: {status: this.orderStatus}})
        }
      },
      // 取消订单
      cancelDialog(id) {
        this.$confirm('订单不能随意中断，你确定要修改吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          if (this.isProcess) {
            return;
          }
          this.isProcess = true;
          cancelGroupOrder(id).then(res => {
            this.isProcess = false;
            if (res > 0) {
              this.$message({
                type: 'success',
                message: '取消订单成功!'
              })
            } else {
              this.$message({
                type: 'error',
                message: '取消订单失败!'
              })
            }
            this.handleQuery();
          })
        })
      },
      // 确认付款二次确认
      toConfirmOrder(id) {
        this.confirmShow = true;
        this.confirmOrderId = id;
        if (this.$refs["confirmForm"]) {
          this.$refs["confirmForm"].resetFields();
        }
        this.confirmReason = {reason: ''};
      },
      // 确认付款
      confrimOrder() {
        this.$refs['confirmForm'].validate(valid => {
          if (valid && !this.isProcess) {
            this.isProcess = true;
            confirmGroupOrder(this.confirmOrderId, this.confirmReason).then(res => {
              this.isProcess = false;
              if (res > 0) {
                this.$message({
                  type: 'success',
                  message: '确认付款成功'
                });
              } else {
                this.$message({
                  type: 'error',
                  message: '确认付款失败'
                });
              }
              this.confirmShow = false;
              this.confirmOrderId = 0;
              this.handleQuery();
            })
          }
        })
      },
      // 导出所有订单
      exportAll() {
        exportAllOrder().then(res => {
          let blob = new Blob([res], {type: "application/vnd.ms-excel"});
          let objectUrl = URL.createObjectURL(blob);
          window.location.href = objectUrl;
        });
      },
      // 导出选中的订单
      exportSelected() {
        if (this.selectedIds && this.selectedIds.length > 0) {
          exportCheckedGroupOrder(this.selectedIds.toString()).then(res => {
            let blob = new Blob([res], {type: "application/vnd.ms-excel"});
            let objectUrl = URL.createObjectURL(blob);
            window.location.href = objectUrl;
          });
        } else {
          this.$message({
            message: '请至少选择一条订单！',
            type: 'warning'
          })
        }
      },
    }
  }
</script>
