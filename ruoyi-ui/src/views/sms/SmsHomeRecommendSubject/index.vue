<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">

                                      <el-form-item label="名称" prop="subjectName">
        <el-input
          v-model="queryParams.subjectName"
          placeholder="请输入名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
                                      <el-form-item label="状态" prop="recommendStatus">
        <el-select v-model="queryParams.recommendStatus" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in recommendStatusOptions"
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
          v-hasPermi="['sms:SmsHomeRecommendSubject:add']"
        >新增</el-button>
      </el-col>

    </el-row>

    <el-table v-loading="loading" :data="SmsHomeRecommendSubjectList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
                  <el-table-column label="编号" align="center" prop="id" />

                  <el-table-column label="名称" align="center" prop="subjectName" />
                  <el-table-column label="商品图片" width="80">
                                          <template slot-scope="scope">
                                          <img :src="scope.row.pic" width="50"  height="50">
                                          </template>
                                    </el-table-column>
                  <el-table-column label="状态" align="center" prop="recommendStatus" :formatter="recommendStatusFormat" />
                  <el-table-column label="排序" align="center" prop="sort" />
                  <el-table-column label="所属店铺" align="center" prop="storeId" />

                  <el-table-column label="阅读量" align="center" prop="readCount" />
                  <el-table-column label="描述" align="center" prop="description" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['sms:SmsHomeRecommendSubject:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['sms:SmsHomeRecommendSubject:remove']"
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

    <!-- 添加或修改首页推荐专题对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                                                            <el-form-item label="文章" prop="subjectId">
          <el-input v-model="form.subjectId" placeholder="请输入文章" />
        </el-form-item>
                                                                    <el-form-item label="名称" prop="subjectName">
          <el-input v-model="form.subjectName" placeholder="请输入名称" />
        </el-form-item>
                                                                    <el-form-item label="状态">
          <el-select v-model="form.recommendStatus" placeholder="请选择状态">
            <el-option
              v-for="dict in recommendStatusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
                                                                    <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入排序" />
        </el-form-item>
                                                                    <el-form-item label="所属店铺" prop="storeId">
          <el-input v-model="form.storeId" placeholder="请输入所属店铺" />
        </el-form-item>
                                                                    <el-form-item label="图片" prop="pic">
          <el-input v-model="form.pic" placeholder="请输入图片" />
        </el-form-item>
                                                                    <el-form-item label="阅读量" prop="readCount">
          <el-input v-model="form.readCount" placeholder="请输入阅读量" />
        </el-form-item>
                                                                    <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" placeholder="请输入描述" />
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
import { listSmsHomeRecommendSubject, getSmsHomeRecommendSubject, delSmsHomeRecommendSubject, addSmsHomeRecommendSubject, updateSmsHomeRecommendSubject, exportSmsHomeRecommendSubject } from "@/api/sms/SmsHomeRecommendSubject";

export default {
  name: "SmsHomeRecommendSubject",
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
      // 首页推荐专题表格数据
      SmsHomeRecommendSubjectList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
                                                      // 状态字典
        recommendStatusOptions: [],
                                                                  // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
                subjectId: undefined,
                subjectName: undefined,
                recommendStatus: undefined,
                    storeId: undefined,
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
                    this.getDicts("sys_show_hide").then(response => {
      this.recommendStatusOptions = response.data;
    });
                      },
  methods: {
    /** 查询首页推荐专题列表 */
    getList() {
      this.loading = true;
      listSmsHomeRecommendSubject(this.queryParams).then(response => {
        this.SmsHomeRecommendSubjectList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
                                    // 状态字典翻译
        recommendStatusFormat(row, column) {
      return this.selectDictLabel(this.recommendStatusOptions, row.recommendStatus);
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
                subjectId: undefined,
                subjectName: undefined,
                recommendStatus: undefined,
                sort: undefined,
                storeId: undefined,
                pic: undefined,
                readCount: undefined,
                description: undefined
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
      this.title = "添加首页推荐专题";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSmsHomeRecommendSubject(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改首页推荐专题";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateSmsHomeRecommendSubject(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addSmsHomeRecommendSubject(this.form).then(response => {
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
      this.$confirm('是否确认删除首页推荐专题编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delSmsHomeRecommendSubject(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有首页推荐专题数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportSmsHomeRecommendSubject(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
