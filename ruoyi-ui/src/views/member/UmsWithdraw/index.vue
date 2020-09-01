<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="88px">
      <el-form-item label="会员id" prop="customerId">
        <el-input
          v-model="queryParams.customerId"
          placeholder="请输入会员id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态 0 申请  1 审核通过 2 拒绝 3 已打款" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="流水号" prop="tradeNo">
        <el-input
          v-model="queryParams.tradeNo"
          placeholder="请输入流水号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="支付宝账号" prop="account">
        <el-input
          v-model="queryParams.account"
          placeholder="请输入支付宝账号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="支付宝姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入支付宝姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="打款时间" prop="payingTime">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.payingTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择打款时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>



    <el-table v-loading="loading" :data="UmsWithdrawList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="会员id" align="center" prop="customerId" />
      <el-table-column label="提现金额" align="center" prop="money" />
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="流水号" align="center" prop="tradeNo" />
      <el-table-column label="支付宝账号" align="center" prop="account" />
      <el-table-column label="支付宝姓名" align="center" prop="name" />
      <el-table-column label="打款时间" align="center" prop="payingTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.payingTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
                 <el-button v-if="scope.row.status == '0'"  size="mini"
                                                                       type="text"
                                                                       style="margin: 0 5px 5px 0" icon="el-icon-edit"

                            @click="approvalDialog(scope.row.id)">通过
                 </el-button>
                 <el-button v-if="scope.row.status == '0'"  size="mini"
                                                                       type="text"
                                                                       icon="el-icon-delete" style="margin: 0 0 5px 0"
                            @click="refuseDialog(scope.row.id)">拒绝
                 </el-button>
                 <el-button v-if="scope.row.status == '1'"   size="mini"
                                                                        type="text"
                                                                       icon="el-icon-edit"
                            @click="paymentDialog(scope.row.id)">发放
                 </el-button>
                 <el-button v-if="scope.row.status == '1'"  size="mini"
                                                                       type="text"
                                                                       icon="el-icon-delete" style="margin: 0 0 5px 0"
                            @click="rejectDialog(scope.row.id)">驳回
                 </el-button>
               </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />


  </div>
</template>

<script>
import { listUmsWithdraw, getUmsWithdraw, delUmsWithdraw, addUmsWithdraw, updateUmsWithdraw, exportUmsWithdraw } from "@/api/member/UmsWithdraw";
 import {
    withdrawRecordsList,  //查询记录
    passApply,          //审核通过
    refuseApply,        //拒绝(驳回)
    releaseMoney        //发放金额
  } from '@/api/withdrawalrecord';
export default {
  name: "UmsWithdraw",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 提现记录表格数据
      UmsWithdrawList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 状态 0 申请  1 审核通过 2 拒绝 3 已打款字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
        customerId: undefined,
        money: undefined,
        status: undefined,
        tradeNo: undefined,
        account: undefined,
        name: undefined,
        payingTime: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("withdraw_status").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询提现记录列表 */
    getList() {
      this.loading = true;
      listUmsWithdraw(this.queryParams).then(response => {
        this.UmsWithdrawList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 状态 0 申请  1 审核通过 2 拒绝 3 已打款字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
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
                    message: response.subCode
                  })
                }
              });
            })
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
                },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        customerId: undefined,
        money: undefined,
        status: undefined,
        tradeNo: undefined,
        account: undefined,
        name: undefined,
        createTime: undefined,
        payingTime: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 0;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加提现记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getUmsWithdraw(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改提现记录";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateUmsWithdraw(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addUmsWithdraw(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除提现记录编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delUmsWithdraw(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有提现记录数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportUmsWithdraw(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
