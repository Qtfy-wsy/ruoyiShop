<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input placeholder="分类名称" v-model="listQuery.name" size="medium" style="width: 200px;" class="filter-item"/>
      <el-button class="filter-item" type="primary" size="medium" icon="el-icon-search" @click="handleQuery">搜索
      </el-button>
      <br/>
      <el-button class="filter-item" size="medium" style="margin-left: 5px;" type="primary" icon="el-icon-plus"
                 @click="toAddHelpCategory">添加分类
      </el-button>
      <el-button class="filter-item" size="medium" style="margin-left: 5px;" type="danger" icon="el-icon-delete"
                 @click="toBatchDeleteHelpCategory">批量删除
      </el-button>
    </div>

    <el-table
      :data="helpCateList"
      v-loading="listLoading"
      border
      fit
      highlight-current-row
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" align="center" width="80"/>
      <el-table-column label="分类名称">
        <template slot-scope="scope">{{ scope.row.name }}</template>
      </el-table-column>
      <el-table-column label="是否显示">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isShow == '0'" type="success">是</el-tag>
          <el-tag v-if="scope.row.isShow == '1'" type="info">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="排序">
        <template slot-scope="scope">{{ scope.row.sort }}</template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="toUpdateHelpCategory(scope.row.id)">编辑</el-button>
          <el-button size="mini" type="danger" @click="toDeleteHelpCategory(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="listQuery.pageNum+1" :page-sizes="[10,20,30,50]" :page-size="listQuery.pageSize"
                     layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <el-dialog :title="DiaHelpCate==1?'添加帮助分类':'修改帮助分类'" :visible.sync="showDia">
      <el-form label-position="right" :model="form" :rules="rules" ref="form" label-width="100px">
        <el-form-item prop="name">
          <span slot="label">帮助名称</span>
          <el-input v-model="form.name"/>
        </el-form-item>
        <el-form-item prop="sort">
          <span slot="label">排序</span>
          <el-input-number v-model="form.sort" controls-position="right" :min="1" :max="99"></el-input-number>
        </el-form-item>
        <el-form-item>
          <span slot="label"><span class="labelImportant">*</span>显示</span>
          <el-switch
            style="vertical-align: sub"
            v-model="form.isShow"
            active-color="#13ce66"
            inactive-color="#ff4949"
            :active-value="0"
            :inactive-value="1">
          </el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showDia = false">取消</el-button>
        <el-button type="primary" @click="DiaHelpCate==1?addHelpCategory():updateHelpCategory()">确定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import {
    queryHelpCateList,
    addHelpCategory,
    queryHelpCategoryById,
    updateHelpCategory,
    deleteHelpCategory,
  } from '@/api/cms/helpcate';

  export default {
    data() {
      return {
        isProcess: false, // 接口是否处理中
        showDia: false, // 是否显示新增修改弹窗标识
        DiaHelpCate: 1, // 判断新增还是修改标识，1新增，2修改
        delIds: [],// 批量删除选中id数组
        listLoading: true, // 列表加载标识
        helpCateList: null, // 列表数据集合
        total: null, // 总记录数
        listQuery: {
          pageNum: 0, // 当前页码
          pageSize: 10, // 每页显示记录数
          name: '' // 帮助分类名称
        },
        form: { // 提交form表单
          id: '', // 帮助分类id
          name: '', // 帮助分类名称
          sort: '', // 帮助分类排序
          isShow: '',// 是否显示 0 显示 1 不显示
        },
        rules: { // 校验规则
          name: [
            {
              required: true,
              message: '请输入帮助分类名称',
              trigger: 'blur'
            }
          ],
          sort: [
            {
              required: true,
              message: '请输入帮助分类排序',
              trigger: 'blur'
            }
          ]
        },
      }
    },
    created() {
      this.getList()
    },
    methods: {
      // 查询帮助分类列表
      getList() {
        this.listLoading = true;
        queryHelpCateList(this.listQuery).then((response) => {
          this.helpCateList = response.data;
          this.total = response.recordsTotal;
          this.listLoading = false
        })
      },
      // 过滤查询
      handleQuery() {
        this.listQuery.pageNum = 0;
        this.getList();
      },
      // 改变当前页码
      handleCurrentChange(val) {
        this.listQuery.pageNum = val - 1;
        this.getList();
      },
      // 改变每页显示记录数
      handleSizeChange(val) {
        this.listQuery.pageNum = 0;
        this.listQuery.pageSize = val;
        this.getList();
      },
      // 准备新增帮助分类
      toAddHelpCategory() {
        this.showDia = true;
        this.DiaHelpCate = 1;
        this.resetForm();
        if (this.$refs["form"]) {
          this.$refs["form"].resetFields();
        }
      },
      // 新增帮助分类
      addHelpCategory() {
        this.$refs['form'].validate(valid => {
          if (!this.isProcess && valid) {
            this.isProcess = true;
            addHelpCategory(this.form).then(res => {
              this.isProcess = false;
              if (res == 1) {
                this.showDia = false;
                this.getList();
                this.$message({
                  type: 'success',
                  message: '创建成功!'
                });
              } else {
                this.$message({
                  message: '创建失败!',
                  type: 'error'
                })
              }
            })
          }
        });
      },
      // 准备更新帮助分类
      toUpdateHelpCategory(id) {
        queryHelpCategoryById(id).then(res => {
          this.resetForm();
          if (this.$refs["form"]) {
            this.$refs["form"].resetFields();
          }
          this.form = res;
          this.DiaHelpCate = 2;
          this.showDia = true;
        })
      },
      // 更新帮助分类
      updateHelpCategory() {
        this.$refs['form'].validate(valid => {
          if (valid && !this.isProcess) {
            this.isProcess = true;
            updateHelpCategory(this.form).then(res => {
              this.isProcess = false;
              if (res == 1) {
                this.showDia = false;
                this.getList();
                this.$message({
                  type: 'success',
                  message: '更新成功!'
                });
              } else {
                this.$message({
                  message: '更新失败!',
                  type: 'error'
                })
              }
            })
          }
        });
      },
      // 删除帮助分类二次确认
      toDeleteHelpCategory(id) {
        this.$confirm('是否确认删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteHelpCategory(id);
        })
      },
      // 多选选中帮助分类
      handleSelectionChange(val) {
        if (val && val.length > 0) {
          this.delIds = val.map(row => row.id);
        } else {
          this.delIds = [];
        }
      },
      // 批量删除帮助分类二次确认
      toBatchDeleteHelpCategory() {
        if (this.delIds && this.delIds.length > 0) {
          this.$confirm('是否确认删除, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.deleteHelpCategory(this.delIds);
          })
        } else {
          this.$message({
            message: '请至少选择一个帮助分类',
            type: 'warning'
          })
        }
      },
      // 删除帮助分类
      deleteHelpCategory(ids) {
        if (!this.isProcess) {
          this.isProcess = true;
          deleteHelpCategory({"ids": ids.toString()}).then(res => {
            this.isProcess = false;
            if (res >= 1) {
              this.getList();
              this.delIds = [];
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
            } else {
              this.$message({
                message: '删除失败!',
                type: 'error'
              })
            }
          });
        }
      },
      // 重置form表单
      resetForm() {
        this.form = {id: '', name: '', sort: '', isShow: 0};
      },
    }
  }
</script>
