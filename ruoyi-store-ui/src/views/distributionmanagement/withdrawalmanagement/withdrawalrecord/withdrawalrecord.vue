<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input placeholder="交易流水号" size="medium" v-model="listQuery.tradeNo" maxlength="30" style="width: 200px;"
                class="filter-item"/>
      <el-input placeholder="用户手机号" size="medium" v-model="listQuery.mobile" maxlength="30" style="width: 200px;"
                class="filter-item"/>
      <el-button class="filter-item" type="primary" size="medium" icon="el-icon-search" @click="handleQuery">搜索
      </el-button>
    </div>

    <el-table
      :data="list"
      v-loading="listLoading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="流水号" min-width="170">
        <template slot-scope="scope">{{ scope.row.tradeNo }}</template>
      </el-table-column>
      <el-table-column label="用户手机号" min-width="120">
        <template slot-scope="scope">{{ scope.row.mobile }}</template>
      </el-table-column>
      <el-table-column label="提现金额">
        <template slot-scope="scope">{{ scope.row.money }}</template>
      </el-table-column>
      <el-table-column label="支付宝账号" min-width="110">
        <template slot-scope="scope">{{ scope.row.account }}</template>
      </el-table-column>
      <el-table-column label="支付宝姓名" min-width="95">
        <template slot-scope="scope">{{ scope.row.name }}</template>
      </el-table-column>
      <el-table-column label="状态" width="95">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == '0'" type="warning">申请中</el-tag>
          <el-tag v-if="scope.row.status == '1'" type="success">已通过</el-tag>
          <el-tag v-if="scope.row.status == '2'" type="danger">已拒绝</el-tag>
          <el-tag v-if="scope.row.status == '3'" type="success">已打款</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="申请日期" min-width="160">
        <template slot-scope="scope">{{ scope.row.createTime }}</template>
      </el-table-column>
      <el-table-column label="操作" min-width="145">
        <template slot-scope="scope">
          <el-button v-if="scope.row.status == '0'" type="primary" style="margin: 0 5px 5px 0" size="mini"
                     @click="approvalDialog(scope.row.id)">通过
          </el-button>
          <el-button v-if="scope.row.status == '0'" type="danger" style="margin: 0 0 5px 0" size="mini"
                     @click="refuseDialog(scope.row.id)">拒绝
          </el-button>
          <el-button v-if="scope.row.status == '1'" type="primary" style="margin: 0 5px 5px 0" size="mini"
                     @click="paymentDialog(scope.row.id)">发放
          </el-button>
          <el-button v-if="scope.row.status == '1'" type="danger" style="margin: 0 0 5px 0" size="mini"
                     @click="rejectDialog(scope.row.id)">驳回
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


  </div>
</template>

<script>
  import {
    withdrawRecordsList,  //查询记录
    passApply,          //审核通过
    refuseApply,        //拒绝(驳回)
    releaseMoney        //发放金额
  } from '@/api/withdrawalrecord';


  export default {
    data() {
      return {
        isProcess: false,  //接口正在处理中
        listLoading: true,  //加载中
        listQuery: {
          pageNum: 0,
          pageSize: 10,
          tradeNo: '',
          mobile: '',
        }, // 查询参数
        list: null, // 订单数据,
        total: null, // 数据总数,
      }
    },
    created() {
      this.getList()
    },
    methods: {

      //回去页面数据
      getList() {
        this.listLoading = true
        withdrawRecordsList(this.listQuery).then(response => {
          this.list = response.data;
          this.total = response.recordsTotal;
          this.listLoading = false
        });
      },


      //审核通过
      approvalDialog(id) {
        this.$confirm('确定要通过此提现申请吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          if (this.isProcess) {
            return;
          } else {
            this.isProcess = true;
          }
          passApply(id).then(response => {
            this.isProcess = false;
            if (response > 0) {
              this.getList();
              this.$message({
                type: 'success',
                message: '申请通过成功!'
              })
            }
          });
        })
      },

      //拒绝申请
      refuseDialog(id) {
        this.$confirm('确定要拒绝此提现申请吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          if (this.isProcess) {
            return;
          } else {
            this.isProcess = true;
          }
          refuseApply(id).then(response => {
            this.isProcess = false;
            if (response > 0) {
              this.getList();
              this.$message({
                type: 'success',
                message: '申请拒绝成功!'
              })
            }
          });
        })
      },
      //发放金额
      paymentDialog(id) {
        this.$confirm('确定发放金额吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          if (this.isProcess) {
            return;
          } else {
            this.isProcess = true;
          }
          releaseMoney(id).then(response => {
            this.isProcess = false;
            if (response.code == 10000) {
              this.getList();
              this.$message({
                type: 'success',
                message: '发放成功'
              })
            } else {
              this.$message({
                type: 'error',
                message: '系统异常 发放失败'
              })
            }
          });
        })
      },


      //过滤查询
      handleQuery() {
        this.listQuery.pageNum = 0;
        this.getList();
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
      },

      //驳回
      rejectDialog(id) {
        this.$confirm('确定驳回申请吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          if (this.isProcess) {
            return;
          } else {
            this.isProcess = true;
          }
          refuseApply(id).then(response => {
            this.isProcess = false;
            if (response > 0) {
              this.getList();
              this.$message({
                type: 'success',
                message: '申请驳回成功!'
              })
            }
          });
        })
      }
    }
  }
</script>
