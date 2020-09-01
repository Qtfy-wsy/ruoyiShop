<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="会员等级名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入会员等级名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="消费小值" prop="minMoney">
        <el-input
          v-model="queryParams.minMoney"
          placeholder="请输入消费小值"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="消费金额最大值" prop="maxMoney">
        <el-input
          v-model="queryParams.maxMoney"
          placeholder="请输入消费金额最大值"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="会员折扣" prop="discount">
        <el-input
          v-model="queryParams.discount"
          placeholder="请输入会员折扣"
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
          v-hasPermi="['member:UmsMemberLevel:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['member:UmsMemberLevel:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['member:UmsMemberLevel:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['member:UmsMemberLevel:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="UmsMemberLevelList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="id" />
      <el-table-column label="会员等级名称" align="center" prop="name" />
      <el-table-column label="消费小值" align="center" prop="minMoney" />
      <el-table-column label="消费金额最大值" align="center" prop="maxMoney" />
      <el-table-column label="会员折扣" align="center" prop="discount" />
      <el-table-column label="修改时间" align="center" prop="modifyTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.modifyTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['member:UmsMemberLevel:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['member:UmsMemberLevel:remove']"
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

    <!-- 添加或修改会员等级对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="会员等级名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入会员等级名称" />
        </el-form-item>
         <el-form-item>
                  <span slot="label"><span class="labelImportant">*</span>需消费金额</span>
                  <el-tooltip class="item" effect="dark" content="会员消费金额达到此标准最低分值后会自动升级为当前等级" placement="right">
                    <div style="display: inline-block">
                      <el-col :span="11">
                        <el-form-item prop="minMoney">
                          <el-input type="number" v-model="form.minMoney" maxlength="11">
                            <template slot="append">元</template>
                          </el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :span="2" style="text-align: center">~</el-col>
                      <el-col :span="11">
                        <el-form-item prop="maxMoney">
                          <el-input type="number" v-model="form.maxMoney" maxlength="11">
                            <template slot="append">元</template>
                          </el-input>
                        </el-form-item>
                      </el-col>
                    </div>
                  </el-tooltip>
                </el-form-item>

        <el-form-item label="会员折扣" prop="discount">
          <el-input v-model="form.discount" placeholder="请输入会员折扣" />
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
import { listUmsMemberLevel, getUmsMemberLevel, delUmsMemberLevel, addUmsMemberLevel, updateUmsMemberLevel, exportUmsMemberLevel } from "@/api/member/UmsMemberLevel";
 import {specstrValidator, digitsValidator, oneToHundredDigitsValidator} from '@/utils/validate';
export default {
  name: "UmsMemberLevel",
  data() {
    // 校验最低金额
        const checkMinMoney = (rule, value, callback) => {
          if (value === 0) {
            callback();
          } else {
            if (!value) {
              return callback(new Error('请输入非负整数'));
            }
            if (!/^\d+$/.test(value)) {
              callback(new Error('请输入非负整数'));
            } else {
              callback();
            }
          }
        };
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
      // 会员等级表格数据
      UmsMemberLevelList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
        name: undefined,
        minMoney: undefined,
        maxMoney: undefined,
        discount: undefined,
        modifyTime: undefined,
        delTime: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: { // 校验规则
               name: [
                 {
                   required: true,
                   trigger: 'blur',
                   validator: specstrValidator,
                 }
               ],
               minMoney: [
                 {
                   required: true,
                   trigger: 'blur',
                   validator: checkMinMoney,
                 }
               ],
               maxMoney: [
                 {
                   required: true,
                   trigger: 'blur',
                   validator: digitsValidator,
                 }
               ],
               showDiscount: [
                 {
                   required: true,
                   trigger: 'blur',
                   validator: oneToHundredDigitsValidator,
                 }
               ]
             },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询会员等级列表 */
    getList() {
      this.loading = true;
      listUmsMemberLevel(this.queryParams).then(response => {
        this.UmsMemberLevelList = response.rows;
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
        minMoney: undefined,
        maxMoney: undefined,
        discount: undefined,
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
      this.title = "添加会员等级";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getUmsMemberLevel(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改会员等级";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateUmsMemberLevel(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addUmsMemberLevel(this.form).then(response => {
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
      this.$confirm('是否确认删除会员等级编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delUmsMemberLevel(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有会员等级数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportUmsMemberLevel(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
