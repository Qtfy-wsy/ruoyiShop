<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="是否可以退款和退货" prop="allowBack">
        <el-input
          v-model="queryParams.allowBack"
          placeholder="请输入是否可以退款和退货  0 可以 1 不可以 默认0 "
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单自定收货几天前的订单" prop="aotuConfirm">
        <el-input
          v-model="queryParams.aotuConfirm"
          placeholder="请输入订单自定收货几天前的订单 默认1"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="退款说明" prop="refundsDesc">
        <el-input
          v-model="queryParams.refundsDesc"
          placeholder="请输入退款说明"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="退货说明" prop="returnDesc">
        <el-input
          v-model="queryParams.returnDesc"
          placeholder="请输入退货说明"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否支持货到付款" prop="cashonDelivery">
        <el-input
          v-model="queryParams.cashonDelivery"
          placeholder="请输入是否支持货到付款  0 支付 1 不支持 默认0 "
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['order:OmsOrderSetting:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['order:OmsOrderSetting:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['order:OmsOrderSetting:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['order:OmsOrderSetting:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="OmsOrderSettingList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="是否可以退款和退货" align="center" prop="allowBack" />
      <el-table-column label="订单自定收货几天前的订单" align="center" prop="aotuConfirm" />
      <el-table-column label="退款说明" align="center" prop="refundsDesc" />
      <el-table-column label="退货说明" align="center" prop="returnDesc" />
      <el-table-column label="是否支持货到付款" align="center" prop="cashonDelivery" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['order:OmsOrderSetting:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['order:OmsOrderSetting:remove']"
          >删除</el-button>
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

    <!-- 添加或修改订单设置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="是否可以退款和退货" prop="allowBack">
          <el-input v-model="form.allowBack" placeholder="请输入是否可以退款和退货" />
        </el-form-item>
        <el-form-item label="订单自定收货几天前的订单" prop="aotuConfirm">
          <el-input v-model="form.aotuConfirm" placeholder="请输入订单自定收货几天前的订单" />
        </el-form-item>
        <el-form-item label="退款说明" prop="refundsDesc">
          <el-input v-model="form.refundsDesc" placeholder="请输入退款说明" />
        </el-form-item>
        <el-form-item label="退货说明" prop="returnDesc">
          <el-input v-model="form.returnDesc" placeholder="请输入退货说明" />
        </el-form-item>
        <el-form-item label="是否支持货到付款" prop="cashonDelivery">
          <el-input v-model="form.cashonDelivery" placeholder="请输入是否支持货到付款" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listOmsOrderSetting, getOmsOrderSetting, delOmsOrderSetting, addOmsOrderSetting, updateOmsOrderSetting, exportOmsOrderSetting } from "@/api/order/OmsOrderSetting";

export default {
  name: "OmsOrderSetting",
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
      // 订单设置表格数据
      OmsOrderSettingList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
        allowBack: undefined,
        aotuConfirm: undefined,
        refundsDesc: undefined,
        returnDesc: undefined,
        cashonDelivery: undefined
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
  },
  methods: {
    /** 查询订单设置列表 */
    getList() {
      this.loading = true;
      listOmsOrderSetting(this.queryParams).then(response => {
        this.OmsOrderSettingList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
        allowBack: undefined,
        aotuConfirm: undefined,
        refundsDesc: undefined,
        returnDesc: undefined,
        cashonDelivery: undefined
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
      this.title = "添加订单设置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOmsOrderSetting(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改订单设置";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateOmsOrderSetting(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addOmsOrderSetting(this.form).then(response => {
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
      this.$confirm('是否确认删除订单设置编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delOmsOrderSetting(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有订单设置数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportOmsOrderSetting(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
