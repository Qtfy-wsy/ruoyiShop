<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="118px">
      <el-form-item label="订单code" prop="orderCode">
        <el-input
          v-model="queryParams.orderCode"
          placeholder="请输入订单code"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="账单进出类型 0 进 1 出" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择账单进出类型 0 进 1 出" clearable size="small">
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="店铺id" prop="storeId">
        <el-input
          v-model="queryParams.storeId"
          placeholder="请输入店铺id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="店铺名称" prop="storeName">
        <el-input
          v-model="queryParams.storeName"
          placeholder="请输入店铺名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="账单记录类型 1 提货完成 2 代客下单完成" prop="recordType">
        <el-select v-model="queryParams.recordType" placeholder="请选择账单记录类型 1 提货完成 2 代客下单完成" clearable size="small">
          <el-option
            v-for="dict in recordTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="订单价格" prop="orderPrice">
        <el-input
          v-model="queryParams.orderPrice"
          placeholder="请输入订单价格"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="结算状态 0 未结算 1 已结算 默认0 " prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择结算状态 0 未结算 1 已结算 默认0 " clearable size="small">
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
          v-hasPermi="['store:TStoreBillingRecords:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['store:TStoreBillingRecords:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['store:TStoreBillingRecords:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['store:TStoreBillingRecords:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="TStoreBillingRecordsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="订单code" align="center" prop="orderCode" />
      <el-table-column label="账单进出类型 0 进 1 出" align="center" prop="type" :formatter="typeFormat" />
      <el-table-column label="店铺id" align="center" prop="storeId" />
      <el-table-column label="店铺名称" align="center" prop="storeName" />
      <el-table-column label="账单记录类型 1 提货完成 2 代客下单完成" align="center" prop="recordType" :formatter="recordTypeFormat" />
      <el-table-column label="订单价格" align="center" prop="orderPrice" />
      <el-table-column label="结算状态 0 未结算 1 已结算 默认0 " align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['store:TStoreBillingRecords:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['store:TStoreBillingRecords:remove']"
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

    <!-- 添加或修改门店账单收入支出对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单code" prop="orderCode">
          <el-input v-model="form.orderCode" placeholder="请输入订单code" />
        </el-form-item>
        <el-form-item label="账单进出类型 0 进 1 出">
          <el-select v-model="form.type" placeholder="请选择账单进出类型 0 进 1 出">
            <el-option
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="店铺id" prop="storeId">
          <el-input v-model="form.storeId" placeholder="请输入店铺id" />
        </el-form-item>
        <el-form-item label="店铺名称" prop="storeName">
          <el-input v-model="form.storeName" placeholder="请输入店铺名称" />
        </el-form-item>
        <el-form-item label="账单记录类型 1 提货完成 2 代客下单完成">
          <el-select v-model="form.recordType" placeholder="请选择账单记录类型 1 提货完成 2 代客下单完成">
            <el-option
              v-for="dict in recordTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="订单价格" prop="orderPrice">
          <el-input v-model="form.orderPrice" placeholder="请输入订单价格" />
        </el-form-item>
        <el-form-item label="结算状态 0 未结算 1 已结算 默认0 ">
          <el-select v-model="form.status" placeholder="请选择结算状态 0 未结算 1 已结算 默认0 ">
            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
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
import { listTStoreBillingRecords, getTStoreBillingRecords, delTStoreBillingRecords, addTStoreBillingRecords, updateTStoreBillingRecords, exportTStoreBillingRecords } from "@/api/store/TStoreBillingRecords";

export default {
  name: "TStoreBillingRecords",
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
      // 门店账单收入支出表格数据
      TStoreBillingRecordsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 账单进出类型 0 进 1 出字典
      typeOptions: [],
      // 账单记录类型 1 提货完成 2 代客下单完成字典
      recordTypeOptions: [],
      // 结算状态 0 未结算 1 已结算 默认0 字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
        orderCode: undefined,
        type: undefined,
        storeId: undefined,
        storeName: undefined,
        recordType: undefined,
        orderPrice: undefined,
        status: undefined
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
    this.getDicts("bill_type").then(response => {
      this.typeOptions = response.data;
    });
    this.getDicts("bill_records_type").then(response => {
      this.recordTypeOptions = response.data;
    });
    this.getDicts("jiesuan_type").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询门店账单收入支出列表 */
    getList() {
      this.loading = true;
      listTStoreBillingRecords(this.queryParams).then(response => {
        this.TStoreBillingRecordsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 账单进出类型 0 进 1 出字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
    },
    // 账单记录类型 1 提货完成 2 代客下单完成字典翻译
    recordTypeFormat(row, column) {
      return this.selectDictLabel(this.recordTypeOptions, row.recordType);
    },
    // 结算状态 0 未结算 1 已结算 默认0 字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
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
        orderCode: undefined,
        type: undefined,
        storeId: undefined,
        storeName: undefined,
        recordType: undefined,
        orderPrice: undefined,
        createTime: undefined,
        status: undefined
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
      this.title = "添加门店账单收入支出";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTStoreBillingRecords(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改门店账单收入支出";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateTStoreBillingRecords(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addTStoreBillingRecords(this.form).then(response => {
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
      this.$confirm('是否确认删除门店账单收入支出编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delTStoreBillingRecords(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有门店账单收入支出数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportTStoreBillingRecords(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
