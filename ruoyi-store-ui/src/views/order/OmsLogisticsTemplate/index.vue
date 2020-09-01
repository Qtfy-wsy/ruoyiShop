<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="98px">
      <el-form-item label="物流模版名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入物流模版名称"
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
         <router-link to="/marketingManager/marketingGoods/Addlogisticstemplate">
                <el-button class="filter-item" size="medium" type="primary" icon="el-icon-plus">添加物流模板</el-button>
          </router-link>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['order:OmsLogisticsTemplate:remove']"
        >删除</el-button>
      </el-col>

    </el-row>

    <el-table v-loading="loading" :data="OmsLogisticsTemplateList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="物流模版名称" align="center" prop="name" />
      <el-table-column label="是否默认模版" align="center" prop="isDefault" />
      <el-table-column label="谁承担运费" align="center" prop="freightBear" :formatter="freightBearFormat" />
      <el-table-column label="计价方式" align="center" prop="pricintMethod" :formatter="pricintMethodFormat" />


      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">

                    <router-link :to="{path:'/marketingManager/marketingGoods/updlogisticstemplate',query:{id:scope.row.id}}">
                      <el-button type="primary" style="margin: 0 5px 0 0" size="mini">修改</el-button>
                    </router-link>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['order:OmsLogisticsTemplate:remove']"
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


  </div>
</template>

<script>
import { listOmsLogisticsTemplate, getOmsLogisticsTemplate, delOmsLogisticsTemplate, addOmsLogisticsTemplate, updateOmsLogisticsTemplate, exportOmsLogisticsTemplate } from "@/api/order/OmsLogisticsTemplate";

export default {
  name: "OmsLogisticsTemplate",
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
      // 物流模版表格数据
      OmsLogisticsTemplateList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 谁承担运费 0 买家 1 商家 默认 0 买家字典
      freightBearOptions: [],
      // 计价方式 0 按件  1 按重量  默认0 字典
      pricintMethodOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
        name: undefined,
        freightBear: undefined,
        pricintMethod: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "物流模版名称不能为空", trigger: "blur" }
        ],
        isDefault: [
          { required: true, message: "是否默认模版 0 是 1 否 默认1 不能为空", trigger: "blur" }
        ],
        freightBear: [
          { required: true, message: "谁承担运费 0 买家 1 商家 默认 0 买家不能为空", trigger: "blur" }
        ],
        pricintMethod: [
          { required: true, message: "计价方式 0 按件  1 按重量  默认0 不能为空", trigger: "blur" }
        ],
        storeId: [
          { required: true, message: "店铺id  平台的为 0 不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("fright_price").then(response => {
      this.freightBearOptions = response.data;
    });
    this.getDicts("fright_ways").then(response => {
      this.pricintMethodOptions = response.data;
    });
  },
  methods: {
    /** 查询物流模版列表 */
    getList() {
      this.loading = true;
      listOmsLogisticsTemplate(this.queryParams).then(response => {
        this.OmsLogisticsTemplateList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 谁承担运费 0 买家 1 商家 默认 0 买家字典翻译
    freightBearFormat(row, column) {
      return this.selectDictLabel(this.freightBearOptions, row.freightBear);
    },
    // 计价方式 0 按件  1 按重量  默认0 字典翻译
    pricintMethodFormat(row, column) {
      return this.selectDictLabel(this.pricintMethodOptions, row.pricintMethod);
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
        isDefault: undefined,
        freightBear: undefined,
        pricintMethod: undefined,
        storeId: undefined,
        delFlag: undefined,
        createTime: undefined,
        modifyTime: undefined,
        delTime: undefined
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
      this.title = "添加物流模版";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOmsLogisticsTemplate(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改物流模版";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateOmsLogisticsTemplate(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addOmsLogisticsTemplate(this.form).then(response => {
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
      this.$confirm('是否确认删除物流模版编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delOmsLogisticsTemplate(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有物流模版数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportOmsLogisticsTemplate(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
