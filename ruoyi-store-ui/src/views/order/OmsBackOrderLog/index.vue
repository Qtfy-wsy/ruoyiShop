<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="退单id 对应oms_back_order表中的id" prop="backOrderId">
        <el-input
          v-model="queryParams.backOrderId"
          placeholder="请输入退单id 对应oms_back_order表中的id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作人类型 1 用户  2 商家" prop="operationType">
        <el-select v-model="queryParams.operationType" placeholder="请选择操作人类型 1 用户  2 商家" clearable size="small">
          <el-option
            v-for="dict in operationTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="1:退款申请 " prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择1:退款申请 " clearable size="small">
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
          v-hasPermi="['order:OmsBackOrderLog:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['order:OmsBackOrderLog:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['order:OmsBackOrderLog:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['order:OmsBackOrderLog:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="OmsBackOrderLogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="退单id 对应oms_back_order表中的id" align="center" prop="backOrderId" />
      <el-table-column label="操作人类型 1 用户  2 商家" align="center" prop="operationType" :formatter="operationTypeFormat" />
      <el-table-column label="留言" align="center" prop="message" />
      <el-table-column label="1:退款申请 " align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['order:OmsBackOrderLog:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['order:OmsBackOrderLog:remove']"
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

    <!-- 添加或修改退款退货操作日志对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="退单id 对应oms_back_order表中的id" prop="backOrderId">
          <el-input v-model="form.backOrderId" placeholder="请输入退单id 对应oms_back_order表中的id" />
        </el-form-item>
        <el-form-item label="操作人类型 1 用户  2 商家">
          <el-select v-model="form.operationType" placeholder="请选择操作人类型 1 用户  2 商家">
            <el-option
              v-for="dict in operationTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="留言" prop="message">
          <el-input v-model="form.message" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="1:退款申请 ">
          <el-select v-model="form.status" placeholder="请选择1:退款申请 ">
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
import { listOmsBackOrderLog, getOmsBackOrderLog, delOmsBackOrderLog, addOmsBackOrderLog, updateOmsBackOrderLog, exportOmsBackOrderLog } from "@/api/order/OmsBackOrderLog";

export default {
  name: "OmsBackOrderLog",
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
      // 退款退货操作日志表格数据
      OmsBackOrderLogList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 操作人类型 1 用户  2 商家字典
      operationTypeOptions: [],
      // 1:退款申请 字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
        backOrderId: undefined,
        operationType: undefined,
        status: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        backOrderId: [
          { required: true, message: "退单id 对应oms_back_order表中的id不能为空", trigger: "blur" }
        ],
        operationType: [
          { required: true, message: "操作人类型 1 用户  2 商家不能为空", trigger: "blur" }
        ],

      }
    };
  },
  created() {
    this.getList();
    this.getDicts("operate_type").then(response => {
      this.operationTypeOptions = response.data;
    });
    this.getDicts("afterSaleStatus").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询退款退货操作日志列表 */
    getList() {
      this.loading = true;
      listOmsBackOrderLog(this.queryParams).then(response => {
        this.OmsBackOrderLogList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 操作人类型 1 用户  2 商家字典翻译
    operationTypeFormat(row, column) {
      return this.selectDictLabel(this.operationTypeOptions, row.operationType);
    },
    // 1:退款申请 字典翻译
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
        backOrderId: undefined,
        operationType: undefined,
        message: undefined,
        status: undefined,
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
      this.title = "添加退款退货操作日志";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOmsBackOrderLog(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改退款退货操作日志";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateOmsBackOrderLog(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addOmsBackOrderLog(this.form).then(response => {
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
      this.$confirm('是否确认删除退款退货操作日志编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delOmsBackOrderLog(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有退款退货操作日志数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportOmsBackOrderLog(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
