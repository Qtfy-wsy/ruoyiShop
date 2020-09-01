<template>
  <div class="app-container">
    <el-tabs v-model="activeName" type="card">
      <el-tab-pane label="签约信息" name="0">
        <div class="filter-container">
          <el-button class="filter-item" size="medium" style="margin-left: 5px;" type="primary" icon="el-icon-plus"
                     @click="dialogFormVisible = true">添加签约分类
          </el-button>
        </div>
        <el-table
          :data="list"
          v-loading="listLoading"
          border
          fit
          highlight-current-row
        >
          <el-table-column label="签约分类">
            <template slot-scope="scope">{{ scope.row.name }}</template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button size="mini" style="margin: 0" type="danger" @click="deleteDialog(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>


      </el-tab-pane>
      <el-tab-pane label="期限/关店" name="1">
        <el-form label-position="right" label-width="160px" style="margin-top: 20px">
          <el-form-item>
            <span slot="label"><span class="labelImportant">*</span>店铺期限</span>
            <el-date-picker
              v-model="timeValue"
              type="datetime"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="店铺状态">
            <el-switch
              v-model="statusValue"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="开店"
              inactive-text="关店">
            </el-switch>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updateData">保存设置</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>

    <el-dialog title="添加规格" :visible.sync="dialogFormVisible">
      <el-form label-position="right" label-width="100px">
        <el-form-item>
          <span slot="label"><span class="labelImportant">*</span>属性名称</span>
          <el-cascader style="width: 100%" v-model="categoryIds" :props="props" clearable></el-cascader>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="createData">确定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

  import {
    storeDetailInfoByStoreId,  //根据店铺id查询店铺详情信息
    queryCategoryByParentId,  //根据父级id查询所有子分类
    addStoreSignedCategory, //添加签约分类
    deleteStoreSingedCategoryById, //根据店铺id和分类id删除签约分类
    editStoreTimeAndIsClose, //编辑店铺有效期,结算周期,是否关店
  } from '@/api/store/storeinfoset';


  import {
    parseTime,  //根据店铺id查询店铺详情信息
  } from '@/utils/index';

  export default {
    data() {
      return {
        categoryIds: null,  //选中的分类 id
        activeName: '0',
        dialogFormVisible: false, //显示标记
        timeValue: '',  //店铺到期时间
        statusValue: true,  //店铺状态 2 开  4 关
        list: null, //签约分类数据
        isProcess: false, //接口是否正在处理中
        listLoading: true,  //页面加载中
        props: {
          multiple: true,
          lazy: true,
          lazyLoad(node, resolve) {
            const {level} = node;
            let parentId;
            let categoryList = [];
            if (node.value) {
              parentId = node.value.split('-')[0];
            } else {
              parentId = 0;
            }
            queryCategoryByParentId(parentId).then(response => {
              categoryList = categoryList.concat(response);
              const nodes = Array.from(categoryList)
                .map(item => ({
                  value: item.id + '-' + item.grade,
                  label: item.name,
                  leaf: level >= 2
                }));
              // 通过调用resolve将子节点数据返回，通知组件数据加载完成
              resolve(nodes);
            });
          }
        },  //分类
      }
    },
    created() {
      this.getList()
    },
    methods: {

      //获取页面数据
      getList() {
        this.listLoading = true;
        let param = {
          storeId: this.$route.query.storeId
        };
        storeDetailInfoByStoreId(param).then(response => {
          this.list = response.threeCategoryList;
          if (response.storeInfo.status == '2') {
            this.statusValue = true;
          } else {
            this.statusValue = false;
          }
          this.timeValue = response.storeInfo.effectiveTime;
          this.listLoading = false
        });
      },

      //删除签约分类
      deleteDialog(id) {
        this.$confirm('确定要删除此签约分类吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {

          if (this.isProcess) {
            return;
          }
          this.isProcess = true;
          let param = {
            storeId: this.$route.query.storeId,
            cateId: id
          };
          deleteStoreSingedCategoryById(param).then(response => {
            this.isProcess = false;
            if (response > 0) {
              this.$message({
                type: 'success',
                message: '删除签约分类成功!'
              })
            } else {
              this.$message({
                type: 'error',
                message: '删除签约分类失败!'
              })
            }
            this.getList();
          });
        })
      },

      //确定添加分类
      createData() {
        let tempArray = new Array();
        for (let k = 0; k < this.categoryIds.length; k++) {
          for (let i = 0; i < this.categoryIds[k].length; i++) {
            if (this.categoryIds[k][i].split('-')[1] == '3') {
              tempArray.push(this.categoryIds[k][i].split('-')[0]);
            }
          }
        }
        if (!tempArray.length > 0) {
          this.$message({
            message: '请至少选择一条分类！',
            type: 'warning'
          });
          return;
        }
        if (this.isProcess) {
          return;
        }
        this.isProcess = true;
        let addParame = {
          storeId: this.$route.query.storeId,
          categoryIds: tempArray.toString()
        };
        addStoreSignedCategory(addParame).then(response => {
          this.dialogFormVisible = false;
          this.isProcess = false;
          if (response > 0) {
            this.$message({
              type: 'success',
              message: '添加签约分类成功!'
            });
          } else if (response == -1) {
            this.$message({
              type: 'error',
              message: '添加签约分类失败!'
            });
          } else if (response == -2) {
            this.$message({
              type: 'error',
              message: '请不要添加重复分类!'
            });
          }
          this.getList();
        });


      },


      //更改到期时间
      updateData() {
        if (!this.timeValue) {
          this.$message({
            message: '请选择时间！',
            type: 'warning'
          });
          return;
        }
        if (this.isProcess) {
          return;
        }
        this.isProcess = true;
        let param = {
          effectiveTime: this.timeValue,
          id: this.$route.query.storeId,
          status: this.statusValue ? '2' : '4'
        }
        editStoreTimeAndIsClose(param).then(response => {
          this.isProcess = false;
          if (response > 0) {
            this.$message({
              type: 'success',
              message: '更新成功!'
            });
          } else {
            this.$message({
              type: 'error',
              message: '更新失败!'
            });
          }
        });
      }
    }
  }
</script>
