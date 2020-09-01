<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="门店红包id" prop="redEnvelopeId">
        <el-input
          v-model="queryParams.redEnvelopeId"
          placeholder="请输入门店红包id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="红包的卷码" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入红包的卷码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="领取红包的会员id" prop="customerId">
        <el-input
          v-model="queryParams.customerId"
          placeholder="请输入领取红包的会员id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="红包卷状态  0 未领取 1已领取未使用 2 已使用 3已失效" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择红包卷状态  0 未领取 1已领取未使用 2 已使用 3已失效" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="领取红包" prop="receiveTime">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.receiveTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择领取红包">
        </el-date-picker>
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
          v-hasPermi="['store:TStoreRedEnvelopeCode:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['store:TStoreRedEnvelopeCode:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['store:TStoreRedEnvelopeCode:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['store:TStoreRedEnvelopeCode:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="TStoreRedEnvelopeCodeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="门店红包id" align="center" prop="redEnvelopeId" />
      <el-table-column label="红包的卷码" align="center" prop="code" />
      <el-table-column label="领取红包的会员id" align="center" prop="customerId" />
      <el-table-column label="红包卷状态  0 未领取 1已领取未使用 2 已使用 3已失效" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="领取红包" align="center" prop="receiveTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.receiveTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['store:TStoreRedEnvelopeCode:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['store:TStoreRedEnvelopeCode:remove']"
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

    <!-- 添加或修改门店红包卷吗对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="门店红包id" prop="redEnvelopeId">
          <el-input v-model="form.redEnvelopeId" placeholder="请输入门店红包id" />
        </el-form-item>
        <el-form-item label="红包的卷码" prop="code">
          <el-input v-model="form.code" placeholder="请输入红包的卷码" />
        </el-form-item>
        <el-form-item label="领取红包的会员id" prop="customerId">
          <el-input v-model="form.customerId" placeholder="请输入领取红包的会员id" />
        </el-form-item>
        <el-form-item label="红包卷状态  0 未领取 1已领取未使用 2 已使用 3已失效">
          <el-select v-model="form.status" placeholder="请选择红包卷状态  0 未领取 1已领取未使用 2 已使用 3已失效">
            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="领取红包" prop="receiveTime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.receiveTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择领取红包">
          </el-date-picker>
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
import { listTStoreRedEnvelopeCode, getTStoreRedEnvelopeCode, delTStoreRedEnvelopeCode, addTStoreRedEnvelopeCode, updateTStoreRedEnvelopeCode, exportTStoreRedEnvelopeCode } from "@/api/store/TStoreRedEnvelopeCode";

export default {
  name: "TStoreRedEnvelopeCode",
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
      // 门店红包卷吗表格数据
      TStoreRedEnvelopeCodeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 红包卷状态  0 未领取 1已领取未使用 2 已使用 3已失效字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
        redEnvelopeId: undefined,
        code: undefined,
        customerId: undefined,
        status: undefined,
        receiveTime: undefined
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
    this.getDicts("redStatus").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询门店红包卷吗列表 */
    getList() {
      this.loading = true;
      listTStoreRedEnvelopeCode(this.queryParams).then(response => {
        this.TStoreRedEnvelopeCodeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 红包卷状态  0 未领取 1已领取未使用 2 已使用 3已失效字典翻译
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
        redEnvelopeId: undefined,
        code: undefined,
        customerId: undefined,
        status: undefined,
        receiveTime: undefined
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
      this.title = "添加门店红包卷吗";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTStoreRedEnvelopeCode(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改门店红包卷吗";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateTStoreRedEnvelopeCode(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addTStoreRedEnvelopeCode(this.form).then(response => {
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
      this.$confirm('是否确认删除门店红包卷吗编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delTStoreRedEnvelopeCode(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有门店红包卷吗数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportTStoreRedEnvelopeCode(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
