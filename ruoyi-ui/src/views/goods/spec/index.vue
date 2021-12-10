<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="规格名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入规格名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="规格别名" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入规格别名"
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
          v-hasPermi="['goods:spec:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['goods:spec:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['goods:spec:remove']"
        >删除
        </el-button>
      </el-col>

    </el-row>

    <el-table v-loading="loading" :data="specList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="主键id" align="center" prop="id"/>
      <el-table-column label="规格名称" align="center" prop="name"/>
      <el-table-column label="规格别名" align="center" prop="nickName"/>
      <el-table-column label="创建者名称" align="center" prop="createName"/>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['goods:spec:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['goods:spec:remove']"
          >删除
          </el-button>
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

    <!-- 添加或修改规格对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="规格名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入规格名称"/>
        </el-form-item>
        <el-form-item label="规格别名" prop="nickName">
          <el-input v-model="form.nickName" placeholder="请输入规格别名"/>
        </el-form-item>

        <el-form-item>
          <span slot="label"><span class="labelImportant">*</span>规格值</span>
          <el-button class="filter-item" size="medium" type="primary" icon="el-icon-plus"
                     @click="addSpecValue(form.id)">
            添加规格值
          </el-button>
        </el-form-item>
        <el-form-item>
          <el-table
            :data="form.specValues"
            border
            fit
            highlight-current-row
            style="line-height: 20px"
          >
            <el-table-column label="规格值名称">
              <template slot-scope="scope">
                <el-input v-model="scope.row.name" size="medium" class="filter-item"/>
              </template>
            </el-table-column>
            <el-table-column label="排序">
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.sort" size="small" controls-position="right" :min="1"
                                 :max="99"></el-input-number>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button type="text" icon="el-icon-delete" size="mini"
                           @click.native.prevent="deleteSpecValue(scope.$index,scope.row.id)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
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
  import {listSpec, getSpec, delSpec, addSpec, updateSpec, exportSpec} from "@/api/goods/spec";

  export default {
    name: "Spec",
    data() {
      return {
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        specList: null, // 列表数据集合
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 总条数
        total: 0,
        // 规格表格数据
        specList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        form: { // 提交form表单
          id: '', // 规格id
          name: '', // 规格名称
          nickName: '', // 规格别名
          specValues: [{name: '', sort: ''}] // 规格值数组
        },
        // 查询参数
        queryParams: {
          pageNum: 0,
          pageSize: 10,
          name: undefined,
          nickName: undefined,

        },

        // 表单校验
        rules: {
          name: [
            {required: true, message: "规格名称不能为空", trigger: "blur"}
          ],

        }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询规格列表 */
      getList() {
        this.loading = true;
        listSpec(this.queryParams).then(response => {
          this.specList = response.rows;
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
          nickName: undefined,


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
        this.single = selection.length != 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.form.specValues = [{name: '', sort: ''}];
        // this.reset();
        this.open = true;
        this.title = "添加规格";
        console.log(this.form.specValues)
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getSpec(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改规格";
        });
      },
      // 删除规格值
      deleteSpecValue(index, specValueId) {
        // 修改规格时并且specValueId不为空时（意思不是修改时新添加的规格值）才需要判断是否被商品使用
        if (this.DiaSpec != 1 && specValueId) {
          // 修改规格时删除规格值需要判断该规格值是否被商品使用
          isSpecValueCanDelete(specValueId).then(res => {
            if (res) {
              this.form.specValues.splice(index, 1)
            } else {
              this.$message({
                message: '商品已经使用规格值不能删除!',
                type: 'error'
              })
            }
          })
        } else {
          this.form.specValues.splice(index, 1)
        }
      },
      // 新增规格值
      addSpecValue(specId) {
        // 修改规格时并且specId不为空时，给规格值赋值specId
        if (this.title == "修改规格" && specId) {
          this.form.specValues.push({name: '', sort: '', specId: specId})
        } else {
          this.form.specValues.push({name: '', sort: ''})
        }
      },
      // 校验商品规格值
      validateSpecValue() {
        let validateResult = true;
        let errorMsg = '';
        if (this.form.specValues.length == 0) {
          validateResult = false;
          errorMsg = '至少添加一个规格值';
        } else {
          for (var i = 0; i < this.form.specValues.length; i++) {

            // 没有规格值名称
            if (!this.form.specValues[i].name) {
              validateResult = false;
              errorMsg = '请填写规格值名称';
              break;
            }

            //  校验规格值排序是否为数字
            if (!(/^\d+$/.test(this.form.specValues[i].sort))) {
              validateResult = false;
              errorMsg = '规格值排序请输入数字';
              break;
            }
          }
        }

        if (!validateResult) {
          this.$message({
            message: errorMsg,
            type: 'error'
          })
        }
        return validateResult;
      },
      /** 提交按钮 */
      submitForm: function () {
        this.$refs["form"].validate(valid => {
          if (valid && this.validateSpecValue()) {
            if (this.title == "修改规格") {
              updateSpec(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addSpec(this.form).then(response => {
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
        this.$confirm('是否确认删除规格编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delSpec(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function () {
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有规格数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportSpec(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function () {
        });
      }
    }
  };
</script>
