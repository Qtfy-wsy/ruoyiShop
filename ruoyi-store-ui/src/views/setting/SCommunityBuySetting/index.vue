<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="团长名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入团长名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否开启审核  0 开启 1 关闭 默认0" prop="audit">
        <el-input
          v-model="queryParams.audit"
          placeholder="请输入是否开启审核  0 开启 1 关闭 默认0"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审核结果通知  0 通知 1 不通知 默认0 " prop="smsAuditNotice">
        <el-input
          v-model="queryParams.smsAuditNotice"
          placeholder="请输入审核结果通知  0 通知 1 不通知 默认0 "
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="打款通知 0 通知 1 不通知默认0 " prop="smsPayNotice">
        <el-input
          v-model="queryParams.smsPayNotice"
          placeholder="请输入打款通知 0 通知 1 不通知默认0 "
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="佣金结算通知 0 通知 1 不通知默认0 " prop="smsCommissionNotice">
        <el-input
          v-model="queryParams.smsCommissionNotice"
          placeholder="请输入佣金结算通知 0 通知 1 不通知默认0 "
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
          v-hasPermi="['setting:SCommunityBuySetting:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['setting:SCommunityBuySetting:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['setting:SCommunityBuySetting:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['setting:SCommunityBuySetting:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="SCommunityBuySettingList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="佣金结算通知 0 通知 1 不通知默认0 " align="center" prop="id" />
      <el-table-column label="团长名称" align="center" prop="name" />
      <el-table-column label="是否开启审核  0 开启 1 关闭 默认0" align="center" prop="audit" />
      <el-table-column label="审核结果通知  0 通知 1 不通知 默认0 " align="center" prop="smsAuditNotice" />
      <el-table-column label="打款通知 0 通知 1 不通知默认0 " align="center" prop="smsPayNotice" />
      <el-table-column label="佣金结算通知 0 通知 1 不通知默认0 " align="center" prop="smsCommissionNotice" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['setting:SCommunityBuySetting:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['setting:SCommunityBuySetting:remove']"
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

    <!-- 添加或修改社区团购设置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="团长名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入团长名称" />
        </el-form-item>
        <el-form-item label="是否开启审核  0 开启 1 关闭 默认0" prop="audit">
          <el-input v-model="form.audit" placeholder="请输入是否开启审核  0 开启 1 关闭 默认0" />
        </el-form-item>
        <el-form-item label="审核结果通知  0 通知 1 不通知 默认0 " prop="smsAuditNotice">
          <el-input v-model="form.smsAuditNotice" placeholder="请输入审核结果通知  0 通知 1 不通知 默认0 " />
        </el-form-item>
        <el-form-item label="打款通知 0 通知 1 不通知默认0 " prop="smsPayNotice">
          <el-input v-model="form.smsPayNotice" placeholder="请输入打款通知 0 通知 1 不通知默认0 " />
        </el-form-item>
        <el-form-item label="佣金结算通知 0 通知 1 不通知默认0 " prop="smsCommissionNotice">
          <el-input v-model="form.smsCommissionNotice" placeholder="请输入佣金结算通知 0 通知 1 不通知默认0 " />
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
import { listSCommunityBuySetting, getSCommunityBuySetting, delSCommunityBuySetting, addSCommunityBuySetting, updateSCommunityBuySetting, exportSCommunityBuySetting } from "@/api/setting/SCommunityBuySetting";

export default {
  name: "SCommunityBuySetting",
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
      // 社区团购设置表格数据
      SCommunityBuySettingList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
        name: undefined,
        audit: undefined,
        smsAuditNotice: undefined,
        smsPayNotice: undefined,
        smsCommissionNotice: undefined
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
    /** 查询社区团购设置列表 */
    getList() {
      this.loading = true;
      listSCommunityBuySetting(this.queryParams).then(response => {
        this.SCommunityBuySettingList = response.rows;
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
        name: undefined,
        audit: undefined,
        smsAuditNotice: undefined,
        smsPayNotice: undefined,
        smsCommissionNotice: undefined
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
      this.title = "添加社区团购设置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSCommunityBuySetting(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改社区团购设置";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateSCommunityBuySetting(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addSCommunityBuySetting(this.form).then(response => {
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
      this.$confirm('是否确认删除社区团购设置编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delSCommunityBuySetting(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有社区团购设置数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportSCommunityBuySetting(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
