<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="订单id" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入订单id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发票类型  0 不需要发票 1普通发票 2增值税发票 默认0" prop="invoiceType">
        <el-select v-model="queryParams.invoiceType" placeholder="请选择发票类型  0 不需要发票 1普通发票 2增值税发票 默认0" clearable size="small">
          <el-option
            v-for="dict in invoiceTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="发票抬头" prop="invoiceTitle">
        <el-input
          v-model="queryParams.invoiceTitle"
          placeholder="请输入发票抬头"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发票内容 \n1:非图书商品\n2:明细\n3:耗材\n4:办公用品" prop="invoiceContent">
        <el-input
          v-model="queryParams.invoiceContent"
          placeholder="请输入发票内容 \n1:非图书商品\n2:明细\n3:耗材\n4:办公用品"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="税号" prop="invoiceTaxid">
        <el-input
          v-model="queryParams.invoiceTaxid"
          placeholder="请输入税号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="抬头类型  1 企业  2 个人" prop="invoiceTitleType">
        <el-select v-model="queryParams.invoiceTitleType" placeholder="请选择抬头类型  1 企业  2 个人" clearable size="small">
          <el-option
            v-for="dict in invoiceTitleTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="单位名称" prop="invoiceCompanyName">
        <el-input
          v-model="queryParams.invoiceCompanyName"
          placeholder="请输入单位名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="注册地址" prop="invoiceRegisterAddress">
        <el-input
          v-model="queryParams.invoiceRegisterAddress"
          placeholder="请输入注册地址"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="注册电话" prop="invoiceRegisterMobile">
        <el-input
          v-model="queryParams.invoiceRegisterMobile"
          placeholder="请输入注册电话"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开户银行" prop="invoiceOpenBank">
        <el-input
          v-model="queryParams.invoiceOpenBank"
          placeholder="请输入开户银行"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="银行账户" prop="invoiceBankAccount">
        <el-input
          v-model="queryParams.invoiceBankAccount"
          placeholder="请输入银行账户"
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
          v-hasPermi="['store:TStoreOrderAttr:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['store:TStoreOrderAttr:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['store:TStoreOrderAttr:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['store:TStoreOrderAttr:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="TStoreOrderAttrList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="订单id" align="center" prop="orderId" />
      <el-table-column label="发票类型  0 不需要发票 1普通发票 2增值税发票 默认0" align="center" prop="invoiceType" :formatter="invoiceTypeFormat" />
      <el-table-column label="发票抬头" align="center" prop="invoiceTitle" />
      <el-table-column label="发票内容 \n1:非图书商品\n2:明细\n3:耗材\n4:办公用品" align="center" prop="invoiceContent" />
      <el-table-column label="税号" align="center" prop="invoiceTaxid" />
      <el-table-column label="订单备注" align="center" prop="remark" />
      <el-table-column label="抬头类型  1 企业  2 个人" align="center" prop="invoiceTitleType" :formatter="invoiceTitleTypeFormat" />
      <el-table-column label="单位名称" align="center" prop="invoiceCompanyName" />
      <el-table-column label="注册地址" align="center" prop="invoiceRegisterAddress" />
      <el-table-column label="注册电话" align="center" prop="invoiceRegisterMobile" />
      <el-table-column label="开户银行" align="center" prop="invoiceOpenBank" />
      <el-table-column label="银行账户" align="center" prop="invoiceBankAccount" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['store:TStoreOrderAttr:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['store:TStoreOrderAttr:remove']"
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

    <!-- 添加或修改门店订单附属信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单id" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入订单id" />
        </el-form-item>
        <el-form-item label="发票类型  0 不需要发票 1普通发票 2增值税发票 默认0">
          <el-select v-model="form.invoiceType" placeholder="请选择发票类型  0 不需要发票 1普通发票 2增值税发票 默认0">
            <el-option
              v-for="dict in invoiceTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="发票抬头" prop="invoiceTitle">
          <el-input v-model="form.invoiceTitle" placeholder="请输入发票抬头" />
        </el-form-item>
        <el-form-item label="发票内容 \n1:非图书商品\n2:明细\n3:耗材\n4:办公用品" prop="invoiceContent">
          <el-input v-model="form.invoiceContent" placeholder="请输入发票内容 \n1:非图书商品\n2:明细\n3:耗材\n4:办公用品" />
        </el-form-item>
        <el-form-item label="税号" prop="invoiceTaxid">
          <el-input v-model="form.invoiceTaxid" placeholder="请输入税号" />
        </el-form-item>
        <el-form-item label="抬头类型  1 企业  2 个人">
          <el-select v-model="form.invoiceTitleType" placeholder="请选择抬头类型  1 企业  2 个人">
            <el-option
              v-for="dict in invoiceTitleTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="单位名称" prop="invoiceCompanyName">
          <el-input v-model="form.invoiceCompanyName" placeholder="请输入单位名称" />
        </el-form-item>
        <el-form-item label="注册地址" prop="invoiceRegisterAddress">
          <el-input v-model="form.invoiceRegisterAddress" placeholder="请输入注册地址" />
        </el-form-item>
        <el-form-item label="注册电话" prop="invoiceRegisterMobile">
          <el-input v-model="form.invoiceRegisterMobile" placeholder="请输入注册电话" />
        </el-form-item>
        <el-form-item label="开户银行" prop="invoiceOpenBank">
          <el-input v-model="form.invoiceOpenBank" placeholder="请输入开户银行" />
        </el-form-item>
        <el-form-item label="银行账户" prop="invoiceBankAccount">
          <el-input v-model="form.invoiceBankAccount" placeholder="请输入银行账户" />
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
import { listTStoreOrderAttr, getTStoreOrderAttr, delTStoreOrderAttr, addTStoreOrderAttr, updateTStoreOrderAttr, exportTStoreOrderAttr } from "@/api/store/TStoreOrderAttr";

export default {
  name: "TStoreOrderAttr",
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
      // 门店订单附属信息表格数据
      TStoreOrderAttrList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 发票类型  0 不需要发票 1普通发票 2增值税发票 默认0字典
      invoiceTypeOptions: [],
      // 抬头类型  1 企业  2 个人字典
      invoiceTitleTypeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
        orderId: undefined,
        invoiceType: undefined,
        invoiceTitle: undefined,
        invoiceContent: undefined,
        invoiceTaxid: undefined,
        invoiceTitleType: undefined,
        invoiceCompanyName: undefined,
        invoiceRegisterAddress: undefined,
        invoiceRegisterMobile: undefined,
        invoiceOpenBank: undefined,
        invoiceBankAccount: undefined
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
    this.getDicts("invoceType").then(response => {
      this.invoiceTypeOptions = response.data;
    });
    this.getDicts("invoice_head").then(response => {
      this.invoiceTitleTypeOptions = response.data;
    });
  },
  methods: {
    /** 查询门店订单附属信息列表 */
    getList() {
      this.loading = true;
      listTStoreOrderAttr(this.queryParams).then(response => {
        this.TStoreOrderAttrList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 发票类型  0 不需要发票 1普通发票 2增值税发票 默认0字典翻译
    invoiceTypeFormat(row, column) {
      return this.selectDictLabel(this.invoiceTypeOptions, row.invoiceType);
    },
    // 抬头类型  1 企业  2 个人字典翻译
    invoiceTitleTypeFormat(row, column) {
      return this.selectDictLabel(this.invoiceTitleTypeOptions, row.invoiceTitleType);
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
        invoiceType: undefined,
        invoiceTitle: undefined,
        invoiceContent: undefined,
        invoiceTaxid: undefined,
        remark: undefined,
        invoiceTitleType: undefined,
        invoiceCompanyName: undefined,
        invoiceRegisterAddress: undefined,
        invoiceRegisterMobile: undefined,
        invoiceOpenBank: undefined,
        invoiceBankAccount: undefined
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
      this.title = "添加门店订单附属信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTStoreOrderAttr(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改门店订单附属信息";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateTStoreOrderAttr(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addTStoreOrderAttr(this.form).then(response => {
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
      this.$confirm('是否确认删除门店订单附属信息编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delTStoreOrderAttr(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有门店订单附属信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportTStoreOrderAttr(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
