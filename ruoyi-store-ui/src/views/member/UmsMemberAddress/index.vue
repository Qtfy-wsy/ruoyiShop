<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="用户id  对应ums_member表中的id" prop="customerId">
        <el-input
          v-model="queryParams.customerId"
          placeholder="请输入用户id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="收货人姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入收货人姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号码" prop="mobile">
        <el-input
          v-model="queryParams.mobile"
          placeholder="请输入手机号码"
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
          v-hasPermi="['member:UmsMemberAddress:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['member:UmsMemberAddress:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['member:UmsMemberAddress:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['member:UmsMemberAddress:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="UmsMemberAddressList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="用户id" align="center" prop="customerId" />
      <el-table-column label="收货人姓名" align="center" prop="name" />
      <el-table-column label="手机号码" align="center" prop="mobile" />
      <el-table-column label="收货人固定电话" align="center" prop="phone" />
      <el-table-column label="邮编" align="center" prop="zipCode" />
      <el-table-column label="收货人地址" align="center" prop="address" />
      <el-table-column label="收货人详细地址" align="center" prop="detailAddress" />
      <el-table-column label="省id" align="center" prop="provinceId" />
      <el-table-column label="市id" align="center" prop="cityId" />
      <el-table-column label="区id" align="center" prop="countryId" />
      <el-table-column label="是否默认收货地址 " align="center" prop="isDefault" />

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">

          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['member:UmsMemberAddress:remove']"
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

    <!-- 添加或修改用户收货地址对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户id  对应ums_member表中的id" prop="customerId">
          <el-input v-model="form.customerId" placeholder="请输入用户id  对应ums_member表中的id" />
        </el-form-item>
        <el-form-item label="收货人姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入收货人姓名" />
        </el-form-item>
        <el-form-item label="手机号码" prop="mobile">
          <el-input v-model="form.mobile" placeholder="请输入手机号码" />
        </el-form-item>
        <el-form-item label="收货人固定电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入收货人固定电话" />
        </el-form-item>
        <el-form-item label="邮编" prop="zipCode">
          <el-input v-model="form.zipCode" placeholder="请输入邮编" />
        </el-form-item>
        <el-form-item label="收货人地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入收货人地址" />
        </el-form-item>
        <el-form-item label="收货人详细地址" prop="detailAddress">
          <el-input v-model="form.detailAddress" placeholder="请输入收货人详细地址" />
        </el-form-item>
        <el-form-item label="省id" prop="provinceId">
          <el-input v-model="form.provinceId" placeholder="请输入省id" />
        </el-form-item>
        <el-form-item label="市id" prop="cityId">
          <el-input v-model="form.cityId" placeholder="请输入市id" />
        </el-form-item>
        <el-form-item label="区id" prop="countryId">
          <el-input v-model="form.countryId" placeholder="请输入区id" />
        </el-form-item>
        <el-form-item label="是否默认收货地址  0 否 1 是  默认0 " prop="isDefault">
          <el-input v-model="form.isDefault" placeholder="请输入是否默认收货地址  0 否 1 是  默认0 " />
        </el-form-item>
        <el-form-item label="删除标记  0 未删除 1 删除" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标记  0 未删除 1 删除" />
        </el-form-item>
        <el-form-item label="删除时间" prop="delTime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.delTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择删除时间">
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
import { listUmsMemberAddress, getUmsMemberAddress, delUmsMemberAddress, addUmsMemberAddress, updateUmsMemberAddress, exportUmsMemberAddress } from "@/api/member/UmsMemberAddress";

export default {
  name: "UmsMemberAddress",
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
      // 用户收货地址表格数据
      UmsMemberAddressList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
        customerId: undefined,
        name: undefined,
        mobile: undefined,
        isDefault: undefined,
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
    /** 查询用户收货地址列表 */
    getList() {
      this.loading = true;
      listUmsMemberAddress(this.queryParams).then(response => {
        this.UmsMemberAddressList = response.rows;
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
        customerId: undefined,
        name: undefined,
        mobile: undefined,
        phone: undefined,
        zipCode: undefined,
        address: undefined,
        detailAddress: undefined,
        provinceId: undefined,
        cityId: undefined,
        countryId: undefined,
        isDefault: undefined,
        delFlag: undefined,
        createTime: undefined,
        delTime: undefined,
        updateTime: undefined
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
      this.title = "添加用户收货地址";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getUmsMemberAddress(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改用户收货地址";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateUmsMemberAddress(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addUmsMemberAddress(this.form).then(response => {
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
      this.$confirm('是否确认删除用户收货地址编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delUmsMemberAddress(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有用户收货地址数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportUmsMemberAddress(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
