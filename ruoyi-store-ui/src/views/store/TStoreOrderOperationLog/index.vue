<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="门店订单id 对应t_store_order 表中的id" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入门店订单id 对应t_store_order 表中的id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作类型 1 确认付款 2 核销" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择操作类型 1 确认付款 2 核销" clearable size="small">
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="操作人" prop="operationName">
        <el-input
          v-model="queryParams.operationName"
          placeholder="请输入操作人"
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
          v-hasPermi="['store:TStoreOrderOperationLog:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['store:TStoreOrderOperationLog:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['store:TStoreOrderOperationLog:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['store:TStoreOrderOperationLog:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="TStoreOrderOperationLogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="门店订单id 对应t_store_order 表中的id" align="center" prop="orderId" />
      <el-table-column label="操作类型 1 确认付款 2 核销" align="center" prop="type" :formatter="typeFormat" />
      <el-table-column label="说明" align="center" prop="remark" />
      <el-table-column label="操作人" align="center" prop="operationName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['store:TStoreOrderOperationLog:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['store:TStoreOrderOperationLog:remove']"
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

    <!-- 添加或修改门店订单操作日志对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="门店订单id 对应t_store_order 表中的id" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入门店订单id 对应t_store_order 表中的id" />
        </el-form-item>
        <el-form-item label="操作类型 1 确认付款 2 核销">
          <el-select v-model="form.type" placeholder="请选择操作类型 1 确认付款 2 核销">
            <el-option
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="操作人" prop="operationName">
          <el-input v-model="form.operationName" placeholder="请输入操作人" />
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
import { listTStoreOrderOperationLog, getTStoreOrderOperationLog, delTStoreOrderOperationLog, addTStoreOrderOperationLog, updateTStoreOrderOperationLog, exportTStoreOrderOperationLog } from "@/api/store/TStoreOrderOperationLog";

export default {
  name: "TStoreOrderOperationLog",
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
      // 门店订单操作日志表格数据
      TStoreOrderOperationLogList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 操作类型 1 确认付款 2 核销字典
      typeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
        orderId: undefined,
        type: undefined,
        operationName: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderId: [
          { required: true, message: "门店订单id 对应t_store_order 表中的id不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "操作类型 1 确认付款 2 核销（提货） 3 取消订单 不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("storeOperateType").then(response => {
      this.typeOptions = response.data;
    });
  },
  methods: {
    /** 查询门店订单操作日志列表 */
    getList() {
      this.loading = true;
      listTStoreOrderOperationLog(this.queryParams).then(response => {
        this.TStoreOrderOperationLogList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 操作类型 1 确认付款 2 核销字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
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
        orderId: undefined,
        type: undefined,
        remark: undefined,
        operationName: undefined,
        createTime: undefined
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
      this.title = "添加门店订单操作日志";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTStoreOrderOperationLog(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改门店订单操作日志";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateTStoreOrderOperationLog(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addTStoreOrderOperationLog(this.form).then(response => {
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
      this.$confirm('是否确认删除门店订单操作日志编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delTStoreOrderOperationLog(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有门店订单操作日志数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportTStoreOrderOperationLog(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
