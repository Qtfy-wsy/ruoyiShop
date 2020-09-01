<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="12">
        <el-button class="filter-item" style="width: 100%" type="primary" icon="el-icon-plus"
                   @click="preparereateFristColumn">添加一级栏目
        </el-button>
        <div style="border: 1px solid #ebebeb; margin-top: 20px; border-radius: 4px">
          <el-table
            :data="firstColumn"
            ref="firstColumn"
            v-loading="listLoading"
            :show-header="false"
            height="515"
            style="border-radius: 4px"
            fit
            highlight-current-row
            @cell-click="firstClick"
          >
            <el-table-column label="栏目名称">
              <template slot-scope="scope">{{ scope.row.name }}</template>
            </el-table-column>
            <el-table-column label="排序">
              <template slot-scope="scope">{{ scope.row.sort }}</template>
            </el-table-column>
            <el-table-column label="显示">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.status == '0'" type="success">是</el-tag>
                <el-tag v-if="scope.row.status == '1'" type="info">否</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="100" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button type="primary" icon="el-icon-edit" size="mini" circle
                           @click="openUpdateFristWind(scope.row)"></el-button>
                <el-button type="danger" icon="el-icon-delete" size="mini" circle
                           @click="toDeleteColumn(scope.row.id)"></el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-col>
      <el-col :span="12">
        <el-button class="filter-item" style="width: 100%" type="primary" icon="el-icon-plus"
                   @click="preparereateSecondColumn">添加二级栏目
        </el-button>
        <div style="border: 1px solid #ebebeb; margin-top: 20px; border-radius: 4px">
          <el-table
            :data="secondColumn"
            ref="secondColumn"
            v-loading="listLoading"
            :show-header="false"
            height="515"
            style="border-radius: 4px"
            fit
            highlight-current-row
          >
            <el-table-column label="栏目名称">
              <template slot-scope="scope">{{ scope.row.name }}</template>
            </el-table-column>
            <el-table-column label="排序">
              <template slot-scope="scope">{{ scope.row.sort }}</template>
            </el-table-column>
            <el-table-column label="显示">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.status == '0'" type="success">是</el-tag>
                <el-tag v-if="scope.row.status == '1'" type="info">否</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="100" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button type="primary" icon="el-icon-edit" size="mini" circle
                           @click="openUpdateSecondWind(scope.row)"></el-button>
                <el-button type="danger" icon="el-icon-delete" size="mini" circle
                           @click="toDeleteColumn(scope.row.id)"></el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-col>
    </el-row>

    <el-dialog :title="editType == 1 ? '新增一级栏目' : '编辑一级栏目'" :visible.sync="addFirstColumnVisible">
      <el-form label-position="right" :model="firstColumnForm" :rules="firstColumnRules" ref="firstColumnForm"
               label-width="100px">
        <el-form-item prop="name">
          <span slot="label">栏目名称</span>
          <el-input v-model="firstColumnForm.name"/>
        </el-form-item>
        <el-form-item prop="sort">
          <span slot="label">排序</span>
          <template>
            <el-input-number size="small" controls-position="right" :min="1" :max="99"
                             :step-strictly="true" v-model="firstColumnForm.sort"></el-input-number>
          </template>
        </el-form-item>
        <el-form-item label="显示">
          <el-switch
            style="vertical-align: sub"
            v-model="firstColumnForm.isShow"
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addFirstColumnVisible = false">取消</el-button>
        <el-button type="primary" @click="editType == 1 ? addFirstColumn() : updateFirstColumn()">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="editType == 1 ? '新增二级分类' : '编辑二级分类'" :visible.sync="addSecondColumnVisible">
      <el-form label-position="right" :model="secondColumnForm" :rules="secondColumnRules" ref="secondColumnForm"
               label-width="100px">
        <el-form-item prop="name">
          <span slot="label">栏目名称</span>
          <el-input v-model="secondColumnForm.name"/>
        </el-form-item>
        <el-form-item prop="parentId">
          <span slot="label">上级栏目</span>
          <el-select v-model="secondColumnForm.parentId" style="width: 50%" filterable placeholder="请选择栏目">
            <el-option
              v-for="item in firstColumn"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="sort">
          <span slot="label">排序</span>
          <template>
            <el-input-number size="small" controls-position="right" :min="1" :max="99"
                             :step-strictly="true" v-model="secondColumnForm.sort"></el-input-number>
          </template>
        </el-form-item>
        <el-form-item label="显示">
          <el-switch
            style="vertical-align: sub"
            v-model="secondColumnForm.isShow"
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addSecondColumnVisible = false">取消</el-button>
        <el-button type="primary" @click="editType == 1 ? addSecondColumn() : updateSecondColumn()">确定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

  import {
    queryColumnList,  //查询所有栏目列表
    addColumn, //添加栏目
    editColumn, //编辑栏目
    deleteColumn, //删除栏目
  } from '@/api/cms/columnlist';


  import {
    isEmpty, //非空
    digitsValidator //非负整数
  } from '@/utils/validate'

  export default {
    data() {
      return {
        isProcess: false,//接口处理中
        editType: 1,//操作类型  1 添加 2 修改
        choosedFid: '', // 当前选择的一级分类
        addFirstColumnVisible: false, //一级分类编辑窗口显示标记
        addSecondColumnVisible: false, //二级分类编辑窗口显示标记
        listLoading: true, //数据加载标记
        firstColumn: [],//一级栏目数组
        secondColumn: [],//二级栏目数组
        allColumn: [],//所有栏目数组
        columnValue: [],//添加二级分类时选择的上级分类
        firstColumnForm: {
          name: '',
          sort: '',
          isShow: true,
        },//一级栏目表单
        secondColumnForm: {
          name: '',
          sort: '',
          parentId: '',
          isShow: true,
        },//二级栏目表单
        //一级栏目校验
        firstColumnRules: { //添加一级分类校验规则
          name: [
            {
              required: true,
              trigger: 'blur',
              validator: isEmpty,
            }
          ],
          sort: [
            {
              required: true,
              trigger: 'blur',
              validator: digitsValidator,
            }
          ]
        },
        //二级栏目校验
        secondColumnRules: {
          name: [
            {
              required: true,
              trigger: 'blur',
              validator: isEmpty,
            }
          ],
          sort: [
            {
              required: true,
              trigger: 'blur',
              validator: digitsValidator,
            }
          ],
          parentId: [
            {
              required: true,
              message: '请选择上级分类',
              trigger: 'change'
            }
          ],
        }
      }
    },
    created() {
      //初始化数据
      this.getList()
    },
    methods: {
      //加载一级栏目
      getList() {
        this.listLoading = true;
        queryColumnList().then((response) => {
          this.listLoading = false
          this.allColumn = response;
          for (let i = 0; i < response.length; i++) {
            if (response[i].parentId == 0) {
              let temp = {
                id: response[i].id,
                name: response[i].name,
                status: response[i].isShow,
                sort: response[i].sort,
              };
              this.firstColumn.push(temp);
            }
          }
          if (this.firstColumn.length > 0) {
            this.$refs.firstColumn.setCurrentRow(this.firstColumn[0]);
            this.choosedFid = this.firstColumn[0].id;
            this.loadSecondColumn(this.firstColumn[0].id);
          }
        })
      },
      //准备添加一级栏目
      preparereateFristColumn() {
        this.editType = 1;
        this.firstColumnForm = {
          name: '',
          sort: '',
          isShow: true,
        };
        if (this.$refs["firstColumnForm"]) {
          this.$refs["firstColumnForm"].resetFields();
        }
        this.addFirstColumnVisible = true;
      },
      //准备编辑一级分类
      openUpdateFristWind(row) {
        this.editType = 2;
        this.firstColumnForm = {
          id: row.id,
          name: row.name,
          sort: row.sort,
          isShow: row.status == '0' ? true : false
        };
        if (this.$refs["firstColumnForm"]) {
          this.$refs["firstColumnForm"].resetFields();
        }
        this.addFirstColumnVisible = true;
      },
      //添加一级栏目
      addFirstColumn() {
        this.$refs['firstColumnForm'].validate(valid => {
          if (valid) {
            let column = {
              name: this.firstColumnForm.name,
              sort: this.firstColumnForm.sort,
              isShow: this.firstColumnForm.isShow ? '0' : '1',
            };
            this.publicAddColumn(column);
          }
        })
      },
      //编辑一级栏目
      updateFirstColumn() {
        console.log(this.firstColumnForm);
        this.$refs['firstColumnForm'].validate(valid => {
          if (valid) {
            let column = {
              id: this.firstColumnForm.id,
              name: this.firstColumnForm.name,
              sort: this.firstColumnForm.sort,
              isShow: this.firstColumnForm.isShow ? '0' : '1',
            };
            this.publicUpdateColumn(column);
          }
        })
      },
      //准备添加二级栏目
      preparereateSecondColumn() {
        this.editType = 1;
        this.secondColumnForm = {
          parentId: this.choosedFid,
          name: '',
          sort: '',
          isShow: true,
        };
        if (this.$refs["secondColumnForm"]) {
          this.$refs["secondColumnForm"].resetFields();
        }
        this.addSecondColumnVisible = true;
      },
      //准备编辑二级栏目
      openUpdateSecondWind(row) {
        this.editType = 2;
        this.secondColumnForm = {
          id: row.id,
          name: row.name,
          sort: row.sort,
          parentId: row.parentId,
          isShow: row.status == '0' ? true : false
        };
        if (this.$refs["secondColumnForm"]) {
          this.$refs["secondColumnForm"].resetFields();
        }
        this.addSecondColumnVisible = true;
      },
      //编辑二级栏目
      updateSecondColumn() {
        this.$refs['secondColumnForm'].validate(valid => {
          if (valid) {
            let column = {
              id: this.secondColumnForm.id,
              parentId: this.secondColumnForm.parentId,
              name: this.secondColumnForm.name,
              sort: this.secondColumnForm.sort,
              isShow: this.secondColumnForm.isShow ? '0' : '1',
            };
            this.publicUpdateColumn(column);
          }
        })
      },
      //添加二级栏目
      addSecondColumn() {
        this.$refs['secondColumnForm'].validate(valid => {
          if (valid) {
            let column = {
              parentId: this.secondColumnForm.parentId,
              name: this.secondColumnForm.name,
              sort: this.secondColumnForm.sort,
              isShow: this.secondColumnForm.isShow ? '0' : '1',
            };
            this.publicAddColumn(column);
          }
        })
      },
      //添加栏目
      publicAddColumn(column) {
        if (this.isProcess) {
          return;
        }
        this.isProcess = true;
        addColumn(column).then((response) => {
          this.isProcess = false;
          this.addFirstColumnVisible = false;
          this.addSecondColumnVisible = false;
          if (response > 0) {
            this.firstColumn = [];
            this.getList();
            this.$message({
              type: 'success',
              message: '添加栏目成功!'
            });
          }
        })
      },
      //编辑栏目
      publicUpdateColumn(column) {
        if (this.isProcess) {
          return;
        }
        this.isProcess = true;
        editColumn(column).then((response) => {
          this.isProcess = false;
          this.addFirstColumnVisible = false;
          this.addSecondColumnVisible = false;
          if (response > 0) {
            this.firstColumn = [];
            this.getList();
            this.$message({
              type: 'success',
              message: '编辑栏目成功!'
            });
          }
        })
      },
      //加载二级栏目
      loadSecondColumn(parentId) {
        this.secondColumn = this.findColumnFromAllCate(parentId);
        if (this.secondColumn.length > 0) {
          this.$refs.secondColumn.setCurrentRow(this.secondColumn[0]);
        }
      },
      //根据父类 ID 查询子栏目
      findColumnFromAllCate(parentId) {
        let tempArray = new Array();
        for (let i = 0; i < this.allColumn.length; i++) {
          if (this.allColumn[i].parentId == parentId) {
            let temp = {
              name: this.allColumn[i].name,
              id: this.allColumn[i].id,
              status: this.allColumn[i].isShow,
              sort: this.allColumn[i].sort,
              parentId: this.allColumn[i].parentId
            }
            tempArray.push(temp);
          }
        }
        return tempArray;
      },
      //一级分类点击事件
      firstClick(row, column, cell) {
        this.choosedFid = row.id;
        this.loadSecondColumn(row.id);
      },
      //删除栏目
      toDeleteColumn(id) {
        this.$confirm('确定要删除此栏目吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteColumn({id: id}).then((response) => {
            if (response > 0) {
              this.firstColumn = [];
              this.getList();
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
            }
          });
        })
      },
    }
  }
</script>
